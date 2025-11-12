package db.researcher;

import java.sql.*;
import java.util.*;
import db.util.DBConn;
import db.util.DBUtil;

public class ResearcherDAOImpl implements ResearcherDAO {
    private final Connection conn;

    public ResearcherDAOImpl() {
        this.conn = DBConn.getConnection(); // 앱 종료 시까지 재사용
    }

    @Override
    public void insertResearcherDAO(ResearcherDTO dto) throws SQLException {
        String sql = """
            INSERT INTO researcher(
                researcher_code, org_code, name, tel, email
            )
            VALUES('RES_' || LPAD(seq_researcher.NEXTVAL, 3, '0'), ?, ?, ?, ?)
        """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            // researcher_code는 시퀀스로 자동 생성되므로 제외
            pstmt.setString(1, dto.getOrgCode());
            pstmt.setString(2, dto.getName());
            pstmt.setString(3, dto.getTel());
            pstmt.setString(4, dto.getEmail());

            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            DBUtil.rollback(conn);
            throw e;
        }
    }

    @Override
    public void updateResearcherDAO(ResearcherDTO dto) throws SQLException {
        String sql = "UPDATE Researcher SET name=?, tel=?, email=? WHERE researcher_code=? AND org_code=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            pstmt.setString(1, dto.getName());
            pstmt.setString(2, dto.getTel());
            pstmt.setString(3, dto.getEmail());
            pstmt.setString(4, dto.getResearcherCode());
            pstmt.setString(5, dto.getOrgCode());

            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            DBUtil.rollback(conn);
            throw e;
        }
    }

    @Override
    public void deleteResearcherDAO(String researcherCode) throws SQLException {
        String sql = "DELETE FROM Researcher WHERE researcher_code=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            pstmt.setString(1, researcherCode);
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            DBUtil.rollback(conn);
            throw e;
        }
    }

    @Override
    public ResearcherDTO selectResearcherByCode(String researcherCode) throws SQLException {
        String sql = "SELECT researcher_code, org_code, name, tel, email FROM Researcher WHERE researcher_code=?";
        ResearcherDTO dto = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, researcherCode);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    dto = new ResearcherDTO();
                    dto.setResearcherCode(rs.getString("researcher_code"));
                    dto.setOrgCode(rs.getString("org_code"));
                    dto.setName(rs.getString("name"));
                    dto.setTel(rs.getString("tel"));
                    dto.setEmail(rs.getString("email"));
                }
            }
        }
        return dto;
    }

    @Override
    public List<ResearcherDTO> listResearchersByOrg(String orgCode) throws SQLException {
        String sql = "SELECT researcher_code, name, tel, email FROM Researcher WHERE org_code=?";
        List<ResearcherDTO> list = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, orgCode);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ResearcherDTO dto = new ResearcherDTO();
                    dto.setResearcherCode(rs.getString("researcher_code"));
                    dto.setOrgCode(orgCode);
                    dto.setName(rs.getString("name"));
                    dto.setTel(rs.getString("tel"));
                    dto.setEmail(rs.getString("email"));
                    list.add(dto);
                }
            }
        }
        return list;
    }
}
