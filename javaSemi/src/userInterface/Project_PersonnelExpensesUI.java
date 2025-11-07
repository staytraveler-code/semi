package userInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Project_PersonnelExpensesUI {
    private List<String> personnelList = new ArrayList<>();
    private BufferedReader br;

    public Project_PersonnelExpensesUI() {
        personnelList.add("김바보 - 3,000,000원");
        personnelList.add("이멍청 - 2,500,000원");
        personnelList.add("박천재 - 4,000,000원");
    }

    public void setBufferedReader(BufferedReader br) {
        this.br = br;
    }

    public void printPersonnelExpensesList() {
        System.out.println("===== 인건비 지급 내역 =====");
        if (personnelList.isEmpty()) {
            System.out.println("(등록된 인건비 내역이 없습니다)");
        } else {
            for (int i = 0; i < personnelList.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, personnelList.get(i));
            }
        }
        System.out.println("===========================\n");
    }

    public void addPersonnelExpenses() throws IOException {
        System.out.print("추가할 인건비 내역 입력 ▶ ");
        String input = br.readLine();
        personnelList.add(input);
        System.out.println("✅ 인건비 내역 추가 완료!\n");
    }

    public void updatePersonnelExpenses() throws IOException {
        printPersonnelExpensesList();
        System.out.print("수정할 번호 입력 ▶ ");
        int idx = Integer.parseInt(br.readLine()) - 1;
        if (idx >= 0 && idx < personnelList.size()) {
            System.out.print("새로운 내용 입력 ▶ ");
            String newValue = br.readLine();
            personnelList.set(idx, newValue);
            System.out.println("✅ 수정 완료!\n");
        } else {
            System.out.println("⚠️ 잘못된 번호입니다.\n");
        }
    }

    public void deletePersonnelExpenses() throws IOException {
        printPersonnelExpensesList();
        System.out.print("삭제할 번호 입력 ▶ ");
        int idx = Integer.parseInt(br.readLine()) - 1;
        if (idx >= 0 && idx < personnelList.size()) {
            personnelList.remove(idx);
            System.out.println("✅ 삭제 완료!\n");
        } else {
            System.out.println("⚠️ 잘못된 번호입니다.\n");
        }
    }
}
