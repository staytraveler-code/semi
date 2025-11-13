package userInterface;

import db.fund.management.FundManagementDAO;
import db.fund.management.FundManagementDAOImpl;
import db.fund.management.FundManagementDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class Project_FundUI {
    private BufferedReader br;
    private FundManagementDAO fundDAO = new FundManagementDAOImpl();
    private String projectCode; // 현재 프로젝트 코드

    public void setBufferedReader(BufferedReader br) {
        this.br = br;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public void printFundUsageList() {
        System.out.println("===== 연구비 사용 내역 =====");
        try {
            List<FundManagementDTO> list = fundDAO.listRecord(projectCode);
            if(list.size() == 0) {
            	System.out.println("(등록된 연구비 사용 내역이 없습니다)");
            } else {
            	for (FundManagementDTO dto : list) {
            		System.out.printf("코드: %s | 담당자: %s | 분류: %s | 사용일: %s | 금액: %d | 내용: %s | 업체: %s | 증빙: %s | 메모: %s%n",
            				dto.getFcode(), dto.getCharger_name(), dto.getCategory(),
            				dto.getDate_used(), dto.getExpense(), dto.getContent(),
            				dto.getVendor_name(), dto.getProof_type(), dto.getMemo());
            	}
            }
        } catch (Exception e) {
            System.out.println("⚠️ 연구비 사용 내역 조회 오류: " + e.getMessage());
        }
        System.out.println("============================\n");
    }

    public void addFundUsage() throws IOException {
        FundManagementDTO dto = new FundManagementDTO();
        dto.setPcode(projectCode);
        System.out.print("연구원 코드 ▶ "); dto.setRcode(br.readLine());
        System.out.print("담당자 이름 ▶ "); dto.setCharger_name(br.readLine());
        System.out.print("분류 ▶ "); dto.setCategory(br.readLine());
        System.out.print("사용일 (YYYY-MM-DD) ▶ "); dto.setDate_used(br.readLine());
        System.out.print("금액 ▶ "); dto.setExpense(Long.parseLong(br.readLine()));
        System.out.print("내용 ▶ "); dto.setContent(br.readLine());
        System.out.print("업체명 ▶ "); dto.setVendor_name(br.readLine());
        System.out.print("증빙 ▶ "); dto.setProof_type(br.readLine());
        System.out.print("메모 ▶ "); dto.setMemo(br.readLine());

        try {
            fundDAO.insertRecord(dto);
            System.out.println("✅ 연구비 사용 내역 추가 완료!\n");
        } catch (Exception e) {
            System.out.println("⚠️ 추가 실패: " + e.getMessage());
        }
    }

    public void updateFundUsage() throws IOException {
    	
        printFundUsageList();
        
        FundManagementDTO dto = null;
        System.out.print("수정할 코드 입력 ▶ "); 
        String fcode = br.readLine();

        try {
            if (!fundDAO.isProjectFundRecord(projectCode, fcode)) { 
                System.out.println("⚠️ 권한이 없거나 존재하지 않는 코드입니다.\n"); 
                return; 
            }
            
            dto = fundDAO.findByFundCode(fcode);
            String input;

            System.out.print("담당자 이름 (" + dto.getCharger_name() + ") ▶ "); 
            input = br.readLine(); if (!input.isEmpty()) dto.setCharger_name(input);
            System.out.print("분류 (" + dto.getCategory() + ") ▶ ");
            input = br.readLine(); if (!input.isEmpty()) dto.setCategory(input);
            System.out.print("사용일 (" + dto.getDate_used() + ") ▶ "); 
            input = br.readLine(); if (!input.isEmpty()) dto.setDate_used(input);
            System.out.print("금액 (" + dto.getExpense() + ") ▶ "); 
            input = br.readLine(); if (!input.isEmpty()) dto.setExpense(Long.parseLong(input));
            System.out.print("내용 (" + dto.getContent() + ") ▶ "); 
            input = br.readLine(); if (!input.isEmpty()) dto.setContent(input);
            System.out.print("업체명 (" + dto.getVendor_name() + ") ▶ "); 
            input = br.readLine(); if (!input.isEmpty()) dto.setVendor_name(input);
            System.out.print("증빙 (" + dto.getProof_type() + ") ▶ "); 
            input = br.readLine(); if (!input.isEmpty()) dto.setProof_type(input);
            System.out.print("메모 (" + dto.getMemo() + ") ▶ "); 
            input = br.readLine(); if (!input.isEmpty()) dto.setMemo(input);

            fundDAO.updateRecord(dto);
            
            System.out.println("✅ 연구비 사용 내역 수정 완료!\n");
        } catch (Exception e) { 
            System.out.println("⚠️ 수정 실패: " + e.getMessage()); 
        }
    }

    public void deleteFundUsage() throws IOException {
    	
        printFundUsageList();
        
        System.out.print("삭제할 코드 입력 ▶ "); 
        String fcode = br.readLine();
        
        try { 
        	if (!fundDAO.isProjectFundRecord(projectCode, fcode)) { 
                System.out.println("⚠️ 권한이 없거나 존재하지 않는 코드입니다.\n"); 
                return; 
            }
        	
            fundDAO.deleteRecord(fcode); 
            
            System.out.println("✅ 삭제 완료!\n"); 
        } catch (Exception e) { 
            System.out.println("⚠️ 삭제 실패: " + e.getMessage()); 
        }
    }
}
