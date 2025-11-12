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
            System.out.println("\n======================================================================================");
            System.out.println("              연구원 정보 관리 (" + orgCode + ")");
            System.out.println("=======================================================================================");
            printResearcherList();

            System.out.println("""
                1. 연구원 추가
                2. 연구원 수정
                3. 연구원 삭제
                0. 뒤로가기
                00. 종료
            """);

            System.out.print("선택 ▶ ");
            String input = br.readLine();

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
            List<ResearcherDTO> list = ((ResearcherDAOImpl) researcherDAO).listResearchersByOrg(orgCode);

            if (list.isEmpty()) {
                System.out.println("⚠️ 등록된 연구원이 없습니다.\n");
                return;
            }

            System.out.printf("%-10s │ %-15s │ %-15s │ %-25s%n",
                    "연구원코드", "이름", "전화번호", "이메일");
            System.out.println("────────────────────────────────────────────────────────────────────────────────────────");

            for (ResearcherDTO r : list) {
                System.out.printf("%-10s │ %-15s │ %-15s │ %-25s%n",
                        r.getResearcherCode(), r.getName(), r.getTel(), r.getEmail());
            }
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

            System.out.print("이름 ▶ ");
            dto.setName(br.readLine());
            System.out.print("전화번호 ▶ ");
            dto.setTel(br.readLine());
            System.out.print("이메일 ▶ ");
            dto.setEmail(br.readLine());

            researcherDAO.insertResearcherDAO(dto);
            System.out.println("✅ 연구원 등록 완료\n");

        } catch (SQLException e) {
            System.out.println("❌ 연구원 등록 실패: " + e.getMessage());
        }
    }

    // 연구원 수정 (자기 기관 소속만 가능)
    private void updateResearcher() throws IOException {
        try {
            System.out.print("수정할 연구원 코드 ▶ ");
            String code = br.readLine();

            ResearcherDTO dto = ((ResearcherDAOImpl) researcherDAO).selectResearcherByCode(code);
            if (dto == null) {
                System.out.println("⚠️ 해당 연구원이 존재하지 않습니다.");
                return;
            }

            // 소속 기관 체크
            if (!dto.getOrgCode().equals(orgCode)) {
                System.out.println("⚠️ 수정 불가: 해당 연구원은 다른 기관 소속입니다.");
                return;
            }

            System.out.println("연구원 정보 수정 (Enter: 기존값 유지)");
            System.out.println("----------------------------------------");

            System.out.println("현재 이름: " + dto.getName());
            System.out.print("이름 ▶ ");
            String newName = br.readLine();
            if (!newName.isBlank()) dto.setName(newName);

            System.out.println("현재 전화번호: " + dto.getTel());
            System.out.print("전화번호 ▶ ");
            String newTel = br.readLine();
            if (!newTel.isBlank()) dto.setTel(newTel);

            System.out.println("현재 이메일: " + dto.getEmail());
            System.out.print("이메일 ▶ ");
            String newEmail = br.readLine();
            if (!newEmail.isBlank()) dto.setEmail(newEmail);

            researcherDAO.updateResearcherDAO(dto);
            System.out.println("✅ 연구원 정보 수정 완료\n");

        } catch (SQLException e) {
            System.out.println("❌ 수정 실패: " + e.getMessage());
        }
    }

    // 연구원 삭제 (자기 기관 소속만 가능)
    private void deleteResearcher() throws IOException {
        try {
            System.out.print("삭제할 연구원 코드 ▶ ");
            String code = br.readLine();

            ResearcherDTO dto = ((ResearcherDAOImpl) researcherDAO).selectResearcherByCode(code);
            if (dto == null) {
                System.out.println("⚠️ 해당 연구원이 존재하지 않습니다.");
                return;
            }

            // 소속 기관 체크
            if (!dto.getOrgCode().equals(orgCode)) {
                System.out.println("⚠️ 삭제 불가: 해당 연구원은 다른 기관 소속입니다.");
                return;
            }

            ((ResearcherDAOImpl) researcherDAO).deleteResearcherDAO(code);
            System.out.println("✅ 연구원 삭제 완료\n");

        } catch (SQLException e) {
            System.out.println("❌ 삭제 실패: " + e.getMessage());
        }
    }
}
