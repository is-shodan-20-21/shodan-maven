package Collection;

import java.io.Serializable;
import Model.Game;

/*
	ParsedGame è stato implementato al fine di nascondere il bottone di aggiunta del carrello ..
    .. nelle pagine del negozio e della dashboard.

    Permette di stabilire una correlazione booleana diretta fra un gioco e il suo possedimento da parte di un utente.
*/
public class ParsedGame implements Serializable {

	private static final long serialVersionUID = 1781620604484876509L;

    private Game game;
    private boolean owned;

    public ParsedGame(Game game, boolean owned) {
        this.game = game;
        this.owned = owned;
    }

    public Game getGame() {
        return game;
    }

    public boolean getOwned() {
        return owned;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

}
