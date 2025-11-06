package semi.rd;

public class UI {

	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;

	public class UI {
	    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	    public UI() {
	        try {
	            ShowInitialMenu();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    // 초기 화면
	    private void ShowInitialMenu() throws IOException {
	        while (true) {
	            System.out.println("""
	                ===============================
	                      국가 R & D 관리 프로그램
	                ===============================
	                1. 로그인 (Sign In)
	                2. 회원가입 (Sign Up)
	                0. 종료 (Exit)
	                ===============================
	                """);
	            System.out.print("메뉴 선택 ▶ ");
	            String input = br.readLine();

	            switch (input) {
	                case "1" -> signIn();
	                case "2" -> signUp();
	                case "0" -> { return; }
	                default -> System.out.println("⚠️ 잘못된 입력입니다.\n");
	            }
	        }
	    }

	    // 로그인
	    private void signIn() throws IOException {
	        System.out.println("""
	            ===============================
	                       로그인
	            ===============================
	            """);

	        System.out.print("아이디: ");
	        String id = br.readLine();

	        System.out.print("비밀번호: ");
	        String pw = br.readLine();

	        System.out.println(">> 로그인 성공 (테스트용)\n");
	        OrgMainMenu();
	    }

	    // 회원가입
	    private void signUp() throws IOException {
	        System.out.println("""
	            ===============================
	                       회원가입
	            ===============================
	            """);

	        System.out.print("아이디: ");
	        String id = br.readLine();

	        System.out.print("비밀번호: ");
	        String pw = br.readLine();

	        System.out.print("이름: ");
	        String name = br.readLine();

	        System.out.println(">> 회원가입 완료 (로직 미구현)\n");
	    }

	    // 기관 메인 메뉴
	    private void OrgMainMenu() throws IOException {
	        while (true) {
	            System.out.println("""
	                ===============================
	                      국가 R & D 관리 프로그램
	                ===============================
	                1. 과제 관리
	                2. 회원 정보 관리
	                3. 연구원 정보 관리
	                0. 종료
	                ===============================
	                """);
	            System.out.print("메뉴 선택 ▶ ");
	            String input = br.readLine();

	            switch (input) {
	                case "1" -> ManageProject();
	                case "2" -> memberManagement();
	                case "3" -> ManageResearcherInformation();
	                case "0" -> {
	                    System.out.println("로그아웃합니다.\n");
	                    return;
	                }
	                default -> System.out.println("⚠️ 잘못된 입력입니다.\n");
	            }
	        }
	    }

	    // 과제 조회
	    private void ManageProject() throws IOException {
	        while (true) {
	            System.out.println("""
	                ===============================
	                      과제 리스트
	                ===============================
	                1111. 바보 과제 1차  
	                1112. 바보 과제 2차
	                1115. 멍청이 과제 5차
	                ===============================
	                """);
	            System.out.print("관리할 과제 선택 ▶ (종료 0, 뒤로가기 00) ");
	            String input = br.readLine();

	            switch (input) {
	                case "1111", "1112", "1115" -> PrintProjectDetail(input);
	                case "00" -> { return; } // 뒤로가기
	                case "0" -> {
	                    System.out.println("로그아웃합니다.\n");
	                    return;
	                }
	                default -> System.out.println("⚠️ 잘못된 입력입니다.\n");
	            }
	        }
	    }

	    private void PrintProjectList() { }

	    // 과제 상세
	    private void PrintProjectDetail(String projectId) throws IOException {
	        while (true) {
	            System.out.println("===== " + projectId + " 상세 =====");
	            System.out.println("1. 성과 관리 2. 연구비 관리 3. 인건비 관리 4. 마일스톤 관리 5. 연구원 관리 0. 뒤로가기");
	            System.out.print("메뉴 선택 ▶ ");
	            String input = br.readLine();

	            switch (input) {
	                case "1" -> ManagePerformance();
	                case "2" -> ManageFund();
	                case "3" -> ManagePersonnelCost();
	                case "4" -> ManageMilestone();
	                case "5" -> ManageResearcher();
	                case "0" -> { return; }
	                default -> System.out.println("⚠️ 잘못된 입력입니다.\n");
	            }
	        }
	    }

	    // 회원 정보 관리
	    private void memberManagement() throws IOException {
	        while (true) {
	            System.out.println("1. 정보 수정 2. 뒤로가기");
	            System.out.print("메뉴 선택 ▶ ");
	            String input = br.readLine();

	            switch (input) {
	                case "1" -> EditOrganizationInformation();
	                case "2" -> { return; }
	                case "0" -> { return; }
	                default -> System.out.println("⚠️ 잘못된 입력입니다.\n");
	            }
	        }
	    }

	    private void EditOrganizationInformation() throws IOException {
	        System.out.println("정보 수정 완료");
	        memberManagement();
	    }

	    // 연구원 정보 관리
	    private void ManageResearcherInformation() {
	        System.out.println("연구원 정보 관리 화면 (껍데기)");
	    }

	    // 과제 관리 기능 껍데기
	    private void ManagePerformance() { System.out.println("성과 관리 (껍데기)"); }
	    private void ManageFund() { System.out.println("연구비 관리 (껍데기)"); }
	    private void ManagePersonnelCost() { System.out.println("인건비 관리 (껍데기)"); }
	    private void ManageMilestone() { System.out.println("마일스톤 관리 (껍데기)"); }
	    private void ManageResearcher() { System.out.println("연구원 관리 (껍데기)"); }
	}


}
