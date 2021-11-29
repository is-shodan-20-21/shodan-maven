package Model;

import java.io.Serializable;
import java.sql.Date;

/*
	[MODEL] Mappatura delle seguenti tabelle:
	- {GAME}

	L'ID viene referenziato in {HAS_CART} e {HAS_GAME} da {USERS}.
*/
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
	
	/*
		Questo campo è espresso come intero. L'accostamento della valuta in euro avviene tramite markup (&euro).
	*/
	public int getPrice() {
		return price;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	/*
		La "cover" o "image" è la copertina del gioco.
	*/
	public String getImage() {
		return image;
	}
	
	public Date getRelease() {
		return release;
	}

	/*
		Il "landscape" è l'immagine scenografica in background nella pagina individuale del gioco.
	*/
	public String getLandscape() {
		return landscape;
	}

	/*
		Routine di aggiornamento del prezzo utilizzata dal Cataloghista
	*/
	public void setPrice(int price) {
		this.price = price;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setRelease(Date release) {
		this.release = release;
	}

	public void setLandscape(String landscape) {
		this.landscape = landscape;
	}
}
