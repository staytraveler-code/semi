package db.partner.organization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.util.DBConn;

public class PartnerOrganizationDAOImpl implements PartnerOrganizationDAO {
	private Connection conn = DBConn.getConnection();
	

	@Override
	public void insertPartnerOrganizationDAO(PartnerOrganizationDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			conn.setAutoCommit(false);
			
			
			// 다시 확인
			sql = "INSERT INTO partner_organization(partner_org_name,biz_reg_no,partner_org_tel,partner_org_email,"
					+ "partner_org_address,charger_name,charger_tel)"
					+ "bank_name,account_number, account_holder"
					+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)" ;
			
			sql = "INSERT INTO partner_organization "
	                + "(partner_org_name, biz_reg_no, partner_org_tel, partner_org_email, "
	                + "partner_org_address, manager_name, manager_tel, bank_name, account_no, account_holder) "
	                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
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
				}catch (Exception e2) {
				}
		}
		
	}

}
