package model.dto;

public class LevelDTO {

	protected int levelId;
	protected String difficulty;
	protected int lenStart;
	protected int lenEnd;
	protected int levelScore;
	
	public int getLevelId() {
		return levelId;
	}
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	public int getLenStart() {
		return lenStart;
	}
	public void setLenStart(int lenStart) {
		this.lenStart = lenStart;
	}
	public int getLenEnd() {
		return lenEnd;
	}
	public void setLenEnd(int lenEnd) {
		this.lenEnd = lenEnd;
	}
	public int getLevelScore() {
		return levelScore;
	}
	public void setLevelScore(int levelScore) {
		this.levelScore = levelScore;
	}
}
