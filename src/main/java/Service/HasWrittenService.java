package Service;

import Model.HasWritten;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HasWrittenService implements Serializable {
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
	
	public HasWrittenService(Connection db) {
		this.db = db;
	}

    public HasWritten getAuthor(int blog_id) {
        HasWritten authorSet = null;
		
		try {
			String query = "SELECT * FROM has_written WHERE blog_id = ?";
			
			statement = db.prepareStatement(query);
            statement.setInt(1, blog_id);

			ResultSet result = statement.executeQuery();
			
			System.out.println("# HasWrittenService > Query > " + query);

			result.beforeFirst();
			
			if(result.next())
                authorSet = new HasWritten(
                    result.getInt("user_id"),
                    blog_id
                );
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return authorSet;
    }

    public void addAuthor(HasWritten authorSet) {
		try {
			String query = "INSERT INTO has_written(user_id, blog_id) VALUES (?, ?)";
			
			statement = db.prepareStatement(query);
            statement.setInt(1, authorSet.getUserId());
            statement.setInt(2, authorSet.getBlogId());

			statement.executeUpdate();
			
			System.out.println("# HasWrittenService > Query > " + query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public void removeAuthor(HasWritten authorSet) {
		try {
			String query = "DELETE FROM has_written WHERE user_id = ? AND blog_id = ?";
			
			statement = db.prepareStatement(query);
            statement.setInt(1, authorSet.getUserId());
            statement.setInt(2, authorSet.getBlogId());

			statement.executeUpdate();
			
			System.out.println("# HasWrittenService > Query > " + query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
