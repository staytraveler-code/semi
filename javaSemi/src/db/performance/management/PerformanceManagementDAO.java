package db.performance.management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.util.DBConn;
import db.util.DBUtil;

public class PerformanceManagementDAO {
	Connection conn = DBConn.getConnection();

	// 과제별 성과 목록 조회 -- 완료
	public List<PerformanceManagementDTO> performanceList(String projectCode) throws Exception {
		List<PerformanceManagementDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT performance_code AS code, name, category, content, p_date, memo "
					+ "FROM performance_management WHERE project_code = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, projectCode); // 선택된 과제 코드

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PerformanceManagementDTO dto = new PerformanceManagementDTO();
				dto.setPerfCode(rs.getString("code"));
				dto.setName(rs.getString("name"));
				dto.setCategory(rs.getString("category"));
				dto.setContent(rs.getString("content"));
				dto.setpDate(rs.getString("p_date"));
				dto.setMemo(rs.getString("memo"));
				list.add(dto);

			}

		} catch (Exception e) {
			throw e;
		} finally {
			DBUtil.close(pstmt);
			DBUtil.close(rs);
		}
		return list;
	}

	// 성과 추가 -- 완료
	public int insertPerformance(PerformanceManagementDTO dto, String projectCode) throws Exception {
		int result = 0;
		try {
			conn.setAutoCommit(false);

			String sql = "INSERT INTO performance_management (performance_code, project_code, "
					+ "name, category, content, p_date, memo) VALUES ('PERF_' || LPAD(SEQ_PERFORMANCE_MANAGEMENT.NEXTVAL, 3, '0'), "
					+ "?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, projectCode);
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getCategory());
			pstmt.setString(4, dto.getContent());
			pstmt.setDate(5, parseDate(dto.getpDate(), null));
			pstmt.setString(6, dto.getMemo());
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

	// 성과 업데이트 -- 완료
	public int updatePerformance(PerformanceManagementDTO dto) throws Exception {
		int result = 0;
		String sql = "UPDATE performance_management SET name=?, category=?, content=?, "
				+ "p_date=TO_DATE(?, 'YYYYMMDD'), memo=? WHERE performance_code=? ";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			conn.setAutoCommit(false);

			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getCategory());
			pstmt.setString(3, dto.getContent());
			pstmt.setDate(4, parseDate(dto.getpDate(), null));
			pstmt.setString(5, dto.getMemo());
			pstmt.setString(6, dto.getPerfCode());
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

	// 성과 삭제 -- 완료
	public int deletePerformance(String perfCode) throws Exception {
		int result = 0;
		String sql = "DELETE FROM performance_management WHERE performance_code = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			conn.setAutoCommit(false);
			
			pstmt.setString(1, perfCode);
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

	// 코드 판별(존재여부)
	public boolean existsPerformanceCode(String perfCode) throws Exception {
		boolean exists = false;
		try {
			String sql = "SELECT COUNT(*) AS cnt FROM performance_management WHERE performance_code = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, perfCode);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next() && rs.getInt("cnt") > 0) {
				exists = true;
			}
		} catch (Exception e) {
			throw new Exception("코드 존재 여부 확인 중 오류", e);
		}
		return exists;
	}

	// 과제에 성과가 있는지 확인
	public boolean isPerformnace(String code, String projectCode) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT 1 "
					+ "FROM performance_management WHERE performance_code = ? AND project_code = ?";

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

	private java.sql.Date parseDate(String dateStr, String existingDate) {
		if (dateStr == null || dateStr.isBlank())
			dateStr = existingDate;
		if (dateStr == null || dateStr.isBlank())
			return null;

		try {
			return java.sql.Date.valueOf(dateStr); // 시분초 없이 연월일만
		} catch (IllegalArgumentException e) {
			System.out.println("⚠️ 잘못된 날짜 형식: " + dateStr + " (YYYY-MM-DD)");
			return null;
		}
	}

}