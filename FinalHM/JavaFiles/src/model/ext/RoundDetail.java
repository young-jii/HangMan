package model.ext;

import java.lang.reflect.Array;
import java.util.ArrayList;

import model.dto.RoundDetailDTO;

public class RoundDetail extends RoundDetailDTO {
	
	private Round round;
	private ArrayList<Integer> wordIndex;
	private ArrayList<Integer> correctIndex;
	boolean isWin;
	
	public RoundDetail() {
		
	}

	public RoundDetail(Round round) {
		this.round = round;
		this.wordIndex = new ArrayList<>();
		for(int i=0; i<round.getWord().getWordLen(); i++) {
			wordIndex.add(i);
		}
		this.correctIndex = new ArrayList<>();
		this.input = "";
		this.wordTF = "";
	}
	
	public void update(char input, char wordTF) {
		this.input += input;
		this.wordTF += wordTF;
	}
	public void update(int[] inputIndex) {
		for(int i : inputIndex) {
			correctIndex.add(i);
			wordIndex.remove(Integer.valueOf(i));
		}
	}
	
	public Round getRound() {
		return round;
	}
	public ArrayList<Integer> getWordIndex() {
		return wordIndex;
	}
	public ArrayList<Integer> getCorrectIndex() {
		return correctIndex;
	}
	public boolean getIsWin() {
		return isWin;
	}
	public void setIsWin(boolean isWin) {
		this.isWin = isWin;
	}
}
