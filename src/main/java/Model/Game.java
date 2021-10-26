package Model;

import java.io.Serializable;
import java.sql.Date;

public class Game implements Serializable {

	private static final long serialVersionUID = -9122194956940676053L;
	
	private int id;
	private int price;
	private String name;
	private String description;
	private String image;
	private Date release;
	private String landscape;
	
	public Game(int id, int price, String name, String description, String image, Date release, String landscape) {
		this.id = id;
		this.price = price;
		this.name = name;
		this.description = description;
		this.image = image;
		this.release = release;
		this.landscape = landscape;
	}
	
	public int getId() {
		return id;
	}
	
	public int getPrice() {
		return price;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getImage() {
		return image;
	}
	
	public Date getRelease() {
		return release;
	}

	public String getLandscape() {
		return landscape;
	}
	
}
