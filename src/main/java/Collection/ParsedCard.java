package Collection;

import Model.Card;

public class ParsedCard {
    
    Card card;
    long safe_digits;

    public ParsedCard(Card card) {
        this.card = card;
        String number = card.getCard_number() + "";
        this.safe_digits = Long.valueOf(number.substring(0, 4));
    }

    public Card getCard() {
        return this.card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public long getSafe_digits() {
        return this.safe_digits;
    }

    public void setSafe_digits(long safe_digits) {
        this.safe_digits = safe_digits;
    }

}
