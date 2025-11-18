package userInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import db.project.ProjectDAO;
import db.project.ProjectDTO;

public class ProjectUI {
    private BufferedReader br;       // 사용자 입력을 받는 BufferedReader
    private UI ui;                   // 상위 UI 클래스. 프로그램 종료 등을 제어
    private ProjectDAO projectDAO = new ProjectDAO(); // DB와 연결하여 프로젝트 정보를 가져오는 DAO
    private String orgCode;          // 로그인한 기관 코드

    // 세부 관리 클래스 인스턴스
    private Project_FundUI projectFund = new Project_FundUI();                 // 연구비 관리 UI
    private Project_MilestoneUI projectMilestone = new Project_MilestoneUI();   // 마일스톤 관리 UI
    private Project_ResearcherUI projectResearcher = new Project_ResearcherUI();// 프로젝트 연구원 관리 UI
    private Project_PerformanceUI projectPerformance = new Project_PerformanceUI(); // 성과 관리 UI

    // 생성자: 각 세부 관리 UI에 BufferedReader를 세팅
    public ProjectUI(BufferedReader br, UI ui, String orgCode) {
        this.br = br;
        this.ui = ui;
        this.orgCode = orgCode;

        projectFund.setBufferedReader(br);
        projectMilestone.setBufferedReader(br);
        projectPerformance.setBufferedReader(br);
        projectResearcher.setBufferedReader(br);
    }

    // 과제 선택
    public void chooseProject() {
        while (true) {
            // 화면 출력
            System.out.println("───────────────────────────────────────────────────────────────────────────────────────────");
            System.out.println("                                       과제 리스트                                            ");
            System.out.println("───────────────────────────────────────────────────────────────────────────────────────────");

            printProjectList(); // 등록된 과제 목록 출력

            try {
                // 사용자에게 과제 코드 입력 받기
                String input = InputHandler.getOptionalMenuInput(br, "관리할 과제 코드 입력 ▶ (0. 뒤로가기, 00. 종료) ").toUpperCase();

                // 뒤로가기
                if ("0".equals(input))
                    return;

                // 프로그램 종료
                if ("00".equals(input))
                    ui.exit();

                // 입력한 코드와 일치하는 프로젝트 선택
                ProjectDTO selected = projectDAO.getProjectsByOrganization(orgCode).stream()
                        .filter(p -> p.getProjectCode().equalsIgnoreCase(input))
                        .findFirst().orElse(null);

                if (selected != null) {
                    // 선택된 프로젝트가 존재하면 상세 메뉴 보여주기
                    showProjectMenu(input);
                } else {
                    // 존재하지 않는 과제 코드 입력 시 경고
                    System.out.println("\n⚠️ 존재하지 않는 과제 코드입니다.\n");
                }
            } catch (IOException | RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
            	e.printStackTrace();
			}
        }
    }

    // 과제 관리 메뉴 선택
    private void showProjectMenu(String projectId) {
        try {
            while (true) {
                System.out.println("\n==============================================================");
                System.out.println("     [" + projectId + "] 과제 관리");
                System.out.println("==============================================================");
                
                // 과제 상세 정보 출력
                showProjectDetail(projectId);
                System.out.println("==============================================================");
                
                // 메뉴 출력
                System.out.println(" 1. 성과 관리, 2. 연구비 관리, 3. 마일스톤 관리, 4. 연구원 관리, 0. 뒤로가기, 00. 종료");

                String input = InputHandler.getOptionalMenuInput(br, "메뉴 선택 ▶ (0. 뒤로가기, 00. 종료) ");

                // 메뉴 선택에 따른 처리
                switch (input) {
                    case "1" -> managePerformance(projectId);
                    case "2" -> manageFund(projectId);
                    case "3" -> manageMilestone(projectId);
                    case "4" -> manageProjectResearcher(projectId);
                    case "0" -> { return; } // 뒤로가기
                    case "00" -> ui.exit();  // 종료
                    default -> System.out.println("\n⚠️ 잘못된 입력입니다.\n");
                }
            }
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
		}
    }

    // 과제 리스트 출력
    private void printProjectList() {
        // DB에서 현재 기관(orgCode) 소속 프로젝트 가져오기
        List<ProjectDTO> projects = projectDAO.getProjectsByOrganization(orgCode);

        if (projects.isEmpty()) {
            System.out.println("⚠️ 등록된 과제가 없습니다.");
            SleepUtil.sleep(1000);
            return;
        }
        System.out.printf("%-8s │ %-37s │ %-8s │ %-10s │ %-10s\n", "과제코드", "과제명", "기관코드", "단계", "상태");
        System.out.println("───────────────────────────────────────────────────────────────────────────────────────────");

        // 프로젝트 리스트 출력
        for (ProjectDTO p : projects) {
            System.out.printf("%-10s │ %-30s │ %-10s │ %-10s │ %-10s\n", 
                p.getProjectCode(),
                truncate(p.getTitle(), 30), 
                p.getOrgCode(), 
                p.getStage(), 
                p.getStatus()
            );
        }
        System.out.println();
    }

