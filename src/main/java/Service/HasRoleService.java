package Service;

import Model.Role;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HasRoleService implements Serializable {
    private static final long serialVersionUID = -7885019384296693371L;
	
	public enum AdminStatus {
		WRITER,
		STOREMAN
	}

	public enum UserStatus {
		USER
	}

	private Connection db;
	private PreparedStatement statement;
	
	public HasRoleService(Connection db) {
		this.db = db;
	}

    public ArrayList<Role> getRoles(int user_id) {
        ArrayList<Role> roles = new ArrayList<Role>();
		
		try {
			String query = "SELECT * FROM has_role WHERE user_id = ?";
			
			statement = db.prepareStatement(query);
            statement.setInt(1, user_id);

			ResultSet result = statement.executeQuery();
			
			System.out.println("# HasRoleService > Query > " + query);
			
			if(!result.next())
				return null;
			
			result.beforeFirst();
			
			while(result.next()) {
				roles.add(
					new Role(
						result.getInt("user_id"),
						result.getString("role")
					)
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return roles;
    }

	public Role getMainRole(ArrayList<Role> roles) {
		for(Role role : roles)
			if(!role.getRoleName().equals(UserStatus.USER.toString()))
				return new Role(role.getUserId(), role.getRoleName());

		return new Role(roles.get(0).getUserId(), UserStatus.USER.toString());
	}
}
