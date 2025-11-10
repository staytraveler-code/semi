package db.milestone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.util.DBConn;

public class MilestoneDAO {
	List<MilestoneDTO> list = new ArrayList<>();
	Connection conn = DBConn.getConnection();
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	
	
	public List<MilestoneDTO> listMilestone() {
	    List<MilestoneDTO> list = new ArrayList<>();
	    try {
	        String sql = "SELECT milestone_code, project_code, name, description, p_end_date, a_end_date, status FROM Milestone";
	        pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            MilestoneDTO dto = new MilestoneDTO();
	            dto.setMileCode(rs.getString("milestone_code"));
	            dto.setpCode(rs.getString("project_code")); // ← 여기 추가
	            dto.setName(rs.getString("name"));
	            dto.setDesc(rs.getString("description"));
	            dto.setPeDate(rs.getString("p_end_date"));
	            dto.setAeDate(rs.getString("a_end_date"));
	            dto.setStatus(rs.getString("status"));
	            list.add(dto);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	
	// 추가
	public int insertMilestone(MilestoneDTO dto) {
	    int result = 0;
	    String sql = "INSERT INTO milestone "
	               + "(milestone_code, project_code, name, description, p_end_date, a_end_date, status) "
	               + "VALUES (?, ?, ?, ?, ?, ?, ?)";

	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, dto.getMileCode());
	        pstmt.setString(2, dto.getpCode());
	        pstmt.setString(3, dto.getName());
	        pstmt.setString(4, dto.getDesc());
	        pstmt.setDate(5, java.sql.Date.valueOf(dto.getPeDate())); // "YYYY-MM-DD" 형식 문자열
	        pstmt.setDate(6, java.sql.Date.valueOf(dto.getAeDate())); // 위와 동일
	        pstmt.setString(7, dto.getStatus());

	        result = pstmt.executeUpdate(); // 성공 시 1 반환

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return result;
	}
	
	
	//수정
	
	//삭제

}
