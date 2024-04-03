package model.dao;

import java.sql.SQLException;

import model.ext.Score;

public class ScoreDAO extends ModelDAO {

	public ScoreDAO() throws ClassNotFoundException, SQLException {
		super();
	}

	public int insert(Score score) throws SQLException {
		int result = 0;
		String sql = "insert into scoretb (userid, gameid, totalscore, tfscore, bonusscore, wordscore) values (?,?,?,?,?,?)";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, score.getUserId());
			psmt.setInt(2, score.getGame().getGameId());
			psmt.setInt(3, score.getTotalScore());
			psmt.setInt(4, score.getTfScore());
			psmt.setInt(5, score.getBonusScore());
			psmt.setInt(6, score.getWordScore());

			result = psmt.executeUpdate();

			if (result > 0) {
//				System.out.println("스코어 인서트 성공");
			} else {
				System.out.println("스코어 인서트 실패");
			}

		} catch (Exception e) {
			System.out.println("sql 문장 오류");
		} finally {
//			close(psmt, conn);
		}
		
		return result;
	}
}
