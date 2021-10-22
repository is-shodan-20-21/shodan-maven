package Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Model.Role;
import Handler.ShodanViews.RequestedView;

public class ViewService {
    private Connection db;
	private Statement statement;

    public static final String DEFAULT_VIEW = "View/Default.jsp";
    public static final String DEFAULT_DIR = "View/";
	
	public ViewService(Connection db) {
		this.db = db;
	}

    public String getView(Role role, RequestedView view) {
        String path = DEFAULT_VIEW;

        try {
            this.statement = db.createStatement();

            String query = "SELECT path FROM views WHERE role = \"" + role.getRoleName() + "\" AND view = \"" + view.toString() + "\"";

            ResultSet result = statement.executeQuery(query);

            if(result.next())
                path = DEFAULT_DIR + result.getString("path");
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return path;
    }
}
