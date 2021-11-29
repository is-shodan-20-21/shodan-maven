package Control;

import Model.Article;
import Model.User;
import Service.ArticleService;
import Service.UserService;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BlogServlet")
public class BlogServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		System.out.println("# BlogServlet > Session: " + request.getSession().getId());
		
		Connection db = (Connection) request.getServletContext().getAttribute("databaseConnection");
		
		String endpoint = request.getParameter("endpoint");
		
		switch(request.getParameter("action")) {
			case "blog":
				int limit = request.getParameter("limit") != null 
							? Integer.parseInt(request.getParameter("limit")) 
							: 0;
	
				ArrayList<Article> articles = new ArticleService(db).getAllArticles(limit);
				
				if(articles != null) {
					request.setAttribute("articles", articles);
					request.getRequestDispatcher(endpoint).forward(request, response);
					response.setStatus(200);
				} else 
					response.setStatus(400);
				
				System.out.println("# BlogServlet > GET > Tutti gli articoli");
				
				break;
			
			case "article":
				int blog_id = request.getParameter("blog_id") != null 
							? Integer.parseInt(request.getParameter("blog_id")) 
							: 0;
				
				Article article = new ArticleService(db).getArticle(blog_id);
				
				System.out.println("# BlogServlet > GET > " + article.toString());
				
				request.setAttribute("article", article);
				request.getRequestDispatcher(endpoint).forward(request, response);
				
				break;
				
			default:
				System.out.println("# BlogServlet > GET > Nessuna azione specificata");
				
				break;
		}
	}
	
	protected void doPost(
			HttpServletRequest request,
			HttpServletResponse response
	) throws ServletException, IOException {
		System.out.println("# BlogServlet > Session: " + request.getSession().getId());
		
		Connection db = (Connection) request.getServletContext().getAttribute("databaseConnection");

		User user;

		if(request.getParameter("cookie").equals("false")) {
			user = new UserService(db).getUserBySession(request.getParameter("jsession"));
		} else
			user = (User) request.getSession().getAttribute("user_metadata");
		
		switch(request.getParameter("action")) {
			case "addArticle":
			
				Article newArticle = new Article(
					request.getParameter("title"),
					request.getParameter("shortTitle"), 
					request.getParameter("html"),
					user
				);

				new ArticleService(db).addArticle(newArticle);
		
				request.setAttribute("messageArticleAdd", "Articolo aggiunto con successo");
				response.setStatus(200);

				System.out.println("# BlogServlet > POST > Articolo aggiunto > " + request.getParameter("title"));
		
				break;
		
			case "deleteArticle":
				int blog_id = Integer.valueOf(request.getParameter("deleteArticleId"));
				Article article = new ArticleService(db).getArticle(blog_id);
				
				if(article != null) {
					new ArticleService(db).deleteArticle(blog_id);
					response.setStatus(200);
					System.out.println("# BlogServelt > POST > Articolo eliminato > " + article.getTitle());
				} else {
					response.setStatus(400);
					System.out.println("# BlogServelt > POST > Articolo insistente");
				}
		
			default:
				System.out.println("# BlogServelt > POST > Nessuna azione specificata");
			
			break;
		}
	}
	
}
