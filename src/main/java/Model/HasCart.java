package Model;

/*
	[MODEL] Mappatura delle seguenti tabelle:
	- {HAS_CART}

    Sfrutta gli ID di {USERS} e {GAMES}.
*/
public class HasCart {
	
	private int user_id;
	private int game_id;
	
	public HasCart(int user_id, int game_id) {
		this.user_id = user_id;
		this.game_id = game_id;
	}

	public int getUserId() {
		return user_id;
	}

	public int getGameId() {
		return game_id;
	}

	public void setGameId(int game_id) {
		this.game_id = game_id;
	}

	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	
}
