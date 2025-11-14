package db.organization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.util.DBConn;
import db.util.DBUtil;

public class OrganizationDAOImpl implements OrganizationDAO {
	private final Connection conn;

	public OrganizationDAOImpl() {
		this.conn = DBConn.getConnection(); // DAO 내에서 재사용
	}

	@Override
	public void insertOrganization(OrganizationDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			conn.setAutoCommit(false);

			String sql = """
					    INSERT INTO organization(
					        org_code, id, pwd, name, type, biz_reg_no, tel, email, address
					    )
					    VALUES('ORG_' || LPAD(seq_org_code.NEXTVAL, 3, '0'), ?, ?, ?, ?, ?, ?, ?, ?)
					""";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getOrgId());
			pstmt.setString(2, dto.getOrgPwd());
			pstmt.setString(3, dto.getOrgName());
			pstmt.setString(4, dto.getOrgType());
			pstmt.setString(5, dto.getBizRegNo());
			pstmt.setString(6, dto.getOrgTel());
			pstmt.setString(7, dto.getOrgEmail());
			pstmt.setString(8, dto.getOrgAddress());

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
	public void updateOrganization(OrganizationDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			conn.setAutoCommit(false);

			sql = """
					    UPDATE organization
					       SET name = ?, type = ?, biz_reg_no = ?, tel = ?, email = ?, address = ?
					     WHERE id = ?
					""";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getOrgName());
			pstmt.setString(2, dto.getOrgType());
			pstmt.setString(3, dto.getBizRegNo());
			pstmt.setString(4, dto.getOrgTel());
			pstmt.setString(5, dto.getOrgEmail());
			pstmt.setString(6, dto.getOrgAddress());
			pstmt.setString(7, dto.getOrgId());
			
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
	public void deleteOrganization(String id) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			conn.setAutoCommit(false);
			

			sql = "DELETE FROM organization WHERE id = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
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
	public OrganizationDTO selectRecord(String id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrganizationDTO dto = null;

		try {
			String sql = """
					    SELECT org_code, id, pwd, name, type, biz_reg_no, tel, email, address
					      FROM organization
					     WHERE id = ?
						""";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new OrganizationDTO();
				dto.setOrgCode(rs.getString("org_code"));
				dto.setOrgId(rs.getString("id"));
				dto.setOrgPwd(rs.getString("pwd"));
				dto.setOrgName(rs.getString("name"));
				dto.setOrgType(rs.getString("type"));
				dto.setBizRegNo(rs.getString("biz_reg_no"));
				dto.setOrgTel(rs.getString("tel"));
				dto.setOrgEmail(rs.getString("email"));
				dto.setOrgAddress(rs.getString("address"));
			}

		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		}

		return dto;
	}

	@Override
	public OrganizationDTO findById(String id) throws SQLException {
		return selectRecord(id);
	}

	@Override
    public boolean isBizRegNoExists(String bizRegNo) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(*) FROM organization WHERE biz_reg_no = ?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bizRegNo);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // 존재하면 true
            }
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }

        return false; // 존재하지 않으면 false
    }
}
