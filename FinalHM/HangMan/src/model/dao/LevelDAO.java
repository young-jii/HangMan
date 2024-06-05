package model.dao;

import java.sql.SQLException;

import model.ext.Level;

public class LevelDAO extends ModelDAO {

	public LevelDAO() throws ClassNotFoundException, SQLException {
		super();
	}
	
	public Level selectByDifficulty(String difficulty) throws SQLException {
		Level level = null;
		String sql = "select levelid, difficulty, lenstart, lenend, levelscore from leveltb where difficulty = ?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, difficulty);
			
			rs = psmt.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					level = new Level(
							rs.getInt(1),
							rs.getString(2),
							rs.getInt(3),
							rs.getInt(4),
							rs.getInt(5)
							);
					return level;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
//			close(rs, psmt, conn);
			return level;
		}
	}

}
