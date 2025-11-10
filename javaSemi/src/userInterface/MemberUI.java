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

    // 로그인한 회원 정보 화면에 표시
    private void printLoggedMemberInfo() {
        try {
            OrganizationDTO dto = organizationDAO.selectRecord(loggedMemberId);
            if (dto == null) {
                System.out.println("❌ 회원 정보가 존재하지 않습니다.");
                return;
            }

            System.out.println("===== 회원 정보 =====");
            System.out.println("ID       : " + dto.getOrgId());
            System.out.println("이름     : " + dto.getOrgName());
            System.out.println("기관 유형 : " + dto.getOrgType());
            System.out.println("사업자번호: " + dto.getBizRegNo());
            System.out.println("전화번호 : " + dto.getOrgTel());
            System.out.println("이메일   : " + dto.getOrgEmail());
            System.out.println("주소     : " + dto.getOrgAddress());
            System.out.println("====================\n");

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

            System.out.println("회원 정보 수정 (Enter: 기존값 유지)");
            System.out.println("------------------------------");

            System.out.println("현재 이름: " + dto.getOrgName());
            System.out.print("이름 ▶ ");
            String newName = br.readLine();
            if (!newName.isBlank()) dto.setOrgName(newName);

            System.out.println("현재 전화번호: " + dto.getOrgTel());
            System.out.print("전화번호 ▶ ");
            String newTel = br.readLine();
            if (!newTel.isBlank()) dto.setOrgTel(newTel);

            System.out.println("현재 이메일: " + dto.getOrgEmail());
            System.out.print("이메일 ▶ ");
            String newEmail = br.readLine();
            if (!newEmail.isBlank()) dto.setOrgEmail(newEmail);

            System.out.println("현재 주소: " + dto.getOrgAddress());
            System.out.print("주소 ▶ ");
            String newAddress = br.readLine();
            if (!newAddress.isBlank()) dto.setOrgAddress(newAddress);

            organizationDAO.updateOrganization(dto);
            System.out.println("✅ 회원 정보 수정 완료\n");

        } catch (Exception e) {
            System.out.println("⚠️ 회원 정보 수정 실패: " + e.getMessage());
        }
    }
}
