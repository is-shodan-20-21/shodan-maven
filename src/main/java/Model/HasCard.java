package Model;

import java.io.Serializable;

public class HasCard implements Serializable {

	private static final long serialVersionUID = -9122194956940676022L;
    
    private User user;
    private Card card;

    public HasCard(User user, Card card) {
        this.user = user;
        this.card = card;
    }

    public User getUser() {
        return this.user;
    }

    public Card getCard() {
        return this.card;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCard(Card card) {
        this.card = card;
    }

}
