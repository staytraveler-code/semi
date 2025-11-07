package db.milestone;

import java.util.List;

public class MainTest {
	public static void main(String[] args) {
		new MainTest().printMilestoneList();
	}

	public void printMilestoneList() { // 마일스톤 전체 출력
		MilestoneDAO dao = new MilestoneDAO(); // DAO 객체 생성
		List<MilestoneDTO> list = dao.listMilestone(); // 조회 메서드 호출

		if (list.isEmpty()) {
			System.out.println("(등록된 마일스톤이 없습니다)");
		}

		System.out.println("===== 마일스톤 목록 =====");

		System.out.printf("%-8s %-20s %-50s %-20s %-20s %10s %n", "코드", "목표", "내용", "계획완료일", "실제완료일", "상태");
		System.out.println("=".repeat(150));

		for (MilestoneDTO dto : list) {
			System.out.printf("%-8s %-20s %-50s %-20s %-20s %10s %n", dto.getMileCode(), dto.getName(), dto.getDesc(),
					dto.getPeDate(), dto.getAeDate(), dto.getStatus());
		}

		System.out.println("=".repeat(150));
	}
}