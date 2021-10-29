package Model;

import java.io.Serializable;

/*
	[MODEL] Mappatura delle seguenti tabelle:
	- {HAS_CARD}

    Sfrutta gli ID di {USERS} e {CARDS}.
*/
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

}
