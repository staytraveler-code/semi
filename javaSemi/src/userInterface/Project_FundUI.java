package userInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import db.fund.management.FundManagementDAOImpl;
import db.fund.management.FundManagementDTO;

public class Project_FundUI {
    private BufferedReader br;
    private FundManagementDAOImpl dao = new FundManagementDAOImpl();

    public void setBufferedReader(BufferedReader br) {
        this.br = br;
    }

    public void printFundUsageList() {
        System.out.println("===== 연구비 사용 내역 =====");

		List<FundManagementDTO> list = dao.listRecord();

		for (FundManagementDTO dto : list) {
			System.out.print(dto.getFcode() + "\t");
			System.out.print(dto.getPcode() + "\t");
			System.out.print(dto.getRcode() + "\t");
			System.out.print(dto.getCharger_name() + "\t");
			System.out.print(dto.getCategory() + "\t");
			System.out.print(dto.getDate_used() + "\t");
			System.out.print(dto.getExpense() + "\t");
			System.out.print(dto.getContent() + "\t");
			System.out.print(dto.getVendor_name() + "\t");
			System.out.print(dto.getProof_type() + "\t");
			System.out.println(dto.getMemo());
		}
		System.out.println();
        System.out.println("===========================\n");
    }

    public void addFundUsageList() throws IOException {
    	System.out.println("\n[사용 내역 추가]");

		try {
			FundManagementDTO dto = new FundManagementDTO();

			System.out.print("프로젝트 코드 입력 ▶ ");
			dto.setPcode(br.readLine());
			
			System.out.print("집행 연구원 코드 입력 ▶ ");
			dto.setRcode(br.readLine());
			
			System.out.print("집행 연구원 이름 입력 ▶ ");
			dto.setCharger_name(br.readLine());
			
			System.out.print("카테고리 입력 ▶ ");
			dto.setCategory(br.readLine());
			
			System.out.print("집행 일자 입력 ▶ ");
			dto.setDate_used(br.readLine());
			
			System.out.print("집행 금액 입력 ▶ ");
			dto.setExpense(Long.parseLong(br.readLine()));
			
			System.out.print("사용 내역 입력 ▶ ");
			dto.setContent(br.readLine());
			
			System.out.print("거래처명 입력 ▶ ");
			dto.setVendor_name(br.readLine());
			
			System.out.print("증빙 유형 입력 ▶ ");
			dto.setProof_type(br.readLine());
			
			System.out.print("비고 입력 ▶ ");
			dto.setMemo(br.readLine());

			dao.insertRecord(dto);

			System.out.println("정상적으로 추가되었습니다.");
		} catch (SQLException e) {
			if (e.getErrorCode() == 1407) { // UPDATE - NOT NULL 위반
				System.out.println("에러-필수 입력사항을 입력하지 않았습니다.");
			} else if(e.getErrorCode()==1840 || e.getErrorCode()==1861) {
				System.out.println("날짜 입력 형식 오류입니다.");
			} else {
				System.out.println(e.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
		
    }

    public void updateFundUsageList() throws IOException {
    	System.out.println("\n[사용 내역 수정]");

		try {
			FundManagementDTO dto = new FundManagementDTO();
			
			System.out.print("수정할 코드 입력 ▶ ");
			dto.setFcode(Integer.parseInt(br.readLine()));

			System.out.print("프로젝트 코드 입력 ▶ ");
			dto.setPcode(br.readLine());
			
			System.out.print("집행 연구원 코드 입력 ▶ ");
			dto.setRcode(br.readLine());
			
			System.out.print("집행 연구원 이름 입력 ▶ ");
			dto.setCharger_name(br.readLine());
			
			System.out.print("카테고리 입력 ▶ ");
			dto.setCategory(br.readLine());
			
			System.out.print("집행 일자 입력 ▶ ");
			dto.setDate_used(br.readLine());
			
			System.out.print("집행 금액 입력 ▶ ");
			dto.setExpense(Long.parseLong(br.readLine()));
			
			System.out.print("사용 내역 입력 ▶ ");
			dto.setContent(br.readLine());
			
			System.out.print("거래처명 입력 ▶ ");
			dto.setVendor_name(br.readLine());
			
			System.out.print("증빙 유형 입력 ▶ ");
			dto.setProof_type(br.readLine());
			
			System.out.print("비고 입력 ▶ ");
			dto.setMemo(br.readLine());

			dao.updateRecord(dto);

			System.out.println("사용 내역이 수정되었습니다.");
		} catch (SQLException e) {
			if (e.getErrorCode() == 1407) { // UPDATE - NOT NULL 위반
				System.out.println("에러-필수 입력사항을 입력하지 않았습니다.");
			} else if(e.getErrorCode()==1840 || e.getErrorCode()==1861) {
				System.out.println("날짜 입력 형식 오류입니다.");
			} else {
				System.out.println(e.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
      
    }

    public void deleteFundUsageList() throws IOException {
    	System.out.println("\n[사용 내역 삭제]");

		try {
			int code;

			System.out.print("삭제할 프로젝트 코드 입력 ▶ ");
			code = Integer.parseInt(br.readLine());

			dao.deleteRecord(code);

			System.out.println("사용 내역이 삭제되었습니다.");
		} catch (SQLException e) {
			if (e.getErrorCode() == 1407) { // UPDATE - NOT NULL 위반
				System.out.println("에러-필수 입력사항을 입력하지 않았습니다.");
			} else if(e.getErrorCode()==1840 || e.getErrorCode()==1861) {
				System.out.println("날짜 입력 형식 오류입니다.");
			} else {
				System.out.println(e.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
    }
}
