package Service;


import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Card;
import Model.Game;

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
                    result.getDate("card_date")
                );
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return card;
    }

    public void insertCard(Card card) {
        try {
            String query = "INSERT INTO cards(card_type, card_number, card_owner, card_date) VALUES ("
                + card.getCard_type() + ", "
                + card.getCard_number() + ", "
                + card.getCard_owner() + ", "
                + card.getCard_date() + ")";

            statement = db.prepareStatement(query);
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

}
