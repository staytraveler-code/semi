package db.researcher;

import java.sql.SQLException;
import java.util.List;

public interface ResearcherDAO {
    void insertResearcherDAO(ResearcherDTO dto) throws SQLException;       // 연구원 등록
    void updateResearcherDAO(ResearcherDTO dto) throws SQLException;       // 연구원 수정
    void deleteResearcherDAO(String researcherCode) throws SQLException;   // 연구원 삭제
    ResearcherDTO selectResearcherByCode(String researcherCode) throws SQLException; // 단일 조회
    List<ResearcherDTO> listResearchersByOrg(String orgCode) throws SQLException;    // 기관별 전체 조회
}
