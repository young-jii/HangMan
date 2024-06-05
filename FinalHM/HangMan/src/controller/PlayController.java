package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.dao.GameDAO;
import model.dao.LevelDAO;
import model.dao.RoundDAO;
import model.dao.RoundDetailDAO;
import model.dao.ScoreDAO;
import model.dao.WordDAO;
import model.dto.RoundDTO;
import model.ext.Game;
import model.ext.Level;
import model.ext.Round;
import model.ext.RoundDetail;
import model.ext.Score;
import model.ext.Word;

public class PlayController implements Playable {

	private Scanner scanner;
	private LevelDAO levelDAO;
	private WordDAO wordDAO;
	private RoundDAO roundDAO;
	private RoundDetailDAO roundDetailDAO;
	private GameDAO gameDAO;
	private ScoreDAO scoreDAO;

	public PlayController(Scanner scanner) throws ClassNotFoundException, SQLException {
		this.scanner = scanner;
		levelDAO = new LevelDAO();
		wordDAO = new WordDAO();
		roundDAO = new RoundDAO();
		roundDetailDAO = new RoundDetailDAO();
		gameDAO = new GameDAO();
		scoreDAO = new ScoreDAO();
	}

	@Override
	public Game start(String playerID) throws SQLException {
		Game game;

		Level level = selectLevel();
		ArrayList<Word> wordlist = getLevelWord(level);
		Word word = getRandomWord(wordlist);
//		System.out.println(word.getSpelling());

		Round startRound = setUpRound(level, word);
		game = new Game(startRound);
		game.setUserId(playerID);
		gameDAO.insert(game);

		int gameId = gameDAO.select(game).getGameId();
		startRound.setGameId(gameId);
		startRound.setUserId(playerID);
		roundDAO.insert(startRound);
		startRound.setRoundId(roundDAO.select(startRound).getRoundId());

		boolean playable;
		do {
			if (game.getStartRound() != null) {
				playable = play(game.getStartRound(), game);
				continue;
			}
			Round nextRound = setUpRound(level, getRandomWord(wordlist));
			nextRound.setGameId(gameId);
			nextRound.setUserId(playerID);
			roundDAO.insert(nextRound);
			nextRound.setRoundId(roundDAO.select(nextRound).getRoundId());

			playable = play(nextRound, game);

		} while (playable);

		game.setGameId(gameId);
		return game;
	}

	@Override
	public Level selectLevel() throws SQLException {
		Level level = null;
		String difficulty;
		while (true) {
			System.out.println("난이도를 선택하세요." + "\n");
			System.out.print("[1] easy [2] normal [3] hard >> ");
			int levelChoosen = scanner.nextInt();
			System.out.println();
			if (levelChoosen <= 3) {
				if (levelChoosen == 1) {
					difficulty = "EASY";
					break;
				} else if (levelChoosen == 2) {
					difficulty = "NORMAL";
					break;
				} else if (levelChoosen == 3) {
					difficulty = "HARD";
					break;
				}
				System.out.println();
			} else {
				System.out.print("\n" + "다시 입력하세요." + "\n");
			}
		}
		level = levelDAO.selectByDifficulty(difficulty);
		return level;

	}

	@Override
	public String showGameResult(Game game) throws SQLException {
		Score score = calculate(game);
		score.setUserId(game.getUserId());
		scoreDAO.insert(score);
		System.out.println("\n" + "현재 게임의 결과 >> " + score.getTotalScore() + "점" + "\n");

		while (true) {
			System.out.println("메뉴를 선택하세요." + "\n");
			System.out.print("[1] retry [2] end >> ");
			int selected = scanner.nextInt();
			System.out.println();
			if (selected <= 2) {
				if (selected == 1) {
					return "GAME";
				} else if (selected == 2) {
					return "RANK";
				}
				System.out.println();
			} else {
				System.out.print("\n" + "다시 입력하세요." + "\n");
			}
		}
	}

