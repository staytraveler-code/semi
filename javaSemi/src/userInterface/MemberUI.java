package userInterface;

import java.io.BufferedReader;
import java.io.IOException;

public class MemberUI {
    private BufferedReader br;
    private UI ui;

    public MemberUI(BufferedReader br, UI ui) {
        this.br = br;
        this.ui = ui;
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

    private void editMemberInformation() {
        System.out.println("회원 정보 수정 완료 (테스트용)");
    }
}
