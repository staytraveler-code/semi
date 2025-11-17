package administrator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import db.organization.OrganizationDTO;
import db.project.ProjectDTO;
import db.util.DBConn;
import userInterface.InputHandler;

public class AdminUI {
	private AdminDAO dao = new AdminDAO();
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public AdminUI() throws IOException {
		showMenu();
	}

	public void showMenu() throws IOException {

		while (true) {
			System.out.print("""
					===============================
						    < 관 리 자 모 드 >
					===============================
					1. 등록된 기관 전체조회
					2. 등록된 과제 전체조회
					0. 종료
					===============================
					""");

			String input = br.readLine();

			switch (input) {
			case "1" -> OrganizationUI(); // 등록된 기관 전체조회
			case "2" -> ProjectUI(); // 등록된 과제 전체조회
			case "0" -> exit();
			default -> System.out.println("⚠️ 잘못된 입력입니다.\n");
			}

		}

	}

	// 기관 조회 UI
	public void OrganizationUI() {
		try {
			OrganizationList();
			while (true) {
				System.out.println("\n==============================================================");
				System.out.println("조회 기능");
				System.out.println("==============================================================");
				System.out.println(" 1.기관명 2.기관타입 3.기관주소지 0. 뒤로가기, 00. 종료");
				String input = (InputHandler.getRequiredInput(br, "메뉴 선택 ▶ (0. 뒤로가기, 00. 종료)"));
				System.out.println();

				switch (input) {
				case "1" -> OrganizationNameList();
				case "2" -> OrganizationTypeList();
				case "3" -> OrganizationAddrList();
				case "0" -> {
					return;
				}
				case "00" -> exit();
				default -> System.out.println("\n⚠️ 잘못된 입력입니다.\n");
				}
			}
		} catch (IOException e) {
			System.out.println("⚠️ 잘못된 입력입니다.\n");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	// "⚠️ 데이터베이스 오류: " + e.getMessage()

	// 전체기관목록 출력
	public void OrganizationList() {
		try {
			System.out.println("=========전체기관목록=========");
			List<OrganizationDTO> list = dao.ListOrgan();

			if (list.isEmpty()) {
				System.out.println("(등록된 기관이 없습니다)");
			} else {
				for (int i = 0; i < list.size(); i++) {
					OrganizationDTO dto = list.get(i);
					System.out.printf("%d. [%s] %s | %s | %s | %s | %s | %s | %s%n", i + 1, dto.getOrgCode(),
							dto.getOrgName(), dto.getOrgType(), dto.getBizRegNo(), dto.getOrgTel(), dto.getOrgEmail(),
							dto.getOrgAddress(), dto.getOrgId());
				}
			}
			System.out.println("======================\n");

		} catch (SQLException e) {
			System.out.println("⚠️ 데이터베이스 오류: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("⚠️ 잘못된 입력입니다.\n");
		} catch (Exception e) {
			System.out.println("⚠️ 기관 조회 오류: " + e.getMessage());
		}
	}

	// 기관이름 검색
	public void OrganizationNameList() {
		String key = null;
		try {
			System.out.print("조회하고 싶은 기관명 => ");
			key = br.readLine();

			List<OrganizationDTO> list = dao.findByOrganName(key);

			System.out.println("=========기관명 : " + key + "=========");

			if (list.isEmpty()) {
				System.out.println("(등록된 이름이 없습니다)");
			} else {
				for (int i = 0; i < list.size(); i++) {
					OrganizationDTO dto = list.get(i);
					System.out.printf("%d. [%s] %s | %s | %s | %s | %s | %s | %s%n", i + 1, dto.getOrgCode(),
							dto.getOrgName(), dto.getOrgType(), dto.getBizRegNo(), dto.getOrgTel(), dto.getOrgEmail(),
							dto.getOrgAddress(), dto.getOrgId());
				}
			}
			System.out.println("======================\n");
		} catch (SQLException e) {
			System.out.println("⚠️ 데이터베이스 오류: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("⚠️ 잘못된 입력입니다.\n");
		} catch (Exception e) {
			System.out.println("⚠️ 기관 조회 오류: " + e.getMessage());
		}
	}

	// 기관타입 검색
	public void OrganizationTypeList() {
		String key = null;
		try {
			System.out.print("조회하고 싶은 타입 => ");
			key = br.readLine();

			List<OrganizationDTO> list = dao.findByOrganType(key);

			System.out.println("=========타입 : " + key + "=========");

			if (list.isEmpty()) {
				System.out.println("(등록된 타입이 없습니다)");
			} else {
				for (int i = 0; i < list.size(); i++) {
					OrganizationDTO dto = list.get(i);
					System.out.printf("%d. [%s] %s | %s | %s | %s | %s | %s | %s%n", i + 1, dto.getOrgCode(),
							dto.getOrgName(), dto.getOrgType(), dto.getBizRegNo(), dto.getOrgTel(), dto.getOrgEmail(),
							dto.getOrgAddress(), dto.getOrgId());
				}
			}
			System.out.println("======================\n");
		} catch (SQLException e) {
			System.out.println("⚠️ 데이터베이스 오류: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("⚠️ 잘못된 입력입니다.\n");
		} catch (Exception e) {
			System.out.println("⚠️ 타입 조회 오류: " + e.getMessage());
		}
	}

	// 기관 주소지 검색
	public void OrganizationAddrList() {
		String key = null;
		try {
			System.out.print("조회하고 싶은 주소지 => ");
			key = br.readLine();

			List<OrganizationDTO> list = dao.findByOrganAddr(key);

			System.out.println("=========주소지 : " + key + "=========");

			if (list.isEmpty()) {
				System.out.println("(등록된 주소가 없습니다)");
			} else {
				for (int i = 0; i < list.size(); i++) {
					OrganizationDTO dto = list.get(i);
					System.out.printf("%d. [%s] %s | %s | %s | %s | %s | %s | %s%n", i + 1, dto.getOrgCode(),
							dto.getOrgName(), dto.getOrgType(), dto.getBizRegNo(), dto.getOrgTel(), dto.getOrgEmail(),
							dto.getOrgAddress(), dto.getOrgId());
				}
			}
			System.out.println("======================\n");
		} catch (SQLException e) {
			System.out.println("⚠️ 데이터베이스 오류: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("⚠️ 잘못된 입력입니다.\n");
		} catch (Exception e) {
			System.out.println("⚠️ 주소지 조회 오류: " + e.getMessage());
		}
	}

	// 과제 조회 ui
	public void ProjectUI() {
		try {
			ProjectList();
			while (true) {
				System.out.println("\n==============================================================");
				System.out.println("조회 기능");
				System.out.println("==============================================================");
				System.out.println(" 1.과제 주제별 2.과제 단계별 3.과제 상태별 4.예산 내림차순 정렬 0. 뒤로가기, 00. 종료");
				String input = (InputHandler.getRequiredInput(br, "메뉴 선택 ▶ (0. 뒤로가기, 00. 종료)"));
				System.out.println();

				switch (input) {
				case "1" -> ProjectByTitle();
				case "2" -> ProjectByStage();
				case "3" -> ProjectByStatus();
				case "4" -> ProjectBudgeSort();
				case "0" -> {
					return;
				}
				case "00" -> exit();
				default -> System.out.println("\n⚠️ 잘못된 입력입니다.\n");
				}
			}
		} catch (IOException e) {
			System.out.println("⚠️ 잘못된 입력입니다.\n");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 전체과제목록 출력
	public void ProjectList() {
		try {
			System.out.println("=========전체과제목록=========");
			List<ProjectDTO> list = dao.ListProject();

			if (list.isEmpty()) {
				System.out.println("(등록된 과제가 없습니다)");
			} else {
				for (int i = 0; i < list.size(); i++) {
					ProjectDTO dto = list.get(i);
					System.out.printf("%d. [%s] %s | %s | %s | %s | %s | %s | %s%n", i + 1, dto.getProjectCode(),
							dto.getOrgName(), dto.getTitle(), dto.getStage(), dto.getStatus(), dto.getBudget(),
							dto.getStartDate(), dto.getEndDate());
				}
			}
			System.out.println("======================\n");
		} catch (SQLException e) {
			System.out.println("⚠️ 데이터베이스 오류: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("⚠️ 잘못된 입력입니다.\n");
		} catch (Exception e) {
			System.out.println("⚠️ 과제 조회 오류: " + e.getMessage());
		}
	}

	// 1. 과제 주제별
	public void ProjectByTitle() {
		String key = null;
		try {
			System.out.print("조회하고 싶은 주제 => ");
			key = br.readLine();

			List<ProjectDTO> list = dao.FindByProjectTitle(key);

			System.out.println("=========주제 : " + key + "=========");

			if (list.isEmpty()) {
				System.out.println("(등록된 주제가 없습니다)");
			} else {
				for (int i = 0; i < list.size(); i++) {
					ProjectDTO dto = list.get(i);
					System.out.printf("%d. [%s] %s | %s | %s | %s | %s | %s | %s%n", i + 1, dto.getProjectCode(),
							dto.getOrgName(), dto.getTitle(), dto.getStage(), dto.getStatus(), dto.getBudget(),
							dto.getStartDate(), dto.getEndDate());
				}
			}
			System.out.println("======================\n");
		} catch (SQLException e) {
			System.out.println("⚠️ 데이터베이스 오류: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("⚠️ 잘못된 입력입니다.\n");
		} catch (Exception e) {
			System.out.println("⚠️ 주제 조회 오류: " + e.getMessage());
		}
	}

	// 2. 과제 단계별로 검색(1~4단계
	public void ProjectByStage() {
		String key = null;
		try {
			System.out.print("조회하고 싶은 단계(1단계~4단계) => ");
			key = br.readLine();

			List<ProjectDTO> list = dao.FindByProjectStage(key);

			System.out.println("=========단계 : " + key + "=========");

			if (list.isEmpty()) {
				System.out.println("(등록된 단계가 없습니다)");
			} else {
				for (int i = 0; i < list.size(); i++) {
					ProjectDTO dto = list.get(i);
					System.out.printf("%d. [%s] %s | %s | %s | %s | %s | %s | %s%n", i + 1, dto.getProjectCode(),
							dto.getOrgName(), dto.getTitle(), dto.getStage(), dto.getStatus(), dto.getBudget(),
							dto.getStartDate(), dto.getEndDate());
				}
			}
			System.out.println("======================\n");
		} catch (SQLException e) {
			System.out.println("⚠️ 데이터베이스 오류: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("⚠️ 잘못된 입력입니다.\n");
		} catch (Exception e) {
			System.out.println("⚠️ 단계 조회 오류: " + e.getMessage());
		}
	}

	// 3. 과제 상태별로 검색(진행중 완료 지연 등
	public void ProjectByStatus() {
		String key = null;
		try {
			System.out.print("조회하고 싶은 상태(계획,진행,완료,지연) => ");
			key = br.readLine();

			List<ProjectDTO> list = dao.FindByProjectStatus(key);

			System.out.println("=========상태 : " + key + "=========");

			if (list.isEmpty()) {
				System.out.println("(등록된 상태가 없습니다)");
			} else {
				for (int i = 0; i < list.size(); i++) {
					ProjectDTO dto = list.get(i);
					System.out.printf("%d. [%s] %s | %s | %s | %s | %s | %s | %s%n", i + 1, dto.getProjectCode(),
							dto.getOrgName(), dto.getTitle(), dto.getStage(), dto.getStatus(), dto.getBudget(),
							dto.getStartDate(), dto.getEndDate());
				}
			}
			System.out.println("======================\n");
		} catch (SQLException e) {
			System.out.println("⚠️ 데이터베이스 오류: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("⚠️ 잘못된 입력입니다.\n");
		} catch (Exception e) {
			System.out.println("⚠️ 상태 조회 오류: " + e.getMessage());
		}
	}

	// 4. 과제 예산 내림차순 정렬
	public void ProjectBudgeSort() {
		try {
			System.out.println("=========예산 내림차순 목록=========");
			List<ProjectDTO> list = dao.ListProjectBudget();

			if (list.isEmpty()) {
				System.out.println("(등록된 과제가 없습니다)");
			} else {
				for (int i = 0; i < list.size(); i++) {
					ProjectDTO dto = list.get(i);
					System.out.printf("%d. [%s] %s | %s | %s | %s | %s | %s | %s%n", i + 1, dto.getProjectCode(),
							dto.getOrgName(), dto.getTitle(), dto.getStage(), dto.getStatus(), dto.getBudget(),
							dto.getStartDate(), dto.getEndDate());
				}
			}
			System.out.println("======================\n");
		} catch (SQLException e) {
			System.out.println("⚠️ 데이터베이스 오류: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("⚠️ 잘못된 입력입니다.\n");
		} catch (Exception e) {
			System.out.println("⚠️ 예산 조회 오류: " + e.getMessage());
		}
	}

	// 종료
	public void exit() {
		System.out.println("프로그램 종료");
		DBConn.close();
		System.exit(0);
	}

}