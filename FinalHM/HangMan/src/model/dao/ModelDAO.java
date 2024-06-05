package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ModelDAO {

	private final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String USER = "hm";
	private final String PASSWORD = "12345";

	protected Connection conn;
	protected PreparedStatement psmt;
	protected ResultSet rs;
	
	protected String tableName;

	public ModelDAO() throws ClassNotFoundException, SQLException {
		conn = getConnection();
		tableName = getClass().getName().replace("DAO", "");
	}
	
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
	
	public void close(Connection conn) throws SQLException {
		if (conn != null){
            conn.close();
        }
	}
	public void close(PreparedStatement psmt) throws SQLException {
		if (psmt != null){
			psmt.close();
		}
	}
	public void close(ResultSet rs) throws SQLException {
		if (rs != null){
			rs.close();
		}
	}
	public void close(PreparedStatement psmt, Connection conn) throws SQLException {
		if (psmt != null){
			psmt.close();
		}
		if (conn != null){
            conn.close();
        }
	}
	public void close(ResultSet rs, PreparedStatement psmt, Connection conn) throws SQLException {
		if (psmt != null){
			psmt.close();
		}
		if (conn != null){
			conn.close();
		}
	}
	
}
