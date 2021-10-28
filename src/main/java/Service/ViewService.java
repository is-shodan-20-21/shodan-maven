package Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.Role;
import Handler.ShodanViews.RequestedView;

public class ViewService {
    private Connection db;
	private PreparedStatement statement;

    public static final String DEFAULT_VIEW = "View/Default.jsp";
    public static final String DEFAULT_DIR = "View/";
	
	public ViewService(Connection db) {
		this.db = db;
	}

    public String getView(Role role, RequestedView view) {
        String path = DEFAULT_VIEW;

        try {
            String query = "SELECT path FROM views WHERE role = ? AND view = ?";

            statement = db.prepareStatement(query);
            statement.setString(1, role.getRoleName());
            statement.setString(2, view.toString());

            ResultSet result = statement.executeQuery();

            if(result.next())
                path = DEFAULT_DIR + result.getString("path");
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return path;
    }
}
