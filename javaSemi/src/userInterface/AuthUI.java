package userInterface;

import db.organization.OrganizationDAO;
import db.organization.OrganizationDTO;
import db.organization.OrganizationDAOImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

public class AuthUI {
    private BufferedReader br;
    private UI ui;
    private OrganizationDAO orgDAO = new OrganizationDAOImpl();

    public AuthUI(BufferedReader br, UI ui) {
        this.br = br;
        this.ui = ui;
    }

    // 로그인
    public void signIn() throws IOException {
        System.out.println("===== 로그인 =====");
        
        try {
        	System.out.print("로그인 : ");
        	String id = br.readLine();
        	
        	System.out.print("비밀번호 : ");
        	String pw = br.readLine();
        	
        	// DB조회
        	OrganizationDTO org = orgDAO.selectRecord(id);
        	
        	if(org == null || !org.getOrgPwd().equals(pw)) {
        		System.out.println(" ⦁ 아이디 또는 비밀번호가 잘못 되었습니다. 아이디와 비밀번호를 정확히 입력해 주세요.");
        		return;
        	}
        	// 로그인 성공
        
            System.out.println("[기관명] ▶ " + org.getOrgName());
            
            ui.onOrgLogin(org.getOrgId());// 로그인 성공 후 기관 ID 전달
            
            
        }catch (IOException e) {
        	 System.out.println("입력 중 오류 발생: " + e.getMessage());
		}catch (SQLException e) {
			 System.out.println("로그인 처리 중 DB 오류 발생: " + e.getMessage());
		}
    }

 

    // 회원가입
    public void signUp() throws IOException {
        System.out.println("===== 회원가입 =====");
        OrganizationDTO dto = new OrganizationDTO();

        System.out.print("아이디: ");
        String id = br.readLine();

        try {
            // ID 중복 체크
            OrganizationDTO existing = orgDAO.selectRecord(id);
            if (existing != null) {
                System.out.println("⦁ 아이디 : 사용할 수 없는 아이디입니다. 다른 아이디를 입력해주세요. \n");
                return;
            }

            dto.setOrgId(id); // 사용 가능 ID 세팅
            
            // 비밀번호 필수 입력 체크
       
            String pw;
            while (true) {
                System.out.print("비밀번호: ");
                pw = br.readLine(); // 먼저 입력 받기

                if (pw == null || pw.trim().isEmpty()) {
                    System.out.println("⦁ 비밀번호는 필수 정보입니다.");
                } else {
                    dto.setOrgPwd(pw); // 정상 입력이면 DTO에 저장
                    break;
                }
            }

            System.out.print("기관명: ");
            dto.setOrgName(br.readLine());

            System.out.print("기관 유형(대학/기업/공공기관 등): ");
            dto.setOrgType(br.readLine());
            
            String bizRegNo;

            while (true) {
            System.out.print("사업자등록번호: "); // 중복체크
            bizRegNo = br.readLine();
            
            if (!bizRegNo.matches("[0-9-]+")) {
                System.out.println("⦁ 숫자만 입력 가능합니다.");
                continue;
            }
            
            if(orgDAO.isBizRegNoExists(bizRegNo)) {
            	System.out.println("⦁ 이미 등록된 사업자 번호입니다. ");
            	continue; 
          
            }
            break;
            
            }
            dto.setBizRegNo(bizRegNo);
            

            System.out.print("전화번호: ");
            dto.setOrgTel(br.readLine());

            System.out.print("이메일: ");
            dto.setOrgEmail(br.readLine());

            System.out.print("주소: ");
            dto.setOrgAddress(br.readLine());

            orgDAO.insertOrganization(dto);
            System.out.println("⦁ 회원가입이 완료 되었습니다. \n");

        } catch (SQLException e) {
            System.out.println("❌ 데이터베이스 오류: " + e.getMessage());
        }
    }


}
