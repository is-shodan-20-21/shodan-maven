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
	
	public Article(int id, String title, String shortTitle, String html) {
		this.id = id;
		this.title = title;
		this.shortTitle = shortTitle;
		this.html = html;
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
	
	public String toString() {
		return "Article [" + title + "][" + shortTitle + "][" + html + "]";
	}
	
}
