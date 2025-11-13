package db.fund.management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.util.DBConn;
import db.util.DBUtil;

public class FundManagementDAOImpl implements FundManagementDAO{
	private Connection conn = DBConn.getConnection();

	@Override
	public void insertRecord(FundManagementDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			conn.setAutoCommit(false);
			
			sql = "INSERT INTO Fund_Management(fund_code, project_code, researcher_code,"
					+ "charger_name, category, date_used, expense, content, vendor_name,"
					+ "proof_type, memo) "
					+ "VALUES ('F' || LPAD(fund_management_fund_code_seq.NEXTVAL, 4, '0'), ?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getPcode());
			pstmt.setString(2, dto.getRcode());
			pstmt.setString(3, dto.getCharger_name());
			pstmt.setString(4, dto.getCategory());
			pstmt.setString(5, dto.getDate_used());
			pstmt.setLong(6, dto.getExpense());
			pstmt.setString(7, dto.getContent());
			pstmt.setString(8, dto.getVendor_name());
			pstmt.setString(9, dto.getProof_type());
			pstmt.setString(10, dto.getMemo());

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
	public void updateRecord(FundManagementDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			conn.setAutoCommit(false);
			
			sql = "UPDATE Fund_Management SET " 
					+ "project_code = ?,"
					+ "researcher_code = ?,"
					+ "charger_name = ?,"
					+ "category = ?,"
					+ "date_used = TO_DATE(?, 'YYYY-MM-DD'),"
					+ "expense = ?,"
					+ "content = ?,"
					+ "vendor_name = ?,"
					+ "proof_type = ?,"
					+ "memo = ?"
					+ "WHERE fund_code = ?";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getPcode());
			pstmt.setString(2, dto.getRcode());
			pstmt.setString(3, dto.getCharger_name());
			pstmt.setString(4, dto.getCategory());
			pstmt.setString(5, dto.getDate_used());
			pstmt.setLong(6, dto.getExpense());
			pstmt.setString(7, dto.getContent());
			pstmt.setString(8, dto.getVendor_name());
			pstmt.setString(9, dto.getProof_type());
			pstmt.setString(10, dto.getMemo());
			pstmt.setString(11, dto.getFcode());

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
	public void deleteRecord(String code) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			conn.setAutoCommit(false);
			
			sql = "DELETE Fund_Management WHERE fund_code = ?";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, code);

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
	public List<FundManagementDTO> listRecord() {
		List<FundManagementDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT fund_code, project_code, researcher_code, charger_name, category,"
					+ "date_used, expense, content, vendor_name, proof_type, memo "
					+ "FROM Fund_Management ORDER BY fund_code";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				FundManagementDTO dto = new FundManagementDTO();

				dto.setFcode(rs.getString("fund_code"));
				dto.setPcode(rs.getString("project_code"));
				dto.setRcode(rs.getString("researcher_code"));
				dto.setCharger_name(rs.getString("charger_name"));
				dto.setCategory(rs.getString("category"));
				dto.setDate_used(rs.getDate("date_used").toString());
				dto.setExpense(rs.getLong("expense"));
				dto.setContent(rs.getString("content"));
				dto.setVendor_name(rs.getString("vendor_name"));
				dto.setProof_type(rs.getString("proof_type"));
				dto.setMemo(rs.getString("memo"));

				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return list;
	}

	@Override
	public FundManagementDTO findByFundCode(String code) {
		FundManagementDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT fund_code, project_code, researcher_code, charger_name, category,"
					+ "date_used, expense, content, vendor_name, proof_type, memo "
					+ "FROM Fund_Management WHERE fund_code = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, code);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				dto = new FundManagementDTO();
				
				dto.setFcode(rs.getString("fund_code"));
				dto.setPcode(rs.getString("project_code"));
				dto.setRcode(rs.getString("researcher_code"));
				dto.setCharger_name(rs.getString("charger_name"));
				dto.setCategory(rs.getString("category"));
				dto.setDate_used(rs.getDate("date_used").toString());
				dto.setExpense(rs.getLong("expense"));
				dto.setContent(rs.getString("content"));
				dto.setVendor_name(rs.getString("vendor_name"));
				dto.setProof_type(rs.getString("proof_type"));
				dto.setMemo(rs.getString("memo"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		
		return dto;
	}

	@Override
	public boolean isProjectFundRecord(String pcode, String fcode) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT fund_code, project_code, researcher_code, charger_name, category,"
					+ "date_used, expense, content, vendor_name, proof_type, memo "
					+ "FROM Fund_Management WHERE fund_code = ? AND project_code = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fcode);
			pstmt.setString(2, pcode);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		
		return false;
	}

	@Override
	public List<FundManagementDTO> listRecord(String code) {
		List<FundManagementDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT fund_code, project_code, researcher_code, charger_name, category,"
					+ "date_used, expense, content, vendor_name, proof_type, memo "
					+ "FROM Fund_Management WHERE project_code = ? ORDER BY fund_code";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, code);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				FundManagementDTO dto = new FundManagementDTO();

				dto.setFcode(rs.getString("fund_code"));
				dto.setPcode(rs.getString("project_code"));
				dto.setRcode(rs.getString("researcher_code"));
				dto.setCharger_name(rs.getString("charger_name"));
				dto.setCategory(rs.getString("category"));
				dto.setDate_used(rs.getDate("date_used").toString());
				dto.setExpense(rs.getLong("expense"));
				dto.setContent(rs.getString("content"));
				dto.setVendor_name(rs.getString("vendor_name"));
				dto.setProof_type(rs.getString("proof_type"));
				dto.setMemo(rs.getString("memo"));

				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return list;
	}

}
