package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

	private static final long serialVersionUID = -3555522637968508649L;
	
	private int id;
	private int money;
	private String name;
	private String password;
	private String email;
	private String session;
	private ArrayList<Role> roles;
	
	public User(int id, String username, String password, String email, int money, String session, ArrayList<Role> roles) {
		this.id = id;
		this.money = money;
		this.name = username;
		this.password = password;
		this.email = email;
		this.session = session;
		this.roles = roles;
	}
	
	public int getId() {
		return id;
	}
	
	public int getMoney() {
		return money;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getEmail() {
		return email;
	}

	public String getSession() {
		return session;
	}
	
	public ArrayList<Role> getRoles() {
		return roles;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setMoney(int money) {
		this.money = money;
	}
	
	public void setUsername(String name) {
		this.name = name;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setSession(String session) {
		this.session = session;
	}

	public void setRole(ArrayList<Role> roles) {
		this.roles = roles;
	}

	public String toString() {
		return "[" + id + "] " + name; 
	}
	
}
