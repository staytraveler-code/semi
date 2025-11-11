package userInterface;

import db.researcher.ResearcherDAO;
import db.researcher.ResearcherDAOImpl;
import db.researcher.ResearcherDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Project_ResearcherUI {
    private BufferedReader br;
    private UI ui;
    private ResearcherDAO researcherDAO;
    private String projectCode; // 프로젝트별 관리

    public Project_ResearcherUI() {
        this.researcherDAO = new ResearcherDAOImpl();
    }

    public void setBufferedReader(BufferedReader br) {
        this.br = br;
    }

    public void setUI(UI ui) {
        this.ui = ui;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
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
            List<ResearcherDTO> list = researcherDAO.listResearchersByOrg(projectCode); // 프로젝트 코드 기준으로 필터링 필요

            if (list.isEmpty()) {
                System.out.println("⚠️ 해당 프로젝트에 배정된 연구원이 없습니다.\n");
                return;
            }

            System.out.println("프로젝트 연구원 목록:");
            System.out.printf("%-10s | %-10s | %-15s | %-15s | %-20s%n",
                    "연구원코드", "기관코드", "이름", "전화", "이메일");
            System.out.println("--------------------------------------------------------------------");

            for (ResearcherDTO dto : list) {
                System.out.printf("%-10s | %-10s | %-15s | %-15s | %-20s%n",
                        dto.getResearcherCode(),
                        dto.getOrgCode(),
                        dto.getName(),
                        dto.getTel(),
                        dto.getEmail());
            }
            System.out.println();

        } catch (SQLException e) {
            System.err.println("⚠️ 연구원 조회 오류: " + e.getMessage());
        }
    }

    private void addProjectResearcher() {
        try {
            ResearcherDTO dto = new ResearcherDTO();
            System.out.print("연구원 코드: ");
            dto.setResearcherCode(br.readLine());
            System.out.print("기관 코드: ");
            dto.setOrgCode(br.readLine());
            System.out.print("이름: ");
            dto.setName(br.readLine());
            System.out.print("전화: ");
            dto.setTel(br.readLine());
            System.out.print("이메일: ");
            dto.setEmail(br.readLine());

            researcherDAO.insertResearcherDAO(dto);
            System.out.println("✅ 연구원 추가 완료\n");

        } catch (IOException | SQLException e) {
            System.err.println("⚠️ 연구원 추가 오류: " + e.getMessage());
        }
    }

    private void deleteProjectResearcher() {
        try {
            System.out.print("삭제할 연구원 코드: ");
            String code = br.readLine();
            researcherDAO.deleteResearcherDAO(code);
            System.out.println("✅ 연구원 삭제 완료\n");
        } catch (IOException | SQLException e) {
            System.err.println("⚠️ 연구원 삭제 오류: " + e.getMessage());
        }
    }
}
