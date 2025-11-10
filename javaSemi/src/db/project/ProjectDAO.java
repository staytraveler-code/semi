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

        try (Connection conn = DBConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, orgCode);
            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
