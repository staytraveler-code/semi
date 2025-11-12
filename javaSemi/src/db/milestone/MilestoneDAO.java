package db.milestone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import db.util.DBConn;
import db.util.DBUtil;

public class MilestoneDAO {
    private final Connection conn;

    public MilestoneDAO() {
        this.conn = DBConn.getConnection();
    }

    // 특정 프로젝트 마일스톤 목록
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
                     "VALUES ('MS_' || LPAD(seq_milestone.NEXTVAL, 3, '0'), ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            pstmt.setString(1, dto.getpCode());
            pstmt.setString(2, dto.getName());
            pstmt.setString(3, dto.getDesc());
            pstmt.setDate(4, parseDate(dto.getPeDate(), null));
            pstmt.setDate(5, parseDate(dto.getAeDate(), null));
            pstmt.setString(6, dto.getStatus());

            result = pstmt.executeUpdate();
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            DBUtil.rollback(conn);
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
            pstmt.setDate(3, parseDate(dto.getPeDate(), null));
            pstmt.setDate(4, parseDate(dto.getAeDate(), null));
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

    // 마일스톤이 특정 기관의 과제에 속하는지 확인
    public boolean isMilestoneBelongsToOrg(String mileCode, String orgCode) {
        String sql = """
            SELECT 1
            FROM Milestone m
            JOIN Project p ON m.project_code = p.project_code
            WHERE m.milestone_code = ? AND p.org_code = ?
        """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mileCode);
            pstmt.setString(2, orgCode);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private java.sql.Date parseDate(String dateStr, String existingDate) {
        if (dateStr == null || dateStr.isBlank()) dateStr = existingDate;
        if (dateStr == null || dateStr.isBlank()) return null;

        try {
            return java.sql.Date.valueOf(dateStr); // 시분초 없이 연월일만
        } catch (IllegalArgumentException e) {
            System.out.println("⚠️ 잘못된 날짜 형식: " + dateStr + " (YYYY-MM-DD)");
            return null;
        }
    }
}
