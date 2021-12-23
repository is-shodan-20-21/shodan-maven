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
                        result.getDate("transaction_date"),
                        result.getInt("transaction_price")
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
                        result.getDate("transaction_date"),
                        result.getInt("transaction_price")
                    )
                );
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public boolean insertTransaction(Transaction transaction) {
        String query = "INSERT INTO transactions(user_id, game_id, transaction_date, transaction_price) VALUES (?, ?, ?, ?)";

        System.out.println(query);

        try {
            statement = db.prepareStatement(query);
            statement.setInt(1, transaction.getUser().getId());
            statement.setInt(2, transaction.getGame().getId());
            statement.setDate(3, transaction.getTransaction_date());
            statement.setInt(4, transaction.getTransaction_price());

            if (statement.executeUpdate()==0)
                return false;
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteTransaction(Transaction transaction) {
        try {
            String query = "DELETE FROM transactions WHERE user_id = ? AND game_id = ?";
            statement = db.prepareStatement(query);
            statement.setInt(1, transaction.getUser().getId());
            statement.setInt(2, transaction.getGame().getId());

            if (statement.executeUpdate()==0)
                return false;
    
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}