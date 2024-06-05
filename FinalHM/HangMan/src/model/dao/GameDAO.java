package model.dao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.dto.GameDTO;
import model.ext.Game;
import model.ext.User;

public class GameDAO extends ModelDAO {

	public GameDAO() throws ClassNotFoundException, SQLException {
		super();
	}
	
	public Game select(Game game) throws SQLException {
		Game insertGame = null;
		String sql = "select gameid from gametb where rownum =1 order by gameid desc";
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					insertGame = new Game(rs.getInt(1));
					return insertGame;
				}
			} else {
				System.out.println("select 실패");
			}
		} catch (SQLException e) {
			System.out.println("sql 문장 오류...!");
		} finally {
//			close(rs, psmt, conn);
			return insertGame;
		}
	}
	
	public int insert(GameDTO game) throws SQLException {
		int result = 0;
		String sql = "insert into gametb (userid, datetime) values (?,?)";
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String creationTime = now.format(formatter);
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, game.getUserId());
			psmt.setString(2, creationTime);

			result = psmt.executeUpdate();

			if (result > 0) {
//				System.out.println("게임 인서트 성공");
			} else {
				System.out.println("게임 인서트 실패");
			}

		} catch (Exception e) {
			System.out.println("sql 문장 오류");
		} finally {
//			close(psmt, conn);
		}
		
		return result;
	}
	
}