	@Override
	public ArrayList<Word> getLevelWord(Level level) throws SQLException {
		String difficulty = level.getDifficulty();
		return wordDAO.selectByDifficulty(difficulty);
	}

	@Override
	public Word getRandomWord(ArrayList<Word> wordlist) {
		Random random = new Random();
		int ranNum = random.nextInt(wordlist.size() - 1);
		return wordlist.get(ranNum);
	}

	@Override
	public Round setUpRound(Level level, Word word) {
		return new Round(level, word);
	}

	@Override
	public boolean play(Round nextRound, Game game) throws SQLException {
//		System.out.println(nextRound.getWord().getSpelling());
		System.out.println("\n" + (game.getRounds().size() + 1) + " 차 라운드 도전입니다." + "\n");

		nextRound.getRoundId();
		RoundDetail round = new RoundDetail(nextRound);

		Word word = round.getRound().getWord();
		initialPrint(word);

		do {
			char input;
			do {
				input = getInput();
			} while (checkDupInput(input, round));

			char ox;
			if (hasLetter(input, word.getSpelling())) {
				int[] inputIndex = findIndexOf(input, word.getSpelling());
				ArrayList<Integer>[] indexSet = reorder(round, inputIndex);
				updatePrint(word, indexSet);

				ox = 'o';
			} else {
				ox = 'x';
			}

			round.update(input, ox);
			
		} while (!roundOver(round));
		
		roundDetailDAO.insert(round);
		
		System.out.println("\n" + "정답은 >> " + word.getSpelling());
		
		ArrayList<RoundDetail> rounds = game.getRounds();
		rounds.add(round);

		return round.getIsWin();
	}

	@Override
	public boolean roundOver(RoundDetail round) {
		boolean isOver = false;

		if (completeDraw(round) || completeWord(round)) {
			isOver = true;
		}

		return isOver;
	}

	@Override
	public boolean completeDraw(RoundDetail round) {
		boolean isCompleted = false;

		if (countStroke(round) == 6) {
			round.setIsWin(false);
			isCompleted = true;
		}

		return isCompleted;
	}

	@Override
	public boolean completeWord(RoundDetail round) {
		boolean isCompleted = false;

		if (countLeft(round) == 0) {
			round.setIsWin(true);
			isCompleted = true;
		}

		return isCompleted;
	}

	@Override
	public int countStroke(RoundDetail round) {
		int stroke = 0;

		Pattern pattern = Pattern.compile("x");
		Matcher matcher = pattern.matcher(round.getWordTF());
		while (matcher.find())
			++stroke;

		return stroke;
	}

	@Override
	public int countLeft(RoundDetail round) {
		int left = round.getWordIndex().size();

		return left;
	}

	@Override
	public void initialPrint(Word word) {
		for (int i = 0; i < word.getWordLen(); i++) {
			print('\s');
		}
		System.out.println();
	}

	@Override
	public void updatePrint(Word word, ArrayList<Integer>[] indexSet) {
		for (int i = 0; i < word.getWordLen(); i++) {
			char letter = word.getSpelling().charAt(i);
			if (indexSet[0].contains(i))
				print(letter, 34);
			if (indexSet[1].contains(i))
				print(letter);
			if (indexSet[2].contains(i))
				print('\s');
		}
		System.out.println();
	}

	@Override
	public void print(char letter) {
		String underlined = new String();

		underlined += '\s';
		underlined += "\u001B[1m";
		underlined += letter;
		underlined += '\u0332';
		underlined += "\u001B[0m";

		System.out.print(underlined);
	}

	@Override
	public void print(char letter, int ansiEscapeColorCode) {
		String underlined = new String();

		underlined += '\s';
		underlined += "\u001B[1;" + ansiEscapeColorCode + "m";
		underlined += letter;
		underlined += '\u0332';
		underlined += "\u001B[0m";

		System.out.print(underlined);
	}

