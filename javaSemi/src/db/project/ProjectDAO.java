package db.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.util.DBConn;
import db.util.DBUtil;

public class ProjectDAO {

	public List<ProjectDTO> getProjectsByOrganization(String orgCode) {
		List<ProjectDTO> list = new ArrayList<>();
		String sql = "SELECT * FROM Project WHERE org_code = ? ORDER BY project_code";

		Connection conn = DBConn.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, orgCode);

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
				return list;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps);
		}
	return list;
	}
}

