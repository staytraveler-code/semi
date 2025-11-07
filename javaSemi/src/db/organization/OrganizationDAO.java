package db.organization;

import java.sql.SQLException;

public interface OrganizationDAO {

	public void insertOrganization(OrganizationDTO dto) throws SQLException;
	public void updateOrganization(OrganizationDTO dto) throws SQLException;
	public void deleteOrganization(String id) throws SQLException;
	
    /* 특정 ID 기록 조회 */
    public OrganizationDTO selectRecord(String id) throws SQLException;
}
	

