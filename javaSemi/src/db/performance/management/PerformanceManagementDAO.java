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

	// 과제별 성과 목록 조회
	public List<PerformanceManagementDTO> performanceList(String projectCode) throws Exception {
		List<PerformanceManagementDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT performance_code AS code, name, category, content, p_date, memo "
					+ "FROM performance_management WHERE project_code = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, projectCode);

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
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
			DBUtil.close(rs);
		}
		return list;
	}

	// 성과 추가
	public void insertPerformance(PerformanceManagementDTO dto, String projectCode) throws Exception {
		try {
			conn.setAutoCommit(false);

			String sql = "INSERT INTO performance_management (performance_code, project_code, "
					+ "name, category, content, p_date, memo) VALUES ('PERF_' || LPAD(SEQ_PERFORMANCE_MANAGEMENT.NEXTVAL, 3, '0'), "
					+ "?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?)";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, projectCode);
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getCategory());
			pstmt.setString(4, dto.getContent());
			pstmt.setString(5, dto.getpDate().substring(0,10));
			pstmt.setString(6, dto.getMemo());
			
			pstmt.executeUpdate();
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
	}

	// 성과 수정
	public void updatePerformance(PerformanceManagementDTO dto) throws Exception {
		
		String sql = "UPDATE performance_management SET name=?, category=?, content=?, "
				+ "p_date=TO_DATE(?, 'YYYY-MM-DD'), memo=? WHERE performance_code=? ";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			conn.setAutoCommit(false);

			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getCategory());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getpDate().substring(0,10));
			pstmt.setString(5, dto.getMemo());
			pstmt.setString(6, dto.getPerfCode());
			
			pstmt.executeUpdate();
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
	}

	// 성과 삭제
	public void deletePerformance(String perfCode) throws Exception {
		
		String sql = "DELETE FROM performance_management WHERE performance_code = ?";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			conn.setAutoCommit(false);
			
			pstmt.setString(1, perfCode);
			
			pstmt.executeUpdate();
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
	}

	// 성과 존재 여부 판별
	public boolean existsPerformanceCode(String perfCode) throws Exception {
		
		String sql = "SELECT COUNT(*) AS cnt FROM performance_management WHERE performance_code = ?";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, perfCode);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next() && rs.getInt("cnt") > 0) {
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return false;
	}

	// 해당 성과가 본인 과제 소유인지 확인
	public boolean isProjectIncludePerformance(String performCode, String projectCode) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT 1 "
					+ "FROM performance_management WHERE performance_code = ? AND project_code = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, performCode);
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
}