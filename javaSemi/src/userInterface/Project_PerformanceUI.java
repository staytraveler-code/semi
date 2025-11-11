package userInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import db.performance.management.PerformanceManagementDAO;
import db.performance.management.PerformanceManagementDTO;

public class Project_PerformanceUI {
    private List<String> performanceList = new ArrayList<>();
    private PerformanceManagementDAO dao = new PerformanceManagementDAO(); 
//    private PerformanceManagementDTO dto = new PerformanceManagementDTO(); 
    
    private BufferedReader br;
    private String orgCode;
    private String projectCode;
    public void setBufferedReader(BufferedReader br) {
    	this.br = br;
    }
    
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
    
    
    public void printPerformanceList() {
        System.out.println("===== 성과 목록 =====");

        // DAO에서 해당 기관 + 과제의 성과 목록 가져오기
        List<PerformanceManagementDTO> list = dao.performanceList(orgCode, projectCode);

        if (list.isEmpty()) {
            System.out.println("(등록된 성과가 없습니다)");
        } else {
            for (int i = 0; i < list.size(); i++) {
                PerformanceManagementDTO dto = list.get(i);
                System.out.printf("%d. [%s] %s | %s | %s | %s%n",
                        i + 1,
                        dto.getPerfCode(),
                        dto.getName(),
                        dto.getCategory(),
                        dto.getpDate(),
                        dto.getMemo());
            }
        }
        System.out.println("=====================\n");
    }
    
    
//    public Project_PerformanceUI() {
//        performanceList.add("논문 게재 - SCI급 저널");
//        performanceList.add("특허 출원 - 1건");
//        performanceList.add("기술이전 - 2건");
//    }
//

//    public void printPerformanceList() {
//        System.out.println("===== 성과 목록 =====");
//        if (performanceList.isEmpty()) {
//            System.out.println("(등록된 성과가 없습니다)");
//        } else {
//            for (int i = 0; i < performanceList.size(); i++) {
//                System.out.printf("%d. %s%n", i + 1, performanceList.get(i));
//            }
//        }
//        System.out.println("=====================\n");
//    }

    public void addPerformance() throws IOException {
        System.out.print("추가할 성과 입력 ▶ ");
        String input = br.readLine();
        performanceList.add(input);
        System.out.println("✅ 성과 추가 완료!\n");
    }

    public void updatePerformance() throws IOException {
//        printPerformanceList();
        System.out.print("수정할 번호 입력 ▶ ");
        int idx = Integer.parseInt(br.readLine()) - 1;
        if (idx >= 0 && idx < performanceList.size()) {
            System.out.print("새로운 내용 입력 ▶ ");
            String newValue = br.readLine();
            performanceList.set(idx, newValue);
            System.out.println("✅ 수정 완료!\n");
        } else {
            System.out.println("⚠️ 잘못된 번호입니다.\n");
        }
    }

    public void deletePerformance() throws IOException {
//        printPerformanceList();
        System.out.print("삭제할 번호 입력 ▶ ");
        int idx = Integer.parseInt(br.readLine()) - 1;
        if (idx >= 0 && idx < performanceList.size()) {
            performanceList.remove(idx);
            System.out.println("✅ 삭제 완료!\n");
        } else {
            System.out.println("⚠️ 잘못된 번호입니다.\n");
        }
    }
}
