package db.mimistry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import db.util.DBConn;
import db.util.DBUtil;

public class MinistryDAO {
    private Connection conn = DBConn.getConnection(); // 클래스 필드로 재사용

    // 전체 부처 목록 조회
    public List<MinistryDTO> getMinistryList() {
        List<MinistryDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM Ministry ORDER BY ministry_code";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                MinistryDTO dto = new MinistryDTO();
                dto.setMinistryCode(rs.getString("MINISTRY_CODE"));
                dto.setName(rs.getString("NAME"));
                dto.setTel(rs.getString("TEL"));
                dto.setEmail(rs.getString("EMAIL"));
                list.add(dto);
            }

        } catch (SQLException e) {
            System.err.println("[MinistryDAO] getMinistryList Error: " + e.getMessage());
        }

        return list;
    }

    // 단일 부처 조회
    public MinistryDTO getMinistryByCode(String code) {
        MinistryDTO dto = null;
        String sql = "SELECT * FROM Ministry WHERE ministry_code = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dto = new MinistryDTO();
                    dto.setMinistryCode(rs.getString("MINISTRY_CODE"));
                    dto.setName(rs.getString("NAME"));
                    dto.setTel(rs.getString("TEL"));
                    dto.setEmail(rs.getString("EMAIL"));
                }
            }

        } catch (SQLException e) {
            System.err.println("[MinistryDAO] getMinistryByCode Error: " + e.getMessage());

        }

        return dto;
    }

    // 부처 등록
    public int insertMinistry(MinistryDTO dto) {
        int result = 0;
        String sql = "INSERT INTO Ministry (ministry_code, name, tel, email) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dto.getMinistryCode());
            ps.setString(2, dto.getName());
            ps.setString(3, dto.getTel());
            ps.setString(4, dto.getEmail());
            result = ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            System.err.println("[MinistryDAO] insertMinistry Error: " + e.getMessage());
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
        }

        return result;
    }

    // 부처 정보 수정
    public int updateMinistry(MinistryDTO dto) {
        int result = 0;
        String sql = "UPDATE Ministry SET name = ?, tel = ?, email = ? WHERE ministry_code = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dto.getName());
            ps.setString(2, dto.getTel());
            ps.setString(3, dto.getEmail());
            ps.setString(4, dto.getMinistryCode());
            result = ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            System.err.println("[MinistryDAO] updateMinistry Error: " + e.getMessage());
            DBUtil.rollback(conn);

        }

        return result;
    }

    // 부처 삭제
    public int deleteMinistry(String code) {
        int result = 0;
        String sql = "DELETE FROM Ministry WHERE ministry_code = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            result = ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            System.err.println("[MinistryDAO] deleteMinistry Error: " + e.getMessage());
            DBUtil.rollback(conn);
        }

        return result;
    }
}
