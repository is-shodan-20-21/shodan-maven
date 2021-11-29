package Model;

import java.io.Serializable;
import java.sql.Date;

/*
	[MODEL] Mappatura delle seguenti tabelle:
	- {ARTICLE}

    L'ID viene referenziato in {HAS_CARD} da {USERS}.
*/
public class Card implements Serializable {

	private static final long serialVersionUID = -9122193956940676022L;
    
    private int card_id;
    private String card_type;
    private long card_number;
    private String card_owner;
    private Date card_date;
    private User user;

    public Card(int card_id, String card_type, long card_number, String card_owner, Date card_date, User user) {
        this.card_id = card_id;
        this.card_type = card_type;
        this.card_number = card_number;
        this.card_owner = card_owner;
        this.card_date = card_date;
        this.user = user;
    }

    public int getCard_id() {
        return this.card_id;
    }

    /*
        Circuito utilizzato, ie. VISA, Mastercard...
    */
    public String getCard_type() {
        return this.card_type;
    }

    public long getCard_number() {
        return this.card_number;
    }

    public String getCard_owner() {
        return this.card_owner;
    }

    public Date getCard_date() {
        return this.card_date;
    }

    public User getOwner() {
        return this.user;
    }
}
