package Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.Transaction;
import Model.User;

public class TransactionService {

	private Connection db;
	private PreparedStatement statement;
	
	public TransactionService(Connection db) {
		this.db = db;
	}

    public ArrayList<Transaction> getTransactionsByUser(User user) {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        String query = "SELECT * FROM transactions WHERE user_id = ?";

        try {
            statement = db.prepareStatement(query);
            statement.setInt(1, user.getId());

            ResultSet result = statement.executeQuery();

            while(result.next()) {
                transactions.add(
                    new Transaction(
                        user, 
                        new GameService(this.db).getGame(
                            result.getInt("game_id")
                        ),
                        result.getDate("transaction_date")
                    )
                );
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public ArrayList<Transaction> getTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        String query = "SELECT * FROM transactions";

        try {
            statement = db.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            while(result.next()) {
                transactions.add(
                    new Transaction(
                        new UserService(this.db).getUser(
                            result.getInt("user_id")
                        ), 
                        new GameService(this.db).getGame(
                            result.getInt("game_id")
                        ),
                        result.getDate("transaction_date")
                    )
                );
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public void insertTransaction(Transaction transaction) {
        System.out.println(transaction.getTransaction_date());
        String query = "INSERT INTO transactions(user_id, game_id, transaction_date) VALUES ("
            + transaction.getUser().getId() + ", "
            + transaction.getGame().getId() + ", \""
            + transaction.getTransaction_date() + "\")";

        System.out.println(query);

        try {
            statement = db.prepareStatement(query);
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}