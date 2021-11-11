package Control;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import Collection.ParsedGame;
import Model.Game;
import Model.User;
import Service.GameService;
import Service.HasCartService;
import Service.HasGameService;
import Service.UserService;

@WebServlet("/GameServlet")
/*
	@MultipartConfig è necessario per le logiche di creazione di un nuovo gioco ..
	.. nelle quali viene richiesto l'upload di più immagini via client.
*/
@MultipartConfig
public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = -8724190928795580877L;
	
	protected void doGet(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		User user = null;
		Connection db = (Connection) request.getServletContext().getAttribute("databaseConnection");
		String endpoint = request.getParameter("endpoint");
		int limit = (request.getParameter("limit") != null) 
					? Integer.parseInt(request.getParameter("limit"))
					: 0;
		
		if(request.getParameter("cookie").equals("false"))
			user = new UserService(db).getUserBySession(request.getParameter("jsession"));
		else
			user = (User) request.getSession().getAttribute("user_metadata");

		switch(request.getParameter("action")) {
			case "parsed":
				ArrayList<Game> raw_games = new GameService(db).getAllDescendingGames(limit);
				ArrayList<ParsedGame> parser = new ArrayList<ParsedGame>();

				if(user == null)
					for(Game game : raw_games)
						parser.add(new ParsedGame(game, false));
				else {
					for(Game game : raw_games) {
						boolean bridge = new HasGameService(db).hasGame(user, game);
						boolean river = new HasCartService(db).hasInCart(user, game);

						parser.add(new ParsedGame(game, bridge || river));
					}
				}

				request.setAttribute("source", parser);
				request.getRequestDispatcher(endpoint).forward(request, response);
				response.setStatus(200);

				break;

			case "shop":
				ArrayList<Game> ascending = new GameService(db).getAllAscendingGames(limit);
				ArrayList<Game> descending = new GameService(db).getAllDescendingGames(limit);
				
				if(request.getParameter("order") == null) {
					if(ascending != null) {
						request.setAttribute("games", ascending);
						request.getRequestDispatcher(endpoint).forward(request, response);
						response.setStatus(200);
					} else 
						response.setStatus(400);
				} else {
					if(descending != null) {
						request.setAttribute("games", descending);
						request.getRequestDispatcher(endpoint).forward(request, response);
						response.setStatus(200);
					} else
						response.setStatus(400);
				}
					
				System.out.println("# GameServlet > GET > Ultimi 5 giochi del negozio");
				
				break;
				
			case "library":
				ArrayList<Game> games = new GameService(db).getAllGamesByUser(user.getId());
				ArrayList<ParsedGame> libraryParser = new ArrayList<ParsedGame>();

				for(Game game : games)
					libraryParser.add(new ParsedGame(game, true));
				
				if(games != null) {
					request.setAttribute("source", libraryParser);
					request.getRequestDispatcher(endpoint).forward(request, response);
					response.setStatus(200);
				} else
					response.setStatus(400);
					
				
				System.out.println("# GameServlet > GET > Libreria personale dell'utente");
				
				break;
				
			case "game":
				int game_id;
				
				try {
					game_id = Integer.parseInt(request.getParameter("game_id"));
				} catch(NumberFormatException e) {
					response.setStatus(400);
					return;
				}
				
				Game game = new GameService(db).getGame(game_id);
				
				if(game == null) {
					System.out.println("# GameServlet > GET > ID non valido");
					
					response.setStatus(400);
				} else {
					System.out.println("# GameServlet > GET > Pagina del gioco ID " + game_id);
					
					response.setStatus(200);
					request.setAttribute("game", game);
					request.getRequestDispatcher(endpoint).forward(request, response);	
				}
				
				break;
				
			case "hasGame":
				if(user != null && new HasGameService(db).hasGame(
					user, 
					new GameService(db).getGame(
						Integer.valueOf(
							request.getParameter("game_id")
						)
					)
				)) {
					response.setStatus(200);
					return;
				}

				response.setStatus(400);

				break;

			case "searchGameShop":
				ArrayList<Game> searchedGamesShop = new GameService(db).searchGames(request.getParameter("search_query"));
				ArrayList<ParsedGame> searchedGamesParserShop = new ArrayList<ParsedGame>();

				if(searchedGamesShop != null) {
					if(user == null)
						for(Game lookedGame : searchedGamesShop)
						searchedGamesParserShop.add(new ParsedGame(lookedGame, false));
					else {
						for(Game lookedGame : searchedGamesShop) {
							boolean bridge = new HasGameService(db).hasGame(user, lookedGame);
							boolean river = new HasCartService(db).hasInCart(user, lookedGame);
								
							searchedGamesParserShop.add(new ParsedGame(lookedGame, bridge || river));
						}
					}

					request.setAttribute("source", searchedGamesParserShop);
					request.getRequestDispatcher(endpoint).forward(request, response);
					response.setStatus(200);
				} else
					response.setStatus(400);
				
				System.out.println("# GameServlet > GET > Ricerca di " + request.getParameter("search_query"));
				
				break;
			
			case "searchGameLibrary":
				ArrayList<Game> searchedGamesLibrary = new GameService(db).searchGamesInLibrary(user.getId(), request.getParameter("search_query"));
				ArrayList<ParsedGame> searchedGamesParserLibrary = new ArrayList<ParsedGame>();

				if(searchedGamesLibrary != null) {
					for(Game lookedGame : searchedGamesLibrary)
						searchedGamesParserLibrary.add(new ParsedGame(lookedGame, true));

					request.setAttribute("source", searchedGamesParserLibrary);
					request.getRequestDispatcher(endpoint).forward(request, response);
					response.setStatus(200);
				} else
					response.setStatus(400);
				
				System.out.println("# GameServlet > GET > Ricerca di " + request.getParameter("search_query"));
				
				break;

			case "getGames":
				ArrayList<Game> gameList = new GameService(db).getAllAscendingGames(0);
				if(gameList != null){
					request.setAttribute("gameList", gameList);
					request.getRequestDispatcher(endpoint).forward(request, response);
					response.setStatus(200);
				}else
					response.setStatus(400);

				System.out.println("# GameServlet > GET > Tutti i giochi");

				break;

			default:
				System.out.println("# GameServlet > GET > Nessuna azione specificata");
				
				break;
				
		}
	}
	
	protected void doPost(
			HttpServletRequest request,
			HttpServletResponse response
	) throws ServletException, IOException {
		System.out.println("# GameServlet > Session: " + request.getSession().getId());
		
		Connection db = (Connection) request.getServletContext().getAttribute("databaseConnection");

		/*
			A0 -> Prezzo negativo
			A1 -> Formato della data non valido
			A2 -> Immagini già presenti nel database
			A3 -> Impossibile caricare le immagini
			A4 -> Input non validi
		*/

		switch(request.getParameter("action")) {
			case "addGame":
				System.out.println("# GameServlet > POST > Provo ad aggiungere un gioco...");

				if(Integer.valueOf(request.getParameter("game-price")) < 0) {
					response.sendRedirect("?__ERR:NO=A0");
					System.out.println("# GameServlet > POST > Costo negativo");
					response.setStatus(400);
					return;
				}

				String fileNameImage;
				String fileNameLandscape;

				Date rawDate = Date.valueOf(request.getParameter("game-date"));
				int parsedDate = Integer.valueOf(rawDate.toString().split("-")[0]);
				
				int lesserYear = 1970;
				int greaterYear = 2000;
				
				if(parsedDate < lesserYear || parsedDate > greaterYear) {
					response.sendRedirect("?__ERR:NO=A1");
					System.out.println("# GameServlet > POST > Data non valida");
					response.setStatus(400);
					
					return;
				}
				
				try {
					Part filePartImage = request.getPart("game-image");
					Part filePartLandscape = request.getPart("game-landscape");

					fileNameImage = Paths.get(GameServlet.getSubmittedFileName(filePartImage)).getFileName().toString();
					fileNameLandscape = Paths.get(GameServlet.getSubmittedFileName(filePartLandscape)).getFileName().toString();

					InputStream fileContentImage = filePartImage.getInputStream();
					InputStream fileContentLandscape = filePartLandscape.getInputStream();

					File filePathImage = new File(getServletContext().getRealPath("Static/GamePictures"));
					File filePathLandscape = new File(getServletContext().getRealPath("Static/GameLandscapes"));

					File fileImage = new File(filePathImage, fileNameImage);
					File fileLandscape = new File(filePathLandscape, fileNameLandscape);
					
					filePathImage.mkdir();
					filePathLandscape.mkdir();
					
					if(!fileImage.exists() && !fileLandscape.exists()) {
						Files.copy(fileContentImage, fileImage.toPath());
						Files.copy(fileContentLandscape, fileLandscape.toPath());

						System.out.println("# GameServlet > POST > Immagini caricate");
					} else {
						response.sendRedirect("?__ERR:NO=A2");
						System.out.println("# GameServlet > POST > Immagini già presenti nel database");
						response.setStatus(400);
						
						return;
					}
				} catch(IOException e) {
					e.printStackTrace();
					
					response.sendRedirect("?__ERR:NO=A3");
					System.out.println("# GameServlet > POST > Impossibile caricare le immagini");
					response.setStatus(400);
					
					return;
				}
				
				try {		
					new GameService(db).addGame(
						request.getParameter("game-name"), 
						fileNameImage, 
						Integer.valueOf(request.getParameter("game-price")),
						rawDate,
						request.getParameter("game-description"),
						fileNameLandscape
					);
				} catch(IllegalArgumentException e) {
					e.printStackTrace();
					
					response.sendRedirect("?__ERR:NO=A4");
					System.out.println("# GameServlet > POST > IllegalArgumentException");
					response.setStatus(400);

					return;
				}
				
				response.sendRedirect("?__SRVC:OK");
				response.setStatus(200);
			
				System.out.println("# GameServlet > POST > Gioco aggiunto > " + request.getParameter("game-name"));
			
				break;
			
			case "deleteGame":
				Game game = new GameService(db).getGame(Integer.valueOf(request.getParameter("deleteGameId")));
				
				if(game != null) {
					response.setStatus(200);
					new GameService(db).deleteGame(game.getId());
					new HasCartService(db).removeItemForAll(game);
					new HasGameService(db).removeItemForAll(game);
				} else
					response.setStatus(400);
					
				break;

			case "updateGame":
				Game updatedGame = new GameService(db).getGame(Integer.valueOf(request.getParameter("updateGameId")));
				int newPrice = Integer.valueOf(request.getParameter("updateGamePrice"));
				
				if(updatedGame != null && newPrice >= 0) {
					response.setStatus(200);
					updatedGame.setPrice(newPrice);
					new GameService(db).updateGame(updatedGame);
				}
				else
					response.setStatus(400);
					
				break;
			
			default:
				System.out.println("# GameServlet > POST > Nessuna azione specificata");
				
				break;
		}
		
	}

	/*
		Curiosamente, Tomcat 7.x non supporta getSubmittedFileName(), introdotto invece nelle versioni 8.x.
		
		Scrivere questo metodo, pervenuto in una guida su Stack Overflow (https://stackoverflow.com/questions/2422468/how-can-i-upload-files-to-a-server-using-jsp-servlet/) a mano ..
		.. risulta necessario. L'alternativa sarebbe cambiare completamente toolchain, essendo obbligati a usare Cargo oppure TomEE invece di Tomcat 7.x.

		Per questo motivo, si è preferito utilizzare il metodo proposto per le piattaforme che impiegano Servlet 3.0.
	*/
	private static String getSubmittedFileName(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
			}
		}
		return null;
	}
	
}
