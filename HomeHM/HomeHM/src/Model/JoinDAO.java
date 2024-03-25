package Model;

import java.sql.* ;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class JoinDAO {
    Connection conn = null ;
    PreparedStatement psmt = null ;
    ResultSet rs = null ;

    int result = 0 ;
    ArrayList<JoinDTO> loginList = new ArrayList<JoinDTO>() ;

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") ;
    String creationTime ;

    public JoinDAO(){
        creationTime = now.format(formatter) ;
    }

    public void getconn() {
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String db_url = "jdbc:mysql://localhost:3306/HangMan";
        String db_user = "root";
        String db_pw = "admin1234";
        conn = DriverManager.getConnection(db_url, db_user, db_pw) ;       
        } catch(Exception e){
            System.out.println("getconn method 오류..!");
        }
    }

    public void returnClose(){
        try {
            if (rs != null){
                rs.close();
            }
            if (psmt != null){
                psmt.close();
            }
            if (conn != null){
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("close fail");
        }
    }

    public int input(JoinDTO dto) {
        getconn();
        String sql = "insert into userdt (userid, userpw, username, created_dt) values (?, ?, ?, ?)" ;

        try {
            psmt = conn.prepareStatement(sql) ;
            psmt.setString(1, dto.getId());
            psmt.setString(2, dto.getPw());
            psmt.setString(3, dto.getName());
            psmt.setString(4, creationTime);

            result = psmt.executeUpdate();
            
            if(result > 0){
                System.out.println("회원 가입 성공");
            } else {
                System.out.println("회원 가입 실패");
            }

        } catch (Exception e) {
            System.out.println("sql 문장 오류");
        } finally {
            returnClose();
        }
        return result ;
    }

    public ArrayList<JoinDTO> login(JoinDTO dto){
        getconn();
        String sql = "select userid, username, created_dt from userdt where userid = ? and userpw = ?" ;
        try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPw());
			rs = psmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					String id = rs.getString(1);
					String name = rs.getString(2);
					String create_dt = rs.getString(3);
					loginList.add(new JoinDTO(id, name, create_dt));
				}
			} else {
				System.out.println("select 실패");
			}
		} catch (SQLException e) {
			System.out.println("sql 문장 오류...!");
		} finally {
			returnClose();
		}
		return loginList;
    }


}
