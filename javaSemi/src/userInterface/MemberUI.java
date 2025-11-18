package userInterface;

import db.organization.OrganizationDAO;
import db.organization.OrganizationDTO;

import java.io.BufferedReader;
import java.io.IOException;

public class MemberUI {
	private BufferedReader br;
	private UI ui;
	private OrganizationDAO organizationDAO;
	private String loggedMemberId;

	public MemberUI(BufferedReader br, UI ui, OrganizationDAO organizationDAO, String loggedMemberId) {
		this.br = br;
		this.ui = ui;
		this.organizationDAO = organizationDAO;
		this.loggedMemberId = loggedMemberId;
	}

	public void manageMemberInformation() throws IOException {
		while (true) {
			// 현재 로그인한 회원 정보 출력
			printLoggedMemberInfo();

			// 메뉴 출력
			System.out.println("1. 정보 수정 0. 뒤로가기 00. 종료");
			String input = InputHandler.getOptionalMenuInput(br, "메뉴 선택 ▶ ");

			switch (input) {
			case "1" -> editMemberInformation();
			case "0" -> {
				return;
			}
			case "00" -> ui.exit();
			default -> System.out.println("⚠️ 잘못된 입력입니다.\n");
			}
		}
	}

	// 로그인한 회원 정보 화면에 표시
	private void printLoggedMemberInfo() {
		try {
			OrganizationDTO dto = organizationDAO.selectRecord(loggedMemberId);
			if (dto == null) {
				System.out.println("❌ 회원 정보가 존재하지 않습니다.");
				return;
			}

			System.out.println("───────────────────────────[ 회원 정보 ]───────────────────────────────");
			System.out.println("ID\t  : " + dto.getOrgId());
			System.out.println("이름\t  : " + dto.getOrgName());
			System.out.println("기관유형\t  : " + dto.getOrgType());
			System.out.println("사업자등록번호 : " + dto.getBizRegNo());
			System.out.println("전화번호\t  : " + dto.getOrgTel());

			if (dto.getOrgEmail()==null) {
				System.out.println("이메일\t  : " + "없음");
			} else {
				System.out.println("이메일\t  : " + dto.getOrgEmail());
			}
			System.out.println("주소\t  : " + dto.getOrgAddress());
			System.out.println("─────────────────────────────────────────────────────────────────────");
			System.out.println();

		} catch (Exception e) {
			System.out.println("⚠️ 회원 정보 조회 실패: " + e.getMessage());
		}
	}

	private void editMemberInformation() throws IOException {
		try {
			OrganizationDTO dto = organizationDAO.selectRecord(loggedMemberId);
			if (dto == null) {
				System.out.println("❌ 회원 정보가 존재하지 않습니다.");
				return;
			}

			System.out.println(" === 회원 정보 수정 === (Enter: 기존값 유지, 00 : 입력 중단)");

			String input = InputHandler.getOptionalInput(br, "현재 이름: " + dto.getOrgName() + " ▶ ");
			if (!input.isBlank())
				dto.setOrgName(input);
			input = InputHandler.getOptionalInput(br, "현재 기관유형: " + dto.getOrgType() + " ▶ ");
			if (!input.isBlank())
				dto.setOrgType(input);
			input = InputHandler.getOptionalTelInput(br, "현재 전화번호: " + dto.getOrgTel() + " ▶ ");
			if (!input.isBlank())
				dto.setOrgTel(input);
			input = InputHandler.getOptionalInput(br, "현재 이메일: " + dto.getOrgEmail() + " ▶ ");
			if (!input.isBlank())
				dto.setOrgEmail(input);
			input = InputHandler.getOptionalInput(br, "현재 주소: " + dto.getOrgAddress() + " ▶ ");
			if (!input.isBlank())
				dto.setOrgAddress(input);

			organizationDAO.updateOrganization(dto);
			System.out.println("회원 정보 수정 완료 \n");

		} catch (InputCancelledException e) {
			System.out.println("❌ 입력을 중단하였습니다.");
		} catch (Exception e) {
			System.out.println("⚠️ 회원 정보 수정 실패: " + e.getMessage());
		}
	}
}