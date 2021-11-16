package Model;

/*
	[MODEL] Mappatura delle seguenti tabelle:
	- {HAS_WRITTEN}

    Sfrutta gli ID di {USERS} e {BLOGS}.
*/
public class HasWritten {
	
	private int user_id;
	private int blog_id;
	
	public HasWritten(int user_id, int blog_id) {
		this.user_id = user_id;
		this.blog_id = blog_id;
	}

	public int getUserId() {
		return user_id;
	}

	public int getBlogId() {
		return blog_id;
	}

	public void setGameId(int blog_id) {
		this.blog_id = blog_id;
	}

	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	
}
