package userInterface;

import java.io.BufferedReader;
import java.util.List;

import db.performance.management.PerformanceManagementDAO;
import db.performance.management.PerformanceManagementDTO;

public class Project_PerformanceUI {
	private PerformanceManagementDAO dao = new PerformanceManagementDAO();

	private BufferedReader br;
	private String projectCode;

	public void setBufferedReader(BufferedReader br) {
		this.br = br;
	}
	
	public void setProjectCode(String projectCode, String orgCode) {
		this.projectCode = projectCode;
	}

	// DAO에서 해당 과제의 성과 목록 가져오기
	public void printPerformanceList() {
		try {
			List<PerformanceManagementDTO> list = dao.performanceList(projectCode);

			if (list.isEmpty()) {
				System.out.println("───────────────────────────────────────────────────");
            	System.out.println("⚠️ 해당 프로젝트의 성과가 존재하지 않습니다.");
            	System.out.println("───────────────────────────────────────────────────");
            	return;
			}
			
			System.out.println("────────────────────────────────[ 성과 목록 ]────────────────────────────────────");
			for (int i = 0; i < list.size(); i++) {
				PerformanceManagementDTO dto = list.get(i);
				System.out.printf("%d. [ %s ] [ %s ] %s | %s | %s \n", i + 1,
						dto.getPerfCode(), dto.getCategory(), dto.getName(), dto.getpDate().substring(0,10), dto.getMemo());
			}
			System.out.println("───────────────────────────────────────────────────────────────────────────────");
		} catch (Exception e) {
			System.out.println("⚠️ 성과 조회 오류: " + e.getMessage());
		}
	}

	// 과제에 성과 추가하기
	public void addPerformance() {

		try {
			PerformanceManagementDTO dto = new PerformanceManagementDTO();

			System.out.println("⦁ 성과 추가");
			
			dto.setName(InputHandler.getRequiredInput(br, "제목 ▶ "));
			dto.setCategory(InputHandler.getRequiredInput(br, "카테고리 ▶ "));
			dto.setContent(InputHandler.getRequiredInput(br, "내용 ▶ "));
			dto.setpDate(InputHandler.getRequiredDateInput(br, "발생일자(YYYY-MM-DD) ▶ "));
			dto.setMemo(InputHandler.getOptionalInput(br, "메모 ▶ "));

			dao.insertPerformance(dto, projectCode);
            System.out.println("✅ 성과 추가 완료!\n");

		} catch (InputCancelledException e) {
			System.out.println("❌ 입력을 중단하였습니다.");
		} catch (Exception e) {
            System.out.println("⚠️ 성과 추가 실패: " + e.getMessage());
        }
	}

	// 기존 목록의 성과 수정하기
	public void updatePerformance() {
		try {
			System.out.println("⦁ 성과 수정 (Enter: 기존값 유지)");
			String perfCode = InputHandler.getRequiredInput(br, "수정할 성과코드 입력 ▶ ").toUpperCase();

			// 성과 목록에 있는지 체크
			if (!dao.isProjectIncludePerformance(perfCode, projectCode)) {
				System.out.println("⚠️ 목록에 있는 성과코드를 입력해주세요.\n");
				SleepUtil.sleep(1000);
				return;
			}

			// 기존값 빼두기
			List<PerformanceManagementDTO> list = dao.performanceList(projectCode);
			PerformanceManagementDTO target = null;
			
			for (PerformanceManagementDTO dto : list) {
				if (dto.getPerfCode().equals(perfCode)) {
					target = dto;
					break;
				}
			}
			
			// target.setPerfCode(perfCode);\

			String input = InputHandler.getOptionalInput(br, "제목(" + target.getName() + ") ▶ "); 
			if (!input.isBlank()) {
				target.setName(input);
			}

			input = InputHandler.getOptionalInput(br, "카테고리(" + target.getCategory() + ") ▶ ");
			if (!input.isBlank()) {
				target.setCategory(input);
			}

			input = InputHandler.getOptionalInput(br, "내용(" + target.getContent() + ") ▶ ");
			if (!input.isBlank()) {
				target.setContent(input);
			}

			String pInput = InputHandler.getOptionalDateInput(br, "성과발생일(" + target.getpDate().substring(0,10) + ") ▶ ");
			if (!pInput.isBlank()) {
				target.setpDate(pInput);
			}

			input = InputHandler.getOptionalInput(br, "메모(" + target.getMemo() + ") ▶ ");
			if (!input.isBlank()) {
				target.setMemo(input);
			}

			dao.updatePerformance(target);
            System.out.println("✅ 성과 수정 완료!\n");

		} catch (InputCancelledException e) {
			System.out.println("❌ 입력을 중단하였습니다.");
		} catch (Exception e) {
            System.out.println("⚠️ 성과 수정 실패: " + e.getMessage());
        }
	}

	// 성과 삭제
	public void deletePerformance()  {
		try {
			System.out.println("⦁ 성과 삭제");
			String code = InputHandler.getOptionalInput(br, "삭제할 성과코드 입력 ▶ ");

			// 성과 목록에 있는지 체크
			if (!dao.isProjectIncludePerformance(code, projectCode)) {
				System.out.println("⚠️ 목록에 있는 성과코드를 선택해주세요");
				SleepUtil.sleep(1000);
				return;
			}
			
			dao.deletePerformance(code);
            System.out.println("✅ 성과 삭제 완료!\n");

		} catch (InputCancelledException e) {
			System.out.println("❌ 입력을 중단하였습니다.");
		} catch (Exception e) {
            System.out.println("⚠️ 성과 삭제 실패: " + e.getMessage());
        }
	}
}
