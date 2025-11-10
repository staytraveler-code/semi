package db.partner.organization;

import java.sql.SQLException;

public interface PartnerOrganizationDAO {
	public void insertPartnerOrganizationDAO(PartnerOrganizationDTO dto) throws SQLException;
	public void updatePartnerOrganizationDAO(PartnerOrganizationDTO dto) throws SQLException;
	
}
