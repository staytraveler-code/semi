package db.organization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.util.DBConn;

public class OrganizationDAOImpl implements OrganizationDAO {
    private Connection conn;

    public OrganizationDAOImpl() {
        this.conn = DBConn.getConnection();
    }

    // 기관 등록
    @Override
    public void insertOrganization(OrganizationDTO dto) throws SQLException {
        String sql = """
            INSERT INTO organization(id, pwd, name, type, biz_reg_no, tel, email, address)
            VALUES(?,?,?,?,?,?,?,?)
        """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dto.getOrgId());
            pstmt.setString(2, dto.getOrgPwd());
            pstmt.setString(3, dto.getOrgName());
            pstmt.setString(4, dto.getOrgType());
            pstmt.setString(5, dto.getBizRegNo());
            pstmt.setString(6, dto.getOrgTel());
            pstmt.setString(7, dto.getOrgEmail());
            pstmt.setString(8, dto.getOrgAddress());
            pstmt.executeUpdate();
        }
    }

    // 기관 수정
    @Override
    public void updateOrganization(OrganizationDTO dto) throws SQLException {
        String sql = """
            UPDATE organization
               SET name = ?, type = ?, biz_reg_no = ?, tel = ?, email = ?, address = ?
             WHERE id = ?
        """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dto.getOrgName());
            pstmt.setString(2, dto.getOrgType());
            pstmt.setString(3, dto.getBizRegNo());
            pstmt.setString(4, dto.getOrgTel());
            pstmt.setString(5, dto.getOrgEmail());
            pstmt.setString(6, dto.getOrgAddress());
            pstmt.setString(7, dto.getOrgId());
            pstmt.executeUpdate();
        }
    }

    // 기관 삭제
    @Override
    public void deleteOrganization(String id) throws SQLException {
        String sql = "DELETE FROM organization WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    // 기관 단일 조회
    @Override
    public OrganizationDTO selectRecord(String id) throws SQLException {
        String sql = """
            SELECT id, pwd, name, type, biz_reg_no, tel, email, address
              FROM organization
             WHERE id = ?
        """;

        OrganizationDTO dto = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    dto = new OrganizationDTO();
                    dto.setOrgId(rs.getString("id"));
                    dto.setOrgPwd(rs.getString("pwd"));
                    dto.setOrgName(rs.getString("name"));
                    dto.setOrgType(rs.getString("type"));
                    dto.setBizRegNo(rs.getString("biz_reg_no"));
                    dto.setOrgTel(rs.getString("tel"));
                    dto.setOrgEmail(rs.getString("email"));
                    dto.setOrgAddress(rs.getString("address"));
                }
            }
        }

        return dto;
    }

    // 로그인용 아이디 확인 (selectRecord와 동일하게 동작)
    public OrganizationDTO findById(String id) throws SQLException {
        return selectRecord(id);
    }
}