    // 과제 상세 정보 출력
    private void showProjectDetail(String projectId) {
        try {
            ProjectDTO dto = projectDAO.getProjectDetail(projectId);

            if (dto == null) {
                System.out.println("해당 과제 정보를 찾을 수 없습니다.");
                return;
            }

            // 상세 정보 출력
            System.out.println("과제 코드\t: " + dto.getProjectCode());
            System.out.println("기관 코드\t: " + dto.getOrgCode());
            System.out.println("과제명\t: " + dto.getTitle());
            System.out.println("단계\t: " + dto.getStage());
            System.out.println("상태\t: " + dto.getStatus());
            System.out.printf("예산\t: %,d\n", dto.getBudget());
            System.out.println("시작일\t: " + (dto.getStartDate() != null ? dto.getStartDate() : "N/A"));
            System.out.println("종료일\t: " + (dto.getEndDate() != null ? dto.getEndDate() : "N/A"));
            System.out.println("부처\t: " + (dto.getMinistryName() == null || dto.getMinistryName().isBlank() ? "없음" : dto.getMinistryName()));
            System.out.println("협약기관\t: " + (dto.getPartnerOrgName() == null || dto.getPartnerOrgName().isBlank() ? "없음" : dto.getPartnerOrgName()));

            // 마일스톤 진행도
            int total = dto.getTotalMilestones();
            int completed = dto.getCompletedMilestones();
            double ratio = dto.getProgressRatio() * 100.0;

            if (total == 0) {
                System.out.println("등록된 마일스톤 없음 - 진행도 측정 불가");
            } else {
                System.out.printf("진행도\t: %d / %d (%.2f%%)\n", completed, total, ratio);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 텍스트 줄이는 메소드
    private String truncate(String text, int max) {
        return (text.length() > max) ? text.substring(0, max - 3) + "..." : text;
    }

    // 프로젝트 성과 관리
    private void managePerformance(String projectId) {
        try {
            while (true) {
                projectPerformance.setProjectCode(projectId, orgCode); // 현재 프로젝트 코드 세팅
                projectPerformance.printPerformanceList();             // 성과 리스트 출력
                System.out.println("""
                        1. 성과 추가
                        2. 성과 수정
                        3. 성과 삭제
                        0. 뒤로가기
                        """);
                String input = InputHandler.getOptionalMenuInput(br, "메뉴 선택 ▶ (0. 뒤로가기) ");

                switch (input) {
                    case "1" -> projectPerformance.addPerformance();
                    case "2" -> projectPerformance.updatePerformance();
                    case "3" -> projectPerformance.deletePerformance();
                    case "0" -> { return; }
                    default -> System.out.println("⚠️ 잘못된 입력입니다.\n");
                }
            }
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
		}
    }

    // 프로젝트 연구비 관리
    private void manageFund(String projectId) {
        try {
            projectFund.setProjectCode(projectId, orgCode); // 현재 프로젝트 코드 세팅
            while (true) {
                projectFund.printFundUsageList(); // 연구비 사용 내역 출력
                System.out.println("""
                        1. 연구비 사용 내역 추가
                        2. 연구비 사용 내역 수정
                        3. 연구비 사용 내역 삭제
                        0. 뒤로가기
                        """);
                String input = InputHandler.getOptionalMenuInput(br, "메뉴 선택 ▶ (0. 뒤로가기) ");

                switch (input) {
                    case "1" -> projectFund.addFundUsage();
                    case "2" -> projectFund.updateFundUsage();
                    case "3" -> projectFund.deleteFundUsage();
                    case "0" -> { return; }
                    default -> System.out.println("⚠️ 잘못된 입력입니다.\n");
                }
            }
        } catch (IOException e) {
        	 e.printStackTrace();
        } catch (RuntimeException e) {
        	 e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    // 프로젝트 마일스톤 관리
    private void manageMilestone(String projectId) {
        try {
            projectMilestone.setProjectCode(projectId);  // 현재 프로젝트 코드 세팅
            while (true) {
                projectMilestone.printMilestoneList();     // 마일스톤 리스트 출력
                System.out.println("""
                        1. 마일스톤 추가
                        2. 마일스톤 수정
                        3. 마일스톤 삭제
                        0. 뒤로가기
                        """);
                String input = InputHandler.getOptionalMenuInput(br, "메뉴 선택 ▶ (0. 뒤로가기) ");

                switch (input) {
                    case "1" -> projectMilestone.addMilestone();
                    case "2" -> projectMilestone.updateMilestone();
                    case "3" -> projectMilestone.deleteMilestone();
                    case "0" -> { return; }
                    default -> System.out.println("⚠️ 잘못된 입력입니다.\n");
                }
            }
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
		}
    }

    // 프로젝트 연구원 관리
    private void manageProjectResearcher(String projectId) {
        try {
            projectResearcher.setCode(projectId, orgCode); // 프로젝트 코드 세팅
            projectResearcher.manageProjectResearchers(); // 프로젝트 연구원 관리 UI 실행
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
		}
    }
}
