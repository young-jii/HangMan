package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.ext.Level;
import model.ext.Word;

public class WordDAO extends ModelDAO {

	public WordDAO() throws ClassNotFoundException, SQLException {
		super();
	}

	public ArrayList<Word> selectByDifficulty(String difficulty) throws SQLException {
		ArrayList<Word> wordList = new ArrayList<>();
		
		String sql = "select w.wordid, w.spelling, w.wordlen "
				+ " from wordtb w, leveltb l where l.difficulty = ? and wordlen between l.lenstart and l.lenend";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, difficulty);
			rs = psmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					wordList.add(new Word(
									rs.getInt(1),
									rs.getString(2),
									rs.getInt(3)
								));
				}
			}
		} catch (SQLException e) {
			System.out.println("sql 문장 오류");
		} finally {
//			close(rs,psmt,conn);
			return wordList;
		}
	}
}
