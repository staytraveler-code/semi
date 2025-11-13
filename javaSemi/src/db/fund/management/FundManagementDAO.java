package db.fund.management;

import java.sql.SQLException;
import java.util.List;

public interface FundManagementDAO {
	public void insertRecord(FundManagementDTO dto) throws SQLException;
	public void updateRecord(FundManagementDTO dto) throws SQLException;
	public void deleteRecord(String code) throws SQLException;
	
	public FundManagementDTO findByFundCode(String code);
	public boolean isProjectFundRecord(String pcode, String fcode);
	
	public List<FundManagementDTO> listRecord();
	public List<FundManagementDTO> listRecord(String code);
}
