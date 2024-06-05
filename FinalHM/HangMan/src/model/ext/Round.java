package model.ext;

import model.dto.RoundDTO;

public class Round extends RoundDTO {
	
	private Word word;
	private Level level;
	
	public Round() {
	}
	public Round(int roundid) {
		this.roundId = roundid;
	}
	
	public Round(Level level, Word word) {
		this.word = word;
		this.level = level;
	}
	
	public Word getWord() {
		return word;
	}
	public Level getLevel() {
		return level;
	}
}
