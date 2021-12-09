package Control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Collection.ParsedCard;
import Model.Role;
import Model.Card;
import Model.Game;
import Model.User;
import Service.CardService;
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
		
			case "cardList":
				ArrayList<Card> cards = new CardService(db).getAllCards();
				ArrayList<ParsedCard> parsed = new ArrayList<ParsedCard>();

				for(Card card : cards)
					parsed.add(new ParsedCard(card));
					
				request.setAttribute("cards", parsed);
				request.getRequestDispatcher(endpoint).forward(request, response);
				response.setStatus(200);
				break;

			case "updateBalance":

				User dup = user;

				if(Integer.valueOf(request.getParameter("amount")) != null) {
					if(Integer.valueOf(request.getParameter("amount")) > 0) {
						Card card = new CardService(db).getCard(Integer.valueOf(request.getParameter("cardId")));

						if(card != null) {
							Date cardDate = card.getCard_date();
							java.util.Date today = new java.util.Date();

							// Metodo deprecato
							today.setHours(0);

							if(today.before(cardDate)) {
								dup.setMoney(dup.getMoney() + Integer.valueOf(request.getParameter("amount")));

								try {
									new UserService(db).updateUser(user);
									response.setStatus(200);
								} catch(SQLException e) {
									response.setStatus(400);
								}
								System.out.println("# TC_RicaricaSaldo > Ricarica effettuata");
								return;
							} else 
								System.out.println("# TC_RicaricaSaldo > Carta scaduta");
						} else
							System.out.println("# TC_RicaricaSaldo > Carta non esistente");
					} else
						System.out.println("# TC_RicaricaSaldo > Quantità non valida");
				} else
					System.out.println("# TC_RicaricaSaldo > Formato quantità non valido");

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
				
				try {
					new UserService(db).updateUser(user);
				} catch(SQLException e) {
					e.printStackTrace();
				}
				
				System.out.println("# UserServlet > GET > Pagamento concluso con successo");
				
				response.getWriter().println("Pagamento concluso con successo!");
				request.getSession().setAttribute("user_metadata", user);
				
				break;
		}
	}
	
	protected void doPost(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException, NumberFormatException {
		Connection db = (Connection) request.getServletContext().getAttribute("databaseConnection");
		
		if(request.getParameter("cookie") != null) {
			if(request.getParameter("cookie").equals("false"))
				user = new UserService(db).getUserBySession(request.getParameter("jsession"));
			else
				user = (User) request.getSession().getAttribute("user_metadata");
		}

		switch(request.getParameter("action")) {
			case "newCard":
				String card_type = request.getParameter("cardType");
				String card_owner = request.getParameter("cardOwner");
				int card_owner_length = card_owner.length();
				Long card_number = Long.valueOf(request.getParameter("cardNumber"));
				int card_number_digits = request.getParameter("cardNumber").length();
				Date card_date = Date.valueOf(request.getParameter("cardDate"));
				String card_cvv = request.getParameter("cardCVV");
				int card_cvv_digits = card_cvv.length();
				java.util.Date today = new java.util.Date();

				today.setHours(0);

				if(card_type.length() >= 0) {
					if(card_number != null) {
						if(card_number_digits == 16) {
							if(new CardService(db).getCardByNumber(card_number) == null) {
								if(card_owner != null) {
									if(card_owner_length >= 0) {
										if(card_cvv != null) {
											if(card_cvv_digits == 3) {
												if(card_date != null) {
													if(today.before(card_date)) {
														Card newCard = new Card(
															0,
															card_type,
															card_number,
															card_owner,
															card_date,
															user
														);
			
														new CardService(db).insertCard(newCard);

														System.out.println("# TC_AggiungiCarta > Carta aggiunta con successo (LC2.LI2.FI2.EXID2.LT2.FT2.LCVV2.FCVV2.FD2.S2)");
														response.setStatus(200);

														return;
													} else
														System.out.println("# TC_AggiuntiCarta > Carta scaduta");
												} else
													System.out.println("# TC_AggiungiCarta > Formato data non valido");
											} else
												System.out.println("# TC_AggiungiCarta > Formato CVV non valido");
										} else
											System.out.println("# TC_AggiungiCarta > CVV: campo obbligatorio");
									} else
										System.out.println("# TC_AggiungiCarta > Formato titolare non valido");
								} else
									System.out.println("# TC_AggiungiCarta > Titolare: campo obbligatorio"); 
							} else
								System.out.println("# TC_AggiungiCarta > Carta già esistente");
						} else
							System.out.println("# TC_AggiungiCarta > Formato numero carta non valido");
					} else
						System.out.println("# TC_AggiungiCarta > Numero carta: campo obbligatorio");
				} else
					System.out.println("# TC_AggiungiCarta > Circuito: campo obbligatorio");

				response.setStatus(400);
				break;

			case "logout":
				System.out.println("# UserServlet > POST > Logout dell'utente");
				
				if(request.getParameter("cookie").equals("false")) {
					user = new UserService(db).getUserBySession(request.getParameter("jsession"));
				} else
					user = (User) request.getSession().getAttribute("user_metadata");
				
				new UserService(db).destroySession(user);
				request.getSession().removeAttribute("user_metadata");
				
				break;
		}
	}
}