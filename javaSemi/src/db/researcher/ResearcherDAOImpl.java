package db.researcher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.util.DBConn;

public class ResearcherDAOImpl implements ResearcherDAO {
	private Connection conn = DBConn.getConnection();
	String sql;
	

	@Override
	public void insertResearcherDAO(ResearcherDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			conn.setAutoCommit(false);
			
			sql = "INSERT INTO Researcher(researcher_code, org_code, name, tel, email) "
				    + "VALUES(?, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,dto.getResearcherCode());
			pstmt.setString(2,dto.getOrgCode());
			pstmt.setString(3,dto.getName());
			pstmt.setString(4,dto.getTel());
			pstmt.setString(5,dto.getEmail());
			
			pstmt.executeUpdate();
			conn.commit();
			
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e2) {				
				}
		}
		
	}


	@Override
	public void updateResearcherDAO(ResearcherDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			conn.setAutoCommit(false);
			
			sql = "UPDATE Researcher SET org_code = ?, name = ?, tel = ?, email = ? "
		            + "WHERE researcher_code = ?";
		            
		            
			pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, dto.getOrgCode());
	        pstmt.setString(2, dto.getName());
	        pstmt.setString(3, dto.getTel());
	        pstmt.setString(4, dto.getEmail());
	        pstmt.setString(5, dto.getResearcherCode());

	        pstmt.executeUpdate();
	        conn.commit();
			
			
		} catch (Exception e) {
	        try {
	            conn.rollback();
	        } catch (Exception e2) {
	            e2.printStackTrace();
	        }
	        e.printStackTrace();
	    } finally {
	        if (pstmt != null)
	            try {
	                pstmt.close();
	            } catch (Exception e2) {
	            }
	    }
	}
}