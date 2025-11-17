package userInterface;

import java.io.BufferedReader;
import java.util.List;

import db.milestone.MilestoneDAO;
import db.milestone.MilestoneDTO;

public class Project_MilestoneUI {
	private BufferedReader br;
	private MilestoneDAO milestoneDAO = new MilestoneDAO();
	private String projectCode;

	public void setBufferedReader(BufferedReader br) {
		this.br = br;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	// 마일스톤 목록 출력
	public void printMilestoneList() {
		try {
			List<MilestoneDTO> list = milestoneDAO.listMilestoneByProject(projectCode);
			
			if (list.isEmpty()) {
				System.out.println("───────────────────────────────────────────────────");
            	System.out.println("⚠️ 해당 프로젝트의 마일스톤이 존재하지 않습니다.");
            	System.out.println("───────────────────────────────────────────────────");
            	return;
			}

			System.out.println("─────────────────────────────────────────────────────[ 마일스톤 목록 ]───────────────────────────────────────────────────────");
			System.out.printf("%-10s | %-25s | %-30s | %-15s | %-15s | %-10s%n",
            				  "마일스톤 코드", "목표", "내용", "계획완료일", "실제완료일", "상태");
			System.out.println("──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
			
			for (MilestoneDTO dto : list) {
				System.out.printf("%-12s | %-22s | %-27s | %-20s | %-20s | %-10s%n",
						dto.getMileCode(), dto.getName(), dto.getDesc(), dto.getPeDate().substring(0, 10),
						dto.getAeDate().substring(0, 10), dto.getStatus());
			}

			System.out.println("──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");

		} catch (Exception e) {
			System.out.println("⚠️ 마일스톤 조회 오류: " + e.getMessage());
		}
	}

	// 마일스톤 추가
	public void addMilestone() {
		try {
			MilestoneDTO dto = new MilestoneDTO();

			System.out.println("⦁ 마일스톤 추가");
			
			dto.setpCode(projectCode);
			dto.setName(InputHandler.getRequiredInput(br, "목표 ▶ "));
			dto.setDesc(InputHandler.getRequiredInput(br, "내용 ▶ "));
			dto.setPeDate(InputHandler.getRequiredDateInput(br,"계획완료일 (YYYY-MM-DD) ▶ "));
			dto.setAeDate(InputHandler.getRequiredDateInput(br,"실제완료일 (YYYY-MM-DD) ▶ "));
			dto.setStatus("미완료");

			milestoneDAO.insertMilestone(dto);
			System.out.println("✅ 마일스톤 추가 완료!\n");

		} catch (Exception e) {
			System.out.println("⚠️ 마일스톤 추가 실패: " + e.getMessage());
		}
	}

	// 마일스톤 수정
	public void updateMilestone() {
		try {
			List<MilestoneDTO> list = milestoneDAO.listMilestoneByProject(projectCode);

			if (list.isEmpty()) {
				System.out.println("⚠️ 등록된 마일스톤이 없습니다.");
				return;
			}
			
			System.out.println("⦁ 마일스톤 수정 (Enter: 기존값 유지)");
			String mCode = InputHandler.getRequiredInput(br, "수정할 마일스톤 코드 입력 ▶ ").toUpperCase();

			// 접근 가능한 마일스톤인지 체크
			if (!milestoneDAO.isProjectIncludeMilestone(mCode, projectCode)) {
				System.out.println("⚠️ 목록에 있는 마일스톤 코드를 입력해주세요.\n");
				SleepUtil.sleep(1000);
				return;
			}

			// 기존 입력값 빼두기 작업
			MilestoneDTO target = null;
			for (MilestoneDTO dto : list) {
				if (dto.getMileCode().equals(mCode)) {
					target = dto;
					break;
				}
			}

			String input = InputHandler.getOptionalInput(br, "목표(" + target.getName() + ") ▶ ");
			if (!input.isBlank()) target.setName(input);
			input = InputHandler.getOptionalInput(br, "내용(" + target.getDesc() + ") ▶ ");
			if (!input.isBlank()) target.setDesc(input);
			String peInput = InputHandler.getOptionalDateInput(br, "계획완료일(" + target.getPeDate().substring(0,10) + ") ▶ ");
			if (!peInput.isBlank()) target.setPeDate(peInput);
			String aeInput = InputHandler.getOptionalDateInput(br, "실제완료일(" + target.getAeDate().substring(0,10) + ") ▶ "); 
			if (!aeInput.isBlank()) target.setAeDate(aeInput);
			while(true) {
				input = InputHandler.getOptionalInput(br, "상태(" + target.getStatus() + ") ▶ ");
				if (input.isBlank()) {
					break;
				} else if(input.equals("완료") || input.equals("미완료")) {
					target.setStatus(input);				
					break;
				} else {
					System.out.println("⚠️ 상태는 [완료], [미완료]만 입력 가능합니다.\n");
				}
			}
			
			milestoneDAO.updateMilestone(target);
			System.out.println("✅ 마일스톤 수정 완료!\n");

		} catch (Exception e) {
			System.out.println("⚠️ 마일스톤 수정 실패: " + e.getMessage());
		}
	}

	// 마일스톤 삭제
	public void deleteMilestone() {
		try {

			System.out.println("⦁ 마일스톤 삭제");
			String mCode = InputHandler.getOptionalInput(br, "삭제할 마일스톤 코드 입력 ▶ ").toUpperCase();

			// 접근 가능한 마일스톤인지 체크
			if (!milestoneDAO.isProjectIncludeMilestone(mCode, projectCode)) {
				System.out.println("⚠️ 목록에 있는 마일스톤 코드를 입력해주세요.\n");
				SleepUtil.sleep(1000);
				return;
			}

			milestoneDAO.deleteMilestone(mCode);
			System.out.println("✅ 마일스톤 삭제 완료!\n");
			
		} catch (Exception e) {
			System.out.println("⚠️ 마일스톤 삭제 실패: " + e.getMessage());
		}
	}

}
