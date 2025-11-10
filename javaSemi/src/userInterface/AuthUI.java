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
    private OrganizationDAO orgDAO = new OrganizationDAOImpl(); // DAO 구현체 사용

    public AuthUI(BufferedReader br, UI ui) {
        this.br = br;
        this.ui = ui;
    }

    // 로그인
    public void signIn() throws IOException {
        System.out.println("===== 로그인 =====");

        System.out.print("아이디: ");
        String id = br.readLine();

        System.out.print("비밀번호: ");
        String pw = br.readLine();

        try {
            OrganizationDTO org = orgDAO.selectRecord(id); // findById 대신 selectRecord 사용

            if (org == null || !org.getOrgPwd().equals(pw)) {
                System.out.println("⚠️ 아이디가 존재하지 않거나, 비밀번호가 틀렸습니다.\n");
                return;
            }
            System.out.println("✅ 로그인 성공! " + org.getOrgName() + " 기관 환영합니다.\n");
            ui.onOrgLogin(org.getOrgId()); // 로그인 성공 후 기관 ID 전달

        } catch (SQLException e) {
            System.out.println("❌ 로그인 중 오류 발생: " + e.getMessage());
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
                System.out.println("⚠️ 이미 존재하는 아이디입니다. 다른 아이디를 사용하세요.\n");
                return; // 회원가입 중단
            }

            dto.setOrgId(id); // 사용 가능 ID 세팅

            System.out.print("비밀번호: ");
            dto.setOrgPwd(br.readLine());

            System.out.print("기관명: ");
            dto.setOrgName(br.readLine());

            System.out.print("기관 유형(대학/기업/공공기관 등): ");
            dto.setOrgType(br.readLine());

            System.out.print("사업자등록번호: ");
            dto.setBizRegNo(br.readLine());

            System.out.print("전화번호: ");
            dto.setOrgTel(br.readLine());

            System.out.print("이메일: ");
            dto.setOrgEmail(br.readLine());

            System.out.print("주소: ");
            dto.setOrgAddress(br.readLine());

            orgDAO.insertOrganization(dto);
            System.out.println("✅ 회원가입 완료!\n");

        } catch (SQLException e) {
            System.out.println("❌ 데이터베이스 오류: " + e.getMessage());
        }
    }


}
