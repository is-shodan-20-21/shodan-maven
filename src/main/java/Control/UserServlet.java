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
import Database.DBConnectionPool;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = -4587622200104894945L;

	User user;
	Connection db;

	public UserServlet() {
		this.user = null;
	}
	
	public void doGet(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		System.out.println("# UserServlet > Session: " + request.getSession().getId());

		String endpoint = request.getParameter("endpoint");
		
		Connection db = null;
		try {
			db = DBConnectionPool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		if(request.getParameter("puppet") == null) {
			if (request.getParameter("cookie") != null && request.getParameter("cookie").equals("false"))
				user = new UserService(db).getUserBySession(request.getParameter("jsession"));
		else
			user = (User) request.getSession().getAttribute("user_metadata");
		} else
			user = new UserService(db).getUserByUsername("admin");

		
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
				ArrayList<Card> cards = new CardService(db).getCardsByOwner(user);
				ArrayList<ParsedCard> parsed = new ArrayList<ParsedCard>();

				for(Card card : cards)
					parsed.add(new ParsedCard(card));
					
				request.setAttribute("cards", parsed);
				request.getRequestDispatcher(endpoint).forward(request, response);
				response.setStatus(200);
				break;

			case "updateBalance":

				User dup = user;

				try {
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
									response.getWriter().println("Ricarica effettuata con successo!");
									request.setAttribute("testMessage", "Ricarica effettuata con successo");
									return;
								} catch(SQLException e) {
									response.setStatus(400);
								}
								System.out.println("# TC_RicaricaSaldo > Ricarica effettuata");
								return;
							} else {
								System.out.println("# TC_RicaricaSaldo > Carta scaduta");
								request.setAttribute("testMessage", "Carta scaduta");
								response.getWriter().println("Carta scaduta");
							}
						} else {
							System.out.println("# TC_RicaricaSaldo > Carta non esistente");
							request.setAttribute("testMessage", "Carta non esistente");
							response.getWriter().println("Carta non esistente");
						}
					} else {
						System.out.println("# TC_RicaricaSaldo > Quantità non valida");
						request.setAttribute("testMessage", "Quantita' non valida");
						response.getWriter().println("Quantità non valida");
					}
				} catch(NumberFormatException e) {
					System.out.println("# TC_RicaricaSaldo > Formato quantità non valido");
					request.setAttribute("testMessage", "Formato quantita' non valido");
					response.getWriter().println("Formato quantita' non valido");
				}

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
	
	public void doPost(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException, NumberFormatException {
		//Connection db = (Connection) request.getServletContext().getAttribute("databaseConnection");

		Connection db = null;
		try {
			db = DBConnectionPool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		if(request.getParameter("puppet") == null) {
			if (request.getParameter("cookie") != null && request.getParameter("cookie").equals("false"))
				user = new UserService(db).getUserBySession(request.getParameter("jsession"));
		else
			user = (User) request.getSession().getAttribute("user_metadata");
		} else
			user = new UserService(db).getUserByUsername("admin");

		switch(request.getParameter("action")) {
			case "newCard":
				String card_type = request.getParameter("cardType");
				String card_owner = request.getParameter("cardOwner");
				int card_owner_length = card_owner.length();
				String card_cvv = request.getParameter("cardCVV");
				int card_cvv_digits = card_cvv.length();
				java.util.Date today = new java.util.Date();

				today.setHours(0);

				if(!card_type.equals("")) {
					if(!request.getParameter("cardNumber").equals("")) {
						Long card_number = Long.valueOf(request.getParameter("cardNumber"));
						int card_number_digits = request.getParameter("cardNumber").length();
						if(card_number_digits == 16) {
							if(new CardService(db).getCardByNumber(card_number) == null) {
								if(!card_owner.equals("")) {
									if(card_owner_length >= 5) {
										if(!card_cvv.equals("")) {
											if(card_cvv.matches("[0-9]+") && card_cvv_digits == 3) {
												try {
													Date card_date = Date.valueOf(request.getParameter("cardDate"));
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

														System.out.println("# TC_AggiungiCarta > Carta aggiunta con successo");
														request.setAttribute("testMessage", "Carta aggiunta con successo");
														response.setStatus(200);

														return;
													} else {
														response.getWriter().println("Carta scaduta");
														request.setAttribute("testMessage", "Carta scaduta");
													}
												} catch(IllegalArgumentException e) {
													response.getWriter().println("Formato data non valido");
													request.setAttribute("testMessage", "Formato data non valido");
												}
											} else {
												response.getWriter().println("Formato CVV non valido");
												request.setAttribute("testMessage", "Formato CVV non valido");
											}
										} else {
											response.getWriter().println("CVV: campo obbligatorio");
											request.setAttribute("testMessage", "CVV: campo obbligatorio");
										}
									} else {
										response.getWriter().println("Formato titolare non valido");
										request.setAttribute("testMessage", "Formato titolare non valido");
									}
								} else {
									response.getWriter().println("Titolare: campo obbligatorio"); 
									request.setAttribute("testMessage", "Titolare: campo obbligatorio");
								}
							} else {
								response.getWriter().println("Carta gia' esistente");
								request.setAttribute("testMessage", "Carta gia' esistente");
							}
						} else {
							response.getWriter().println("Formato numero carta non valido");
							request.setAttribute("testMessage", "Formato numero carta non valido");
						}
					} else {
						response.getWriter().println("Numero carta: campo obbligatorio");
						request.setAttribute("testMessage", "Numero carta: campo obbligatorio");
					}
				} else {
					response.getWriter().println("Circuito: campo obbligatorio");
					request.setAttribute("testMessage", "Circuito: campo obbligatorio");
				}

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