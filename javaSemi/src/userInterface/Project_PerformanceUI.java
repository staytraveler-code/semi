package userInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import db.milestone.MilestoneDTO;
import db.performance.management.PerformanceManagementDAO;
import db.performance.management.PerformanceManagementDTO;

public class Project_PerformanceUI {
	private PerformanceManagementDAO dao = new PerformanceManagementDAO();
	private PerformanceManagementDTO dto = new PerformanceManagementDTO();

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

	// DAO에서 해당 과제의 성과 목록 가져오기 -- 완료
	public void printPerformanceList() {
		try {
			System.out.println("=========성과목록=========");
			List<PerformanceManagementDTO> list = dao.performanceList(projectCode);

			if (list.isEmpty()) {
				System.out.println("(등록된 성과가 없습니다)");
			} else {
				for (int i = 0; i < list.size(); i++) {
					PerformanceManagementDTO dto = list.get(i);
					System.out.printf("%d. [%s] %s | %s | %s | %s%n", i + 1, dto.getPerfCode(), dto.getName(),
							dto.getCategory(), dto.getpDate(), dto.getMemo());
				}
			}

			System.out.println("======================\n");
		} catch (IOException e) {
			System.out.println("입력중 오류 발생");
		} catch (SQLException e) {
			System.out.println("db작업 중 오류발생");
		} catch (Exception e) {
			System.out.println("-예기치 못한 오류발생-");
		}
	}

	// 과제에 성과 추가하기 --완료
	public void addPerformance() {

		try {

			PerformanceManagementDTO dto = new PerformanceManagementDTO();

			System.out.println("추가할 성과 입력");
			System.out.print("제목 ▶ ");
			dto.setName(br.readLine());
			System.out.print("카테고리 ▶ ");
			dto.setCategory(br.readLine());
			System.out.print("내용 ▶ ");
			dto.setContent(br.readLine());
			
			dto.setpDate(readDate("성과발생일자 (YYYY-MM-DD) ▶ "));
			
			System.out.print("메모 ▶ ");
			dto.setMemo(br.readLine());

			int result = dao.insertPerformance(dto, projectCode);

			if (result > 0) {

				System.out.println("✅ 성과 추가 완료!\n");
			} else {
				System.out.println("✅ 성과 추가 실패\n");
			}

		} catch (IOException e) {
			System.out.println("입력중 오류발생");
		} catch (SQLException e) {
			System.out.println("DB작업중 오류발생");
		} catch (Exception e) {
			System.out.println("예기치 못한 오류발생");
		}
	}

	// 기존 목록의 성과 수정하기 -- 완료
	public void updatePerformance() {
		try {
			List<PerformanceManagementDTO> list = dao.performanceList(projectCode);
			if (list.isEmpty()) {
				System.out.println("⚠️ 등록된 성과가 없습니다.");
				return;
			}

			System.out.print("수정할 성과코드 입력 ▶ ");
			String perfCode = br.readLine();

			// 성과 목록에 있는지 체크
			if (!dao.isPerformnace(perfCode, projectCode)) {
				System.out.println("⚠️ 목록에 있는 성과코드를 선택해주세요");
				return;
			}

			// 기존값 빼두기
			PerformanceManagementDTO target = null;
			for (PerformanceManagementDTO dto : list) {
				if (dto.getPerfCode().equals(perfCode)) {
					target = dto;
					break;
				}
			}
			target.setPerfCode(perfCode);

			System.out.println("성과 수정 (Enter: 기존값 유지)");

			System.out.print("제목(" + target.getName() + ") ▶ ");
			String input = br.readLine();
			if (!input.isBlank()) {
				target.setName(input);
			}

			System.out.print("카테고리(" + target.getCategory() + ") ▶ ");
			input = br.readLine();
			if (!input.isBlank()) {
				target.setCategory(input);
			}

			System.out.print("내용(" + target.getContent() + ") ▶ ");
			input = br.readLine();
			if (!input.isBlank()) {
				target.setContent(input);
			}

			String pInput = readDateAllowBlank("성과발생일(" + target.getpDate() + ") ▶ ", target.getpDate());
			target.setpDate(pInput);

			System.out.print("메모(" + target.getMemo() + ") ▶ ");
			input = br.readLine();
			if (!input.isBlank()) {
				target.setMemo(input);
			}

			int result = dao.updatePerformance(target);
			System.out.println(result > 0 ? "✅ 성과 수정 완료!\n" : "⚠️ 수정 실패\n");

		} catch (IOException e) {
			System.out.println("입력중 오류 발생");
		} catch (SQLException e) {
			e.printStackTrace();
//			System.out.println("DB작업중 오류 발생");
		} catch (Exception e) {
			System.out.println("예기치 못한 오류발생");
		}
	}

	// 기존 성과 삭제하기.
	public void deletePerformance()  {
		try {
			System.out.print("삭제할 성과코드 입력 ▶ ");
			String code = br.readLine();

			// 성과 목록에 있는지 체크
			if (!dao.isPerformnace(code, projectCode)) {
				System.out.println("⚠️ 목록에 있는 성과코드를 선택해주세요");
				return;
			}

			
			int result = dao.deletePerformance(code);

			System.out.println(result > 0 ? "✅ 성과 삭제 완료!\n" : "⚠️ 성과 삭제 실패.\n");

		} catch (IOException e) {
			System.out.println("입력중 오류발생");
		} catch (SQLException e) {
			System.out.println("DB작업중 오류발생");
		} catch (Exception e) {
			System.out.println("예기치 못한 오류 발생");
		}
	}

	// 날짜 입력 (Enter → 필수)
	private String readDate(String prompt) throws Exception {
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

	private String readDateAllowBlank(String prompt, String existingDate) throws Exception {
		while (true) {
			System.out.print(prompt);
			String input = br.readLine();
			if (input.isBlank())
				return existingDate;
			try {
				java.sql.Date.valueOf(input);
				return input;
			} catch (Exception e) {
				System.out.println("⚠️ 날짜 형식이 잘못되었습니다. (YYYY-MM-DD)");
			}
		}
	}

}
