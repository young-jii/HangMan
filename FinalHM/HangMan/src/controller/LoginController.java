package controller;

import java.sql.SQLException;
import java.util.Scanner;

import model.dao.UserDAO;
import model.dto.UserDTO;
import model.ext.Game;
import model.ext.Level;
import model.ext.Round;
import model.ext.User;
import model.ext.Word;

public class LoginController implements Loginable {

	private Scanner scanner;
	private UserDAO userDAO;

	public LoginController(Scanner scanner) throws ClassNotFoundException, SQLException {
		this.scanner = scanner;
		userDAO = new UserDAO();
	}

	@Override
	public String launch() {
		System.out.print("[1] 회원 가입 [2] 로그인 [3] 종료 >> ");
		int menu = scanner.nextInt();
		System.out.println();
		
		if (menu == 1)
			return "JOIN";
		
		else if (menu == 2)
			return "LOGIN";
		
		else if (menu == 3)
			return "EXIT";
		
		else
			return "EXCEPTION";
	}

	@Override
	public String join() throws SQLException {
		System.out.println("===⭐ 회원 가입 😘====");

		System.out.print("아이디 : ");
		String id = scanner.next();
		System.out.print("패스워드 : ");
		String pw = scanner.next();
		System.out.print("이름 : ");
		String name = scanner.next();

		User joinUser = new User(id, pw, name);
		int result = userDAO.insert(joinUser);
		if (result > 0) {
			System.out.println("HangMan에 오신 것을 환영합니다. "  + "\n");
//			return "LOGIN";
			return "LAUNCH";
		} else {
			System.out.println("\n" + "회원 가입되지 않았습니다. 초기 화면으로 돌아갑니다." + "\n");
			return "LAUNCH";
		}
	}

	@Override
	public User login() throws SQLException {
		System.out.println("===⭐ 로그인 😘====");

		System.out.print("아이디 : ");
		String id = scanner.next();
		System.out.print("패스워드 : ");
		String pw = scanner.next();

		User user = new User(id, pw);
		User result = userDAO.select(user);
		if (result == null) {
			System.out.println("가입 내역이 없습니다. 회원 가입을 진행해주세요.");
			join();
		} else {
			System.out.println("\n" + "안녕하세요. " + result.getName() + "님. (´▽`ʃ♡ƪ)"  + "\n");
		}
		return result;
	}

	
	@Override
	public String start() {
		while (true) {
			System.out.print("[1] 게임 시작 [2] RANK 확인 [3] 로그 아웃 >> ");
			int choice = scanner.nextInt();
			System.out.println();
			
			if (choice == 1) {
				System.out.println(" << 게임을 시작하겠습니다. >> \n");
				return "GAME";
				
			} else if (choice == 2) {
				System.out.println(" << RANK 확인을 하겠습니다. >> \n");
				return "RANK";
				
			} else if (choice == 3) {
				System.out.println(" << 첫 화면으로 돌아가겠습니다. >> \n");
				return "LAUNCH";
				
			} else {
                System.out.print("\n" + "다시 입력하세요." + "\n");
			}
		}
	}

}
