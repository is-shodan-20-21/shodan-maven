package Control;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Model.Role;
import Model.Game;
import Model.User;
import Service.GameService;
import Service.HasGameService;
import Service.HasRoleService;
import Service.UserService;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = -4587622200104894945L;

	User user;
	Connection db;

	public UserServlet() {
		this.user = null;
	}
	
	protected void doGet(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		System.out.println("# UserServlet > Session: " + request.getSession().getId());

		Connection db = (Connection) request.getServletContext().getAttribute("databaseConnection");
		String endpoint = request.getParameter("endpoint");
		
		if(request.getParameter("cookie") != null) {
			if(request.getParameter("cookie").equals("false"))
				user = new UserService(db).getUserBySession(request.getParameter("jsession"));
			else
				user = (User) request.getSession().getAttribute("user_metadata");
		}
		
		switch(request.getParameter("action")) {
			case "role":
				if(user != null) {
					String main_role = new HasRoleService(db).getMainRole(user.getRoles()).getRoleName();

					System.out.println("# UserSerlvet > GET > Accesso al ruolo (" + main_role + ")");
						
					response.getWriter().println(main_role);
					response.setStatus(200);

					return;
				}
				
				response.setStatus(400);
				
				break;

			case "switchableRoles":
				if(user != null && user.getRoles().size() > 1) {
					System.out.println("# UserServlet > GET > Accesso alla lista dei ruoli dell'utente");

					ArrayList<Role> roles = user.getRoles();

					request.setAttribute("roles", roles);	
					request.getRequestDispatcher(endpoint).forward(request, response);
					response.setStatus(200);
				} else
					response.setStatus(400);
				break;
		
			case "purchase":
				System.out.println("# UserSerlvet > GET > Pagamento in corso da " + user.getName());
				
				int price = Integer.parseInt(
					request.getParameter("price")
				);
				
				System.out.println("# UserServlet > Transizione > Prezzo: " + price + " / Utente: " + user.getMoney());
				
				if(user.getMoney() < price) {
					System.out.println("# UserServlet > GET > L'utente non ha abbastanza fondi per l'acquisto");		
					response.getWriter().println("Non hai abbastanza soldi!");
					break;	
				}
				
				String[] gameIds = request.getParameter("games").split("-");
				
				for(String gameId : gameIds) {
					Game game = new GameService(db).getGame(Integer.parseInt(gameId));
					new HasGameService(db).addGame(
						user,
						game
					);
				}
				
				user.setMoney(user.getMoney() - price);
				
				new UserService(db).updateUser(user);
				
				System.out.println("# UserServlet > GET > Pagamento concluso con successo");
				
				response.getWriter().println("Pagamento concluso con successo!");
				request.getSession().setAttribute("user_metadata", user);
				
				break;
			
			default:
				System.out.println("#UserServlet > GET > Nessuna azione specificata");
				
				break;
		}
	}
	
	protected void doPost(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		Connection db = (Connection) request.getServletContext().getAttribute("databaseConnection");
		
		switch(request.getParameter("action")) {
			case "logout":
				System.out.println("# UserServlet > POST > Logout dell'utente");
				
				if(request.getParameter("cookie").equals("false")) {
					user = new UserService(db).getUserBySession(request.getParameter("jsession"));
				} else
					user = (User) request.getSession().getAttribute("user_metadata");
				
				new UserService(db).destroySession(user);
				request.getSession().removeAttribute("user_metadata");
				
				break;
				
			default:
				System.out.println("# UserServlet > POST > Nessuna azione specificata");
				
				break;
			
		}
	}
	
}
