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
import javax.swing.text.StyledEditorKit.BoldAction;

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

				String title = request.getParameter("title");
				String shortTitle = request.getParameter("shortTitle");
				String html = request.getParameter("html");

				if (title!=null && !title.equals("")) {
					if (shortTitle!=null && !shortTitle.equals("")) {
						if (html!=null && !html.equals("")) {
							
							Article newArticle = new Article(
								title,
								shortTitle,
								html,
								user
							);

							new ArticleService(db).addArticle(newArticle);
					
							request.setAttribute("messageArticleAdd", "Articolo aggiunto con successo");
							response.setStatus(200);

							System.out.println("# BlogServlet > POST > Articolo aggiunto > " + request.getParameter("title"));

							System.out.println("# TC_AggiungiArticolo > Articolo aggiunto con successo!");

						} else {
							System.out.println("# TC_AggiungiArticolo > Contenuto: campo obbligatorio");
						}
					} else {
						System.out.println("# TC_AggiungiArticolo > Sottotitolo: campo obbligatorio");
					}
				} else {
					System.out.println("# TC_AggiungiArticolo > Titolo: campo obbligatorio");
				}
			
				break;
				
		
			case "deleteArticle":

				String blog_id_string = request.getParameter("deleteArticleId");
				if (blog_id_string!=null && !blog_id_string.equals("")) {
					try {
						int blog_id = Integer.valueOf(blog_id_string);
						Article article = new ArticleService(db).getArticle(blog_id);
						if (article!= null) {
							new ArticleService(db).deleteArticle(blog_id);
							response.setStatus(200);
							System.out.println("# BlogServlet > POST > Articolo eliminato > " + article.getTitle());
							System.out.println("# TC_RimuoviArticolo > Articolo aggiunto con successo!");
						} else {
							response.setStatus(400);
							System.out.println("# BlogServlet > POST > Articolo non esistente");
							System.out.println("# TC_RimuoviArticolo > Articolo non esistente");
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
						System.out.println("# TC_RimuoviArticolo > Formato ID errato");
						response.setStatus(400);
					}
				} else {
					System.out.println("# TC_RimuoviArticolo > ID mancante");
					response.setStatus(400);
				}

				break;
		
			default:
				System.out.println("# BlogServlet > POST > Nessuna azione specificata");
			
			break;
		}
	}
	
}
