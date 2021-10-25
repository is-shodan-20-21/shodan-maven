package Control;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Model.User;
import Service.HasCardService;
import Service.TransactionService;
import Service.UserService;
import Utils.PasswordHasher;

@WebServlet("/SettingsServlet")
public class SettingsServlet extends HttpServlet {

	private static final long serialVersionUID = -3000288672809209195L;

	protected void doGet(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		System.out.println("# SettingsServlet > Session: " + request.getSession().getId());
		
		Connection db = (Connection) request.getServletContext().getAttribute("databaseConnection");
		
		User user;
		
		if(request.getParameter("cookie").equals("false")) {
			user = new UserService(db).getUserBySession(request.getParameter("jsession"));
		} else
			user = (User) request.getSession().getAttribute("user_metadata");
		
		switch(request.getParameter("action")) {
			case "userCard":
				String endpointUC = request.getParameter("endpoint");
				request.setAttribute("user", user);
				request.getRequestDispatcher(endpointUC).forward(request, response);
				break;

			case "settingsForms":
				String endpointSF = request.getParameter("endpoint");
				request.getRequestDispatcher(endpointSF).forward(request, response);
				break;

			case "transactionsTable":
				String endpointTT = request.getParameter("endpoint");
				request.setAttribute("transactions", new TransactionService(db).getTransactionsByUser(user));
				request.getRequestDispatcher(endpointTT).forward(request, response);
				break;

			case "cardsTable":
				String endpointCT = request.getParameter("endpoint");
				request.setAttribute("cards", new HasCardService(db).getCards(user));
				request.getRequestDispatcher(endpointCT).forward(request, response);
				break;

			default:
				System.out.println("# SettingsServlet > Bad GET Request");
				break;
		}
	}

	protected void doPost(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		System.out.println("# SettingsServlet > Session: " + request.getSession().getId());
		
		Connection db = (Connection) request.getServletContext().getAttribute("databaseConnection");
		
		User user;
		
		if(request.getParameter("cookie").equals("false")) {
			user = new UserService(db).getUserBySession(request.getParameter("jsession"));
		} else
			user = (User) request.getSession().getAttribute("user_metadata");
		
		switch(request.getParameter("action")) {
			case "settingsForms":
				String endpointSF = request.getParameter("endpoint");
				request.getRequestDispatcher(endpointSF).forward(request, response);
				break;

			case "transactionsTable":
				String endpointTT = request.getParameter("endpoint");
				request.getRequestDispatcher(endpointTT).forward(request, response);
				break;

			case "cardsTable":
				String endpointCT = request.getParameter("endpoint");
				request.getRequestDispatcher(endpointCT).forward(request, response);
				break;

			case "updateEmail":
				String email = request.getParameter("email");
				
				System.out.println("# SettingsServlet > POST > Aggiorna l'email (" + email + ") dell'utente ID " + user.getId());
				
				user.setEmail(email);
				
				if(new UserService(db).updateUser(user)) {
					response.getWriter().println("Email modificata con successo!");
					request.getSession().setAttribute("user_metadata", user);
				} else
					response.getWriter().println("Non &egrave; stato possibile modificare l'email. Ricontrolla i dati!");
				
				break;
		
			case "updatePassword":
				String old_password = PasswordHasher.hash(request.getParameter("old_password"));
				String new_password = request.getParameter("new_password");
				String new_password_again = request.getParameter("new_password_again");
				
				System.out.println("# SettingsServlet > POST > Aggiorna la password dell'utente ID " + user.getId());
				
				if(!old_password.equals(user.getPassword())) {
					response.getWriter().println("Non hai inserito correttamente la tua password attuale!");
					return;
				}
				
				if(!new_password.equals(new_password_again)) {
					response.getWriter().println("Le due password inserite non coincidono!");
					return;
				}
				
				user.setPassword(PasswordHasher.hash(new_password));
				
				if(new UserService(db).updateUser(user)) {
					response.getWriter().println("Password modificata con successo!");
					request.getSession().setAttribute("user_metadata", user);
				} else
					response.getWriter().println("Non &egrave; stato possibile modificare la password. Ricontrolla i dati!");
				
				break;	
		
			default:
				System.out.println("# SettingsServlet > POST > Nessuna azione specificata");
			
			break;
		}
	}
}
