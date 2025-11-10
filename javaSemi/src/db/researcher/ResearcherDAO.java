package db.researcher;

import java.sql.SQLException;

public interface ResearcherDAO {
	public void insertResearcherDAO(ResearcherDTO dto) throws SQLException;
	public void updateResearcherDAO(ResearcherDTO dto) throws SQLException;
	

}
