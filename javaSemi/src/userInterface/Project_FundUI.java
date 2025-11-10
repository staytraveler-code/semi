package userInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Project_FundUI {
    private List<String> fundList = new ArrayList<>();
    private BufferedReader br;

    public Project_FundUI() {
        fundList.add("장비 구입 - 1,000,000원");
        fundList.add("재료비 - 500,000원");
        fundList.add("외주 용역 - 2,000,000원");
    }

    public void setBufferedReader(BufferedReader br) {
        this.br = br;
    }

    public void printFundUsageList() {
        System.out.println("===== 연구비 사용 내역 =====");
        if (fundList.isEmpty()) {
            System.out.println("(등록된 연구비 사용 내역이 없습니다)");
        } else {
            for (int i = 0; i < fundList.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, fundList.get(i));
            }
        }
        System.out.println("===========================\n");
    }

    public void addFundUsageList() throws IOException {
        System.out.print("새로운 사용 내역 입력 ▶ ");
        String input = br.readLine();
        fundList.add(input);
        System.out.println("✅ 연구비 사용 내역 추가 완료!\n");
    }

    public void updateFundUsageList() throws IOException {
        printFundUsageList();
        System.out.print("수정할 번호 입력 ▶ ");
        int idx = Integer.parseInt(br.readLine()) - 1;
        if (idx >= 0 && idx < fundList.size()) {
            System.out.print("새로운 내용 입력 ▶ ");
            String newContent = br.readLine();
            fundList.set(idx, newContent);
            System.out.println("✅ 수정 완료!\n");
        } else {
            System.out.println("⚠️ 잘못된 번호입니다.\n");
        }
    }

    public void deleteFundUsageList() throws IOException {
        printFundUsageList();
        System.out.print("삭제할 번호 입력 ▶ ");
        int idx = Integer.parseInt(br.readLine()) - 1;
        if (idx >= 0 && idx < fundList.size()) {
            fundList.remove(idx);
            System.out.println("✅ 삭제 완료!\n");
        } else {
            System.out.println("⚠️ 잘못된 번호입니다.\n");
        }
    }
}
