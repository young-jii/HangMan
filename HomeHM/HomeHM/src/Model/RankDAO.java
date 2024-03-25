package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RankDAO {

    Connection conn = null ;
    PreparedStatement psmt = null ;
    ResultSet rs = null ;

    int result = 0 ;

    String name = "" ;
    int scoreid = 0 ;
    int totalscore = 0 ;

    ArrayList<RankDTO> rankList = new ArrayList<RankDTO>() ;
    ArrayList<RankDTO> allTable = null ;

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
    
    // 총점 계산 method
    public int calTotalScore(String userId) {
        getconn();
        String scoreidSql = "select sum(totalscore) as total_score from scoredt where userid = ?" ;
        try {
            psmt = conn.prepareStatement(scoreidSql) ;
            psmt.setString(1, userId) ;
            rs = psmt.executeQuery();
            if(rs!=null){
                while(rs.next()){
                    totalscore = rs.getInt(1);
                    System.out.println("해당 user totalscore >> " + totalscore);
                }
            }
        } catch (Exception e) {
            System.out.println("totalScore 계산 오류..!");
        } finally {
            returnClose();
        }
        return totalscore ;
    }

    // userId를 통해 이름 가져오기 method
    public String getUserName(String userId) {
        getconn();
        // 이름만 가져오기
        String nameSql = "select username from userdt where userid = ?" ;
        try {
            psmt = conn.prepareStatement(nameSql) ;
            psmt.setString(1, userId);
            rs = psmt.executeQuery() ;
            if (rs != null) {
				while (rs.next()) {
                    name = rs.getString(1) ;
                    System.out.println("getUserMethod >> 해당 user의 이름! >> " + name);
                }
            }
        } catch (Exception e) {
            System.out.println("getUserMethod >> SQL 실행 오류..!!");
        } finally {
            returnClose();
        }
        return name ;
    }

    // 해당 userId의 데이터가 있는지 없는지 확인 코드
    public boolean checkRankData(String userId) {
        boolean check = false ;
        getconn();
        System.out.println("checkRankData 속 userid >> " + userId);
        String checkRankUser = "select * from rankdt where userid = ?" ;
        try {
            psmt = conn.prepareStatement(checkRankUser) ;
            psmt.setString(1, userId);
            rs = psmt.executeQuery();
            if (rs.next()){
                System.out.println("해당 id의 rank 값이 있습니다.");
                check = true ;
            }
        } catch (SQLException e) {
            System.out.println("checkRankData 실행 오류..!!");
        } finally {
            returnClose();
        }
        return check ;
    }

    // userid의 데이터가 있을 때 totalscore 값이 같은지 확인 코드
    public boolean checkUserScore(String userId, int totalScore){
        boolean check = false ;
        getconn();
        String checkRankScore = "select * from rankdt where userid = ? and allscore = ?" ;
        try {
            psmt = conn.prepareStatement(checkRankScore) ;
            psmt.setString(1, userId);
            psmt.setInt(2, totalScore);
            rs = psmt.executeQuery();
            if(rs.next()) {
                System.out.println("해당 id의 totalscore 가 있습니다.");
                check = true ;
            }
        } catch (Exception e) {
            System.out.println("checkRankScore 실행 오류..!!");
        } finally {
            returnClose();
        }
        return check ;
    }

    // rankdt 테이블 업데이트 또는 삽입 method
    public void update_insert_Rank(String userId, int totalscore) {
        boolean check_dt_data = checkRankData(userId) ;
        boolean check_ur_score = checkUserScore(userId, totalscore) ;
        name = getUserName(userId) ;
        
        getconn();

        String insertsql = "insert into rankdt (userid, username, allscore) values (?, ?, ?)" ;
        String updateSql = "update rankdt set allscore = ? where userid = ?" ;

        try{
            // 기존 데이터가 있는지 확인
            if(check_dt_data == true){
                if(check_ur_score == true) {
                    // 기존 데이터에 총 점수와 userid 가 모두 있는 경우 > 아무것도 하지 않기
                    // 정상 작동 확인!
                } else {
                    // 기존 데이터에 id는 있는데 총 점수가 다른 경우 >> update 하기!
                    psmt = conn.prepareStatement(updateSql) ;
                    psmt.setInt(1, totalscore);
                    psmt.setString(2, userId);
                    result = psmt.executeUpdate() ;
                    if(result > 0) {
                        // System.out.println("updqte 완료!");
                    } else {
                        System.out.println("updqte 실패");
                    }
                }
            } else {
                // 기존 데이터가 없는 경우 > insert 진행
                psmt = conn.prepareStatement(insertsql) ;
                psmt.setString(1, userId);
                psmt.setString(2, name);
                psmt.setInt(3, totalscore);
                result = psmt.executeUpdate();
                if (result > 0){
                    // System.out.println("insert 완료..!");
                } else {
                    System.out.println("insert 실패!");
                }
            }
        } catch (SQLException e) {
            System.out.println("update_insert SQL 실행 오류..!!");
            e.getMessage() ;
            e.printStackTrace();

        } finally {
            returnClose();
        }
    }

    public ArrayList<RankDTO> selectAll(){
        rankList.clear();
        getconn();
        String sql = "select * from rankdt" ;
        try {
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery() ;
            if(rs != null) {
                while (rs.next()) {
                    String id = rs.getString(1);
                    String name = rs.getString(2);
                    int rankNo = rs.getInt(3);
                    int allscore = rs.getInt(4) ;
                    rankList.add(new RankDTO(id, name, rankNo, allscore)) ;
                }
            }
        } catch (Exception e) {
            System.out.println("select all sql 문장 오류...!");
        } finally {
            returnClose();
        }
        return rankList ;
    }

    public ArrayList<RankDTO> RankAll(){
        rankList.clear();
        getconn();
        String sql = "select userid, username, rankno, allscore from rankdt" ;
        try {
            psmt = conn.prepareStatement(sql) ;
            rs = psmt.executeQuery() ;
            if(rs != null) {
                while(rs.next()){
                    String id = rs.getString(1);
                    String name = rs.getString(2) ;
                    int rank = rs.getInt(3) ;
                    int allscore = rs.getInt(4) ;
                    rankList.add(new RankDTO(id, name, rank, allscore)) ;
                }
            }
        } catch (Exception e) {
            System.out.println("show rank all sql 문장 오류...!!");
        } finally {
            returnClose();
        }
        return rankList ;
    }

    public ArrayList<RankDTO> selectOne(String userId, RankDTO dto) {
        rankList.clear();
        getconn();
        String sql = "select userid, username, rankno, allscore from rankdt where userid = ?" ;
        try {
            psmt = conn.prepareStatement(sql) ;
            psmt.setString(1, userId);
            rs = psmt.executeQuery() ;
            if(rs != null) {
                while (rs.next()) {
                    String id = rs.getString(1) ;
                    String name = rs.getString(2) ;
                    int rank = rs.getInt(3) ;
                    int allscore = rs.getInt(4) ;
                    rankList.add(new RankDTO(id, name, rank, allscore)) ;
                }
            } else {
                System.out.println("select one select 실패!");
            }
        } catch (SQLException e) {
            System.out.println("select one sql 문장 오류");
        } finally {
            returnClose(); 
        }
        return rankList ;
    }

    public int[] calScore() {
        allTable = selectAll() ;
        int [] rankCal = new int[allTable.size()] ;
        for(int i = 0 ; i < allTable.size() ; i++){
            int rank = 1 ;
            for (int j = 0 ; j < allTable.size() ; j ++){
                if (allTable.get(i).getAllscore() < allTable.get(j).getAllscore()) {
                    rank ++ ;
                }
                rankCal[i] = rank ;
            }
        }
        return rankCal ;
    }

    public int updateRank(){
        int [] allRankList = calScore() ;
        allTable.clear();
        allTable = selectAll() ;
        getconn();
        String sql = "update rankdt set rankno = ? where userid = ?" ;
        try {
            psmt = conn.prepareStatement(sql) ;
            for(int i = 0 ; i < allTable.size() ; i ++) {
                psmt.setInt(1, allRankList[i]);
                psmt.setString(2, allTable.get(i).getUserid());
                result = psmt.executeUpdate() ;
            }
        } catch (Exception e) {
            System.out.println("update sql 실패...!");
        } finally {
            returnClose();
        }
        return result ;
    }

}