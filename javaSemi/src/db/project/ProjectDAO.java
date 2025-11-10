package db.project;

import db.util.DBConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {

    public List<ProjectDTO> getProjectsByOrganization(String orgCode) {
        List<ProjectDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM Project WHERE org_code = ? ORDER BY project_code";

        Connection conn = DBConn.getConnection(); // 싱글톤 커넥션
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, orgCode); // 한 번만 설정

            rs = ps.executeQuery();
            while (rs.next()) {
                ProjectDTO dto = new ProjectDTO();
                dto.setProjectCode(rs.getString("project_code"));
                dto.setOrgCode(rs.getString("org_code"));
                dto.setTitle(rs.getString("title"));
                dto.setStage(rs.getString("stage"));
                dto.setStatus(rs.getString("status"));
                dto.setBudget(rs.getLong("budget"));
                dto.setStartDate(rs.getDate("start_date"));
                dto.setEndDate(rs.getDate("end_date"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // ResultSet과 PreparedStatement만 닫기, Connection은 닫지 않음
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
        }

        return list;
    }
}
