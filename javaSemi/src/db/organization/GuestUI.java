package db.organization;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class GuestUI {

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private ResearchInstituteDAO dao = new ResearchInstituteDAOImpl(); 

    
    // 기관 회원가입
  
    public void signUp() {
        System.out.println("\n[기관 회원가입]");
        try {
            ResearchInstituteDTO dto = new ResearchInstituteDTO();

            System.out.print("기관 코드: ");
            dto.setOrgCode(br.readLine());

            System.out.print("기관명: ");
            dto.setOrgName(br.readLine());

            System.out.print("기관유형(대학/기업/정부출연): ");
            dto.setOrgType(br.readLine());

            System.out.print("사업자등록번호: ");
            dto.setBizRegNo(br.readLine());

            System.out.print("연락처: ");
            dto.setOrgPhone(br.readLine());

            System.out.print("이메일: ");
            dto.setOrgEmail(br.readLine());

            System.out.print("주소: ");
            dto.setOrgAddress(br.readLine());

            System.out.print("아이디: ");
            dto.setLoginId(br.readLine());

            System.out.print("비밀번호: ");
            dto.setLoginPwd(br.readLine());

            dao.insertTeam(dto);
            System.out.println("회원가입이 완료되었습니다.");

        } catch (SQLIntegrityConstraintViolationException e) {
            if (e.getErrorCode() == 1) {
                System.out.println("이미 존재하는 아이디입니다. 다른 아이디를 사용해주세요.");
            } else if (e.getErrorCode() == 1400) {
                System.out.println("필수 입력사항을 입력하지 않았습니다.");
            } else {
                System.out.println("데이터 입력 오류: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("데이터베이스 오류가 발생했습니다: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    
    // 로그인
   
    public void signIn() {
        System.out.println("\n[로그인]");
        try {
            System.out.print("아이디: ");
            String id = br.readLine();

            System.out.print("비밀번호: ");
            String pwd = br.readLine();

            ResearchInstituteDTO dto = dao.findById(id);

            if (dto == null || !dto.getLoginPwd().equals(pwd)) {
                System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
                return;
            }

            System.out.println(dto.getOrgName() + "님, 로그인 성공!");

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}

