package model.ext;

import model.dto.ScoreDTO;

public class Score extends ScoreDTO {

	private Game game;
	
	public Score(Game game) {
		this.game = game;
	}
	public Game getGame() {
		return game;
	}
}
