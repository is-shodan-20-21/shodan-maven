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
import Model.Game;
import Model.HasCart;
import Model.Transaction;
import Model.User;
import Service.HasCartService;
import Service.HasGameService;
import Service.TransactionService;
import Service.UserService;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {

	private static final long serialVersionUID = 1166145472899719871L;
	
	User user;
	String endpoint;
	
	protected void doGet(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		System.out.println("# CartServlet > Session: " + request.getSession().getId());
		
		Connection db = (Connection) request.getServletContext().getAttribute("databaseConnection");
		
		if(request.getParameter("cookie").equals("false")) {
			user = new UserService(db).getUserBySession(request.getParameter("jsession"));
		} else
			user = (User) request.getSession().getAttribute("user_metadata");
		
		endpoint = request.getParameter("endpoint");
		
		HasCartService service = new HasCartService(db);
		ArrayList<Game> cart = service.selectCart(user);
		
		switch(request.getParameter("action")) {
			case "cartPage":
				if(!cart.isEmpty()) {
					int total = 0;
					
					for(Game game : cart)
						total += game.getPrice();
					
					response.setStatus(200);
					request.setAttribute("games", cart);
					request.setAttribute("total", total);
					request.getRequestDispatcher(endpoint).forward(request, response);
				} else
					response.setStatus(400);
				break;
			
			case "quantity":
				response.getWriter().println(cart.size());
				if(cart.size() == 0)
					response.setStatus(400);
				else
					response.setStatus(200);
				break;
		}
	}	
	
	protected void doPost(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		System.out.println("# CartServlet > Session: " + request.getSession().getId());
		
		Connection db = (Connection) request.getServletContext().getAttribute("databaseConnection");
		
		if(request.getParameter("cookie").equals("false")) {
			user = new UserService(db).getUserBySession(request.getParameter("jsession"));
		} else
			user = (User) request.getSession().getAttribute("user_metadata");
		
		switch(request.getParameter("action")) {
			case "delete":
				new HasCartService(db).dropCart(user);
				break;

			case "removeItem":
				new HasCartService(db).removeItem(
					new HasCart(
						user.getId(), 
						Integer.valueOf(
							request.getParameter("game_id")
						)
					)
				);
				break;
				
			case "pay":
				HasCartService service = new HasCartService(db);
				ArrayList<Game> games = service.selectCart(user);

				if(games != null) {
					if(user.getMoney() >= Integer.valueOf(request.getParameter("total"))) {

						for(Game game : games) {
							new HasGameService(db).addGame(user, game);
							new TransactionService(db).insertTransaction(
								new Transaction(user, game, new java.sql.Date(new java.util.Date().getTime()), game.getPrice())
							);
						}
						
						System.out.println("# CartServlet > Pagamento > Saldo utente: " + user.getMoney() + " - Totale: " + Integer.valueOf(request.getParameter("total")));
						
						user.setMoney(user.getMoney() - Integer.valueOf(request.getParameter("total")));
						
						try {
							new UserService(db).updateUser(user);
						} catch(SQLException e) {
							e.printStackTrace();
						}
						
						request.getSession().setAttribute("user_metadata", user);
						service.dropCart(user);
						response.setStatus(200);

						System.out.println("# TC_PagaOra > Acquisto effettuato con successo");

						return;
					} else
						System.out.println("# TC_PagaOra > Saldo insufficiente");
				} else
					System.out.println("# TC_PagaOra > Carrello non contiene giochi");

				response.setStatus(400);

				break;
			
			case "addGame":
				if(new HasCartService(db).addItem(
					new HasCart(
						user.getId(),
						Integer.valueOf(request.getParameter("game_id"))
					)
				))
					response.setStatus(200);
				else
					response.setStatus(400);
				
				break;
				
		}
	}
	
}
