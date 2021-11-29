package Collection;

import Model.Card;

/*
    ParsedCard è una collezione che permette, in maniera rapida e indolore, di stampare esclusivamente ..
    .. le prime quattro cifre del numero della carta.
*/
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

    public long getSafe_digits() {
        return this.safe_digits;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setSafe_digits(long digits) {
        this.safe_digits = digits;
    }

}
