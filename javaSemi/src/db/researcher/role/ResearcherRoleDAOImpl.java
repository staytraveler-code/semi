package db.researcher.role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.util.DBConn;
import db.util.DBUtil;


public class ResearcherRoleDAOImpl implements ResearcherRoleDAO {

    private Connection conn = DBConn.getConnection();


    @Override
    public void insertResearcherRoleDAO(ResearcherRoleDTO dto) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            conn.setAutoCommit(false);

            String sql = """
            	    INSERT INTO researcher_role(project_code, researcher_code, role, start_date, end_date)
            	    VALUES(?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), TO_DATE(?, 'YYYY-MM-DD'))
            	""";

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, dto.getProjectCode());
            pstmt.setString(2, dto.getResearcherCode());
            pstmt.setString(3, dto.getRole());
            pstmt.setString(4, dto.getStartDate());
            pstmt.setString(5, dto.getEndDate());

            pstmt.executeUpdate();
            conn.commit();
            
		} catch (SQLException e) {
			e.printStackTrace();
			DBUtil.rollback(conn);
			throw e;
		} finally {
			DBUtil.close(pstmt);
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
			}
		}
    }

    @Override
    public void updateResearcherRoleDAO(ResearcherRoleDTO dto) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            conn.setAutoCommit(false);

            String sql = "UPDATE Researcher_Role "
                       + "SET role = ?, start_date = ?, end_date = ? "
                       + "WHERE project_code = ? AND researcher_code = ?";

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, dto.getRole());
            pstmt.setString(2, dto.getStartDate());
            pstmt.setString(3, dto.getEndDate());
            pstmt.setString(4, dto.getProjectCode());
            pstmt.setString(5, dto.getResearcherCode());

            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
			e.printStackTrace();
			DBUtil.rollback(conn);
			throw e;
		} finally {
			DBUtil.close(pstmt);
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
			}
		}
    }

	@Override
	public List<ResearcherRoleDTO> listRole(String code) throws SQLException{
		List<ResearcherRoleDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT project_code, researcher_code, role, start_date, end_date"
					+ " FROM Researcher_Role WHERE project_code = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, code);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ResearcherRoleDTO dto = new ResearcherRoleDTO();

				dto.setProjectCode(rs.getString("project_code"));
				dto.setResearcherCode(rs.getString("researcher_code"));
				dto.setRole(rs.getString("role"));
				dto.setStartDate(rs.getString("start_date"));
				dto.setEndDate(rs.getString("start_date"));

				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return list;
	}

	@Override
	public boolean isProjectIncludeRes(String pCode, String rCode) throws SQLException {
		String sql = "SELECT name FROM Researcher_Role WHERE researcher_code = ? AND project_code = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, rCode);
            pstmt.setString(2, pCode);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        }

        return false;
	}

	@Override
	public void deleteResearcherRoleDAO(String pCode, String rCode) throws SQLException {
		PreparedStatement pstmt = null;

        try {
            conn.setAutoCommit(false);

            String sql = "DELETE Researcher_Role "
                       + "WHERE project_code = ? AND researcher_code = ?";

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, pCode);
            pstmt.setString(2, rCode);

            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
			e.printStackTrace();
			DBUtil.rollback(conn);
			throw e;
		} finally {
			DBUtil.close(pstmt);
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
			}
		}
	}
	
	
}
