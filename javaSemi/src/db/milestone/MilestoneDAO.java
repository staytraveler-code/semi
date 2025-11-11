package db.milestone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import db.util.DBConn;
import db.util.DBUtil;

public class MilestoneDAO {
    private final Connection conn; // DAO 객체 내에서 한 번만 가져옴

    public MilestoneDAO() {
        this.conn = DBConn.getConnection(); // 연결 유지
    }

    // 전체 마일스톤 조회 (특정 프로젝트)
    public List<MilestoneDTO> listMilestoneByProject(String projectCode) {
        List<MilestoneDTO> list = new ArrayList<>();
        String sql = "SELECT milestone_code, project_code, name, description, p_end_date, a_end_date, status " +
                     "FROM Milestone WHERE project_code = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, projectCode);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    MilestoneDTO dto = new MilestoneDTO();
                    dto.setMileCode(rs.getString("milestone_code"));
                    dto.setpCode(rs.getString("project_code"));
                    dto.setName(rs.getString("name"));
                    dto.setDesc(rs.getString("description"));
                    dto.setPeDate(rs.getString("p_end_date"));
                    dto.setAeDate(rs.getString("a_end_date"));
                    dto.setStatus(rs.getString("status"));
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 마일스톤 추가
    public int insertMilestone(MilestoneDTO dto) {
        int result = 0;
        String sql = "INSERT INTO milestone (milestone_code, project_code, name, description, p_end_date, a_end_date, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false); // 수동 커밋

            pstmt.setString(1, dto.getMileCode());
            pstmt.setString(2, dto.getpCode());
            pstmt.setString(3, dto.getName());
            pstmt.setString(4, dto.getDesc());
            pstmt.setDate(5, parseDate(dto.getPeDate()));
            pstmt.setDate(6, parseDate(dto.getAeDate()));
            pstmt.setString(7, dto.getStatus());

            result = pstmt.executeUpdate();
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            DBUtil.rollback(conn); // 실패 시 롤백
        }
        return result;
    }

    // 마일스톤 수정
    public int updateMilestone(MilestoneDTO dto) {
        int result = 0;
        String sql = "UPDATE Milestone SET name=?, description=?, p_end_date=?, a_end_date=?, status=? " +
                     "WHERE milestone_code=? AND project_code=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            pstmt.setString(1, dto.getName());
            pstmt.setString(2, dto.getDesc());
            pstmt.setDate(3, parseDate(dto.getPeDate()));
            pstmt.setDate(4, parseDate(dto.getAeDate()));
            pstmt.setString(5, dto.getStatus());
            pstmt.setString(6, dto.getMileCode());
            pstmt.setString(7, dto.getpCode());

            result = pstmt.executeUpdate();
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            DBUtil.rollback(conn);
        }
        return result;
    }

    // 마일스톤 삭제
    public int deleteMilestone(String mileCode, String projectCode) {
        int result = 0;
        String sql = "DELETE FROM Milestone WHERE milestone_code=? AND project_code=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            pstmt.setString(1, mileCode);
            pstmt.setString(2, projectCode);

            result = pstmt.executeUpdate();
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            DBUtil.rollback(conn);
        }
        return result;
    }

    // 문자열 날짜 -> java.sql.Date
    private java.sql.Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) return null;
        try { return java.sql.Date.valueOf(dateStr); }
        catch (Exception e) { return null; }
    }
}
