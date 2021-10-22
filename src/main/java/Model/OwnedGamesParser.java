package Model;

import java.io.Serializable;

public class OwnedGamesParser implements Serializable {

	private static final long serialVersionUID = 1781620604484876509L;

    private Game game;
    private boolean owned;

    public OwnedGamesParser(Game game, boolean owned) {
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
