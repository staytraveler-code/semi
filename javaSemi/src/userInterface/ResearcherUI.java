package userInterface;

import java.io.BufferedReader;
import java.io.IOException;

public class ResearcherUI {
    private BufferedReader br;
    private UI ui;

    public ResearcherUI(BufferedReader br, UI ui) {
        this.br = br;
        this.ui = ui;
    }

    public void manageResearcherInformation() throws IOException {
        while (true) {
            System.out.println("1. 연구원 수정 0. 뒤로가기 00. 종료");
            printResearcherList();
            System.out.print("메뉴 선택 ▶ ");

            String input = br.readLine();

            switch (input) {
                case "1" -> editResearcherInformation();
                case "0" -> { return; }
                case "00" -> ui.exit();
                default -> System.out.println("⚠️ 잘못된 입력입니다.\n");
            }
        }
    }

    private void editResearcherInformation() {
        System.out.println("연구원 정보 수정 완료 (테스트용)");
    }

    private void printResearcherList() {
        System.out.println("1111 김삿갓  2222 강아지");
    }
}
