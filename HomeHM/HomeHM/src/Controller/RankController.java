package Controller;

import java.util.ArrayList;
import java.util.Scanner;

import Model.RankDAO;
import Model.RankDTO;

public class RankController {
    
    Scanner sc = new Scanner(System.in) ;
    RankDAO dao = new RankDAO() ;
    RankDTO dto = new RankDTO() ;

    ArrayList<RankDTO> rankList = new ArrayList<RankDTO>() ;
    int result = 0 ;


    // JoinController 에서 사용한 userid를 받아오기 위한 생성자 추가
    private String playerID ;
    public RankController(String playerID) {
        this.playerID = playerID;
    }
    public RankController() {
    }

    public void RankView(){
        while (true) {
            result = dao.calTotalScore(playerID) ;
            dao.update_insert_Rank(playerID, result);
            dao.updateRank();
            System.out.print("[1] 전체 순위 확인 [2] 내 순위 확인 [3] 종료 >> ");
            int menu = sc.nextInt();
            System.out.println();
            System.out.println("현재 게임중 ID >> " + playerID + "\n"); // >> 잘 들어옴! 이걸 mao 로 전달해야 함!
            if(menu == 1){
                ShowRankAll();
            } else if (menu == 2) {
                ShowRankOne(playerID, dto);
            } else if (menu == 3) {
                System.out.println("일단, 끝!");
                break ;
            }
        }
    }

    public void ShowRankAll(){
        rankList.clear();
        System.out.println("\n" + "===😘 전체 순위 확인 ⭐===" + "\n");
        rankList = dao.RankAll();
        System.out.println("ranklist 크기!" + rankList.size());
        for(int i = 1 ; i <= rankList.size() ; i++){
            for(int j = 0 ; j < rankList.size() ; j++){
                if(i == rankList.get(j).getRankNo()){
                    System.out.println(" 순위 >> " + rankList.get(j).getRankNo() + " | id >> " + rankList.get(j).getUserid());
                    break;
                }
            }
        }
        System.out.println();
    }

    public void ShowRankOne(String userId, RankDTO dto){
        rankList.clear();
        System.out.println("\n" + "===😘 내 순위 확인 ⭐===" + "\n");
        rankList = dao.selectOne(userId, dto) ;
        System.out.println("ranklist 크기 >> " + rankList.size());
        for(int i = 0 ; i < rankList.size() ; i ++){
                System.out.println(" | id >> " + rankList.get(i).getUserid() 
                + " | 이름 >> " + rankList.get(i).getUsername() 
                + " | 점수 >> " + rankList.get(i).getAllscore() 
                + " | 순위 >> " + rankList.get(i).getRankNo());
        }
        System.out.println();
    }

}
