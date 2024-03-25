package Model;

public class RankDTO {
    private String userid;
    private String username;
    private int rankNo;
    private int scoreid ;
    private int allscore ;

    public RankDTO() {
    }

    public RankDTO(String id, String name, int rankNo, int scoreid, int allscore) {
        this.userid = id ;
        this.username = name ;
        this.rankNo = rankNo ;
        this.scoreid = scoreid ;
        this.allscore = allscore ;
    }

    public RankDTO(String id, String name, int rankNo, int allscore) {
        this.userid = id ;
        this.username = name ;
        this.rankNo = rankNo ;
        this.allscore = allscore ;
    }

    public RankDTO(String userid, int rankNo, int scoreid) {
        this.userid = userid;
        this.rankNo = rankNo;
        this.scoreid = scoreid;
    }

    public RankDTO(String userid) {
        this.userid = userid;
    }
    

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    
    public int getRankNo() {
        return rankNo;
    }
    
    public void setRankNo(int rankNo) {
        this.rankNo = rankNo;
    }
    
    public int getScoreid() {
        return scoreid;
    }
    
    public void setScoreid(int scoreid) {
        this.scoreid = scoreid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAllscore() {
        return allscore;
    }

    public void setAllscore(int allscore) {
        this.allscore = allscore;
    }

        
    
}
