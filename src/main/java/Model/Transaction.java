package Model;

import java.io.Serializable;
import java.sql.Date;

public class Transaction implements Serializable {

	private static final long serialVersionUID = -9122194956940676063L;
    
    private User user;
    private Game game;
    private Date transaction_date;

    public Transaction(User user, Game game, Date transaction_date) {
        this.user = user;
        this.game = game;
        this.transaction_date = transaction_date;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Date getTransaction_date() {
        return this.transaction_date;
    }

    public void setTransaction_date(Date transaction_date) {
        this.transaction_date = transaction_date;
    }

}
