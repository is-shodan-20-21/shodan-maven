package Control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Collection.ParsedCard;
import Database.DBConnectionPool;
import Model.Card;
import Model.User;
import Service.CardService;
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

				ArrayList<Card> cards = new CardService(db).getCardsByOwner(user);
				ArrayList<ParsedCard> parsed = new ArrayList<ParsedCard>();

				for(Card card : cards)
					parsed.add(new ParsedCard(card));

				request.setAttribute("collection", parsed);
				request.getRequestDispatcher(endpointCT).forward(request, response);
				break;

			default:
				System.out.println("# SettingsServlet > Bad GET Request");
				break;
		}
	}

	public void doPost(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		System.out.println("# SettingsServlet > Session: " + request.getSession().getId());
		String testMessage = "";
		
		//Connection db = (Connection) request.getServletContext().getAttribute("databaseConnection");
		Connection db = null;
		try {
			db = DBConnectionPool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		User user;
		
		if (request.getParameter("puppet")==null) {
			if(request.getParameter("cookie").equals("false")) {
				user = new UserService(db).getUserBySession(request.getParameter("jsession"));
			} else
				user = (User) request.getSession().getAttribute("user_metadata");
		} else {
			user = new UserService(db).getUserByUsername("slave");
		}
		System.out.println("Utente: "+user.getName());
		
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
				String lastEmail = request.getParameter("lastEmail");
				String email = request.getParameter("email");
				boolean emailPattern = email.matches("^\\S+@\\S+$");
				
				System.out.println("# SettingsServlet > POST > Aggiorna l'email (" + email + ") dell'utente ID " + user.getId());

				if(lastEmail.equals(user.getEmail())){
					if(email.length() > 0){
						if(emailPattern){
							if(!new UserService(db).findUserByEmail(email)){
								user.setEmail(email);

								try {
									if (request.getParameter("puppet")==null) {
										if(new UserService(db).updateUser(user)) {
											response.getWriter().println("Email modificata con successo!");
											response.setStatus(200);
											request.getSession().setAttribute("user_metadata", user);
											System.out.println("# SettinServlet > TC_ModificaEmail > Email modificata con successo");
											return;
										} else {
											System.out.println("# SettingsServlet > POST > Fallimento nell'aggiornare la mail");
											response.getWriter().println("Non &egrave; stato possibile modificare l'email. Ricontrolla i dati!");
										}
									} else {
										testMessage = "Email modificata con successo";
										request.setAttribute("testMessage", testMessage);
									}	
								} catch(SQLException e) {
									testMessage = "Impossibile aggiornare l'email";
									System.out.println("# SettingsServlet > POST > Impossibile aggiornare l'email");
									response.getWriter().println("Non &egrave; stato possibile modificare l'email. Ricontrolla i dati!");
								}
							}else{
								testMessage = "Email gia in uso";
								System.out.println("# SettinServlet > TC_ModificaEmail > Email già in uso");
								response.getWriter().println("Impossibile usare questa email.");
							}
						}else{
							testMessage = "Formato nuova email non valido";
							System.out.println("# SettinServlet > TC_ModificaEmail > Formato nuova email non valido");
							response.getWriter().println("Formato nuova email non valido.");
						}
					}else{
						testMessage = "Nuova mail campo obbligatorio";
						System.out.println("# SettinServlet > TC_ModificaEmail > Nuova mail campo obbligatorio");
					}
				}else{
					testMessage = "Email errata";
					System.out.println("# SettinServlet > TC_ModificaEmail > Email errata");
					response.getWriter().println("Non hai inserito correttamente la tua email attuale.");
				}
				
				response.setStatus(400);
				request.setAttribute("testMessage", testMessage);

				break;
		
			case "updatePassword":
				String old_password = PasswordHasher.hash(request.getParameter("old_password"));
				String new_password = request.getParameter("new_password");
				String new_password_again = request.getParameter("new_password_again");
				boolean newPasswordPattern = new_password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,}$");
				
				System.out.println("# SettingsServlet > POST > Aggiorna la password dell'utente ID " + user.getId());

				if(old_password.equals(user.getPassword())){
					if(new_password.length() > 0){
						if(newPasswordPattern){
							if(new_password_again.length() > 0){
								if(new_password.equals(new_password_again)){
									user.setPassword(PasswordHasher.hash(new_password));

									try {
										if (request.getParameter("puppet")==null) {
											if(new UserService(db).updateUser(user)) {
												response.setStatus(200);
												response.getWriter().println("Password modificata con successo!");
												request.getSession().setAttribute("user_metadata", user);
												System.out.println("# SettingServlet > TC_ModificaPassword > Password modificata con successo");
												return;
											} else
												response.getWriter().println("Non &egrave; stato possibile modificare la password. Ricontrolla i dati!");
										} else {
											testMessage = "Password modificata con successo";
										}
										
									} catch(SQLException e) {
										e.printStackTrace();
									}
								}else{
									testMessage = "Le password non coincidono";
									System.out.println("# SettingServlet > TC_ModificaPassword > Le password non coincidono");
									response.getWriter().println("Le password non coincidono.");
								}
							}else{
								testMessage = "Ripeti password: campo obbligatorio";
								System.out.println("# SettingServlet > TC_ModificaPassword > Ripeti password: campo obbligatorio");
							}
						}else{
							testMessage = "Formato nuova password non valido";
							System.out.println("# SettingServlet > TC_ModificaPassword > Formato nuova password non valido");
							response.getWriter().println("Formato nuova password non valido.");
						}
					}else{
						testMessage = "Nuova password campo obbligatorio";
						System.out.println("# SettingServlet > TC_ModificaPassword > Nuova password campo obbligatorio");
					}
				}else{
					testMessage = "Password errata";
					System.out.println("# SettingServlet > TC_ModificaPassword > Password errata");
					response.getWriter().println("Password corrente errata.");
				}
				
				response.setStatus(400);
				request.setAttribute("testMessage", testMessage);

				break;
		
			default:
				System.out.println("# SettingsServlet > POST > Nessuna azione specificata");
			
			break;
		}
	}
}
