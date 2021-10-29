package Model;

import java.io.Serializable;

/*
	[MODEL] Mappatura delle seguenti tabelle:
	- {ROLES}
    - ... e sopperisce alla mancanza di {VIEWS}

    Sfrutta l'ID di {USERS}.

    La tabella {VIEWS} non è stata mappata perché non ritenuto necessario. E' sufficiente ottenere il/i ruolo/i ..
    .. dell'utente per ottenere l'ENDPOINT corretto sotto forma di path.

    Inoltre, [this.parsed_role_name] è il testo mostrato a schermo nel menù di selezione del ruolo.

    Il sistema di ruoli di Shodan prevede quattro posizioni:
    - Ospite, default
    - Utente, ottenuto una volta loggati a Shodan
    - Articolista, cioè un admin che gestisce le notizie del blog
    - Cataloghista, cioè un admin che gestisce il catalogo di giochi

    Un attore può ricoprire più di un ruolo se loggato.

    La tabella {VIEWS} associa i ruoli a dei componenti e determina quale componente mostrare ..
    .. all'attore, in funzione dei suoi ruoli per l'appunto.
*/
public class Role implements Serializable {

    private static final long serialVersionUID = 1781620604484876509L;

    private int user_id;
    private String role_name;
    private String parsed_role_name;

    public Role(int user_id, String role_name) {
        this.user_id = user_id;
        this.role_name = role_name;

        switch(this.role_name) {
            case "USER":
                this.parsed_role_name = "Cliente";
                break;
            
            case "WRITER":
                this.parsed_role_name = "Articolista";
                break;

            case "STOREMAN":
                this.parsed_role_name = "Cataloghista";
                break;
        }
    }
    
    public int getUserId() {
        return user_id;
    }

    public String getRoleName() {
        return role_name;
    }
    
    public String getParsedRoleName() {
        return this.parsed_role_name;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public void setRoleName(String role_name) {
        this.role_name = role_name;
    }

    public void setParsedRoleName(String parsed_role_name) {
        this.parsed_role_name = parsed_role_name;
    }
}
