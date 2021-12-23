package Service;


import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Card;
import Model.User;
public class CardService implements Serializable {

	private static final long serialVersionUID = -7187173329847684984L;
	
	private Connection db;
	private PreparedStatement statement;
	
	public CardService(Connection db) {
		this.db = db;
	}
    
    public Card getCard(int card_id) {
        Card card = null;
        try {
            String query = "SELECT * FROM cards WHERE card_id = ?";

            statement = db.prepareStatement(query);
            statement.setInt(1, card_id);

            ResultSet result = statement.executeQuery();

            if(result.next()) {
                card = new Card(
                    card_id,
                    result.getString("card_type"),
                    result.getLong("card_number"),
                    result.getString("card_owner"),
                    result.getDate("card_date"),
                    new UserService(db).getUser(
                        result.getInt("user_id")
                    )
                );
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return card;
    }

    public Card getCardByNumber(Long card_number) {
        Card card = null;
        try {
            String query = "SELECT * FROM cards WHERE card_number = ?";

            statement = db.prepareStatement(query);
            statement.setLong(1, card_number);

            ResultSet result = statement.executeQuery();

            if(result.next()) {
                card = new Card(
                    result.getInt("card_id"),
                    result.getString("card_type"),
                    result.getLong("card_number"),
                    result.getString("card_owner"),
                    result.getDate("card_date"),
                    new UserService(db).getUser(
                        result.getInt("user_id")
                    )
                );
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return card;
    }

    public ArrayList<Card> getCardsByOwner(User user) {
        ArrayList<Card> cards = new ArrayList<Card>();

        try {
            String query = "SELECT * FROM cards WHERE user_id = ?";

            statement = db.prepareStatement(query);
            statement.setInt(1, user.getId());

            ResultSet result = statement.executeQuery();

            while(result.next()) {
                Card card = new CardService(this.db).getCard(
                    result.getInt("card_id")
                );

                cards.add(card);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return cards;
    }

    public ArrayList<Card> getAllCards() {
        ArrayList<Card> cards = new ArrayList<Card>();

        try {
            String query = "SELECT * FROM cards";

            statement = db.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            while(result.next()) {
                Card card = new CardService(this.db).getCard(
                    result.getInt("card_id")
                );

                cards.add(card);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return cards;
    }

    public boolean insertCard(Card card) {
        try {
            String query = "INSERT INTO cards(card_type, card_number, card_owner, card_date, user_id) VALUES (?, ?, ?, ?, ?)";

            statement = db.prepareStatement(query);
            statement.setString(1, card.getCard_type());
            statement.setLong(2, card.getCard_number());
            statement.setString(3, card.getCard_owner());
            statement.setDate(4, card.getCard_date());
            statement.setInt(5, card.getOwner().getId());
            if (statement.executeUpdate()==0)
                return false;

            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean removeCard (Card card) {
        try {
            String query = "DELETE FROM cards WHERE card_id = ?";
            statement = db.prepareStatement(query);
            statement.setInt(1, card.getCard_id());
            if (statement.executeUpdate()==0)
                return false;
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
