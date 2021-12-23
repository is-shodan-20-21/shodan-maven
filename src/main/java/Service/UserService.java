package Service;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import Model.User;

public class UserService implements Serializable {

	private static final long serialVersionUID = -7885019384296693341L;
	
	private Connection db;
	private PreparedStatement statement;
	
	public UserService(Connection db) {
		this.db = db;
	}
	
	public int getIdByUsername(String username) {
		int id = -1;
		
		try {
			String query = "SELECT user_id FROM users WHERE user_name = ?";
			
			statement = db.prepareStatement(query);
			statement.setString(1, username);
			ResultSet result = statement.executeQuery();
			
			System.out.println("# UserService > Query > " + query);
			
			result.next();
			id = result.getInt("user_id");		
		} catch (SQLException e) {
			System.out.println("# UserService > Utente inesistente");
		}
		
		return id;
	}
	
	public User getUser(int id) {
		User user = null;
		
		try {
			String query = "SELECT * FROM users WHERE user_id = ?";
			
			statement = db.prepareStatement(query);
			statement.setInt(1, id);
			
			ResultSet result = statement.executeQuery();
			
			System.out.println("# UserService > Query > " + query);

			if(result.next()) {
				int user_id = result.getInt("user_id");
				user = new User(
					user_id,
					result.getString("user_name"),
					result.getString("user_password"),
					result.getString("user_email"),
					result.getInt("user_money"),
					result.getString("user_session"),
					new HasRoleService(this.db).getRoles(user_id)
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}

	public boolean findUserByEmail(String email) {
		try {
			String query = "SELECT * FROM users WHERE user_email = ?";
			
			statement = db.prepareStatement(query);
			statement.setString(1, email);
			
			ResultSet result = statement.executeQuery();
			
			System.out.println("# UserService > Query > " + query);

			if(result.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean updateUser(User user) throws SQLException {
		try {
			String query = 
				  "UPDATE users SET"
				+ "  user_id = ?"
				+ ", user_name = ?"
				+ ", user_password = ?"
				+ ", user_email = ?"
				+ ", user_money = ?"
				+ ", user_session =  ?"
				+ " WHERE user_id = ?";
		
			System.out.println("# UserService > Query > " + query);
			
			statement = db.prepareStatement(query);
			statement.setInt(1, user.getId());
			statement.setString(2, user.getName());
			statement.setString(3, user.getPassword());
			statement.setString(4, user.getEmail());
			statement.setInt(5, user.getMoney());
			statement.setString(6, user.getSession());
			statement.setInt(7, user.getId());
			
			if (statement.executeUpdate()==0)
				return false;
			
			System.out.println("# UserService > Aggiorno l'utente ID " + user.getId());
			
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean insertUser(String username, String password, String email) {
		try {
			String query = "INSERT INTO users(user_name, user_password, user_email) VALUES(?, ?, ?)";
			
			System.out.println("# UserService > Query > " + query);
			
			statement = db.prepareStatement(query);
			statement.setString(1, username);
			statement.setString(2, password);
			statement.setString(3, email);
			
			statement.executeUpdate();

			query = "SELECT * FROM users WHERE user_name = ?";
			statement = db.prepareStatement(query);
			statement.setString(1, username);

			ResultSet result = statement.executeQuery();

			if(result.next()) {
				query = "INSERT INTO has_role(user_id, role) VALUES (?, \"USER\")";	
				statement = db.prepareStatement(query);
				statement.setInt(1, result.getInt("user_id"));
				statement.executeUpdate();
			}

			System.out.println("# UserService > Inserisco l'utente " + username);
			
			return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean deleteUser(int id) {
		try {
			String query = "DELETE FROM users WHERE user_id = ?";
			
			statement = db.prepareStatement(query);
			statement.setInt(1, id);
			
			if (statement.executeUpdate()==0)
				return false;
			
			System.out.println("# UserService > Query > " + query);
			
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public User getUserBySession(String jsession) {
		try {
			String query = "SELECT user_id FROM users WHERE user_session = ?";
			
			statement = db.prepareStatement(query);
			statement.setString(1, jsession);
			
			ResultSet result = statement.executeQuery();
			
			System.out.println("# SessionService > Query > " + query);
			
			if(result.next())
				return getUser(result.getInt("user_id"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insertSession(String jsession, User user) {
		try {
			String query = "INSERT INTO jsessions(jsession, user_id) VALUES(?, ?)";
			
			statement = db.prepareStatement(query);
			statement.setString(1, jsession);
			statement.setInt(2, user.getId());
			
			statement.executeUpdate();
					
			System.out.println("# SessionService > Query > " + query);
			
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void destroySession(User user) {
		try {
			String query = "UPDATE users SET user_session = null WHERE user_id = ?";
			
			statement = db.prepareStatement(query);
			statement.setInt(1, user.getId());
			
			statement.executeUpdate();
			
			System.out.println("# SessionService > Query > " + query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public User getUserByUsername (String username) {
		try {
			String query = "SELECT user_id FROM users WHERE user_name = ?";
			
			statement = db.prepareStatement(query);
			statement.setString(1, username);
			
			ResultSet result = statement.executeQuery();
			
			System.out.println("# SessionService > Query > " + query);
			
			if(result.next())
				return getUser(result.getInt("user_id"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
