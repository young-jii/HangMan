package model.ext;

import java.util.ArrayList;

import model.dto.UserDTO;

public class User extends UserDTO {
	
	private ArrayList<Game> game;
	
	public User() {
	}
	public User(String userId, String userPw, String name) {
		super(userId, userPw, name);
	}
	public User(String userId, String userPw) {
		super(userId, userPw);
	}
	
}
