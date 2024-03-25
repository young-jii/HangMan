package Controller;

import Model.JoinDAO;
import Model.JoinDTO;

import java.util.ArrayList;
import java.util.Scanner;

public class JoinController {
    JoinDTO dto = new JoinDTO();
    JoinDAO dao = new JoinDAO();
    Scanner sc = new Scanner(System.in);

    int result = 0 ;
    ArrayList<JoinDTO> LoginList = new ArrayList<JoinDTO>();

    public void FirstView(){
        while(true){
			System.out.print("[1] 회원 가입 [2] 로그인 [3] 종료 >> ");
			int menu = sc.nextInt();
			System.out.println();
            if(menu == 1){
                printJoin();
            } else if(menu == 2){
                printLogin();
            } else if(menu == 3){
                System.out.println("게임을 종료합니다.");
                break;
            }
        }
    }

    public void displayFirstView() {
        FirstView(); // FirstView 메서드 호출
    }

    public int Join(String id, String pw, String name){
        dto.setId(id);
        dto.setPw(pw);
        dto.setName(name);
        result = dao.input(dto);
        return result;
    }

    public void printJoin(){
        System.out.println("===⭐ 회원 가입 😘====");
		System.out.print("아이디 : ");
		String id = sc.next();
		System.out.print("패스워드 : ");
		String pw = sc.next();
		System.out.print("이름 : ");
		String name = sc.next();
		Join(id, pw, name);
    }

    public ArrayList<JoinDTO> login(String id, String pw) {
        dto.setId(id);
        dto.setPw(pw);
        LoginList = dao.login(dto);
        return LoginList ;
    }

    public void printLogin(){
        System.out.println("===⭐ 로그인 😘====");
		System.out.print("아이디 : ");
		String id = sc.next();
		System.out.print("패스워드 : ");
		String pw = sc.next();
		System.out.println();
		ArrayList<JoinDTO> joinList = login(id, pw);
		if (joinList.size() == 1) {
			for (int i = 0; i < joinList.size(); i++) {
				System.out.println("<ID> : " + joinList.get(i).getId());
				System.out.println("<이름> : " + joinList.get(i).getName());
				System.out.println("<가입 날짜> : " + joinList.get(i).getCreateDT());
			}
            System.out.println();
			joinList.clear();
            // GameController 로 userid 이동 및 전달
            GameStartController gamestartcontrol = new GameStartController(id) ;
            gamestartcontrol.startGame();

		} else {
			System.out.println("가입 내역이 없습니다. 회원 가입을 진행해주세요.");
		}
    }
}
