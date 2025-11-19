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
        
        // 초기 메뉴 시작
        showInitialMenu();   
    }

    public void showInitialMenu() {
    	try {
    		 while (true) {
    	            System.out.print("""
    	                ===============================
    	                      국가 R & D 관리 프로그램
    	                ===============================
    	                1. 로그인
    	                2. 회원가입
    	                0. 종료
    	                ===============================
    	                """);
    				String input = (InputHandler.getOptionalMenuInput(br, "메뉴 선택 ▶ "));
    	            System.out.println();
    				System.out.println();
    				System.out.println();

    	            switch (input) {
    	                case "1" -> authUI.signIn();
    	                case "2" -> authUI.signUp();
    	                case "0" -> exit();
    	                default -> System.out.println("⚠️ 잘못된 입력입니다.\n");
    	            }
    	        }
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
       
    }

    // 로그인 성공 시
    public void onOrgLogin(String orgId) {
        try {
            OrganizationDTO loginOrg = organizationDAO.findById(orgId);

            if (loginOrg == null) {
                System.out.println("❌ 로그인 정보가 유효하지 않습니다.");
                return;
            }

            projectUI = new ProjectUI(br, this, loginOrg.getOrgCode());
            memberUI = new MemberUI(br, this, organizationDAO, loginOrg.getOrgId());
            researcherUI = new ResearcherUI(br, this, loginOrg.getOrgCode());

            showOrgMainMenu();

        } catch (SQLException e) {
            System.out.println("⚠️ 데이터베이스 오류: " + e.getMessage());
        } catch (Exception e) {
        	e.printStackTrace();
		}
    }

    public void showOrgMainMenu() {
    	try {
    		while (true) {
                System.out.print("""
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
				
                String input = (InputHandler.getOptionalMenuInput(br, "메뉴 선택 ▶ "));
    			System.out.println();
    			System.out.println();
    			System.out.println();
				
                switch (input) {
                    case "1" -> projectUI.chooseProject();
                    case "2" -> memberUI.manageMemberInformation();
                    case "3" -> researcherUI.manageResearcherInformation();
                    case "0" -> { return; }
                    case "00" -> exit();
                    default -> System.out.println("⚠️ 잘못된 입력입니다.\n");
                }
            }
    	} catch (IOException e) {
    		e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
		}
    }

    public void exit() {
        System.out.println("프로그램 종료");
        DBConn.close();
        System.exit(0);
    }
}
