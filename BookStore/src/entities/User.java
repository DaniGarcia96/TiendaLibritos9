package entities;

import dataAccess.UserXmlOp;

public abstract class User {
	private int idUser;
	private String username;
	private String password;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser){
		this.idUser = idUser;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		UserXmlOp.updateUser(this);
	}

	public String getPassword() {
		return password;
	}
	
	public void setType(String type){
		UserXmlOp.updateUser(this);
	}
	
	public abstract String getType();
	
	public boolean login(String username, String password){
		return this.username.equals(username) && this.password.equals(password);
	}
	
	public boolean save(){
		return UserXmlOp.addUser(this);
	}
}
