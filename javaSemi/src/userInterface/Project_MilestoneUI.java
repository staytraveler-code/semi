package userInterface;

import db.milestone.MilestoneDAO;
import db.milestone.MilestoneDTO;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class Project_MilestoneUI {
    private BufferedReader br;
    private MilestoneDAO milestoneDAO = new MilestoneDAO();
    private String projectCode; // 현재 프로젝트 코드

    public void setBufferedReader(BufferedReader br) { this.br = br; }
    public void setProjectCode(String projectCode) { this.projectCode = projectCode; }

    // 목록 출력
    public void printMilestoneList() {
        System.out.println("===== 마일스톤 목록 =============================================================================================================================");
        List<MilestoneDTO> list = milestoneDAO.listMilestoneByProject(projectCode);
        if (list.isEmpty()) System.out.println("(등록된 마일스톤이 없습니다)");

        for (MilestoneDTO dto : list) {
            System.out.printf("코드: %s | 목표: %s | 내용: %s | 계획완료: %s | 실제완료: %s | 상태: %s%n",
                    dto.getMileCode(), dto.getName(), dto.getDesc(),
                    dto.getPeDate(), dto.getAeDate(), dto.getStatus());
        }
        System.out.println("================================================================================================================================================\n");
    }

    // 추가
    public void addMilestone() throws IOException {
        MilestoneDTO dto = new MilestoneDTO();
        dto.setpCode(projectCode);

        System.out.print("마일스톤 코드 ▶ "); dto.setMileCode(br.readLine());
        System.out.print("목표 ▶ "); dto.setName(br.readLine());
        System.out.print("내용 ▶ "); dto.setDesc(br.readLine());
        dto.setPeDate(readDate("계획완료일 (YYYY-MM-DD) ▶ "));
        dto.setAeDate(readDate("실제완료일 (YYYY-MM-DD) ▶ "));
        System.out.print("상태 ▶ "); dto.setStatus(br.readLine());

        int result = milestoneDAO.insertMilestone(dto);
        System.out.println(result > 0 ? "✅ 마일스톤 추가 완료!\n" : "⚠️ 추가 실패\n");
    }

    // 수정
    public void updateMilestone() throws IOException {
        List<MilestoneDTO> list = milestoneDAO.listMilestoneByProject(projectCode);
        if (list.isEmpty()) { System.out.println("⚠️ 등록된 마일스톤이 없습니다."); return; }

        System.out.print("수정할 마일스톤 코드 입력 ▶ ");
        String code = br.readLine();
        MilestoneDTO target = null;

        for (MilestoneDTO dto : list) {
            if (dto.getMileCode().equals(code)) { target = dto; break; }
        }
        if (target == null) { System.out.println("⚠️ 해당 마일스톤이 없습니다."); return; }

        System.out.print("목표(" + target.getName() + ") ▶ ");
        String input = br.readLine(); if (!input.isBlank()) target.setName(input);

        System.out.print("내용(" + target.getDesc() + ") ▶ ");
        input = br.readLine(); if (!input.isBlank()) target.setDesc(input);

        target.setPeDate(readDate("계획완료일(" + target.getPeDate() + ") ▶ "));
        target.setAeDate(readDate("실제완료일(" + target.getAeDate() + ") ▶ "));

        System.out.print("상태(" + target.getStatus() + ") ▶ ");
        input = br.readLine(); if (!input.isBlank()) target.setStatus(input);

        int result = milestoneDAO.updateMilestone(target);
        System.out.println(result > 0 ? "✅ 마일스톤 수정 완료!\n" : "⚠️ 수정 실패\n");
    }

    // 삭제
    public void deleteMilestone() throws IOException {
        System.out.print("삭제할 마일스톤 코드 입력 ▶ ");
        String code = br.readLine();
        int result = milestoneDAO.deleteMilestone(code, projectCode);
        System.out.println(result > 0 ? "✅ 마일스톤 삭제 완료!\n" : "⚠️ 해당 마일스톤이 없습니다.\n");
    }

    // 날짜 입력 검증
    private String readDate(String prompt) throws IOException {
        while (true) {
            System.out.print(prompt);
            String input = br.readLine();
            if (input.isBlank()) return null;
            try { java.sql.Date.valueOf(input); return input; }
            catch (Exception e) { System.out.println("⚠️ 날짜 형식이 잘못되었습니다. (YYYY-MM-DD)"); }
        }
    }
}
