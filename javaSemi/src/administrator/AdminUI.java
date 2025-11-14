package administrator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import db.util.DBConn;

public class AdminUI {

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public AdminUI() throws IOException {
		showMenu();
	}

	public  void showMenu() throws IOException {

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
			case "1" -> System.out.println("등록된기관"); //등록된 기관 전체조회
			case "2" -> System.out.println("등록된과제"); //등록된 과제 전체조회
			case "0" -> exit();
			default -> System.out.println("⚠️ 잘못된 입력입니다.\n");
			}

		}

	}
	
	// 등록된 기관
	
	
	
	
	// 등록된 과제

	
	
	
	
	
	
	
	
	
	
	
	//종료
	public void exit() {
        System.out.println("프로그램 종료");
        DBConn.close();
        System.exit(0);
    }

}