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
        System.out.println("===== 마일스톤 목록 =====");
        List<MilestoneDTO> list = milestoneDAO.listMilestone();
        boolean hasData = false;
        for (MilestoneDTO dto : list) {
            if (dto.getpCode() == null || !dto.getpCode().equals(projectCode)) continue;
            hasData = true;
            System.out.printf("코드: %s | 목표: %s | 내용: %s | 계획완료: %s | 실제완료: %s | 상태: %s%n",
                    dto.getMileCode(), dto.getName(), dto.getDesc(),
                    dto.getPeDate(), dto.getAeDate(), dto.getStatus());
        }
        if (!hasData) System.out.println("(등록된 마일스톤이 없습니다)");
        System.out.println("========================\n");
    }

    // 추가
    public void addMilestone() throws IOException {
        MilestoneDTO dto = new MilestoneDTO();
        dto.setpCode(projectCode);

        System.out.print("마일스톤 코드 ▶ "); dto.setMileCode(br.readLine());
        System.out.print("목표 ▶ "); dto.setName(br.readLine());
        System.out.print("내용 ▶ "); dto.setDesc(br.readLine());
        System.out.print("계획완료일 (YYYY-MM-DD) ▶ "); dto.setPeDate(br.readLine());
        System.out.print("실제완료일 (YYYY-MM-DD) ▶ "); dto.setAeDate(br.readLine());
        System.out.print("상태 ▶ "); dto.setStatus(br.readLine());

        int result = milestoneDAO.insertMilestone(dto);
        if (result > 0) System.out.println("✅ 마일스톤 추가 완료!\n");
        else System.out.println("⚠️ 추가 실패\n");
    }

    // 수정
    public void updateMilestone() throws IOException {
        List<MilestoneDTO> list = milestoneDAO.listMilestone();
        boolean found = false;

        System.out.print("수정할 마일스톤 코드 입력 ▶ ");
        String code = br.readLine();

        for (MilestoneDTO dto : list) {
            if (dto.getpCode() != null && dto.getpCode().equals(projectCode) && dto.getMileCode().equals(code)) {
                found = true;

                System.out.print("목표(" + dto.getName() + ") ▶ ");
                String input = br.readLine();
                if (!input.isBlank()) dto.setName(input);

                System.out.print("내용(" + dto.getDesc() + ") ▶ ");
                input = br.readLine();
                if (!input.isBlank()) dto.setDesc(input);

                System.out.print("계획완료일(" + dto.getPeDate() + ") ▶ ");
                input = br.readLine();
                if (!input.isBlank()) dto.setPeDate(input);

                System.out.print("실제완료일(" + dto.getAeDate() + ") ▶ ");
                input = br.readLine();
                if (!input.isBlank()) dto.setAeDate(input);

                System.out.print("상태(" + dto.getStatus() + ") ▶ ");
                input = br.readLine();
                if (!input.isBlank()) dto.setStatus(input);

                int result = milestoneDAO.updateMilestone(dto);
                if (result > 0) System.out.println("✅ 마일스톤 수정 완료!\n");
                else System.out.println("⚠️ 수정 실패\n");

                break;
            }
        }
        if (!found) System.out.println("⚠️ 해당 마일스톤이 없습니다.\n");
    }

    // 삭제
    public void deleteMilestone() throws IOException {
        System.out.print("삭제할 마일스톤 코드 입력 ▶ ");
        String code = br.readLine();

        int result = milestoneDAO.deleteMilestone(code, projectCode);
        if (result > 0) System.out.println("✅ 마일스톤 삭제 완료!\n");
        else System.out.println("⚠️ 해당 마일스톤이 없습니다.\n");
    }
}
