package controller;

import java.sql.SQLException;

import model.dto.UserDTO;
import model.ext.User;

public interface Loginable {

	public String launch();
	public String join() throws SQLException;
	public User login() throws SQLException;
	public String start();
	
//	public void FirstView();
//	public void printJoin();
//	public void prijntLogin();
	
//	public int Join(String id, String pw, String name);
//	public ArrayList<JoinDTO> login(String id, String pw);
	
//	public int input(JoinDTO dto);
//	public ArrayList<JoinDTO> login(JoinDTO dto);
}
