package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;


public class GameStartDAO {

    GameStartDTO dto = new GameStartDTO();
    Random ran = new Random();

    Connection conn = null ;
    PreparedStatement psmt = null ;
    ResultSet rs = null ;

    private String level;
    public void setLevel(String level){
        this.level = level ;
    }
    public String getLevel(){
        return level ;
    }

    int result = 0 ;
    ArrayList<GameStartDTO> wordList = new ArrayList<GameStartDTO>() ;

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

    // 관리자가 단어를 추가하는 코드
    public int input(GameStartDTO dto) {
        getconn();
        String sql = "insert into userdt (wordID, spelling, wordlen, difficulty) values (?, ?, ?, ?)" ;

        try {
            psmt = conn.prepareStatement(sql) ;
            psmt.setInt(1, 0);
            psmt.setString(2, dto.getSpelling());
            psmt.setInt(3, dto.getSpelling().length());
            psmt.setString(4, getDifficultyByWordLength(dto.getSpelling().length()));

            result = psmt.executeUpdate();
            
            if(result > 0){
                System.out.println("단어 삽입 성공");
            } else {
                System.out.println("단어 삽입 실패");
            }

        } catch (Exception e) {
            System.out.println("sql 문장 오류");
        } finally {
            returnClose();
        }
        return result ;
    }

    // 단어의 길이에 따라서 difficulty 값을 가져오는 메서드
    public String getDifficultyByWordLength(int wordLength) {
        String difficulty = "";
        String sql = "SELECT difficulty FROM leveldt WHERE ? BETWEEN lenstart AND lenend";
        
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, wordLength);
            ResultSet rs = psmt.executeQuery();
            
            if(rs.next()) {
                difficulty = rs.getString("difficulty");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return difficulty;
    }

    // 단어 list 중 random으로 추출하기
    public String getRandomWord(ArrayList<GameStartDTO> wordlist){
        System.out.println("wordlist의 크기 >> " + wordlist.size());
        int ranNum = ran.nextInt(wordlist.size() -1) ;
        String word = wordlist.get(ranNum).getSpelling() ;
        return word ;
    }

    // 난이도 별 전체 단어 가져오기
    public String getLevelWord(String level, GameStartDTO dto){
        getconn();
        String sql = "select spelling from worddt where difficulty = ?";
        try{
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, level);
            rs = psmt.executeQuery();
            if(rs != null){
                while(rs.next()){
                    String word = rs.getString(1) ;
                    wordList.add(new GameStartDTO(word)) ;
                }
            }
        }catch (SQLException e){
            System.out.println("sql 문장 오류");
        } finally{
            returnClose();
        }
        String answerWord = getRandomWord(wordList);
        System.out.println("정답 단어 >> " + answerWord);
        wordList.clear();
        return answerWord ;
    }
    
    // 전체 단어 가져오기 >> normal >> 위 코드가 진행되면 삭제!
    public ArrayList<GameStartDTO> getNormalWord(GameStartDTO dto){
        getconn();
        String sql = "select spelling from worddt where difficulty = 'normal'";
        try{
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            if(rs != null){
                while(rs.next()){
                    String word = rs.getString(1) ;
                    wordList.add(new GameStartDTO(word)) ;
                }
            }
        }catch (SQLException e){
            System.out.println("sql 문장 오류");
        } finally{
            returnClose();
        }
        return wordList ;
    }

    // 전체 단어 가져오기 >> hard >> 위 코드가 진행되면 삭제!
    public ArrayList<GameStartDTO> getHardWord(GameStartDTO dto){
        getconn();
        String sql = "select spelling from worddt where difficulty = 'hard'";
        try{
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            if(rs != null){
                while(rs.next()){
                    String word = rs.getString(1) ;
                    wordList.add(new GameStartDTO(word)) ;
                }
            }
        }catch (SQLException e){
            System.out.println("sql 문장 오류");
        } finally{
            returnClose();
        }
        return wordList ;
    }

    

}
