package Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.Card;
import Model.User;

public class HasCardService {

	private Connection db;
	private PreparedStatement statement;
	
	public HasCardService(Connection db) {
		this.db = db;
	}

    public ArrayList<Card> getCards(User user) {
        ArrayList<Card> cards = new ArrayList<Card>();

        try {
            String query = "SELECT * FROM has_card WHERE user_id = ?";

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

    public void addCard(User user, Card card) {
        try {
            String query = "INSERT INTO has_card(user_id, card_id) VALUES (?, ?)";

            statement = db.prepareStatement(query);
            statement.setInt(1, user.getId());
            statement.setInt(2, card.getCard_id());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
