package db.partner.organization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.util.DBConn;
import db.util.DBUtil;

public class PartnerOrganizationDAOImpl implements PartnerOrganizationDAO {
	private Connection conn = DBConn.getConnection();
	

	@Override
	public void insertPartnerOrganizationDAO(PartnerOrganizationDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			conn.setAutoCommit(false);
			
			
			sql = """
				    INSERT INTO partner_organization(
				        partner_org_code, partner_org_name, biz_reg_no, partner_org_tel,
				        partner_org_email, partner_org_address, manager_name, manager_tel,
				        bank_name, account_no, account_holder
				    )
				    VALUES('PART_' || LPAD(seq_partner_organization.NEXTVAL, 3, '0'),
				           ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
				""";

	
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setString(1, dto.getPartnerOrgName());
			pstmt.setString(2, dto.getBizRegNo());
			pstmt.setString(3, dto.getPartnerOrgTel());
			pstmt.setString(4, dto.getPartnerOrgEmail());
            pstmt.setString(5, dto.getPartnerOrgAddress());
            pstmt.setString(6, dto.getManagerName());
            pstmt.setString(7, dto.getManagerTel());
            pstmt.setString(8, dto.getBankName());
            pstmt.setString(9, dto.getAccountNo());
            pstmt.setString(10, dto.getAccountHolder());
            
            int result = pstmt.executeUpdate();
            if(result == 0) {
            	throw new SQLException("협력기관 등록이 정상적으로 이루어지지 않았습니다.");
            }
     
            conn.commit();
			
			
		} catch (Exception e) {
			DBUtil.rollback(conn);
			throw e;
		}finally {
			if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
		}
		
	}

	@Override
	public void updatePartnerOrganizationDAO(PartnerOrganizationDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		 try {
	            conn.setAutoCommit(false);

	            sql = "UPDATE partner_organization SET "
	                + "partner_org_name=?, biz_reg_no=?, partner_org_tel=?, partner_org_email=?, "
	                + "partner_org_address=?, manager_name=?, manager_tel=?, bank_name=?, account_no=?, account_holder=? "
	                + "WHERE biz_reg_no=?";
	            
	            pstmt = conn.prepareStatement(sql);

	            pstmt.setString(1, dto.getPartnerOrgName());
	            pstmt.setString(2, dto.getBizRegNo());
	            pstmt.setString(3, dto.getPartnerOrgTel());
	            pstmt.setString(4, dto.getPartnerOrgEmail());
	            pstmt.setString(5, dto.getPartnerOrgAddress());
	            pstmt.setString(6, dto.getManagerName());
	            pstmt.setString(7, dto.getManagerTel());
	            pstmt.setString(8, dto.getBankName());
	            pstmt.setString(9, dto.getAccountNo());
	            pstmt.setString(10, dto.getAccountHolder());
	            pstmt.setString(11, dto.getBizRegNo());
	            
	            int result = pstmt.executeUpdate();
	            if(result == 0) {
	            	throw new SQLException("협력기관 정보 수정이 정상적으로 이루어지지 않았습니다.");
	            	
	            }
	            
	           
	            conn.commit();
	            
		 } catch (Exception e) { 
			DBUtil.rollback(conn);
			throw e;
		 }finally {
			 if(pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
	
		}
		
	}

}
