package model.dao;

import java.sql.SQLException;

import model.ext.RoundDetail;

public class RoundDetailDAO extends ModelDAO {

	public RoundDetailDAO() throws ClassNotFoundException, SQLException {
		super();
	}

	public int insert(RoundDetail round) throws SQLException {
		int result = 0;
		String sql = "insert into rounddetailtb (roundid, input, wordtf) values (?,?,?)";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, round.getRound().getRoundId());
			psmt.setString(2, round.getInput());
			psmt.setString(3, round.getWordTF());

			result = psmt.executeUpdate();

			if (result > 0) {
//				System.out.println("라운드디테일 인서트 성공");
			} else {
				System.out.println("라운드디테일 인서트 실패");
			}

		} catch (Exception e) {
			System.out.println("sql 문장 오류");
		} finally {
//			close(psmt, conn);
		}
		
		return result;
	}
}
