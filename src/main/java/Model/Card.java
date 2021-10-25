package Model;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;

public class Card implements Serializable {

	private static final long serialVersionUID = -9122193956940676022L;
    
    private int card_id;
    private String card_type;
    private long card_number;
    private String card_owner;
    private Date card_date;

    public Card(int card_id, String card_type, long card_number, String card_owner, Date card_date) {
        this.card_id = card_id;
        this.card_type = card_type;
        this.card_number = card_number;
        this.card_owner = card_owner;
        this.card_date = card_date;
    }

    public int getCard_id() {
        return this.card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public String getCard_type() {
        return this.card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public long getCard_number() {
        return this.card_number;
    }

    public void setCard_number(long card_number) {
        this.card_number = card_number;
    }

    public String getCard_owner() {
        return this.card_owner;
    }

    public void setCard_owner(String card_owner) {
        this.card_owner = card_owner;
    }

    public Date getCard_date() {
        return this.card_date;
    }

    public void setCard_date(Date card_date) {
        this.card_date = card_date;
    }

}
