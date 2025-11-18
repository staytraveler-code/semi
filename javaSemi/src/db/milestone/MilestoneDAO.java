package db.milestone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.util.DBConn;
import db.util.DBUtil;

public class MilestoneDAO {
	private final Connection conn = DBConn.getConnection();

	// 특정 프로젝트 마일스톤 목록
	public List<MilestoneDTO> listMilestoneByProject(String projectCode) {
		List<MilestoneDTO> list = new ArrayList<>();
		
		String sql = "SELECT milestone_code, project_code, name, description, p_end_date, a_end_date, status "
				+ "FROM Milestone WHERE project_code = ? ORDER BY milestone_code";
		
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
			e.printStackTrace();
		}
		
		return list;
	}

	// 마일스톤 추가
	public void insertMilestone(MilestoneDTO dto) throws SQLException {
		
		String sql = "INSERT INTO milestone (milestone_code, project_code, name, description, p_end_date, a_end_date, status) "
				+ "VALUES ('MS_' || LPAD(seq_milestone.NEXTVAL, 3, '0'), ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), TO_DATE(?, 'YYYY-MM-DD'), ?)";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			conn.setAutoCommit(false);

			pstmt.setString(1, dto.getpCode());
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getDesc());
			pstmt.setString(4, dto.getPeDate());
			pstmt.setString(5, dto.getAeDate());
			pstmt.setString(6, dto.getStatus());

			pstmt.executeUpdate();
			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			DBUtil.rollback(conn);
			throw e;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
			}
		}
	}

	// 마일스톤 수정
	public void updateMilestone(MilestoneDTO dto) throws SQLException {

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

			pstmt.executeUpdate();
			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			DBUtil.rollback(conn);
			throw e;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
			}
		}
	}

	// 마일스톤 삭제
	public void deleteMilestone(String mileCode) throws SQLException {

		String sql = "DELETE FROM Milestone WHERE milestone_code=? ";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			conn.setAutoCommit(false);

			pstmt.setString(1, mileCode);
			
			pstmt.executeUpdate();
			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			DBUtil.rollback(conn);
			throw e;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
			}
		}
	}

	// 마일스톤이 해당 과제의 마일스톤인지 확인
	public boolean isProjectIncludeMilestone(String mCode, String pCode) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT 1 " + "FROM milestone WHERE milestone_code = ? AND project_code = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mCode);
			pstmt.setString(2, pCode);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return false;
	}
	
	// 총 마일스톤 개수 반환
	public int countTotalMilestones(String projectCode) throws Exception {
		String sql = "SELECT COUNT(*) FROM Milestone WHERE project_code = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, projectCode);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}
		return 0;
	}

	// 완료된 마일스톤 개수 반환
	public int countCompletedMilestones(String projectCode) throws Exception {
		String sql = "SELECT COUNT(*) FROM Milestone WHERE project_code = ? AND status = '완료'";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, projectCode);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}
		return 0;
	}

}
