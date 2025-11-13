package userInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import db.researcher.ResearcherDAO;
import db.researcher.ResearcherDAOImpl;
import db.researcher.ResearcherDTO;
import db.researcher.role.ResearcherRoleDAO;
import db.researcher.role.ResearcherRoleDAOImpl;
import db.researcher.role.ResearcherRoleDTO;

public class Project_ResearcherUI {
    private BufferedReader br;
    private UI ui;
    private ResearcherDAO researcherDAO;
    private ResearcherRoleDAO roleDAO;
    private String projectCode; // 프로젝트별 관리
    private String orgCode;

    public Project_ResearcherUI() {
        this.researcherDAO = new ResearcherDAOImpl();
        this.roleDAO = new ResearcherRoleDAOImpl();
    }

    public void setBufferedReader(BufferedReader br) {
        this.br = br;
    }

    public void setUI(UI ui) {
        this.ui = ui;
    }

    public void setCode(String pCode, String oCode) {
        this.projectCode = pCode;
        this.orgCode = oCode;
    }

    // 프로젝트별 연구원 관리 실행
    public void manageProjectResearchers() throws IOException {
        while (true) {
            printProjectResearchers();

            System.out.println("""
                1. 연구원 추가
                2. 연구원 삭제
                0. 뒤로가기
            """);
            System.out.print("선택 ▶ ");
            String input = br.readLine();

            switch (input) {
                case "1" -> addProjectResearcher();
                case "2" -> deleteProjectResearcher();
                case "0" -> { return; }
                default -> System.out.println("⚠️ 잘못된 입력입니다.\n");
            }
        }
    }

    private void printProjectResearchers() {
        try {
            List<ResearcherRoleDTO> list = roleDAO.listRole(projectCode); // 프로젝트 코드 기준으로 필터링 필요

            if (list.isEmpty()) {
                System.out.println("⚠️ 해당 프로젝트에 배정된 연구원이 없습니다.\n");
                return;
            }

            System.out.println("프로젝트 연구원 목록:");
            System.out.printf("%-10s | %-10s | %-15s | %-15s | %-20s%n",
                    "프로젝트코드", "연구원코드", "역할", "참여시작일", "참여종료일");
            System.out.println("-------------------------------------------------------------------------------------------------------------");

            for (ResearcherRoleDTO dto : list) {
                System.out.printf("%-16s | %-15s | %-12s | %-20s | %-20s%n",
                        dto.getProjectCode(),
                        dto.getResearcherCode(),
                        dto.getRole(),
                        dto.getStartDate().substring(0, 10),
                        dto.getEndDate().substring(0, 10));
            }
            System.out.println();

        } catch (SQLException e) {
            System.err.println("⚠️ 연구원 조회 오류: " + e.getMessage());
        }
    }

    private void addProjectResearcher() {
        try {
            ResearcherRoleDTO dto = new ResearcherRoleDTO();
            System.out.print("연구원 코드 : ");
            String rCode = br.readLine();
            
            if(!researcherDAO.isOrgIncludeRes(orgCode, rCode)) {
            	System.out.println("⚠️ 당신의 기관에 소속된 연구원이 아닙니다.\n");
            	return;
            }
            
            dto.setProjectCode(projectCode);
            dto.setResearcherCode(rCode);
            
            System.out.print("역할 : ");
            dto.setRole(br.readLine());
            System.out.print("참여 시작 일자(YYYY-MM-DD) : ");
            dto.setStartDate(br.readLine());
            System.out.print("참여 종료 일자(YYYY-MM-DD) : ");
            dto.setEndDate(br.readLine());

            roleDAO.insertResearcherRoleDAO(dto);
            System.out.println("✅ 연구원 추가 완료\n");

        } catch (IOException | SQLException e) {
            System.err.println("⚠️ 연구원 추가 오류: " + e.getMessage());
        }
    }

    private void deleteProjectResearcher() {
        try {
            System.out.print("삭제할 연구원 코드: ");
            String rCode = br.readLine();
            
            if(!roleDAO.isProjectIncludeRes(projectCode, rCode)) {
            	System.out.println("⚠️ 당신의 기관에 소속된 연구원이 아닙니다.\n");
            	return;
            }
            
            roleDAO.deleteResearcherRoleDAO(projectCode, rCode);
            
            System.out.println("✅ 연구원 삭제 완료\n");
        } catch (IOException | SQLException e) {
            System.err.println("⚠️ 연구원 삭제 오류: " + e.getMessage());
        }
    }
}
