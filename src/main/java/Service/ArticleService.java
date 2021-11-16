package Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.Article;

public class ArticleService {

	private Connection db;
	private PreparedStatement statement;
	
	public ArticleService(Connection db) {
		this.db = db;
	}
	
	public Article getArticle(int id) {
		Article article = null;
		
		try {
			String query = "SELECT * FROM blog WHERE blog_id = ?";
			
			statement = db.prepareStatement(query);
			statement.setInt(1, id);
			
			ResultSet result = statement.executeQuery();
			
			System.out.println("# ArticleService > Executing SELECT * FROM blog WHERE blog_id = " + id);
			
			if(result.next())
				article = new Article(
					result.getInt("blog_id"),
					result.getString("blog_title"),
					result.getString("blog_short_title"),
					result.getString("blog_html")
				);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return article;
	}
	
	public ArrayList<Article> getAllArticles(int limit) {
		ArrayList<Article> blog = new ArrayList<Article>();
		
		try {
			String query = "SELECT * FROM blog" + (limit != 0 ? (" LIMIT " + limit) : "");
			
			statement = db.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			System.out.println("# ArticleService > Query > " + query);
			
			if(!result.next())
				return null;
			
			result.beforeFirst();
			
			while(result.next()) {
				blog.add(
					new Article(
						result.getInt("blog_id"),
						result.getString("blog_title"),
						result.getString("blog_short_title"),
						result.getString("blog_html")
					)
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return blog;
	}
	
	public boolean addArticle(String title, String shortTitle, String html){
		try {
			String query = "INSERT INTO blog(blog_title, blog_short_title, blog_html) VALUES(?, ?, ?)";
		
			System.out.println("# ArticleService > Query > " + query);
			
			statement = db.prepareStatement(query);
			statement.setString(1, title);
			statement.setString(2, shortTitle);
			statement.setString(3, html);
			
			statement.executeUpdate();
			
			System.out.println("# ArticleService > Aggiungo l'articolo " + title);
		
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public Article findArticle(String title, String shortTitle, String html){
		Article article = null;

		try {
			String query = "SELECT * FROM blog WHERE blog_title = ? AND blog_short_title = ? AND blog_html = ?";
		
			System.out.println("# ArticleService > Query > " + query);
			
			statement = db.prepareStatement(query);
			statement.setString(1, title);
			statement.setString(2, shortTitle);
			statement.setString(3, html);
			
			ResultSet result = statement.executeQuery();
			
			System.out.println("# ArticleService > Aggiungo l'articolo " + title);
		
			if(result.next())
				article = new Article(
					result.getInt("blog_id"),
					result.getString("blog_title"),
					result.getString("blog_short_title"),
					result.getString("blog_html")
				);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return article;
	}

	public boolean deleteArticle(int id) {
		try {
			String query = "DELETE FROM blog WHERE blog_id = ?";
			
			System.out.println("# ArticleService > Query > " + query);
			
			statement = db.prepareStatement(query);
			statement.setInt(1, id);
			
			statement.executeUpdate();
			
			System.out.println("# ArticleService > Elimino l'articolo con id " + id);
			
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
