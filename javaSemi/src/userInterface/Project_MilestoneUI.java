package userInterface;

import db.milestone.MilestoneDAO;
import db.milestone.MilestoneDTO;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class Project_MilestoneUI {
    private BufferedReader br;
    private MilestoneDAO milestoneDAO = new MilestoneDAO();
    private String projectCode;
    private String orgCode; // 기관 코드

    public void setBufferedReader(BufferedReader br) { this.br = br; }
    public void setProjectCode(String projectCode) { this.projectCode = projectCode; }
    public void setOrgCode(String orgCode) { this.orgCode = orgCode; }

    // 마일스톤 목록 출력
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

    // 마일스톤 추가
    public void addMilestone() throws IOException {
        // 기관 소속 과제인지 체크
        if (!isProjectBelongsToOrg()) {
            System.out.println("⚠️ 이 과제는 해당 기관의 과제가 아닙니다.");
            return;
        }

        MilestoneDTO dto = new MilestoneDTO();
        dto.setpCode(projectCode);

        System.out.print("목표 ▶ "); dto.setName(br.readLine());
        System.out.print("내용 ▶ "); dto.setDesc(br.readLine());
        dto.setPeDate(readDate("계획완료일 (YYYY-MM-DD) ▶ "));
        dto.setAeDate(readDate("실제완료일 (YYYY-MM-DD) ▶ "));
        System.out.print("상태 ▶ "); dto.setStatus(br.readLine());

        int result = milestoneDAO.insertMilestone(dto);
        System.out.println(result > 0 ? "✅ 마일스톤 추가 완료!\n" : "⚠️ 추가 실패\n");
    }

    // 마일스톤 수정
    public void updateMilestone() throws IOException {
        List<MilestoneDTO> list = milestoneDAO.listMilestoneByProject(projectCode);
        if (list.isEmpty()) { System.out.println("⚠️ 등록된 마일스톤이 없습니다."); return; }

        System.out.print("수정할 마일스톤 코드 입력 ▶ ");
        String code = br.readLine();

        // 기관 체크
        if (!milestoneDAO.isMilestoneBelongsToOrg(code, orgCode)) {
            System.out.println("⚠️ 이 마일스톤은 해당 기관의 과제가 아닙니다.");
            return;
        }

        MilestoneDTO target = null;
        for (MilestoneDTO dto : list) {
            if (dto.getMileCode().equals(code)) { target = dto; break; }
        }
        if (target == null) { System.out.println("⚠️ 해당 마일스톤이 없습니다."); return; }

        System.out.println("마일스톤 정보 수정 (Enter: 기존값 유지)");

        System.out.print("목표(" + target.getName() + ") ▶ ");
        String input = br.readLine();
        if (!input.isBlank()) target.setName(input);

        System.out.print("내용(" + target.getDesc() + ") ▶ ");
        input = br.readLine();
        if (!input.isBlank()) target.setDesc(input);

        String peInput = readDateAllowBlank("계획완료일(" + target.getPeDate() + ") ▶ ", target.getPeDate());
        target.setPeDate(peInput);

        String aeInput = readDateAllowBlank("실제완료일(" + target.getAeDate() + ") ▶ ", target.getAeDate());
        target.setAeDate(aeInput);

        System.out.print("상태(" + target.getStatus() + ") ▶ ");
        input = br.readLine();
        if (!input.isBlank()) target.setStatus(input);

        int result = milestoneDAO.updateMilestone(target);
        System.out.println(result > 0 ? "✅ 마일스톤 수정 완료!\n" : "⚠️ 수정 실패\n");
    }

    // 삭제
    public void deleteMilestone() throws IOException {
        System.out.print("삭제할 마일스톤 코드 입력 ▶ ");
        String code = br.readLine();

        // 기관 체크
        if (!milestoneDAO.isMilestoneBelongsToOrg(code, orgCode)) {
            System.out.println("⚠️ 이 마일스톤은 해당 기관의 과제가 아닙니다.");
            return;
        }

        int result = milestoneDAO.deleteMilestone(code, projectCode);
        System.out.println(result > 0 ? "✅ 마일스톤 삭제 완료!\n" : "⚠️ 해당 마일스톤이 없습니다.\n");
    }

    // 날짜 입력 (Enter → 필수)
    private String readDate(String prompt) throws IOException {
        while (true) {
            System.out.print(prompt);
            String input = br.readLine();
            if (input.isBlank()) {
                System.out.println("⚠️ 날짜 입력은 필수입니다.");
                continue;
            }
            try {
                java.sql.Date.valueOf(input);
                return input;
            } catch (Exception e) {
                System.out.println("⚠️ 날짜 형식이 잘못되었습니다. (YYYY-MM-DD)");
            }
        }
    }

    private String readDateAllowBlank(String prompt, String existingDate) throws IOException {
        while (true) {
            System.out.print(prompt);
            String input = br.readLine();
            if (input.isBlank()) return existingDate;
            try {
                java.sql.Date.valueOf(input);
                return input;
            } catch (Exception e) {
                System.out.println("⚠️ 날짜 형식이 잘못되었습니다. (YYYY-MM-DD)");
            }
        }
    }

    // 프로젝트가 기관 소속인지 확인 (필요 시 projectDAO와 연계)
    private boolean isProjectBelongsToOrg() {
        // 프로젝트가 orgCode 소속인지 확인하는 DAO 메서드 호출
        // return projectDAO.isProjectBelongsToOrg(projectCode, orgCode);
        return true; // 임시: 실제로는 projectDAO에서 체크
    }
}
