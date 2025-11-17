package administrator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.organization.OrganizationDTO;
import db.project.ProjectDTO;
import db.util.DBConn;
import db.util.DBUtil;

public class AdminDAO {
	Connection conn = DBConn.getConnection();

	// 기관 전체 출력리스트
	public List<OrganizationDTO> ListOrgan() throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrganizationDTO> list = new ArrayList<>();

		try {
			String sql = """
					   SELECT org_code, name, type, biz_reg_no, tel, email, address , id
					     FROM organization
					""";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				OrganizationDTO dto = new OrganizationDTO();
				dto.setOrgCode(rs.getString("org_code"));
				dto.setOrgName(rs.getString("name"));
				dto.setOrgType(rs.getString("type"));
				dto.setBizRegNo(rs.getString("biz_reg_no"));
				dto.setOrgTel(rs.getString("tel"));
				dto.setOrgEmail(rs.getString("email"));
				dto.setOrgAddress(rs.getString("address"));
				dto.setOrgId(rs.getString("id"));

				list.add(dto);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return list;
	}

	// 1. 이름으로 기관 검색
	public List<OrganizationDTO> findByOrganName(String key) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrganizationDTO> list = new ArrayList<>();

		try {
			String sql = """
					   SELECT org_code, name, type, biz_reg_no, tel, email, address , id
					     FROM organization WHERE name LIKE ?
					""";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + key + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				OrganizationDTO dto = new OrganizationDTO();
				dto.setOrgCode(rs.getString("org_code"));
				dto.setOrgName(rs.getString("name"));
				dto.setOrgType(rs.getString("type"));
				dto.setBizRegNo(rs.getString("biz_reg_no"));
				dto.setOrgTel(rs.getString("tel"));
				dto.setOrgEmail(rs.getString("email"));
				dto.setOrgAddress(rs.getString("address"));
				dto.setOrgId(rs.getString("id"));

				list.add(dto);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return list;
	}

	// 2. 기관타입으로 기관 검색
	public List<OrganizationDTO> findByOrganType(String key) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrganizationDTO> list = new ArrayList<>();

		try {
			String sql = """
					   SELECT org_code, name, type, biz_reg_no, tel, email, address , id
					     FROM organization WHERE type LIKE ?
					""";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + key + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				OrganizationDTO dto = new OrganizationDTO();
				dto.setOrgCode(rs.getString("org_code"));
				dto.setOrgName(rs.getString("name"));
				dto.setOrgType(rs.getString("type"));
				dto.setBizRegNo(rs.getString("biz_reg_no"));
				dto.setOrgTel(rs.getString("tel"));
				dto.setOrgEmail(rs.getString("email"));
				dto.setOrgAddress(rs.getString("address"));
				dto.setOrgId(rs.getString("id"));

				list.add(dto);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return list;
	}

	// 3. 주소지로 기관 검색
	public List<OrganizationDTO> findByOrganAddr(String key) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrganizationDTO> list = new ArrayList<>();

		try {
			String sql = """
					   SELECT org_code, name, type, biz_reg_no, tel, email, address , id
					     FROM organization WHERE address LIKE ?
					""";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + key + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				OrganizationDTO dto = new OrganizationDTO();
				dto.setOrgCode(rs.getString("org_code"));
				dto.setOrgName(rs.getString("name"));
				dto.setOrgType(rs.getString("type"));
				dto.setBizRegNo(rs.getString("biz_reg_no"));
				dto.setOrgTel(rs.getString("tel"));
				dto.setOrgEmail(rs.getString("email"));
				dto.setOrgAddress(rs.getString("address"));
				dto.setOrgId(rs.getString("id"));

				list.add(dto);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return list;
	}

	// 과제 전체 출력리스트
	public List<ProjectDTO> ListProject() throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ProjectDTO> list = new ArrayList<>();

