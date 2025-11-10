package userInterface;

import db.organization.OrganizationDAO;
import db.organization.OrganizationDAOImpl;
import db.organization.OrganizationDTO;
import db.util.DBConn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class UI {
    private BufferedReader br;
    private AuthUI authUI;
    private ProjectUI projectUI;
    private MemberUI memberUI;
    private ResearcherUI researcherUI;

    private OrganizationDAO organizationDAO;

    public UI() {
        br = new BufferedReader(new InputStreamReader(System.in));
        organizationDAO = new OrganizationDAOImpl();
        authUI = new AuthUI(br, this);
        researcherUI = new ResearcherUI(br, this);
        
        // 초기 메뉴 호출
        try {
            showInitialMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showInitialMenu() throws IOException {
        while (true) {
            System.out.println("""
                ===============================
                      국가 R & D 관리 프로그램
                ===============================
                1. 로그인
                2. 회원가입
                0. 종료
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

    public void onOrgLogin(String orgId) throws IOException {
        try {
            OrganizationDTO loginOrg = organizationDAO.findById(orgId);

            if (loginOrg == null) {
                System.out.println("❌ 로그인 정보가 유효하지 않습니다.");
                return;
            }

            projectUI = new ProjectUI(br, this, loginOrg.getOrgCode());
            memberUI = new MemberUI(br, this, organizationDAO, loginOrg.getOrgId());
            System.out.println("\n✅ 로그인 성공: " + loginOrg.getOrgName() + " (" + loginOrg.getOrgCode() + ")");
            showOrgMainMenu();

        } catch (SQLException e) {
            System.out.println("⚠️ 데이터베이스 오류: " + e.getMessage());
        }
    }

    public void showOrgMainMenu() throws IOException {
        while (true) {
            System.out.println("""
                ===============================
                      기관 메인 메뉴
                ===============================
                1. 과제 관리
                2. 회원 정보 관리
                3. 연구원 정보 관리
                0. 로그아웃
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

    public void exit() {
        System.out.println("프로그램 종료");
        DBConn.close();
        System.exit(0);
    }
}
