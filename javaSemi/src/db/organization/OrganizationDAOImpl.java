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

    @Override
    public void insertOrganization(OrganizationDTO dto) throws SQLException {
        String sql = """
            INSERT INTO organization(
                org_code, id, pwd, name, type, biz_reg_no, tel, email, address
            )
            VALUES('ORG_' || LPAD(seq_org_code.NEXTVAL, 3, '0'), ?, ?, ?, ?, ?, ?, ?, ?)
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

        // 방금 생성된 org_code 조회
        String getCodeSql = "SELECT org_code FROM organization WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(getCodeSql)) {
            pstmt.setString(1, dto.getOrgId());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    dto.setOrgCode(rs.getString("org_code"));
                }
            }
        }
    }

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

    @Override
    public void deleteOrganization(String id) throws SQLException {
        String sql = "DELETE FROM organization WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public OrganizationDTO selectRecord(String id) throws SQLException {
        String sql = """
            SELECT org_code, id, pwd, name, type, biz_reg_no, tel, email, address
              FROM organization
             WHERE id = ?
        """;

        OrganizationDTO dto = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    dto = new OrganizationDTO();
                    dto.setOrgCode(rs.getString("org_code"));
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

    @Override
    public OrganizationDTO findById(String id) throws SQLException {
        return selectRecord(id);
    }
}
