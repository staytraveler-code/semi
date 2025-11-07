package db.fund.management;

import java.sql.SQLException;
import java.util.List;

public interface FundManagementDAO {
	public void insertRecord(FundManagementDTO dto) throws SQLException;
	public void updateRecord(FundManagementDTO dto) throws SQLException;
	public void deleteRecord(int code) throws SQLException;
	
	public List<FundManagementDTO> listRecord();
}
