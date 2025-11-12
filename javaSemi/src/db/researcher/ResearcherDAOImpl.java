package db.researcher;

import java.sql.*;
import java.util.*;
import db.util.DBConn;
import db.util.DBUtil;

public class ResearcherDAOImpl implements ResearcherDAO {
    private Connection conn;

    public ResearcherDAOImpl() {
        this.conn = DBConn.getConnection(); // 종료 시까지 재사용
    }

    @Override
    public void insertResearcherDAO(ResearcherDTO dto) throws SQLException {
        String sql = "INSERT INTO Researcher (researcher_code, org_code, name, tel, email) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;

        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getResearcherCode());
            pstmt.setString(2, dto.getOrgCode());
            pstmt.setString(3, dto.getName());
            pstmt.setString(4, dto.getTel());
            pstmt.setString(5, dto.getEmail());
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            DBUtil.rollback(conn);
            throw e;
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
        }
    }

    @Override
    public void updateResearcherDAO(ResearcherDTO dto) throws SQLException {
        String sql = "UPDATE Researcher SET name=?, tel=?, email=? WHERE researcher_code=? AND org_code=?";
        PreparedStatement pstmt = null;

        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);
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
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
        }
    }

    @Override
    public void deleteResearcherDAO(String researcherCode) throws SQLException {
        String sql = "DELETE FROM Researcher WHERE researcher_code=?";
        PreparedStatement pstmt = null;

        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, researcherCode);
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            DBUtil.rollback(conn);
            throw e;
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
        }
    }

    @Override
    public ResearcherDTO selectResearcherByCode(String researcherCode) throws SQLException {
        String sql = "SELECT researcher_code, org_code, name, tel, email FROM Researcher WHERE researcher_code=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResearcherDTO dto = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, researcherCode);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dto = new ResearcherDTO();
                dto.setResearcherCode(rs.getString("researcher_code"));
                dto.setOrgCode(rs.getString("org_code"));
                dto.setName(rs.getString("name"));
                dto.setTel(rs.getString("tel"));
                dto.setEmail(rs.getString("email"));
            }
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
            if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
        }
        return dto;
    }

    @Override
    public List<ResearcherDTO> listResearchersByOrg(String orgCode) throws SQLException {
        String sql = "SELECT researcher_code, name, tel, email FROM Researcher WHERE org_code=?";
        List<ResearcherDTO> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, orgCode);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ResearcherDTO dto = new ResearcherDTO();
                dto.setResearcherCode(rs.getString("researcher_code"));
                dto.setOrgCode(orgCode);
                dto.setName(rs.getString("name"));
                dto.setTel(rs.getString("tel"));
                dto.setEmail(rs.getString("email"));
                list.add(dto);
            }
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
            if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
        }
        return list;
    }
}
