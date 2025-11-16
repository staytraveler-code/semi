package userInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import db.fund.management.FundManagementDAO;
import db.fund.management.FundManagementDAOImpl;
import db.fund.management.FundManagementDTO;
import db.researcher.ResearcherDAO;
import db.researcher.ResearcherDAOImpl;

public class Project_FundUI {
    private BufferedReader br;
    private FundManagementDAO fundDAO = new FundManagementDAOImpl();
    private ResearcherDAO resDAO = new ResearcherDAOImpl();
    private String projectCode; // 현재 프로젝트 코드
    private String orgCode; // 현재 기관 코드

    public void setBufferedReader(BufferedReader br) {
        this.br = br;
    }

    public void setProjectCode(String projectCode, String orgCode) {
        this.projectCode = projectCode;
        this.orgCode = orgCode;
    }

    public void printFundUsageList() {
        System.out.println("===== 연구비 사용 내역 =====");
        try {
            List<FundManagementDTO> list = fundDAO.listRecord(projectCode);
            if(list.size() == 0) {
            	System.out.println("(등록된 연구비 사용 내역이 없습니다)");
            } else {
            	for (FundManagementDTO dto : list) {
            		System.out.printf("코드: %s | 담당자 코드 : %s | 담당자: %s | 분류: %s | 사용일: %s | 금액: %d | 내용: %s | 업체: %s | 증빙: %s | 메모: %s%n",
            				dto.getFcode(), dto.getRcode(), dto.getCharger_name(), dto.getCategory(),
            				dto.getDate_used(), dto.getExpense(), dto.getContent(),
            				dto.getVendor_name(), dto.getProof_type(), dto.getMemo());
            	}
            }
        } catch (Exception e) {
            System.out.println("⚠️ 연구비 사용 내역 조회 오류: " + e.getMessage());
        }
        System.out.println("============================\n");
    }

    public void addFundUsage() throws IOException, SQLException {
        FundManagementDTO dto = new FundManagementDTO();
        dto.setPcode(projectCode);
        
        String input;

		while (true) {
			input = InputHandler.getOptionalInput(br, "연구원 코드 ▶ ");
			if (!input.isBlank()) {
				if (!resDAO.isOrgIncludeRes(orgCode, input)) {
					System.out.println("⚠️ 당신의 기관에 소속된 연구원이 아니거나, 존재하지 않는 연구원 코드입니다.\n");
					continue;
				}
				dto.setRcode(input); break; // 공백이 아니면서, 자신의 기관에 속해있으면 break
			} else {
				break; // 공백이면 break
			}
		}
		
        dto.setCategory(InputHandler.getRequiredInput(br, "분류 ▶ "));
        dto.setDate_used(InputHandler.getRequiredDateInput(br, "사용일 (YYYY-MM-DD) ▶ "));
        dto.setExpense(Long.parseLong(InputHandler.getRequiredInput(br, "금액 ▶ ")));
        dto.setContent(InputHandler.getRequiredInput(br, "내용 ▶ "));
        dto.setVendor_name(InputHandler.getOptionalInput(br, "업체명 ▶ "));
        dto.setProof_type(InputHandler.getRequiredInput(br, "증빙 ▶ "));
        dto.setMemo(InputHandler.getOptionalInput(br, "메모 ▶ "));

        try {
            fundDAO.insertRecord(dto);
            System.out.println("✅ 연구비 사용 내역 추가 완료!\n");
        } catch (Exception e) {
            System.out.println("⚠️ 추가 실패: " + e.getMessage());
        }
    }

    public void updateFundUsage() throws IOException {
    	
        FundManagementDTO dto = null;
        String fcode =  InputHandler.getOptionalInput(br, "수정할 코드 입력 ▶ ");

        try {
            if (!fundDAO.isProjectFundRecord(projectCode, fcode)) { 
                System.out.println("⚠️ 목록에 있는 연구비 코드를 입력해주세요.\n"); 
                return; 
            }
            
            dto = fundDAO.findByFundCode(fcode);
            String input;

			while (true) {
				input = InputHandler.getOptionalInput(br, "연구원 코드 (" + dto.getRcode() + ") ▶ ");
				if (!input.isBlank()) {
					if (!resDAO.isOrgIncludeRes(orgCode, input)) {
						System.out.println("⚠️ 당신의 기관에 소속된 연구원이 아니거나, 존재하지 않는 연구원 코드입니다.\n");
						continue;
					}
					dto.setRcode(input); break; // 공백이 아니면서, 자신의 기관에 속해있으면 break
				} else {
					break; // 공백이면 break
				}
			}
            
            input = InputHandler.getOptionalInput(br, "분류 (" + dto.getCategory() + ") ▶ ");
            if (!input.isBlank()) dto.setCategory(input);
            input = InputHandler.getOptionalDateInput(br, "사용일 (" + dto.getDate_used() + ") ▶ ");
            if (!input.isBlank()) dto.setDate_used(input);
            input = InputHandler.getOptionalInput(br, "금액 (" + dto.getExpense() + ") ▶ ");
            if (!input.isBlank()) dto.setExpense(Long.parseLong(input));
            input = InputHandler.getOptionalInput(br, "내용 (" + dto.getContent() + ") ▶ ");
            if (!input.isBlank()) dto.setContent(input);
            input = InputHandler.getOptionalInput(br, "업체명 (" + dto.getVendor_name() + ") ▶ "); 
            if (!input.isBlank()) dto.setVendor_name(input);
            input = InputHandler.getOptionalInput(br, "증빙 (" + dto.getProof_type() + ") ▶ ");
            if (!input.isBlank()) dto.setProof_type(input);
            input = InputHandler.getOptionalInput(br, "메모 (" + dto.getMemo() + ") ▶ ");
            if (!input.isBlank()) dto.setMemo(input);

            fundDAO.updateRecord(dto);
            
            System.out.println("✅ 연구비 사용 내역 수정 완료!\n");
        } catch (Exception e) { 
            System.out.println("⚠️ 수정 실패: " + e.getMessage()); 
        }
    }

    public void deleteFundUsage() throws IOException {

        String fcode = InputHandler.getOptionalInput(br, "삭제할 코드 입력 ▶ ");
        
        try { 
        	if (!fundDAO.isProjectFundRecord(projectCode, fcode)) { 
                System.out.println("⚠️ 목록에 있는 연구비 코드를 입력해주세요.\n"); 
                return; 
            }
        	
            fundDAO.deleteRecord(fcode); 
            System.out.println("✅ 삭제 완료!\n"); 
            
        } catch (Exception e) { 
            System.out.println("⚠️ 삭제 실패: " + e.getMessage()); 
        }
    }
}
