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
	private OrganizationDAO orgDAO;

	public AuthUI(BufferedReader br, UI ui) {
		this.br = br;
		this.ui = ui;
		this.orgDAO = new OrganizationDAOImpl();
	}

	// 로그인
	public void signIn() throws IOException {
		System.out.println("===== 로그인 =====");

		try {
			// 아이디 공백 입력 방지
			String id;
			while (true) {
				System.out.print("아이디 : ");
				id = br.readLine();
				if (id.isBlank()) {
					System.out.println("⦁ 아이디는 필수 입력 사항입니다.");
					continue;
				}
				break;
			}

			// 비밀번호
			String pw;
			while (true) {
				System.out.print("비밀번호 : ");
				pw = br.readLine();
				if (pw.isBlank()) {
					System.out.println("⦁ 비밀번호를 입력해주세요.");
					continue;
				}
				break;
			}

			// DB조회
			OrganizationDTO org = orgDAO.selectRecord(id);
			if (org == null || !org.getOrgPwd().equals(pw)) {
			    System.out.println("⦁ 아이디 또는 비밀번호가 잘못되었습니다.\n");
		
			    return; 
			}
		
			// 로그인 성공
			System.out.println("로그인 성공! 🎉");
			System.out.println("기관명 ▶ " + org.getOrgName() + "\n");

			ui.onOrgLogin(org.getOrgId());

		} catch (SQLException e) {
			System.err.println("❌ 로그인 처리 중 DB 오류: " + e.getMessage());
		}
	}
	

	// 회원가입
	public void signUp() throws IOException {
		System.out.println("===== 회원가입 =====");
		
		
		// 아이디 입력 + 중복체크
		try {
			OrganizationDTO dto = new OrganizationDTO();
			
			String id = InputHandler.getRequiredInput(br, "아이디 ▶  ");
            if (orgDAO.selectRecord(id) != null) {
                System.out.println("⦁ 이미 사용 중인 아이디입니다.\n");
                return;           
            }
            
            dto.setOrgId(id);
            
            String pw = InputHandler.getRequiredInput(br, "비밀번호 ▶ ") ;
            dto.setOrgPwd(pw);
            dto.setOrgName(InputHandler.getRequiredInput(br, "기관명 ▶ "));
            dto.setOrgType(InputHandler.getRequiredInput(br, "기관 유형(대학/기업/공공기관 등) ▶ "));
            
            // 사업자번호 중복 체크
            String bizRegNo;
            while (true) {
                bizRegNo = InputHandler.getRequiredBizRegInput(br, "사업자등록번호(000-00-00000) ▶ ");

                if (orgDAO.isBizRegNoExists(bizRegNo)) {
                    System.out.println("⚠️ 이미 등록된 사업자번호입니다.\n");
                } else break;
            }
            dto.setBizRegNo(bizRegNo);
            dto.setOrgTel(InputHandler.getRequiredTelInput(br, "전화번호(010-0000-0000) ▶ "));
            dto.setOrgEmail(InputHandler.getRequiredInput(br, "이메일 ▶ "));
            dto.setOrgAddress(InputHandler.getRequiredInput(br, "주소 ▶ "));
            orgDAO.insertOrganization(dto);


		} catch (SQLException e) {
			System.out.println("❌ 회원가입 DB 오류: " + e.getMessage());
		}
	}

}
