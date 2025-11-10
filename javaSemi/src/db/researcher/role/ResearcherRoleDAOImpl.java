package db.researcher.role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.util.DBConn;


public class ResearcherRoleDAOImpl implements ResearcherRoleDAO {

    private Connection conn = DBConn.getConnection();


    @Override
    public void insertResearcherRoleDAO(ResearcherRoleDTO dto) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            conn.setAutoCommit(false);

            String sql = "INSERT INTO ResearcherRole(project_code, researcher_code, role, start_date, end_date) "
                       + "VALUES (?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);

            // java.util.Date -> java.sql.Date 변환 (null 체크 포함)
            java.sql.Date sqlStartDate = (dto.getStartDate() != null)
                                         ? new java.sql.Date(dto.getStartDate().getTime())
                                         : null;
            java.sql.Date sqlEndDate = (dto.getEndDate() != null)
                                       ? new java.sql.Date(dto.getEndDate().getTime())
                                       : null;

            pstmt.setString(1, dto.getProjectCode());
            pstmt.setString(2, dto.getResearcherCode());
            pstmt.setString(3, dto.getRole());
            pstmt.setDate(4, sqlStartDate);
            pstmt.setDate(5, sqlEndDate);

            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (pstmt != null) {
                try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    @Override
    public void updateResearcherRoleDAO(ResearcherRoleDTO dto) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            conn.setAutoCommit(false);

            String sql = "UPDATE ResearcherRole "
                       + "SET role = ?, start_date = ?, end_date = ? "
                       + "WHERE project_code = ? AND researcher_code = ?";

            pstmt = conn.prepareStatement(sql);

            java.sql.Date sqlStartDate = (dto.getStartDate() != null)
                                         ? new java.sql.Date(dto.getStartDate().getTime())
                                         : null;
            java.sql.Date sqlEndDate = (dto.getEndDate() != null)
                                       ? new java.sql.Date(dto.getEndDate().getTime())
                                       : null;

            pstmt.setString(1, dto.getRole());
            pstmt.setDate(2, sqlStartDate);
            pstmt.setDate(3, sqlEndDate);
            pstmt.setString(4, dto.getProjectCode());
            pstmt.setString(5, dto.getResearcherCode());

            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (pstmt != null) {
                try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }
}
