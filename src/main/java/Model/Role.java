package Model;

import java.io.Serializable;

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
