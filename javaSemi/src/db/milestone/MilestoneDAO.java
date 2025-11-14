package db.milestone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.util.DBConn;
import db.util.DBUtil;

public class MilestoneDAO {
	private final Connection conn = DBConn.getConnection();

//    public MilestoneDAO() {
//        this.conn = DBConn.getConnection();
//    }

	// 특정 프로젝트 마일스톤 목록 -- 완료
	public List<MilestoneDTO> listMilestoneByProject(String projectCode) throws Exception {
		List<MilestoneDTO> list = new ArrayList<>();
		String sql = "SELECT milestone_code, project_code, name, description, p_end_date, a_end_date, status "
				+ "FROM Milestone WHERE project_code = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, projectCode);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					MilestoneDTO dto = new MilestoneDTO();
					dto.setMileCode(rs.getString("milestone_code"));
					dto.setpCode(rs.getString("project_code"));
					dto.setName(rs.getString("name"));
					dto.setDesc(rs.getString("description"));
					dto.setPeDate(rs.getString("p_end_date"));
					dto.setAeDate(rs.getString("a_end_date"));
					dto.setStatus(rs.getString("status"));
					list.add(dto);
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	// 마일스톤 추가 -- 완료
	public int insertMilestone(MilestoneDTO dto, String projectCode) throws Exception {
		int result = 0;
		String sql = "INSERT INTO milestone (milestone_code, project_code, name, description, p_end_date, a_end_date, status) "
				+ "VALUES ('MS_' || LPAD(seq_milestone.NEXTVAL, 3, '0'), ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), TO_DATE(?, 'YYYY-MM-DD'), ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			conn.setAutoCommit(false);

			pstmt.setString(1, projectCode);
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getDesc());
			pstmt.setString(4, dto.getPeDate().substring(0,10));
			pstmt.setString(5, dto.getAeDate().substring(0,10));
			pstmt.setString(6, dto.getStatus());

			result = pstmt.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			DBUtil.rollback(conn);
			throw e;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
			}
		}
		return result;
	}

	// 마일스톤 수정 -- 완료
	public int updateMilestone(MilestoneDTO dto) throws Exception {
		int result = 0;
		String sql = "UPDATE Milestone SET name=?, description=?, p_end_date=TO_DATE(?, 'YYYY-MM-DD'), a_end_date=TO_DATE(?, 'YYYY-MM-DD'), status=? "
				+ "WHERE milestone_code=? AND project_code=?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			conn.setAutoCommit(false);

			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getDesc());
			pstmt.setString(3, dto.getPeDate().substring(0,10));
			pstmt.setString(4, dto.getAeDate().substring(0,10));
			pstmt.setString(5, dto.getStatus());
			pstmt.setString(6, dto.getMileCode());
			pstmt.setString(7, dto.getpCode());

			result = pstmt.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			DBUtil.rollback(conn);
			throw e;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
			}
		}
		return result;
	}

	// 마일스톤 삭제 -- 완료.
	public int deleteMilestone(String mileCode) throws Exception {
		int result = 0;
		String sql = "DELETE FROM Milestone WHERE milestone_code=? ";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			conn.setAutoCommit(false);

			pstmt.setString(1, mileCode);
			result = pstmt.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			DBUtil.rollback(conn);
			throw e;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
			}
		}
		return result;
	}

	// 해당 목록에 있는 마일스톤 코드를 입력해야 참 반환 --완료
	public boolean isMilestoneBelongsToOrg(String code, String projectCode) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT 1 " + "FROM milestone WHERE milestone_code = ? AND project_code = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, code);
			pstmt.setString(2, projectCode);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return false;
	}

//	private java.sql.Date parseDate(String dateStr, String existingDate) {
//		if (dateStr == null || dateStr.isBlank())
//			dateStr = existingDate;
//		if (dateStr == null || dateStr.isBlank())
//			return null;
//
//		try {
//			return java.sql.Date.valueOf(dateStr); // 시분초 없이 연월일만
//		} catch (IllegalArgumentException e) {
//			System.out.println("⚠️ 잘못된 날짜 형식: " + dateStr + " (YYYY-MM-DD)");
//			return null;
//		}
//	}
}
