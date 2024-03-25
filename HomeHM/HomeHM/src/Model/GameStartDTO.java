package Model;

public class GameStartDTO {
    private int wordid ;
    private String spelling ;
    private int wordlen ;
    private String difficulty ;

    public GameStartDTO(int wordid, String spelling, int wordlen, String difficulty) {
        this.wordid = wordid;
        this.spelling = spelling;
        this.wordlen = wordlen;
        this.difficulty = difficulty;
    }

    public GameStartDTO(String spelling) {
        this.spelling = spelling;
    }

    public GameStartDTO() {
    }

    public int getWordid() {
        return wordid;
    }
    public void setWordid(int wordid) {
        this.wordid = wordid;
    }
    public String getSpelling() {
        return spelling;
    }
    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }
    public int getWordlen() {
        return wordlen;
    }
    public void setWordlen(int wordlen) {
        this.wordlen = wordlen;
    }
    public String getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    

}
