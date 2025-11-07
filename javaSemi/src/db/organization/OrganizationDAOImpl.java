package db.organization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.util.DBConn;
import db.util.DBUtil;

public class OrganizationDAOImpl implements OrganizationDAO {
	private Connection conn = DBConn.getConnection();
	
	@Override
	public void insertOrganization(OrganizationDTO dto) throws SQLException {
		// 데이터 추가
		PreparedStatement pstmt = null;
		String sql;
	
		try {
			conn.setAutoCommit(false);
			
			sql = "INSERT INTO organization(name,type,biz_reg_no,tel,email,address,id, pwd)"
				 + " VALUES(?,?,?,?,?,?,?,?)" ;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getOrgName());
			pstmt.setString(2, dto.getOrgType());
			pstmt.setString(3, dto.getBizRegNo());
			pstmt.setString(4, dto.getOrgTel());
			pstmt.setString(5, dto.getOrgEmail());
			pstmt.setString(6, dto.getOrgAddress());
			pstmt.setString(7, dto.getOrgId());
			pstmt.setString(8, dto.getOrgPwd());
			
			pstmt.executeUpdate();
			
			
			conn.commit();
			
			
		} catch (SQLException e) {
			DBUtil.rollback(conn);
			throw e;
		} finally {
			DBUtil.close(pstmt) ;
			
			try {
				conn.setAutoCommit(true);
			}catch (Exception e2) {
			}
		}
	
	}

	@Override
	public void updateOrganization(OrganizationDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			conn.setAutoCommit(false);
			
			sql = "UPDATE organization SET name = ?, type =?, biz_reg_no =?, tel =?, email =?, address =?"
				  + " WHERE id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,dto.getOrgName());
			pstmt.setString(2,dto.getOrgType());
			pstmt.setString(3,dto.getBizRegNo());
			pstmt.setString(4,dto.getOrgTel());
			pstmt.setString(5,dto.getOrgEmail());
			pstmt.setString(6,dto.getOrgAddress());
			pstmt.setString(7,dto.getOrgId());
			
			pstmt.executeUpdate();
			
			conn.commit();
	
		} catch (SQLException e) {
			DBUtil.rollback(conn);
			
			throw e;
		
		}finally {
			DBUtil.close(pstmt);
			
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
				
			}
		}
		
	}

	@Override
	public void deleteOrganization(String id) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			conn.setAutoCommit(false);
			
			sql = "DELETE FROM Organization WHERE org_id =?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			conn.commit();
			
			
		} catch (SQLException e) {
			DBUtil.rollback(conn);
			
			throw e;
		}finally {
			DBUtil.close(pstmt);
			
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
				
			}
		}
		
	}

	
	
	@Override
	public OrganizationDTO selectRecord(String id) throws SQLException {
		PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;
		OrganizationDTO dto = null;
		String sql;
		
		try {
			sql = "SELECT name, type, biz_reg_no, tel, email, address, id, pwd " 
					+ "FROM organization WHERE id = ?" ;
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new OrganizationDTO();
				   dto.setOrgName(rs.getString("name"));
		           dto.setOrgType(rs.getString("type"));
		           dto.setBizRegNo(rs.getString("biz_reg_no"));
		           dto.setOrgTel(rs.getString("tel"));
		           dto.setOrgEmail(rs.getString("email"));
		           dto.setOrgAddress(rs.getString("address"));
		           dto.setOrgId(rs.getString("id"));
		           dto.setOrgPwd(rs.getString("pwd"));
			
			}
				
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		} 
		return dto;
		}


	
	
}
	



