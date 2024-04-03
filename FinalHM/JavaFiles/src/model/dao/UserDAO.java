package model.dao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.dto.UserDTO;
import model.ext.User;

public class UserDAO extends ModelDAO {

	public UserDAO() throws ClassNotFoundException, SQLException {
		super();
	}

	public int insert(User user) throws SQLException {
		int result = 0;
		String sql = "insert into usertb (userid, userpw, name, createddt) values (?, ?, ?, ?)";

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String creationTime = now.format(formatter);

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, user.getUserId());
			psmt.setString(2, user.getUserPw());
			psmt.setString(3, user.getName());
			psmt.setString(4, creationTime);

			result = psmt.executeUpdate();

			if (result > 0) {
//				System.out.println("회원 가입 성공");
			} else {
				System.out.println("회원 가입 실패");
			}

		} catch (Exception e) {
			System.out.println("sql 문장 오류");
		} finally {
//			close(psmt, conn);
		}
		return result;

	}

	public User select(User user) throws SQLException {
		User loginUser = null;
		String sql = "select userid, name, createddt from usertb where userid = ? and userpw = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, user.getUserId());
			psmt.setString(2, user.getUserPw());
			rs = psmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					String id = rs.getString(1);
					String name = rs.getString(2);
					String create_dt = rs.getString(3);
					loginUser = new User();
					loginUser.setUserId(id);
					loginUser.setName(name);
					return loginUser;
				}
			} else {
				System.out.println("select 실패");
			}
		} catch (SQLException e) {
			System.out.println("sql 문장 오류...!");
		} finally {
//			close(rs, psmt, conn);
			return loginUser;
		}
	}
}
