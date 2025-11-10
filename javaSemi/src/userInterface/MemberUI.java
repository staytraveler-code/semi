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
            System.out.println("1. 정보 수정 0. 뒤로가기 00. 종료");
            System.out.print("메뉴 선택 ▶ ");
            String input = br.readLine();

            switch (input) {
                case "1" -> editMemberInformation();
                case "0" -> { return; }
                case "00" -> ui.exit();
                default -> System.out.println("⚠️ 잘못된 입력입니다.\n");
            }
        }
    }

    private void editMemberInformation() throws IOException {
        try {
            OrganizationDTO dto = organizationDAO.selectRecord(loggedMemberId);
            if (dto == null) {
                System.out.println("❌ 회원 정보가 존재하지 않습니다.");
                return;
            }

            System.out.print("이름 ▶ ");
            String newName = br.readLine();

            System.out.print("전화번호 ▶ ");
            String newTel = br.readLine();

            System.out.print("이메일 ▶ ");
            String newEmail = br.readLine();

            System.out.print("주소 ▶ ");
            String newAddress = br.readLine();

            dto.setOrgName(newName);
            dto.setOrgTel(newTel);
            dto.setOrgEmail(newEmail);
            dto.setOrgAddress(newAddress);

            organizationDAO.updateOrganization(dto);
            System.out.println("✅ 회원 정보 수정 완료");

        } catch (Exception e) {
            System.out.println("⚠️ 회원 정보 수정 실패: " + e.getMessage());
        }
    }
}
