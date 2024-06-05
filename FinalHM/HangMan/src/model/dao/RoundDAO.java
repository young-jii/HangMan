package model.dao;

import java.sql.SQLException;

import model.ext.Game;
import model.ext.Round;

public class RoundDAO extends ModelDAO {

	public RoundDAO() throws ClassNotFoundException, SQLException {
		super();
	}

	public Round select(Round round) throws SQLException {
		Round insertRound = null;
		String sql = "select roundid from roundtb where rownum =1 order by gameid desc";
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					insertRound = new Round(rs.getInt(1));
					return insertRound;
				}
			} else {
				System.out.println("select 실패");
			}
		} catch (SQLException e) {
			System.out.println("sql 문장 오류...!");
		} finally {
//			close(rs, psmt, conn);
			return insertRound;
		}
	}
	
	public int insert(Round round) throws SQLException {
		int result = 0;
		String sql = "insert into roundtb (wordid, levelid, gameid, userid) values (?,?,?,?)";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, round.getWord().getWordId());
			psmt.setInt(2, round.getLevel().getLevelId());
			psmt.setInt(3, round.getGameId());
			psmt.setString(4, round.getUserId());

			result = psmt.executeUpdate();

			if (result > 0) {
//				System.out.println("라운드 인서트 성공");
			} else {
				System.out.println("라운드 인서트 실패");
			}

		} catch (Exception e) {
			System.out.println("sql 문장 오류");
		} finally {
//			close(psmt, conn);
		}
		
		return result;
	}
}
