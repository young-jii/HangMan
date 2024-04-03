package model.ext;

import java.util.ArrayList;

import model.dto.GameDTO;
import model.dto.RoundDTO;

public class Game extends GameDTO {

	private Round startRound;
	private ArrayList<RoundDetail> rounds;
	
	public Game(int gameid) {
		this.gameId = gameid;
	}
	public Game(Round startRound) {
		this.startRound = startRound;
	}
	public ArrayList<RoundDetail> getRounds() {
		if(rounds == null) {
			startRound = null;
			rounds = new ArrayList<>();
		}
		return rounds;
	}
	public Round getStartRound() {
		return startRound;
	}
	public void setRounds() {
		rounds = getRounds();
	}
	public void setRound(RoundDetail round) {
		rounds.add(round);
	}
}