		try {
			String sql = """
					   SELECT project_code, org.name AS name, title, stage, status, budget,
					   start_date, end_date
					    FROM project p
					    JOIN organization org ON p.org_code = org.org_code
					""";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProjectDTO dto = new ProjectDTO();
				dto.setProjectCode(rs.getString("project_code"));
				dto.setOrgName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setStage(rs.getString("stage"));
				dto.setStatus(rs.getString("status"));
				dto.setBudget(rs.getLong("budget"));
				dto.setStartDate(rs.getDate("start_date"));
				dto.setEndDate(rs.getDate("end_date"));

				list.add(dto);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		return list;
	}

	// 1. 과제 주제별
	public List<ProjectDTO> FindByProjectTitle(String key) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ProjectDTO> list = new ArrayList<>();

		try {
			String sql = """
					   SELECT project_code, org.name AS name, title, stage, status, budget,
					   start_date, end_date
					    FROM project p
					    JOIN organization org ON p.org_code = org.org_code
					    WHERE title Like ?
					""";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + key + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProjectDTO dto = new ProjectDTO();
				dto.setProjectCode(rs.getString("project_code"));
				dto.setOrgName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setStage(rs.getString("stage"));
				dto.setStatus(rs.getString("status"));
				dto.setBudget(rs.getLong("budget"));
				dto.setStartDate(rs.getDate("start_date"));
				dto.setEndDate(rs.getDate("end_date"));

				list.add(dto);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return list;
	}

	// 2. 과제 단계별로 검색(1~4단계
	public List<ProjectDTO> FindByProjectStage(String key) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ProjectDTO> list = new ArrayList<>();

		try {
			String sql = """
					   SELECT project_code, org.name AS name, title, stage, status, budget,
					   start_date, end_date
					    FROM project p
					    JOIN organization org ON p.org_code = org.org_code
					    WHERE Stage Like ?
					""";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + key + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProjectDTO dto = new ProjectDTO();
				dto.setProjectCode(rs.getString("project_code"));
				dto.setOrgName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setStage(rs.getString("stage"));
				dto.setStatus(rs.getString("status"));
				dto.setBudget(rs.getLong("budget"));
				dto.setStartDate(rs.getDate("start_date"));
				dto.setEndDate(rs.getDate("end_date"));

				list.add(dto);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return list;
	}

	// 3. 과제 상태별로 검색(진행중 완료 지연 등
	public List<ProjectDTO> FindByProjectStatus(String key) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ProjectDTO> list = new ArrayList<>();

		try {
			String sql = """
					   SELECT project_code, org.name AS name, title, stage, status, budget,
					   start_date, end_date
					    FROM project p
					    JOIN organization org ON p.org_code = org.org_code
					    WHERE Status Like ?
					""";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + key + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProjectDTO dto = new ProjectDTO();
				dto.setProjectCode(rs.getString("project_code"));
				dto.setOrgName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setStage(rs.getString("stage"));
				dto.setStatus(rs.getString("status"));
				dto.setBudget(rs.getLong("budget"));
				dto.setStartDate(rs.getDate("start_date"));
				dto.setEndDate(rs.getDate("end_date"));

				list.add(dto);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return list;
	}

	// 4. 과제 예산 내림차순
	public List<ProjectDTO> ListProjectBudget() throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ProjectDTO> list = new ArrayList<>();

		try {
			String sql = """
					   SELECT project_code, org.name AS name, title, stage, status, budget,
					   start_date, end_date
					    FROM project p
					    JOIN organization org ON p.org_code = org.org_code
					    ORDER BY budget DESC
					""";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProjectDTO dto = new ProjectDTO();
				dto.setProjectCode(rs.getString("project_code"));
				dto.setOrgName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setStage(rs.getString("stage"));
				dto.setStatus(rs.getString("status"));
				dto.setBudget(rs.getLong("budget"));
				dto.setStartDate(rs.getDate("start_date"));
				dto.setEndDate(rs.getDate("end_date"));

				list.add(dto);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return list;
	}

}