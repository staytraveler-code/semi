package userInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import db.researcher.ResearcherDAO;
import db.researcher.ResearcherDAOImpl;
import db.researcher.ResearcherDTO;

public class ResearcherUI {
    private BufferedReader br;
    private UI ui;
    private ResearcherDAO researcherDAO;
    private String orgCode; // 현재 로그인 기관

    public ResearcherUI(BufferedReader br, UI ui, String orgCode) {
        this.br = br;
        this.ui = ui;
        this.orgCode = orgCode;
        this.researcherDAO = new ResearcherDAOImpl();
    }

    // 연구원 관리 메인
    public void manageResearcherInformation() throws IOException {
        while (true) {
        	System.out.println();
            System.out.println("─────────────────────────────────────────────────────────────────────────────────────────────");
            System.out.println("                        [ " + orgCode + " ] 연구원 정보 관리");
            System.out.println("─────────────────────────────────────────────────────────────────────────────────────────────");
            printResearcherList();

            System.out.println("""
                1. 연구원 추가
                2. 연구원 수정
                3. 연구원 삭제
                0. 뒤로가기
                00. 종료
            """);

            String input = InputHandler.getOptionalInput(br, "선택 ▶ ");

            switch (input) {
                case "1" -> addResearcher();
                case "2" -> updateResearcher();
                case "3" -> deleteResearcher();
                case "0" -> { return; }
                case "00" -> ui.exit();
                default -> System.out.println("⚠️ 잘못된 입력입니다.\n");
            }
        }
    }

    // 연구원 목록 출력
    private void printResearcherList() {
        try {
            List<ResearcherDTO> list = researcherDAO.listResearchersByOrg(orgCode);
            
            if (list.isEmpty()) {
        		System.out.println("─────────────────────────────────────────");
        		System.out.println("⚠️ 기관에 소속된 연구원이 없습니다.");
        		System.out.println("─────────────────────────────────────────");
        		return;
        	}
        	
        	System.out.println("────────────────────────────────[ 기관 연구원 리스트 ]────────────────────────────────────");
            System.out.printf("%-10s │ %-10s │ %-11s │ %-25s%n",
                    		   "연구원코드", "이름", "전화번호", "이메일");
            System.out.println("─────────────────────────────────────────────────────────────────────────────────────────────");

            for (ResearcherDTO r : list) {
                System.out.printf("%-15s │ %-9s │ %-15s │ %-25s%n",
                        r.getResearcherCode(), r.getName(), r.getTel(), r.getEmail());
            }
            System.out.println("─────────────────────────────────────────────────────────────────────────────────────────────");
            System.out.println();

        } catch (SQLException e) {
            System.out.println("⚠️ 목록 조회 중 오류: " + e.getMessage());
        }
    }

    // 연구원 추가 (자기 기관만 가능)
    private void addResearcher() throws IOException {
        try {
            ResearcherDTO dto = new ResearcherDTO();
            dto.setOrgCode(orgCode); // 항상 자기 기관

            dto.setName(InputHandler.getRequiredInput(br, "이름 ▶ "));
            dto.setTel(InputHandler.getRequiredTelInput(br, "전화번호 ▶ "));
            dto.setEmail(InputHandler.getOptionalInput(br, "이메일 ▶ "));

            researcherDAO.insertResearcherDAO(dto);
            System.out.println("✅ 연구원 등록 완료\n");

        } catch (SQLException e) {
            System.out.println("❌ 연구원 등록 실패: " + e.getMessage());
        }
    }

    // 연구원 수정 (자기 기관 소속만 가능)
    private void updateResearcher() throws IOException {
        try {
            String rCode = InputHandler.getOptionalInput(br, "수정할 연구원 코드 ▶ ").toUpperCase();

            if (!researcherDAO.isOrgIncludeRes(orgCode, rCode)) {
                System.out.println("⚠️ 해당 연구원이 존재하지 않거나, 다른 기관 소속입니다.");
                SleepUtil.sleep(1000);
                return;
            }

            ResearcherDTO dto = researcherDAO.selectResearcherByCode(rCode);
            String input;

            System.out.println("⦁ [ " + rCode + " ] 정보 수정 (Enter: 기존값 유지)");

            input = InputHandler.getOptionalInput(br,  "이름 (" + dto.getName() + ") ▶ ");
            if (!input.isBlank()) dto.setName(input);
            input = InputHandler.getOptionalTelInput(br,  "전화번호 (" + dto.getTel() + ") ▶ ");
            if (!input.isBlank()) dto.setTel(input);
            input = InputHandler.getOptionalInput(br,  "이메일 (" + dto.getEmail() + ") ▶ ");
            if (!input.isBlank()) dto.setEmail(input);

            researcherDAO.updateResearcherDAO(dto);
            System.out.println("✅ 연구원 정보 수정 완료\n");

        } catch (SQLException e) {
            System.out.println("❌ 수정 실패: " + e.getMessage());
        }
    }

    // 연구원 삭제 (자기 기관 소속만 가능)
    private void deleteResearcher() throws IOException {
        try {
        	String rCode = InputHandler.getOptionalInput(br, "삭제할 연구원 코드 ▶ ").toUpperCase();

            if (!researcherDAO.isOrgIncludeRes(orgCode, rCode)) {
                System.out.println("⚠️ 해당 연구원이 존재하지 않거나, 다른 기관 소속입니다.");
                SleepUtil.sleep(1000);
                return;
            }

            researcherDAO.deleteResearcherDAO(rCode);
            System.out.println("✅ 연구원 삭제 완료\n");

        } catch (SQLException e) {
            System.out.println("❌ 삭제 실패: " + e.getMessage());
        }
    }
}
