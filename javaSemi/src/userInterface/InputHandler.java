package userInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class InputHandler {
	
	private static String phoneRegex = "^\\d{2,3}-\\d{3,4}-\\d{4}$";
	// 010-1234-5678, 02-123-4567, 031-1234-5678 등
	
	private static String brnRegex = "^\\d{3}-\\d{2}-\\d{5}$";
	// 사업자등록번호 정규표현식 ( ex. 000-00-00000 )
	
	/**
     * 사용자에게 메시지를 보여주고, 빈 값이 아닌 유효한 입력을 받을 때까지
     * 반복해서 입력을 요청하는 메소드
     * 필수 입력 항목의 경우 해당 메소드 사용
     *
     * @param br     BufferedReader 객체
     * @return trim() 처리된 유효한 입력 문자열
     * @throws IOException, InputCancelledException
     */
	
    public static String getRequiredInput(BufferedReader br, String prompt) throws IOException, InputCancelledException {
        String input = ""; // 반환할 변수
        
        while (true) {
            System.out.print(prompt); // prompt 출력
            input = br.readLine();
            if(input.trim().equals("00")) throw new InputCancelledException();

            // null이 아니고, 좌우 공백 제거 후 비어있지 않다면
            if (input != null && !input.trim().isEmpty()) {
                return input.trim(); // 유효한 값이므로 공백 제거 후 반환
            }
            
            // 유효하지 않으면 오류 메시지 출력 후 루프 계속
            System.out.println("⚠️ 필수 입력 항목입니다. 다시 입력해주세요.");
        }
    }
    
    /**
     * 빈 값이 입력되면 defaultValue 값을 반환하고
     * 빈 값이 아니라면 입력된 값을 반환하는 메소드
     * 필수 입력 항목이지만 공백을 입력하는 경우 기존 값을 부여하고 싶다면 해당 메소드 사용
     *
     * @param br     BufferedReader 객체
     * @return defaultValue (공백 입력 시) 인자로 넘긴 기존 값 반환
     * @return trim() (공백이 아닐 시) 처리된 유효한 입력 문자열 반환
     * @throws IOException, InputCancelledException
     */
    
	public static String getRequiredInput(BufferedReader br, String prompt, String defaultValue) throws IOException, InputCancelledException {
		String input = "";

		System.out.print(prompt);
		input = br.readLine();
		if(input.trim().equals("00")) throw new InputCancelledException();

		if (input != null && !input.trim().isEmpty()) {
			return input.trim(); // 공백이 아니라면 좌우 공백 제거 후 반환
		} else {
			return defaultValue; // 공백 입력 시, defaultValue 반환			
		}
	}
	
	/**
     * 빈 값이면 빈 값 그대로 반환
     * 선택 입력 항목에 해당 메소드 사용
     *
     * @param br     BufferedReader 객체
     * @return trim() 처리된 유효한 입력 문자열 반환
     * @throws IOException, InputCancelledException
     */
	
	public static String getOptionalInput(BufferedReader br, String prompt) throws IOException, InputCancelledException {
		String input = "";

		System.out.print(prompt);
		input = br.readLine();
		if(input.trim().equals("00")) throw new InputCancelledException();

		return input.trim();
	}
	
	public static String getOptionalMenuInput(BufferedReader br, String prompt) throws IOException {
		String input = "";

		System.out.print(prompt);
		input = br.readLine();

		return input.trim();
	}

	// 전화번호 입력 메소드 ( 필수 )
	public static String getRequiredTelInput(BufferedReader br, String prompt) throws IOException, InputCancelledException {
        String input = "";
        
        while (true) {
            input = getRequiredInput(br, prompt);

            if (input.matches(phoneRegex)) {
                return input;
            } 
            
            System.out.println("⚠️ 전화번호 형식이 올바르지 않습니다.");
        }
	}
	
	// 전화번호 입력 메소드 ( 선택 )
	public static String getOptionalTelInput(BufferedReader br, String prompt) throws IOException, InputCancelledException {
		String input = "";

		while (true) {
			System.out.print(prompt);
			input = br.readLine();
			if(input.trim().equals("00")) throw new InputCancelledException();

			if (input == null || input.trim().isEmpty()) {
				return input; // 공백 입력 시 그대로 공백 반환
			}
			
			if (input.matches(phoneRegex)) {
				return input; // 공백이 아니라면 형식에 맞는지 확인
			}

			System.out.println("⚠️ 전화번호 형식이 올바르지 않습니다.");
		}
	}
	
	// 날짜 입력 메소드 ( 필수 )
	public static String getRequiredDateInput(BufferedReader br, String prompt) throws IOException, InputCancelledException {
		
		String input = "";
		while (true) {
            input = getRequiredInput(br, prompt);

            try {
                // LocalDate.parse는 YYYY-MM-DD 형식을 기본으로 사용합니다.
                // 이 라인이 성공하면 -> 형식이 맞고, 실제 존재하는 날짜임
                LocalDate.parse(input); 
                return input;
                
            } catch (DateTimeParseException e) {
                // 파싱 실패 시 -> 형식이 다르거나 (e.g., 20250101)
                //               존재하지 않는 날짜 (e.g., 2025-02-30)
                System.out.println("⚠️ 날짜 형식이 올바르지 않거나 유효하지 않은 날짜입니다. (ex. YYYY-MM-DD)");
            }
        }
	}
	
	// 날짜 입력 메소드 ( 선택 )
	public static String getOptionalDateInput(BufferedReader br, String prompt) throws IOException, InputCancelledException {

		String input = "";
		while (true) {
			System.out.print(prompt);
			input = br.readLine();
			if(input.trim().equals("00")) throw new InputCancelledException();
			
			if (input == null || input.trim().isEmpty()) {
				return input; // 공백 입력 시 그대로 공백 반환
			}

			try {
				LocalDate.parse(input);
				return input;

			} catch (DateTimeParseException e) {
				System.out.println("⚠️ 날짜 형식이 올바르지 않거나 유효하지 않은 날짜입니다. (ex. YYYY-MM-DD)");
			}
		}
	}
	
	// 사업자등록번호 입력 메소드 ( 필수 )
	public static String getRequiredBizRegInput(BufferedReader br, String prompt) throws IOException, InputCancelledException {
		String input = "";
        
        while (true) {
            input = getRequiredInput(br, prompt);
            
            if (input.matches(brnRegex)) {
                return input;
            }
            
            System.out.println("⚠️ 사업자등록번호 형식이 올바르지 않습니다. (ex. 123-45-67890)");
        }
	}
	
	// 사업자등록번호 입력 메소드 ( 선택 )
	public static String getOptionalBizRegInput(BufferedReader br, String prompt) throws IOException, InputCancelledException {
		String input = "";

		while (true) {
			System.out.print(prompt);
			input = br.readLine();
			if(input.trim().equals("00")) throw new InputCancelledException();

			if (input == null || input.trim().isEmpty()) {
				return input; // 공백 입력 시 그대로 공백 반환
			}

			if (input.matches(brnRegex)) {
				return input; // 공백이 아니라면 형식에 맞는지 확인
			}

			System.out.println("⚠️ 전화번호 형식이 올바르지 않습니다.");
		}
	}
	
	// 날짜 비교 메소드
	public static boolean compareDateInput(String sDate, String eDate) throws Exception {
		try {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = f.parse(sDate);
		Date endDate = f.parse(eDate);
		
		return startDate.before(endDate);
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}		
	}
	
}
