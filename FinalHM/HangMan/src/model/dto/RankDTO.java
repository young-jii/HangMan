package model.dto;

public class RankDTO {
    private String userid;
    private int allscore ;
    private int rankNo;

    public RankDTO() {
    }

    public RankDTO(String id, int allscore, int rankNo) {
        this.userid = id ;
        this.allscore = allscore ;
        this.rankNo = rankNo ;
    }

    public RankDTO(String userid, int rankNo) {
        this.userid = userid;
        this.rankNo = rankNo;
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

    public int getAllscore() {
        return allscore;
    }

    public void setAllscore(int allscore) {
        this.allscore = allscore;
    }

        
    
}
