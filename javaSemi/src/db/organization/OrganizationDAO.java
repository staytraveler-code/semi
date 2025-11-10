package db.organization;

import java.sql.SQLException;

public interface OrganizationDAO {
    void insertOrganization(OrganizationDTO dto) throws SQLException;
    void updateOrganization(OrganizationDTO dto) throws SQLException;
    void deleteOrganization(String id) throws SQLException;
    OrganizationDTO selectRecord(String id) throws SQLException;
    OrganizationDTO findById(String id) throws SQLException; // 로그인용
}
