package view;

import java.sql.SQLException;
import java.util.Scanner;

import controller.LoginController;
import controller.PlayController;
import controller.RankController;
import model.ext.Game;
import model.ext.User;

public class Main {

	public static Scanner scanner;
	public static LoginController loginController;
	public static PlayController playController;
	public static RankController rankController;

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		scanner = new Scanner(System.in);
		loginController = new LoginController(scanner);
		playController = new PlayController(scanner);
		rankController = new RankController();

		String playerID = "";
		
		String view = "LAUNCH";
		while (true) {
			if ("LAUNCH".equals(view)) {
				view = loginController.launch();
				
			} else if ("JOIN".equals(view)) {
				view = loginController.join();

			} else if ("LOGIN".equals(view)) {
				User loginUser = loginController.login();
				if(loginUser != null) {
					playerID = loginUser.getUserId();
					view = loginController.start();
				}

			} else if ("GAME".equals(view)) {
				Game game = playController.start(playerID);
				view = playController.showGameResult(game);
				
			} else if ("RANK".equals(view)) {
                int totalScore = rankController.dao.calTotalScore(playerID);
                rankController.dao.update_insert_Rank(playerID, totalScore);
                rankController.dao.updateRank() ;
                view = rankController.RankView(playerID);
				
			} else if ("EXIT".equals(view)) {
				System.out.println("게임을 종료합니다.");
				break;
				
			} else {
				break;
			}
		}
	}
}
