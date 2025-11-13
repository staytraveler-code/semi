package db.researcher.role;

import java.sql.SQLException;
import java.util.List;

public interface ResearcherRoleDAO {
	public void insertResearcherRoleDAO(ResearcherRoleDTO dto) throws SQLException;
	public void updateResearcherRoleDAO(ResearcherRoleDTO dto) throws SQLException;
	public void deleteResearcherRoleDAO(String pCode, String rCode) throws SQLException;
	
	public List<ResearcherRoleDTO> listRole(String code) throws SQLException;
	boolean isProjectIncludeRes(String pCode, String rCode) throws SQLException;

}
