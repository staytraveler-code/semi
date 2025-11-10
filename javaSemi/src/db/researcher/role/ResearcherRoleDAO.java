package db.researcher.role;

import java.sql.SQLException;

public interface ResearcherRoleDAO {
	public void insertResearcherRoleDAO(ResearcherRoleDTO dto) throws SQLException;
	public void updateResearcherRoleDAO(ResearcherRoleDTO dto) throws SQLException;

}
