package db.researcher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.util.DBConn;
import db.util.DBUtil;

public class ResearcherDAOImpl implements ResearcherDAO {
    private Connection conn;

    public ResearcherDAOImpl() {
        this.conn = DBConn.getConnection(); // 프로그램 종료 시까지 재사용
    }
    
    // 연구원 추가
    @Override
    public void insertResearcherDAO(ResearcherDTO dto) throws SQLException {
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO Researcher(researcher_code, org_code, name, tel, email) VALUES (?, ?, ?, ?, ?)";

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
            throw e; // 호출한 곳에서 예외 처리
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
        }
    }

    // 연구원 수정
    @Override
    public void updateResearcherDAO(ResearcherDTO dto) throws SQLException {
        PreparedStatement pstmt = null;
        String sql = "UPDATE Researcher SET org_code = ?, name = ?, tel = ?, email = ? WHERE researcher_code = ?";

        try {
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getOrgCode());
            pstmt.setString(2, dto.getName());
            pstmt.setString(3, dto.getTel());
            pstmt.setString(4, dto.getEmail());
            pstmt.setString(5, dto.getResearcherCode());

            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            throw e;
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
        }
    }

    // 추가: 연구원 삭제
    public void deleteResearcherDAO(String researcherCode) throws SQLException {
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM Researcher WHERE researcher_code = ?";

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

    // 추가: 단일 연구원 조회
    public ResearcherDTO selectResearcherByCode(String researcherCode) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT researcher_code, org_code, name, tel, email FROM Researcher WHERE researcher_code = ?";
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

    // 추가: 특정 기관의 연구원 전체 조회
    public List<ResearcherDTO> listResearchersByOrg(String orgCode) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT researcher_code, org_code, name, tel, email FROM Researcher WHERE org_code = ?";
        List<ResearcherDTO> list = new ArrayList<>();

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, orgCode);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ResearcherDTO dto = new ResearcherDTO();
                dto.setResearcherCode(rs.getString("researcher_code"));
                dto.setOrgCode(rs.getString("org_code"));
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
