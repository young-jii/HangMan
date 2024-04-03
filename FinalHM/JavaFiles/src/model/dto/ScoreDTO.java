package model.dto;

public class ScoreDTO {

	protected int scoreId;
	protected int totalScore;
	protected int wordScore;
	protected int tfScore;
	protected int bonusScore;
	protected int gameId;
	protected String userId;
	
	public int getScoreId() {
		return scoreId;
	}
	public void setScoreId(int scoreId) {
		this.scoreId = scoreId;
	}
	public int getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	public int getWordScore() {
		return wordScore;
	}
	public void setWordScore(int wordScore) {
		this.wordScore = wordScore;
	}
	public int getTfScore() {
		return tfScore;
	}
	public void setTfScore(int tfScore) {
		this.tfScore = tfScore;
	}
	public int getBonusScore() {
		return bonusScore;
	}
	public void setBonusScore(int bonusScore) {
		this.bonusScore = bonusScore;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
