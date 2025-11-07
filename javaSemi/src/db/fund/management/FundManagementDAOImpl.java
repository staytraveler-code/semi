package db.fund.management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class FundManagementDAOImpl implements FundManagementDAO{
	private Connection conn = null;

	@Override
	public void insertRecord(FundManagementDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			conn.setAutoCommit(false);
			
			sql = "INSERT INTO Fund_Management(fund_code, project_code, researcher_code,"
					+ "charger_name, category, date_used, expense, content, vendor_name,"
					+ "proof_type, memo) "
					+ "VALUES (?, ?, ?)";

			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, dto.getId());
//			pstmt.setString(2, dto.getName());
//			pstmt.setString(3, dto.getPwd());

			pstmt.executeUpdate();

			conn.commit();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public void updateRecord(FundManagementDTO dto) throws SQLException {
	}

	@Override
	public void deleteRecord(String id) throws SQLException {
	}

	@Override
	public List<FundManagementDTO> listRecord() {
		return null;
	}

}
