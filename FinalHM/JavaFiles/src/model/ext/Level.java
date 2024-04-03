package model.ext;

import model.dto.LevelDTO;

public class Level extends LevelDTO {
	
	public Level(int levelid, String difficulty, int lenstart, int lenend, int levelscore) {
		this.levelId = levelid;
		this.difficulty = difficulty;
		this.lenStart = lenstart;
		this.lenEnd = lenend;
		this.levelScore = levelscore;
	}
	
}
