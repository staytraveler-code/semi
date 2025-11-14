package db.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.milestone.MilestoneDAO;
import db.util.DBConn;
import db.util.DBUtil;

public class ProjectDAO {

    private final Connection conn = DBConn.getConnection();

    // 기관별 과제 리스트
    public List<ProjectDTO> getProjectsByOrganization(String orgCode) {
        List<ProjectDTO> list = new ArrayList<>();
        String sql = "SELECT project_code, org_code, title, stage, status, budget, start_date, end_date FROM Project WHERE org_code = ? ORDER BY project_code";

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
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs);
            DBUtil.close(ps);
        }
        return list;
    }

    // 과제 상세 정보
    public ProjectDTO getProjectDetail(String projectCode) {
        ProjectDTO dto = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT project_code, org_code, title, stage, status, budget, start_date, end_date FROM Project WHERE project_code = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, projectCode);
            rs = ps.executeQuery();

            if (rs.next()) {
                dto = new ProjectDTO();
                dto.setProjectCode(rs.getString("project_code"));
                dto.setOrgCode(rs.getString("org_code"));
                dto.setTitle(rs.getString("title"));
                dto.setStage(rs.getString("stage"));
                dto.setStatus(rs.getString("status"));
                dto.setBudget(rs.getLong("budget"));
                dto.setStartDate(rs.getDate("start_date"));
                dto.setEndDate(rs.getDate("end_date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs);
            DBUtil.close(ps);
        }

        if (dto == null) return null;

        // 부처명 조회
        List<String> ministryNames = new ArrayList<>();
        String sqlMin = "SELECT m.name FROM Project_Ministry pm JOIN Ministry m ON pm.ministry_code = m.ministry_code WHERE pm.project_code = ?";
        try (PreparedStatement psMin = conn.prepareStatement(sqlMin)) {
            psMin.setString(1, projectCode);
            try (ResultSet rsMin = psMin.executeQuery()) {
                while (rsMin.next()) {
                    ministryNames.add(rsMin.getString("name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dto.setMinistryName(String.join(", ", ministryNames));

        // 협약 기관 조회
        List<String> partnerNames = new ArrayList<>();
        String sqlPar = "SELECT po.name FROM Project_Partner_Org ppo JOIN Partner_Organization po ON ppo.partner_org_code = po.partner_org_code WHERE ppo.project_code = ?";
        try (PreparedStatement psPar = conn.prepareStatement(sqlPar)) {
            psPar.setString(1, projectCode);
            try (ResultSet rsPar = psPar.executeQuery()) {
                while (rsPar.next()) {
                    partnerNames.add(rsPar.getString("name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dto.setPartnerOrgName(String.join(", ", partnerNames));

        // 진행도 계산 (milestondao 메소드 사용)
        try {
            MilestoneDAO milestoneDAO = new MilestoneDAO();
            int total = milestoneDAO.countTotalMilestones(projectCode);
            int completed = milestoneDAO.countCompletedMilestones(projectCode);

            dto.setTotalMilestones(total);
            dto.setCompletedMilestones(completed);

        } catch (Exception e) {
            e.printStackTrace();
            dto.setTotalMilestones(0);
            dto.setCompletedMilestones(0);
        }

        return dto;
    }
}
