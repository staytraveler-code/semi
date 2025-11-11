package db.performance.management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.util.DBConn;
import db.util.DBUtil;

public class PerformanceManagementDAO {
	Connection conn = DBConn.getConnection();
		
	 // ✅ 기관별 + 과제별 성과 목록 조회
    public List<PerformanceManagementDTO> performanceList(String orgCode, String projectCode) {
        List<PerformanceManagementDTO> list = new ArrayList<>();

        try {
            String sql =
                "SELECT p_m.performance_code AS code, " +
                "       p_m.name AS name, " +
                "       p_m.category AS category, " +
                "       p_m.content AS content, " +
                "       p_m.p_date AS p_date, " +
                "       p_m.memo AS memo " +
                "FROM performance_management p_m " +
                "JOIN project p ON p_m.project_code = p.project_code " +
                "WHERE p.org_code = ? AND p.project_code = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, orgCode);       // 로그인한 기관 코드
            pstmt.setString(2, projectCode);   // 선택된 과제 코드

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                PerformanceManagementDTO dto = new PerformanceManagementDTO();
                dto.setPerfCode(rs.getString("code"));
                dto.setName(rs.getString("name"));
                dto.setCategory(rs.getString("category"));
                dto.setContent(rs.getString("content"));
                dto.setpDate(rs.getString("p_date"));
                dto.setMemo(rs.getString("memo"));
                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
            DBUtil.rollback(conn);
        }
        return list;
    }
	
	
	//성과 추가 -- 성과코드,프로젝트코드는 시퀀스로 변경예정(자동적용로직)
	public int insertPerformance(PerformanceManagementDTO dto) {
        int result = 0;
        try {
            String sql = "INSERT INTO performance_management (performance_code, project_code, "
            		+ "name, category, content, p_date, memo) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, dto.getPerfCode());
            pstmt.setString(2, dto.getpCode());
            pstmt.setString(3, dto.getName());
            pstmt.setString(4, dto.getCategory());
            pstmt.setString(5, dto.getContent());
            pstmt.setDate(6, java.sql.Date.valueOf(dto.getpDate()));
            pstmt.setString(7, dto.getMemo());
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            DBUtil.rollback(conn);
        }
        return result;
    }
	
	//성과 업데이트
	public int updatePerformance(PerformanceManagementDTO dto) {
	 int result = 0;
     try {
         String sql = "UPDATE performance_management SET name=?, category=?, content=?, "
         		+ "p_date=?, memo=? WHERE performance_code=? ";
         PreparedStatement pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, dto.getName());
         pstmt.setString(2, dto.getCategory());
         pstmt.setString(3, dto.getContent());
         pstmt.setDate(4, java.sql.Date.valueOf(dto.getpDate()));
         pstmt.setString(5, dto.getMemo());
         pstmt.setString(6, dto.getPerfCode());
         result = pstmt.executeUpdate();
         conn.commit();
     } catch (Exception e) {
         e.printStackTrace();
         DBUtil.rollback(conn);
     }
     return result;
	}
	
	//성과 삭제
	public int deletePerformance(String perfCode) {
	    int result = 0;
	    try {
	        String sql = "DELETE FROM performance_management WHERE performance_code = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, perfCode);
	        result = pstmt.executeUpdate();

	        if (result > 0) {
	            System.out.println("성과가 삭제되었습니다.");
	        } else {
	            System.out.println("삭제할 성과가 존재하지 않습니다.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        DBUtil.rollback(conn);
	    }
	    return result;
	}
	
	
	
	//코드 판별(존재여부)
	public boolean existsPerformanceCode(String perfCode) {
	    boolean exists = false;
	    try {
	        String sql = "SELECT COUNT(*) AS cnt FROM performance_management WHERE performance_code = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, perfCode);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next() && rs.getInt("cnt") > 0) {
	            exists = true;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return exists;
	}
	
}