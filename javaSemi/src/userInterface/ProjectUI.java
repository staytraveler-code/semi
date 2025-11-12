package userInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import db.project.ProjectDAO;
import db.project.ProjectDTO;

public class ProjectUI {
    private BufferedReader br;
    private UI ui;
    private ProjectDAO projectDAO = new ProjectDAO();
    private String orgCode; // 로그인한 기관 코드

    // 세부 관리 클래스
    private Project_FundUI projectFund = new Project_FundUI();
    private Project_MilestoneUI projectMilestone = new Project_MilestoneUI();
    private Project_ResearcherUI projectResearcher = new Project_ResearcherUI();
    private Project_PerformanceUI projectPerformance = new Project_PerformanceUI();
    private Project_PersonnelExpensesUI personnelExpensesUI = new Project_PersonnelExpensesUI();

    public ProjectUI(BufferedReader br, UI ui, String orgCode) {
        this.br = br;
        this.ui = ui;
        this.orgCode = orgCode;

        projectFund.setBufferedReader(br);
        projectMilestone.setBufferedReader(br);
        projectPerformance.setBufferedReader(br);
        personnelExpensesUI.setBufferedReader(br);
        projectResearcher.setBufferedReader(br); // Project_ResearcherUI도 br 세팅
    }

    // 과제 목록 관리
    public void manageProject() throws IOException {
        while (true) {
            System.out.println("\n====================================================================================================================================");
            System.out.println("            과제 리스트");
            System.out.println("====================================================================================================================================");
            printProjectList(); // 기관 과제만 출력

            System.out.print("관리할 과제 코드 입력 ▶ (0. 뒤로가기, 00. 종료) ");
            String input = br.readLine();

            if ("0".equals(input)) return;
            if ("00".equals(input)) ui.exit();

            ProjectDTO selected = projectDAO.getProjectsByOrganization(orgCode).stream()
                    .filter(p -> p.getProjectCode().equalsIgnoreCase(input))
                    .findFirst()
                    .orElse(null);

            if (selected != null) {
                printProjectDetail(selected.getProjectCode());
            } else {
                System.out.println("\n⚠️ 존재하지 않는 과제 코드입니다.\n");
            }
        }
    }

    // 과제 목록 출력
    private void printProjectList() {
        List<ProjectDTO> projects = projectDAO.getProjectsByOrganization(orgCode);

        if (projects.isEmpty()) {
            System.out.println("⚠️ 등록된 과제가 없습니다.");
            // 잠시 정지
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return;
        }

        System.out.printf("%-10s │ %-30s │ %-10s │ %-10s │ %-10s │ %-10s%n",
                "과제코드", "과제명", "기관코드", "단계", "상태", "예산");
        System.out.println("────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");

        for (ProjectDTO p : projects) {
            System.out.printf("%-10s │ %-30s │ %-10s │ %-10s │ %-10s │ %,10d%n",
                    p.getProjectCode(),
                    truncate(p.getTitle(), 30),
                    p.getOrgCode(),
                    p.getStage(),
                    p.getStatus(),
                    p.getBudget());
        }
        System.out.println();
    }

    private String truncate(String text, int max) {
        return (text.length() > max) ? text.substring(0, max - 3) + "..." : text;
    }

    // 개별 과제 상세 메뉴
    private void printProjectDetail(String projectId) throws IOException {
        while (true) {
            System.out.println("\n==============================================================");
            System.out.println("     [" + projectId + "] 과제 상세 관리");
            System.out.println("==============================================================");
            System.out.println("""
                1. 성과 관리
                2. 연구비 관리
                3. 인건비 관리
                4. 마일스톤 관리
                5. 연구원 관리
            --------------------------------------------------------------
                0. 뒤로가기     00. 종료
            """);

            System.out.print("메뉴 선택 ▶ ");
            String input = br.readLine();
            System.out.println();

            switch (input) {
                case "1" -> managePerformance(projectId);
                case "2" -> manageFund(projectId);
                case "3" -> managePersonnelCost();
                case "4" -> manageMilestone(projectId);
                case "5" -> manageProjectResearcher(projectId);
                case "0" -> { return; }
                case "00" -> ui.exit();
                default -> System.out.println("\n⚠️ 잘못된 입력입니다.\n");
            }
        }
    }

    // 1. 성과 관리
    private void managePerformance(String projectId) throws IOException {
        while (true) {
        	 projectPerformance.setOrgCode(orgCode);
        	 projectPerformance.setProjectCode(projectId);
            projectPerformance.printPerformanceList();
            System.out.println("""
                1. 성과 추가
                2. 성과 수정
                3. 성과 삭제
                0. 뒤로가기
            """);
            System.out.print("선택 ▶ ");
            String input = br.readLine();

            switch (input) {
                case "1" -> projectPerformance.addPerformance();
                case "2" -> projectPerformance.updatePerformance();
                case "3" -> projectPerformance.deletePerformance();
                case "0" -> { return; }
                default -> System.out.println("⚠️ 잘못된 입력입니다.\n");
            }
        }
    }

    // 2. 연구비 관리
    private void manageFund(String projectId) throws IOException {
        projectFund.setProjectCode(projectId);
        while (true) {
            projectFund.printFundUsageList();
            System.out.println("""
                1. 연구비 사용 내역 추가
                2. 연구비 사용 내역 수정
                3. 연구비 사용 내역 삭제
                0. 뒤로가기
            """);
            System.out.print("선택 ▶ ");
            String input = br.readLine();

            switch (input) {
                case "1" -> projectFund.addFundUsage();
                case "2" -> projectFund.updateFundUsage();
                case "3" -> projectFund.deleteFundUsage();
                case "0" -> { return; }
                default -> System.out.println("⚠️ 잘못된 입력입니다.\n");
            }
        }
    }

    // 3. 인건비 관리
    private void managePersonnelCost() throws IOException {
        while (true) {
            personnelExpensesUI.printPersonnelExpensesList();
            System.out.println("""
                1. 인건비 추가
                2. 인건비 수정
                3. 인건비 삭제
                0. 뒤로가기
            """);
            System.out.print("선택 ▶ ");
            String input = br.readLine();

            switch (input) {
                case "1" -> personnelExpensesUI.addPersonnelExpenses();
                case "2" -> personnelExpensesUI.updatePersonnelExpenses();
                case "3" -> personnelExpensesUI.deletePersonnelExpenses();
                case "0" -> { return; }
                default -> System.out.println("⚠️ 잘못된 입력입니다.\n");
            }
        }
    }

    // 4. 마일스톤 관리
    private void manageMilestone(String projectId) throws IOException {
        projectMilestone.setProjectCode(projectId);
        while (true) {
            projectMilestone.printMilestoneList();
            System.out.println("""
                1. 마일스톤 추가
                2. 마일스톤 수정
                3. 마일스톤 삭제
                0. 뒤로가기
            """);
            System.out.print("선택 ▶ ");
            String input = br.readLine();

            switch (input) {
                case "1" -> projectMilestone.addMilestone();
                case "2" -> projectMilestone.updateMilestone();
                case "3" -> projectMilestone.deleteMilestone();
                case "0" -> { return; }
                default -> System.out.println("⚠️ 잘못된 입력입니다.\n");
            }
        }
    }

    // 5. 프로젝트 연구원 관리
    private void manageProjectResearcher(String projectId) throws IOException {
        // 프로젝트 코드 세팅
        projectResearcher.setProjectCode(projectId);

        // 프로젝트별 연구원 관리 UI 실행
        projectResearcher.manageProjectResearchers();
    }
}
