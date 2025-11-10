package userInterface;

import java.io.BufferedReader;
import java.io.IOException;

public class AuthUI {
    private BufferedReader br;
    private UI ui;

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

        // TODO: 실제 DB 연동 시 OrganizationDAO를 사용해서 검증하도록 변경
        System.out.println(">> 로그인 성공 (테스트용)\n");

        // ✅ 로그인 성공 시 onOrgLogin 호출 (projectUI 생성됨)
        ui.onOrgLogin(id);
    }

    // 회원가입
    public void signUp() throws IOException {
        System.out.println("===== 회원가입 =====");

        System.out.print("아이디: ");
        String id = br.readLine();

        System.out.print("비밀번호: ");
        String pw = br.readLine();

        System.out.print("이름: ");
        String name = br.readLine();

        System.out.println(">> 회원가입 완료 (테스트용)\n");
    }
}
