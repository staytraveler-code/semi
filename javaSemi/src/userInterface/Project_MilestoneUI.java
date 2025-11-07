package userInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import db.milestone.MilestoneDAO;
import db.milestone.MilestoneDTO;

public class Project_MilestoneUI {
    private List<String> milestoneList = new ArrayList<>();
    private BufferedReader br;

    public Project_MilestoneUI() {
        milestoneList.add("중간 점검 - 완료");
        milestoneList.add("최종 점검 - 예정");
    }

    public void setBufferedReader(BufferedReader br) {
        this.br = br;
    }

    //미완
    public void printMilestoneList() { //마일스톤 전체 출력
         MilestoneDAO dao = new MilestoneDAO(); // DAO 객체 생성
         List<MilestoneDTO> list = dao.listMilestone(); // 조회 메서드 호출
         
         if (list.isEmpty()) {
             System.out.println("(등록된 마일스톤이 없습니다)");
         }

         System.out.println("===== 마일스톤 목록 =====");

         System.out.printf("%-8s %-20s %-50s %-20s %-20s %10s %n",
                    "코드", "목표", "내용", "계획완료일", "실제완료일", "상태");
          System.out.println("=".repeat(150));

            for (MilestoneDTO dto : list) {
                System.out.printf("%-8s %-20s %-50s %-20s %-20s %10s %n",
                        dto.getMileCode(),
                        dto.getName(),
                        dto.getDesc(),
                        dto.getPeDate(),
                        dto.getAeDate(),
                        dto.getStatus());
            }
            
            System.out.println("=".repeat(150));
    }

    public void addMilestone() throws IOException {
        System.out.print("추가할 마일스톤 입력 ▶ ");
        String input = br.readLine();
        milestoneList.add(input);
        System.out.println("✅ 마일스톤 추가 완료!\n");
    }

    public void updateMilestone() throws IOException {
        printMilestoneList();
        System.out.print("수정할 번호 입력 ▶ ");
        int idx = Integer.parseInt(br.readLine()) - 1;
        if (idx >= 0 && idx < milestoneList.size()) {
            System.out.print("새로운 내용 입력 ▶ ");
            String newValue = br.readLine();
            milestoneList.set(idx, newValue);
            System.out.println("✅ 수정 완료!\n");
        } else {
            System.out.println("⚠️ 잘못된 번호입니다.\n");
        }
    }

    public void deleteMilestone() throws IOException {
        printMilestoneList();
        System.out.print("삭제할 번호 입력 ▶ ");
        int idx = Integer.parseInt(br.readLine()) - 1;
        if (idx >= 0 && idx < milestoneList.size()) {
            milestoneList.remove(idx);
            System.out.println("✅ 삭제 완료!\n");
        } else {
            System.out.println("⚠️ 잘못된 번호입니다.\n");
        }
    }
}
