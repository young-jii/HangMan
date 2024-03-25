package Controller;

import java.util.Scanner;

import Model.GameStartDAO;
import Model.GameStartDTO;

public class GameStartController {
    
    Scanner sc = new Scanner(System.in);
    GameStartDAO dao = new GameStartDAO();
    GameStartDTO dto = new GameStartDTO();
    RankController rankControl = new RankController() ;

    // JoinController 에서 사용한 userid를 받아오기 위한 생성자 추가
    private String playerID ;
    public GameStartController(String playerID) {
        this.playerID = playerID;
    }

    public String getAnswer(String level){
        String answer = dao.getLevelWord(level, dto);
        return answer ;
    }

    public void startGame(){
        while(true){
            System.out.println("현재 게임중 ID >> " + playerID + "\n"); // >> game id 는 제대로 받고 있음
            System.out.print("[1] 게임 시작 [2] RANK 확인 [3] 뒤로 가기 >> ");
            int choice = sc.nextInt();
            System.out.println();
            if(choice == 1) {
                System.out.println( " << 게임을 시작하겠습니다. >> " + "\n");
                ChoiceLevel();
            } else if (choice == 2) {
                System.out.println( " << RANK 확인을 하겠습니다. >> " + "\n");
                // RankController 에 gameid 전달
                rankControl = new RankController(playerID) ;
                rankControl.RankView();
            } else if (choice == 3 ) {
                System.out.println( " << 첫 화면으로 돌아가겠습니다. >> " + "\n");
                break;
            }
            System.out.println();
        }
    }

    public void ChoiceLevel(){
        while (true) {
            System.out.println("난이도를 선택하세요." + "\n");
            System.out.print("[1] easy [2] normal [3] hard [4] break >> ");
            int level = sc.nextInt();
            System.out.println();
            if(level <= 3) {
                if(level == 1){
                    // easy 단어를 정답 단어로 가지고 있기
                    String dfficulty = "easy";
                    getAnswer(dfficulty) ;
                } else if (level == 2) {
                    // normal 단어를 정답 단어로 가지고 있기
                    String dfficulty = "normal";
                    getAnswer(dfficulty) ;
                } else if (level == 3) {
                    // hard 단어를 정답 단어로 가지고 있기
                    String dfficulty = "hard";
                    getAnswer(dfficulty) ;
                }
                System.out.println();
            } else if (level == 4) {
                System.out.println("뒤로 가기!");
                break ;
            } 
            else {
                System.out.print("\n" + "다시 입력하세요." + "\n");
            }
        }
    }




}
