package db.researcher;

import java.sql.SQLException;
import java.util.List;

public interface ResearcherDAO {
    // 연구원 추가
    void insertResearcherDAO(ResearcherDTO dto) throws SQLException;

    // 연구원 수정
    void updateResearcherDAO(ResearcherDTO dto) throws SQLException;

    // 연구원 삭제
    void deleteResearcherDAO(String researcherCode) throws SQLException;

    // 연구원 단일 조회
    ResearcherDTO selectResearcherByCode(String researcherCode) throws SQLException;

    // 특정 기관의 연구원 전체 조회
    List<ResearcherDTO> listResearchersByOrg(String orgCode) throws SQLException;
}
