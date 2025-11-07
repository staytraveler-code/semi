package db.project;

import db.util.DBConn;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {

    // 전체 과제 조회 - 관리자용
    public List<ProjectDTO> getAllProjects() {
        List<ProjectDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM Project ORDER BY project_code";

        try (Connection conn = DBConn.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ProjectDTO p = new ProjectDTO();
                p.setProjectCode(rs.getString("PROJECT_CODE"));
                p.setOrgCode(rs.getString("ORG_CODE"));
                p.setTitle(rs.getString("TITLE"));
                p.setStage(rs.getString("STAGE"));
                p.setStatus(rs.getString("STATUS"));
                p.setBudget(rs.getLong("BUDGET"));
                p.setStartDate(rs.getDate("START_DATE"));
                p.setEndDate(rs.getDate("END_DATE"));
                list.add(p);
            }
        } catch (SQLException e) {
            System.err.println("[ProjectDAO] getAllProjects Error: " + e.getMessage());
        }
        return list;
    }

    // 기관별 과제 조회 
    public List<ProjectDTO> getProjectsByOrganization(String orgCode) {
        List<ProjectDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM Project WHERE org_code = ? ORDER BY project_code";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, orgCode);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProjectDTO p = new ProjectDTO();
                    p.setProjectCode(rs.getString("PROJECT_CODE"));
                    p.setOrgCode(rs.getString("ORG_CODE"));
                    p.setTitle(rs.getString("TITLE"));
                    p.setStage(rs.getString("STAGE"));
                    p.setStatus(rs.getString("STATUS"));
                    p.setBudget(rs.getLong("BUDGET"));
                    p.setStartDate(rs.getDate("START_DATE"));
                    p.setEndDate(rs.getDate("END_DATE"));
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("[ProjectDAO] getProjectsByOrganization Error: " + e.getMessage());
        }
        return list;
    }
}
