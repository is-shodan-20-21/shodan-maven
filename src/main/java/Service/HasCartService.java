package Service;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.Game;
import Model.HasCart;
import Model.User;

public class HasCartService implements Serializable {

	private static final long serialVersionUID = -8268400551985297095L;
	
	private Connection db;
	private PreparedStatement statement;
	
	public HasCartService(Connection db) {
		this.db = db;
	}	
	
	public boolean addItem(HasCart cart) {
		String ownedGamesQuery = "SELECT * FROM has_game WHERE user_id = ? AND game_id = ?";
		
		System.out.println("# HasCartService > Query > " + ownedGamesQuery);
		
		try {
			statement = db.prepareStatement(ownedGamesQuery);
			statement.setInt(1, cart.getUserId());
			statement.setInt(2, cart.getGameId());
			
			ResultSet result = statement.executeQuery();
			
			if(result.next())
				return false;
			
		} catch(SQLException e) {
			e.printStackTrace();
			
			return false;
		}
		
		String addGameQuery = "INSERT INTO has_cart VALUES (?, ?)";
		
		System.out.println("# HasCartService > Query > " + addGameQuery);
		
		try {
			statement = db.prepareStatement(addGameQuery);
			statement.setInt(1, cart.getUserId());
			statement.setInt(2, cart.getGameId());
			
			statement.executeUpdate();
			
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean removeItem(HasCart cart) {
		String query = "DELETE FROM has_cart WHERE user_id = ? AND game_id = ?";
		
		System.out.println("# HasCartService > Query > " + query);
		
		try {
			statement = db.prepareStatement(query);
			statement.setInt(1, cart.getUserId());
			statement.setInt(2, cart.getGameId());

			statement.executeUpdate();
			
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public boolean removeItemForAll(Game game) {
		String query = "DELETE FROM has_cart WHERE game_id = ?";
		
		System.out.println("# HasCartService > Query > " + query);
		
		try {
			statement = db.prepareStatement(query);
			statement.setInt(1, game.getId());
			
			statement.executeUpdate();
			
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean dropCart(User user) {
		String query = "DELETE FROM has_cart WHERE user_id = ?";
		
		System.out.println("# HasCartService > Query > " + query);
		
		try {
			statement = db.prepareStatement(query);
			statement.setInt(1, user.getId());
			
			statement.executeUpdate();
			
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public ArrayList<Game> selectCart(User user) {
		ArrayList<Game> cart = new ArrayList<Game>();
		
		String query = "SELECT * FROM has_cart WHERE user_id = ?";
		
		System.out.println("# HasCartService > Query > " + query);
		
		try {
			statement = db.prepareStatement(query);
			statement.setInt(1, user.getId());
			
			ResultSet result = statement.executeQuery();
			
			while(result.next())
				cart.add(new GameService(db).getGame(result.getInt("game_id")));
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return cart;
	}

	public boolean hasInCart(User user, Game game) {
		String query = "SELECT * FROM has_cart WHERE user_id = ? AND game_id = ?";

		try {
			statement = db.prepareStatement(query);
			statement.setInt(1, user.getId());
			statement.setInt(2, game.getId());
			
			ResultSet result = statement.executeQuery();
			
			if(result.next())
				return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
