package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.dto.RankDTO;

public class RankDAO {

	Connection conn = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;

	int result = 0;

	String name = "";
	int scoreid = 0;

	ArrayList<RankDTO> rankList = new ArrayList<RankDTO>();
	ArrayList<RankDTO> allTable = null;

	public void getconn() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String db_url = "jdbc:oracle:thin:@localhost:1521:xe";
			String db_user = "hm";
			String db_pw = "12345";
			conn = DriverManager.getConnection(db_url, db_user, db_pw);
		} catch (Exception e) {
			System.out.println("getconn method 오류..!");
		}
	}

	public void returnClose() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			System.out.println("close fail");
		}
	}

	// 총점 계산 method
	public int calTotalScore(String userId) {
		int totalscore = 0;
		String totalScoredSql = "select sum(totalscore) as total_score from scoretb where userid = ?";
		getconn();
		try {
			psmt = conn.prepareStatement(totalScoredSql);
			psmt.setString(1, userId);
			rs = psmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					totalscore = rs.getInt("total_score");
//                    System.out.println("( calTotalScore>> ) 해당 user totalscore >> " + totalscore);
				}
			} else {
				System.out.println("select 실패!");
			}
		} catch (Exception e) {
			System.out.println("totalScore 계산 오류..!" + e.getMessage());
		} finally {
			returnClose();
		}

		return totalscore;
	}

	// userId를 통해 이름 가져오기 method
	public String getUserName(String userId) {
		getconn();
		// 이름만 가져오기
		String nameSql = "select name from usertb where userid = ?";
		try {
			psmt = conn.prepareStatement(nameSql);
			psmt.setString(1, userId);
			rs = psmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					name = rs.getString(1);
//                    System.out.println("getUserMethod >> 해당 user의 이름! >> " + name);
				}
			}
		} catch (Exception e) {
			System.out.println("getUserMethod >> SQL 실행 오류..!!");
		} finally {
			returnClose();
		}
		return name;
	}

	// 해당 userId의 데이터가 있는지 없는지 확인 코드
	public boolean checkRankData(String userId) {
		boolean check = false;
		getconn();
//        System.out.println("checkRankData 속 userid >> " + userId);
		String checkRankUser = "select * from ranktb where userid = ?";
		try {
			psmt = conn.prepareStatement(checkRankUser);
			psmt.setString(1, userId);
			rs = psmt.executeQuery();
			if (rs.next()) {
//				System.out.println("\n" + "해당 id의 rank 값이 있습니다." + "\n");
				check = true;
			}
		} catch (SQLException e) {
			System.out.println("checkRankData 실행 오류..!!");
		} finally {
			returnClose();
		}
		return check;
	}

	// userid의 데이터가 있을 때 totalscore 값이 같은지 확인 코드
	public boolean checkUserScore(String userId, int totalScore) {
		boolean check = false;
		getconn();
		String checkRankScore = "select * from ranktb where userid = ? and allscore = ?";
		try {
			psmt = conn.prepareStatement(checkRankScore);
			psmt.setString(1, userId);
			psmt.setInt(2, totalScore);
			rs = psmt.executeQuery();
			if (rs.next()) {
//				System.out.println("\n" + "해당 id의 totalscore 가 있습니다." + "\n");
				check = true;
			}
		} catch (Exception e) {
			System.out.println("checkRankScore 실행 오류..!!");
		} finally {
			returnClose();
		}
		return check;
	}

	// rankdt 테이블 업데이트 또는 삽입 method
	public void update_insert_Rank(String userId, int totalscore) {
		boolean check_dt_data = checkRankData(userId);
		boolean check_ur_score = checkUserScore(userId, totalscore);
		name = getUserName(userId);

		getconn();

		String insertsql = "insert into ranktb (userid, allscore) values (?, ?)";
		String updateSql = "update ranktb set allscore = ? where userid = ?";

		try {
			// 기존 데이터가 있는지 확인
			if (check_dt_data == true) {
				if (check_ur_score == true) {
					// 기존 데이터에 총 점수와 userid 가 모두 있는 경우 > 아무것도 하지 않기
					// 정상 작동 확인!
				} else {
					// 기존 데이터에 id는 있는데 총 점수가 다른 경우 >> update 하기!
					psmt = conn.prepareStatement(updateSql);
					psmt.setInt(1, totalscore);
					psmt.setString(2, userId);
					result = psmt.executeUpdate();
					if (result > 0) {
						// System.out.println("updqte 완료!");
					} else {
						System.out.println("updqte 실패");
					}
				}
			} else {
				// 기존 데이터가 없는 경우 > insert 진행
				psmt = conn.prepareStatement(insertsql);
				psmt.setString(1, userId);
				psmt.setInt(2, totalscore);
				result = psmt.executeUpdate();
				if (result > 0) {
					// System.out.println("insert 완료..!");
				} else {
					System.out.println("insert 실패!");
				}
			}
		} catch (SQLException e) {
			System.out.println("update_insert SQL 실행 오류..!!");
			e.getMessage();
			e.printStackTrace();

		} finally {
			returnClose();
		}
	}

	public ArrayList<RankDTO> selectAll() {
		rankList.clear();
		getconn();
		String sql = "select * from ranktb";
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					String id = rs.getString(1);
					int rankNo = rs.getInt(2);
					int allscore = rs.getInt(3);
					rankList.add(new RankDTO(id, rankNo, allscore));
				}
			}
		} catch (Exception e) {
			System.out.println("select all sql 문장 오류...!");
		} finally {
			returnClose();
		}
		return rankList;
	}

	public ArrayList<RankDTO> RankAll() {
		rankList.clear();
		getconn();
		String sql = "select userid, rankno, allscore from ranktb";
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					String id = rs.getString(1);
					int rank = rs.getInt(2);
					int allscore = rs.getInt(3);
					rankList.add(new RankDTO(id, rank, allscore));
				}
			}
		} catch (Exception e) {
			System.out.println("show rank all sql 문장 오류...!!");
		} finally {
			returnClose();
		}
		return rankList;
	}

	public ArrayList<RankDTO> selectOne(String userId) {
		rankList.clear();
		getconn();
		String sql = "select userid, rankno, allscore from ranktb where userid = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, userId);
			rs = psmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					String id = rs.getString(1);
					int rank = rs.getInt(2);
					int allscore = rs.getInt(3);
					rankList.add(new RankDTO(id, rank, allscore));
				}
			} else {
				System.out.println("select one select 실패!");
			}
		} catch (SQLException e) {
			System.out.println("select one sql 문장 오류");
		} finally {
			returnClose();
		}
		return rankList;
	}

	public int[] calScore() {
		allTable = selectAll();
		int[] rankCal = new int[allTable.size()];
		for (int i = 0; i < allTable.size(); i++) {
			int rank = 1;
			for (int j = 0; j < allTable.size(); j++) {
				if (i != j) {
					if (allTable.get(i).getAllscore() < allTable.get(j).getAllscore()) {
						rank++;
					}
				}
			}
			rankCal[i] = rank;
		}
		return rankCal;
	}

	public int updateRank() {
		int[] allRankList = calScore();
		allTable.clear();
		allTable = selectAll();
		getconn();
		String sql = "update ranktb set rankno = ? where userid = ?";
		try {
			psmt = conn.prepareStatement(sql);
			for (int i = 0; i < allTable.size(); i++) {
				psmt.setInt(1, allRankList[i]);
				psmt.setString(2, allTable.get(i).getUserid());
				result = psmt.executeUpdate();
			}
		} catch (Exception e) {
			System.out.println("update sql 실패...!");
		} finally {
			returnClose();
		}
		return result;
	}

}