	@Override
	public void print(char letter, int[] ansiEscapeColorCode) {
		String underlined = new String();

		String colorCodes = new String();
		for (int colorCode : ansiEscapeColorCode) {
			colorCodes += String.valueOf(colorCode);
		}

		underlined += '\s';
		underlined += "\u001B[1;" + String.join(";", colorCodes) + "m";
		underlined += letter;
		underlined += '\u0332';
		underlined += "\u001B[0m";

		System.out.print(underlined);
	}

	@Override
	public ArrayList<Integer>[] reorder(RoundDetail round, int[] inputIndex) {
		ArrayList<Integer>[] indexSet = new ArrayList[3];

		ArrayList<Integer> blank = new ArrayList<>();
		ArrayList<Integer> correct = new ArrayList<>();
		ArrayList<Integer> find = new ArrayList<>();

		for (int i : inputIndex)
			find.add(i);
		for (int i : round.getCorrectIndex())
			correct.add(i);
		round.update(inputIndex);
		for (int i : round.getWordIndex())
			blank.add(i);

		indexSet[0] = find;
		indexSet[1] = correct;
		indexSet[2] = blank;

		return indexSet;
	}

	@Override
	public int[] findIndexOf(char letter, String spelling) {
		int count = countLetter(letter, spelling);
		int[] index = new int[count];

		for (int i = 0; i < spelling.length(); i++) {
			if (spelling.charAt(i) == letter) {
				index[--count] = i;
				if (count == 0)
					break;
			}
		}

		return index;
	}

	@Override
	public int countLetter(char letter, String spelling) {
		int count = 0;

		int original = spelling.length();
		int removed = spelling.replace(String.valueOf(letter), "").length();
		count = original - removed;

		return count;
	}

	@Override
	public boolean hasLetter(char letter, String spelling) {
		boolean hasLetter;

		if (spelling.indexOf(letter) == -1)
			hasLetter = false;
		else
			hasLetter = true;

		return hasLetter;
	}

	@Override
	public char getInput() {
		char input;

		Scanner scanner = new Scanner(System.in);
		System.out.print("알파벳 입력: ");
		input = scanner.next().charAt(0);

		return input;
	}

	@Override
	public boolean checkDupInput(char input, RoundDetail round) {
		boolean okay = false;

		int idx = round.getInput().indexOf(String.valueOf(input));
		if (idx != -1) {
			if (round.getWordTF().charAt(idx) == 'o')
				okay = true;
		}

		return okay;
	}

	@Override
	public Score calculate(Game game) {
		Score score = new Score(game);

		int wordScore = 0;
		int tfScore = 0;
		for (RoundDetail round : game.getRounds()) {
			wordScore += round.getRound().getLevel().getLevelScore();
			tfScore += calculate(round);
		}
		int bonusScore = calculate(game.getRounds());

		score.setWordScore(wordScore);
		score.setTfScore(tfScore);
		score.setBonusScore(bonusScore);
		score.setTotalScore(wordScore + tfScore + bonusScore);

		return score;
	}

	@Override
	public int calculate(RoundDetail round) {
		int score = 0;

		String wordTF = round.getWordTF();
		String tExcluded = wordTF.replace("o", "");
		String fExcluded = wordTF.replace("x", "");
		score += wordTF.length() - tExcluded.length();
		score -= wordTF.length() - fExcluded.length();

		return score;
	}

	@Override
	public int calculate(ArrayList<RoundDetail> rounds) {
		int score = 0;

		int cnt = 0;
		for (RoundDetail round : rounds) {
			if (round.getIsWin()) {
				int aheadIndex = rounds.indexOf(round) - 1;
				aheadIndex = aheadIndex < 0 ? 0 : aheadIndex;
				if (!rounds.get(aheadIndex).getIsWin())
					cnt = 0;
				score += ++cnt;
			}
		}

		return score;
	}

}
