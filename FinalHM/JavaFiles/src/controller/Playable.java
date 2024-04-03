package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import model.ext.Game;
import model.ext.Round;
import model.ext.RoundDetail;
import model.ext.Score;
import model.ext.Word;
import model.ext.Level;

public interface Playable {
	
	public Game start(String playerID) throws SQLException;
	public boolean play(Round nexRound, Game game) throws SQLException;
	
	public Level selectLevel() throws SQLException;
	public ArrayList<Word> getLevelWord(Level level) throws SQLException;
	public Word getRandomWord(ArrayList<Word> words);
	public Round setUpRound(Level level, Word word);
	
	public boolean roundOver(RoundDetail round);
	public boolean completeDraw(RoundDetail round);
	public boolean completeWord(RoundDetail round);
	public int countStroke(RoundDetail round);
	public int countLeft(RoundDetail round);
	
	public void initialPrint(Word word);
	public void updatePrint(Word word, ArrayList<Integer>[] indexSet);
	
	public void print(char letter);
	public void print(char letter, int ansiEscapeColorCode);
	public void print(char letter, int[] ansiEscapeColorCode);
	
	public ArrayList<Integer>[] reorder(RoundDetail round, int[] inputIndex);
	
	public int[] findIndexOf(char letter, String spelling);
	public int countLetter(char letter, String spelling);
	public boolean hasLetter(char letter, String spelling);
	
	public char getInput();
	public boolean checkDupInput(char input, RoundDetail round);
	
	public Score calculate(Game game);
	public int calculate(RoundDetail round);
	public int calculate(ArrayList<RoundDetail> rounds);
	
	public String showGameResult(Game game) throws SQLException;
}
