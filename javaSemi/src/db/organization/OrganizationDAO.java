package db.organization;

import java.sql.SQLException;

public interface OrganizationDAO {
    void insertOrganization(OrganizationDTO dto) throws SQLException;
    void updateOrganization(OrganizationDTO dto) throws SQLException;
    void deleteOrganization(String id) throws SQLException;
    OrganizationDTO selectRecord(String id) throws SQLException;//특정 기관의 정보를 조회
    OrganizationDTO findById(String id) throws SQLException; // 로그인 시 아이디 기준으로 DB에서 기관 정보 조회
    
    boolean isBizRegNoExists(String bizRegNo) throws SQLException; // 사업자등록번호 중복 검사
}	// 새 기관 등록 시 사업자등록번호 중복 여부 확인
	// 중복이면 등록 불가, 중복 없으면 등록 가능
