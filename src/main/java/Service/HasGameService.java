package Service;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Model.Game;
import Model.User;

public class HasGameService implements Serializable {

	private static final long serialVersionUID = -4279943566075781437L;
	
	private Connection db;
	private PreparedStatement statement;
	
	public HasGameService(Connection db) {
		this.db = db;
	}	
	
	public boolean addGame(User user, Game game) {
		try {
			String query = "INSERT INTO has_game VALUES (?, ?)";
			
			statement = db.prepareStatement(query);
			statement.setInt(1, user.getId());
			statement.setInt(2, game.getId());
			
			if (statement.executeUpdate()==0)
				return false;
			
			System.out.println("# GameService > Query > " + query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean hasGame(User user, Game game) {
		try {
			String query = "SELECT * FROM has_game WHERE user_id = ? AND game_id = ?";
			
			statement = db.prepareStatement(query);
			statement.setInt(1, user.getId());
			statement.setInt(2, game.getId());

			if(statement.executeQuery().next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public boolean removeItem(User user, Game game) {
		String query = "DELETE FROM has_game WHERE user_id = ? AND game_id = ?";
		
		System.out.println("# HasGameService > Query > " + query);
		
		try {
			statement = db.prepareStatement(query);
			statement.setInt(1, user.getId());
			statement.setInt(2, game.getId());
			
			if (statement.executeUpdate()==0)
				return false;
			
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public boolean removeItemForAll(Game game) {
		String query = "DELETE FROM has_game WHERE game_id = ?";
		
		System.out.println("# HasGameService > Query > " + query);
		
		try {
			statement = db.prepareStatement(query);
			statement.setInt(1, game.getId());
			
			if (statement.executeUpdate()==0)
				return false;
			
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
