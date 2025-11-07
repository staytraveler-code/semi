package userInterface;

import java.io.BufferedReader;
import java.io.IOException;

public class ProjectUI {
    private BufferedReader br;
    private UI ui;

    // 세부 관리 클래스 인스턴스
    private Project_FundUI projectFund = new Project_FundUI();
    private Project_MilestoneUI projectMilestone = new Project_MilestoneUI();
    private Project_ResearcherUI projectResearcher = new Project_ResearcherUI();
    private Project_PerformanceUI projectPerformance = new Project_PerformanceUI();
    private Project_PersonnelExpensesUI personnelExpensesUI = new Project_PersonnelExpensesUI();

    public ProjectUI(BufferedReader br, UI ui) {
        this.br = br;
        this.ui = ui;

        // 각 하위 UI에 BufferedReader 연결
        projectFund.setBufferedReader(br);
        projectMilestone.setBufferedReader(br);
        projectPerformance.setBufferedReader(br);
        personnelExpensesUI.setBufferedReader(br);
    }

    // ========================================
    //  과제 목록 관리
    // ========================================
    public void manageProject() throws IOException {
        while (true) {
            System.out.println("\n\n===================================");
            System.out.println("            과제 리스트");
            System.out.println("===================================");
            printProjectList();

            System.out.print("관리할 과제 선택 ▶ (0. 뒤로가기, 00. 종료) ");
            String input = br.readLine();

            switch (input) {
                case "1111", "1112", "1115" -> printProjectDetail(input);
                case "0" -> { return; }
                case "00" -> ui.exit();
                default -> System.out.println("\n⚠️ 잘못된 입력입니다.\n");
            }
        }
    }

    private void printProjectList() {
        System.out.println("""
            1111. 바보 과제 1차
            1112. 바보 과제 2차
            1115. 멍청이 과제 5차
        """);
    }

    // ========================================
    //  개별 과제 상세 메뉴
    // ========================================
    private void printProjectDetail(String projectId) throws IOException {
        while (true) {
            System.out.println("\n===================================");
            System.out.println("       [" + projectId + "] 과제 상세 관리");
            System.out.println("===================================");
            System.out.println("""
                1. 성과 관리
                2. 연구비 관리
                3. 인건비 관리
                4. 마일스톤 관리
                5. 연구원 관리
                -----------------------------------
                0. 뒤로가기     00. 종료
            """);

            System.out.print("메뉴 선택 ▶ ");
            String input = br.readLine();

            switch (input) {
                case "1" -> managePerformance();
                case "2" -> manageFund();
                case "3" -> managePersonnelCost();
                case "4" -> manageMilestone();
                case "5" -> manageProjectResearcher();
                case "0" -> { return; }
                case "00" -> ui.exit();
                default -> System.out.println("\n⚠️ 잘못된 입력입니다.\n");
            }
        }
    }

    // ========================================
    // 1. 성과 관리
    // ========================================
    private void managePerformance() throws IOException {
        while (true) {
            System.out.println("\n===== [성과 관리] =====");
            projectPerformance.printPerformanceList();

            System.out.println("""
                1. 성과 추가
                2. 성과 수정
                3. 성과 삭제
                ---------------------
                0. 뒤로가기   00. 종료
            """);
            System.out.print("메뉴 선택 ▶ ");

            String input = br.readLine();

            switch (input) {
                case "1" -> projectPerformance.addPerformance();
                case "2" -> projectPerformance.updatePerformance();
                case "3" -> projectPerformance.deletePerformance();
                case "0" -> { return; }
                case "00" -> ui.exit();
                default -> System.out.println("\n⚠️ 잘못된 입력입니다.\n");
            }
        }
    }

    // ========================================
    // 2. 연구비 관리
    // ========================================
    private void manageFund() throws IOException {
        while (true) {
            System.out.println("\n===== [연구비 관리] =====");
            projectFund.printFundUsageList();

            System.out.println("""
                1. 사용 내역 추가
                2. 사용 내역 수정
                3. 사용 내역 삭제
                ---------------------
                0. 뒤로가기   00. 종료
            """);
            System.out.print("메뉴 선택 ▶ ");

            String input = br.readLine();

            switch (input) {
                case "1" -> projectFund.addFundUsageList();
                case "2" -> projectFund.updateFundUsageList();
                case "3" -> projectFund.deleteFundUsageList();
                case "0" -> { return; }
                case "00" -> ui.exit();
                default -> System.out.println("\n⚠️ 잘못된 입력입니다.\n");
            }
        }
    }

    // ========================================
    // 3. 인건비 관리
    // ========================================
    private void managePersonnelCost() throws IOException {
        while (true) {
            System.out.println("\n===== [인건비 관리] =====");
            personnelExpensesUI.printPersonnelExpensesList();

            System.out.println("""
                1. 인건비 추가
                2. 인건비 수정
                3. 인건비 삭제
                ---------------------
                0. 뒤로가기   00. 종료
            """);
            System.out.print("메뉴 선택 ▶ ");

            String input = br.readLine();

            switch (input) {
                case "1" -> personnelExpensesUI.addPersonnelExpenses();
                case "2" -> personnelExpensesUI.updatePersonnelExpenses();
                case "3" -> personnelExpensesUI.deletePersonnelExpenses();
                case "0" -> { return; }
                case "00" -> ui.exit();
                default -> System.out.println("\n⚠️ 잘못된 입력입니다.\n");
            }
        }
    }

    // ========================================
    // 4. 마일스톤 관리
    // ========================================
    private void manageMilestone() throws IOException {
        while (true) {
            System.out.println("\n===== [마일스톤 관리] =====");
            projectMilestone.printMilestoneList();

            System.out.println("""
                1. 마일스톤 추가
                2. 마일스톤 수정
                3. 마일스톤 삭제
                ---------------------
                0. 뒤로가기   00. 종료
            """);
            System.out.print("메뉴 선택 ▶ ");

            String input = br.readLine();

            switch (input) {
                case "1" -> projectMilestone.addMilestone();
                case "2" -> projectMilestone.updateMilestone();
                case "3" -> projectMilestone.deleteMilestone();
                case "0" -> { return; }
                case "00" -> ui.exit();
                default -> System.out.println("\n⚠️ 잘못된 입력입니다.\n");
            }
        }
    }

    // ========================================
    // 5. 연구원 관리
    // ========================================
    private void manageProjectResearcher() throws IOException {
        while (true) {
            System.out.println("\n===== [연구원 관리] =====");
            projectResearcher.printProjectResearcher();

            System.out.println("""
                1. 연구원 추가
                2. 연구원 삭제
                ---------------------
                0. 뒤로가기   00. 종료
            """);
            System.out.print("메뉴 선택 ▶ ");

            String input = br.readLine();

            switch (input) {
                case "1" -> projectResearcher.addProjectResearcher();
                case "2" -> projectResearcher.deleteProjectResearcher();
                case "0" -> { return; }
                case "00" -> ui.exit();
                default -> System.out.println("\n⚠️ 잘못된 입력입니다.\n");
            }
        }
    }
}
