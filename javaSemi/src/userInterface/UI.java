package userInterface;

import db.util.DBConn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UI {
    private BufferedReader br;
    private AuthUI authUI;
    private ProjectUI projectUI;
    private MemberUI memberUI;
    private ResearcherUI researcherUI;

    public UI() {
        br = new BufferedReader(new InputStreamReader(System.in));
        authUI = new AuthUI(br, this);
        memberUI = new MemberUI(br, this);
        researcherUI = new ResearcherUI(br, this);

        try {
            showInitialMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 초기 화면
    public void showInitialMenu() throws IOException {
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
                case "1" -> authUI.signIn();
                case "2" -> authUI.signUp();
                case "0" -> exit();
                default -> System.out.println("⚠️ 잘못된 입력입니다.\n");
            }
        }
    }

    // 로그인 성공 시 호출 (기관 코드 전달)
    public void onOrgLogin(String orgCode) throws IOException {
        projectUI = new ProjectUI(br, this, orgCode); // 로그인한 기관 코드로 ProjectUI 생성
        showOrgMainMenu();
    }

    // 기관 메인 메뉴
    public void showOrgMainMenu() throws IOException {
        while (true) {
            System.out.println("""
                ===============================
                      기관 메인 메뉴
                ===============================
                1. 과제 관리
                2. 회원 정보 관리
                3. 연구원 정보 관리
                0. 뒤로가기
                00. 종료
                ===============================
                """);
            System.out.print("메뉴 선택 ▶ ");
            String input = br.readLine();

            switch (input) {
                case "1" -> projectUI.manageProject();
                case "2" -> memberUI.manageMemberInformation();
                case "3" -> researcherUI.manageResearcherInformation();
                case "0" -> { return; }
                case "00" -> exit();
                default -> System.out.println("⚠️ 잘못된 입력입니다.\n");
            }
        }
    }

    // 프로그램 종료
    public void exit() {
        System.out.println("프로그램 종료");
        DBConn.close();
        System.exit(0);
    }
}
