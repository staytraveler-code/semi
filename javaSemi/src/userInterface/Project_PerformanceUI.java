package userInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

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
    
    
    public void printPerformanceList() {
        System.out.println("===== 성과 목록 =====");

        // DAO에서 해당 기관 + 과제의 성과 목록 가져오기
        List<PerformanceManagementDTO> list = dao.performanceList(orgCode, projectCode);

        if (list.isEmpty()) {
            System.out.println("(등록된 성과가 없습니다)");
        } else {
            for (int i = 0; i < list.size(); i++) {
                PerformanceManagementDTO dto = list.get(i);
                System.out.printf("%d. [%s] %s | %s | %s | %s%n",
                        i + 1,
                        dto.getPerfCode(),
                        dto.getName(),
                        dto.getCategory(),
                        dto.getpDate(),
                        dto.getMemo());
            }
        }
        System.out.println("=====================\n");
    }
    

    public void addPerformance() throws IOException {

		System.out.println("추가할 성과 입력");
		System.out.print("제목 ▶ ");
		dto.setName(br.readLine());
		System.out.print("카테고리 ▶ ");
		dto.setCategory(br.readLine());
		System.out.print("내용 ▶ ");
		dto.setContent(br.readLine());
		System.out.print("성과발생일자 ▶ ");
		dto.setpDate(br.readLine());
		System.out.print("메모 ▶ ");
		dto.setMemo(br.readLine());

		int result = dao.insertPerformance(dto,projectCode);

		if (result > 0) {

			System.out.println("✅ 성과 추가 완료!\n");
		} else {
			System.out.println("✅ 성과 추가 실패\n");
		}

	}

    
    

    public void updatePerformance() throws IOException {
		try {
			PerformanceManagementDTO dto = new PerformanceManagementDTO();

			System.out.println("[성과 수정]");
			System.out.print("수정할 성과코드 입력: ");
			String perfCode = br.readLine();

			// 존재 여부 먼저 확인
			if (!dao.existsPerformanceCode(perfCode)) {
				System.out.println("해당 성과코드는 존재하지 않습니다. 수정할 수 없습니다.");
				return; // 메서드 종료
			}

			dto.setPerfCode(perfCode);

			System.out.print("이름: ");
			dto.setName(br.readLine());

			System.out.print("카테고리: ");
			dto.setCategory(br.readLine());

			System.out.print("내용: ");
			dto.setContent(br.readLine());

			System.out.print("날짜(YYYY-MM-DD): ");
			dto.setpDate(br.readLine());

			System.out.print("메모: ");
			dto.setMemo(br.readLine());

			int result = dao.updatePerformance(dto);

			if (result > 0) {
				System.out.println("성과 정보가 성공적으로 수정되었습니다.");
			} else {
				System.out.println("성과 수정에 실패했습니다.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deletePerformance() throws IOException {
		try {
			System.out.println("[성과 삭제]");
			System.out.print("삭제할 성과코드 입력 ▶ ");
			String perfCode = br.readLine();

			if (!dao.existsPerformanceCode(perfCode)) {
				System.out.println("해당 성과코드는 존재하지 않습니다. 삭제할 수 없습니다.");
				return;
			}

			int result = dao.deletePerformance(perfCode);

			if (result > 0) {
				System.out.println("✅ 삭제 완료!\n");
			} else {
				System.out.println("삭제 중 오류가 발생했습니다.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
