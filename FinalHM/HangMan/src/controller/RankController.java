package controller;

import java.util.ArrayList;
import java.util.Scanner;

import model.dao.RankDAO;
import model.dto.RankDTO;

public class RankController {

	Scanner sc = new Scanner(System.in);
	public RankDAO dao = new RankDAO();
	RankDTO dto = new RankDTO();

	ArrayList<RankDTO> rankList = new ArrayList<RankDTO>();
	int result = 0;

	// JoinController 에서 사용한 userid를 받아오기 위한 생성자 추가
	private String playerID;

	public RankController() {
	}

	public int totalScore(String userId) {
		dto.getUserid();
		result = dao.calTotalScore(userId);
		return result;
	}

	public String RankView(String playerID) {
		while (true) {
			String name = dao.getUserName(playerID);
			System.out.print("[1] 전체 순위 확인 [2] 내 순위 확인 [3] 종료 >> ");
			int menu = sc.nextInt();
			System.out.println();
			if (menu == 1) {
				ShowRankAll(playerID);
				return "RANK";
				
			} else if (menu == 2) {
				ShowRankOne(playerID, dto);
				return "RANK";
				
			} else if (menu == 3) {
				return "LAUNCH";
			}
		}
	}

	public ArrayList<RankDTO> allRank(String userId) {
		dto.getUserid();
		rankList = dao.RankAll();
		return rankList;
	}

	public void ShowRankAll(String playerID) {
		rankList.clear();
		System.out.println("\n" + "===😘 전체 순위 확인 ⭐===" + "\n");
		rankList = allRank(playerID);
		String name = dao.getUserName(playerID);
		for (int j = 1; j <= rankList.size(); j++) {
			for (int i = 0; i < rankList.size(); i++) {
				if (j == rankList.get(i).getAllscore()) {
					System.out.println(" <<순위>> " + rankList.get(i).getAllscore() + " <<ID>> " + rankList.get(i).getUserid() + " <<점수>> "
						 + rankList.get(i).getRankNo());
				}
			}
		}
		
		System.out.println();
	}
	
	public ArrayList<RankDTO> myRank(String userId) {
		dto.getUserid();
		rankList = dao.RankAll();
		return rankList;
	}

	public void ShowRankOne(String userId, RankDTO dto) {
		String name = dao.getUserName(playerID);
		System.out.println("\n" + "===😘 내 순위 확인 ⭐===" + "\n");

		System.out.println();
        rankList = dao.selectOne(userId) ;
        for (int i = 0; i < rankList.size(); i++) {
				System.out.println(" <<순위>> " + rankList.get(i).getAllscore() + " <<ID>> " + rankList.get(i).getUserid() + " <<점수>> "
					 + rankList.get(i).getRankNo());
			}
		
		System.out.println();
	}

}
