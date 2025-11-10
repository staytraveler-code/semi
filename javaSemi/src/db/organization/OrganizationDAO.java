package db.organization;

import java.sql.SQLException;

public interface OrganizationDAO {
    void insertOrganization(OrganizationDTO dto) throws SQLException;
    void updateOrganization(OrganizationDTO dto) throws SQLException;
    void deleteOrganization(String id) throws SQLException;
    OrganizationDTO selectRecord(String id) throws SQLException;
    
    default OrganizationDTO findById(String id) throws SQLException {
        return selectRecord(id);
    }
}
