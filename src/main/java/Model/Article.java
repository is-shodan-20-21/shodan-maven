package Model;

import java.io.Serializable;

/*
	[MODEL] Mappatura delle seguenti tabelle:
	- {ARTICLE}
*/
public class Article implements Serializable {

	private static final long serialVersionUID = -2719650670762044594L;
	
	private int id;
	private String title;
	private String shortTitle;
	private String html;
	private User author;
	
	public Article(String title, String shortTitle, String html, User author) {
		this.id = -1;
		this.title = title;
		this.shortTitle = shortTitle;
		this.html = html;
		this.author = author;
	}

	public Article(int id, String title, String shortTitle, String html, User author) {
		this.id = id;
		this.title = title;
		this.shortTitle = shortTitle;
		this.html = html;
		this.author = author;
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getShortTitle() {
		return shortTitle;
	}
	
	public String getHtml() {
		return html;
	}

	public User getAuthor() {
		return author;
	}
	
	public String toString() {
		return "Article [" + title + "][" + shortTitle + "][" + html + "]";
	}
	
}
