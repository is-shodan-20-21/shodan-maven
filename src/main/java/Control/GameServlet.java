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

import org.openqa.selenium.InvalidArgumentException;

import Collection.ParsedGame;
import Model.Game;
import Model.User;
import Service.GameService;
import Service.HasCartService;
import Service.HasGameService;
import Service.UserService;

@WebServlet("/GameServlet")
/*
 * @MultipartConfig è necessario per le logiche di creazione di un nuovo gioco
 * ..
 * .. nelle quali viene richiesto l'upload di più immagini via client.
 */
@MultipartConfig
public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = -8724190928795580877L;

	protected void doGet(
			HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User user = null;
		Connection db = (Connection) request.getServletContext().getAttribute("databaseConnection");
		String endpoint = request.getParameter("endpoint");
		int limit = (request.getParameter("limit") != null)
				? Integer.parseInt(request.getParameter("limit"))
				: 0;

		if (request.getParameter("cookie") != null && request.getParameter("cookie").equals("false"))
			user = new UserService(db).getUserBySession(request.getParameter("jsession"));
		else
			user = (User) request.getSession().getAttribute("user_metadata");

		System.out.println("Action: " + request.getParameter("action"));

		switch (request.getParameter("action")) {
			case "parsed":
				ArrayList<Game> raw_games = new GameService(db).getAllDescendingGames(limit);
				ArrayList<ParsedGame> parser = new ArrayList<ParsedGame>();

				if (user == null)
					for (Game game : raw_games)
						parser.add(new ParsedGame(game, false));
				else {
					for (Game game : raw_games) {
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

				if (request.getParameter("order") == null) {
					if (ascending != null) {
						request.setAttribute("games", ascending);
						request.getRequestDispatcher(endpoint).forward(request, response);
						response.setStatus(200);
					} else
						response.setStatus(400);
				} else {
					if (descending != null) {
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

				for (Game game : games)
					libraryParser.add(new ParsedGame(game, true));

				if (games != null) {
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
				} catch (NumberFormatException e) {
					response.setStatus(400);
					return;
				}

				Game game = new GameService(db).getGame(game_id);

				if (game == null) {
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
				if (user != null && new HasGameService(db).hasGame(
						user,
						new GameService(db).getGame(
								Integer.valueOf(
										request.getParameter("game_id"))))) {
					response.setStatus(200);
					return;
				}

				response.setStatus(400);

				break;

			case "searchGameShop":
				ArrayList<Game> searchedGamesShop = new GameService(db)
						.searchGames(request.getParameter("search_query"));
				ArrayList<ParsedGame> searchedGamesParserShop = new ArrayList<ParsedGame>();

				if (searchedGamesShop != null) {
					if (user == null)
						for (Game lookedGame : searchedGamesShop)
							searchedGamesParserShop.add(new ParsedGame(lookedGame, false));
					else {
						for (Game lookedGame : searchedGamesShop) {
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
				ArrayList<Game> searchedGamesLibrary = new GameService(db).searchGamesInLibrary(user.getId(),
						request.getParameter("search_query"));
				ArrayList<ParsedGame> searchedGamesParserLibrary = new ArrayList<ParsedGame>();

				if (searchedGamesLibrary != null) {
					for (Game lookedGame : searchedGamesLibrary)
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
				if (gameList != null) {
					request.setAttribute("gameList", gameList);
					request.getRequestDispatcher(endpoint).forward(request, response);
					response.setStatus(200);
				} else
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
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("# GameServlet > Session: " + request.getSession().getId());

		Connection db = (Connection) request.getServletContext().getAttribute("databaseConnection");

		switch (request.getParameter("action")) {
			case "addGame":
				String oracle = "";

				String gameName = request.getParameter("game-name");
				String gamePrice = request.getParameter("game-price");
				String gameDate = request.getParameter("game-date");
				String gameDescription = request.getParameter("game-description");

				if (gameName != null && !gameName.equals("")) {

					if(gameName.matches("[a-zA-z\\s]*")) {

						if (new GameService(db).getGameByName(gameName) == null) {

							if (gamePrice != null && !gamePrice.equals("")) {

								try {

									if (Integer.valueOf(request.getParameter("game-price")) > 0) {

										if (gameDate != null && !gameDate.equals("")) {

											try {
												Date rawDate = Date.valueOf(gameDate);
												int parsedDate = Integer.valueOf(rawDate.toString().split("-")[0]);

												int lesserYear = 1970;
												int greaterYear = 2000;

												if (parsedDate >= lesserYear && parsedDate <= greaterYear) {

													if (gameDescription != null && !gameDescription.equals("")) {

														if (request.getPart("game-image").getSize() > 0) {
															
															Part filePartImage = request.getPart("game-image");
															
															String fileNameImage = Paths
																.get(GameServlet.getSubmittedFileName(filePartImage))
																.getFileName().toString();

															String fileMimeImage = getServletContext().getMimeType(fileNameImage);

															if(fileMimeImage.startsWith("image/")) {

																if (request.getPart("game-landscape").getSize() > 0) {
																	
																	Part filePartLandscape = request.getPart("game-landscape");

																	String fileNameLandscape = Paths
																				.get(GameServlet.getSubmittedFileName(filePartLandscape))
																				.getFileName()
																				.toString();

																	String fileMimeLandscape = getServletContext().getMimeType(fileNameLandscape);

																	if(fileMimeLandscape.startsWith("image/")) {

																		InputStream fileContentImage = filePartImage.getInputStream();
																		InputStream fileContentLandscape = filePartLandscape
																				.getInputStream();

																		File filePathImage = new File(
																				getServletContext().getRealPath("Static/GamePictures"));
																		File filePathLandscape = new File(
																				getServletContext().getRealPath("Static/GameLandscapes"));

																		File fileImage = new File(filePathImage, fileNameImage);
																		File fileLandscape = new File(filePathLandscape, fileNameLandscape);

																		filePathImage.mkdir();
																		filePathLandscape.mkdir();

																		if (!fileImage.exists() && !fileLandscape.exists()) {
																			Files.copy(fileContentImage, fileImage.toPath());
																			Files.copy(fileContentLandscape, fileLandscape.toPath());

																			try {
																				new GameService(db).addGame(
																					gameName,
																					fileNameImage,
																					Integer.valueOf(gamePrice),
																					rawDate,
																					gameDescription,
																					fileNameLandscape
																				);

																				oracle = "Gioco aggiunto";
																				response.setStatus(200);
																			} catch (IllegalArgumentException e) {
																				e.printStackTrace();

																				oracle = "IllegalArgumentException";
																				response.setStatus(400);
																			}
																		} else {
																			oracle = "Immagini gia' presenti nel database";
																			response.setStatus(400);
																		}
																	} else {
																		oracle = "Formato sfondo non valido";
																		response.setStatus(400);
																	}
																} else {
																	oracle = "Sfondo: campo obbligatorio";
																	response.setStatus(400);
																}
															} else {
																oracle = "Formato cover non valido";
																response.setStatus(400);
															}
														} else {
															oracle = "Cover: campo obbligatorio";
															response.setStatus(400);
														}
													} else {
														oracle = "Descrizione: campo obbligatorio";
														response.setStatus(400);
													}
												} else {
													oracle = "Data fuori intervallo";
													response.setStatus(400);
												}
											} catch(IllegalArgumentException e) {
												oracle = "Formato data non valido";
												response.setStatus(400);
											}
										} else {
											oracle = "Data: campo obbligatorio";
											response.setStatus(400);
										}
									} else {
										oracle = "Prezzo negativo";
										response.setStatus(400);
									}
								} catch(NumberFormatException e) {
									oracle = "Formato prezzo non valido";
									response.setStatus(400);
								}
							} else {
								oracle = "Prezzo: campo obbligatorio";
								response.setStatus(400);
							}
						} else {
							oracle = "Titolo gia' presente";
							response.setStatus(400);
						}
					} else {
						oracle = "Formato nome non valido";
						response.setStatus(400);						
					}
				} else {
					oracle = "Nome: campo obbligatorio";
					response.setStatus(400);
				}

				response.sendRedirect("/shodan_maven/app.jsp?__ORACLE=" + oracle);

				break;

			case "deleteGame":
				String gameId = request.getParameter("deleteGameId");

				if (gameId != null && !gameId.equals("")) {
					if(gameId.matches("[1-9]+")) {
						Game game = new GameService(db).getGame(Integer.valueOf(gameId));

						if (game != null) {
							response.setStatus(200);
							new GameService(db).deleteGame(game.getId());

							new HasCartService(db).removeItemForAll(game);
							new HasGameService(db).removeItemForAll(game);

							response.getWriter().println("Il titolo rimosso con successo");
							return;
						} else {
							response.getWriter().println("Il titolo non e' presente");
							response.setStatus(400);
						}
					} else {
						response.getWriter().println("Formato ID errato");
						response.setStatus(400);	
					}
				} else {
					response.getWriter().println("ID Gioco: campo obbligatorio");
					response.setStatus(400);
				}

				break;

			case "updateGame":

				String gameIdUpdate = request.getParameter("updateGameId");
				String gamePriceUpdate = request.getParameter("updateGamePrice");

				if (gameIdUpdate != null && !gameIdUpdate.equals("")) {
					if(gameIdUpdate.matches("[1-9]+")) {
						Game updatedGame = new GameService(db).getGame(Integer.valueOf(gameIdUpdate));

						if (updatedGame != null) {
							if (gamePriceUpdate != null && !gamePriceUpdate.equals("")) {
								try {
									int newPrice = Integer.valueOf(gamePriceUpdate);

									if (newPrice >= 0) {
										updatedGame.setPrice(newPrice);
										new GameService(db).updateGame(updatedGame);

										response.setStatus(200);
										response.getWriter().println("Prezzo del titolo aggiornato con successo!");
										return;
									} else {
										response.setStatus(400);
										response.getWriter().println("Prezzo negativo");
									}
								} catch(NumberFormatException e) {
									response.setStatus(400);
									response.getWriter().println("Formato prezzo non valido");
								}
							} else {
								response.setStatus(400);
								response.getWriter().println("Prezzo: campo obbligatorio");
							}
						} else {
							response.setStatus(400);
							response.getWriter().println("Titolo non presente");
						}
					} else {
						response.setStatus(400);
						response.getWriter().println("Formato ID non valido");						
					}
				} else {
					response.setStatus(400);
					response.getWriter().println("ID: campo obbligatorio");
				}

				break;

			default:
				System.out.println("# GameServlet > POST > Nessuna azione specificata");
				break;
		}

	}

	/*
	 * Curiosamente, Tomcat 7.x non supporta getSubmittedFileName(), introdotto
	 * invece nelle versioni 8.x.
	 * 
	 * Scrivere questo metodo, pervenuto in una guida su Stack Overflow
	 * (https://stackoverflow.com/questions/2422468/how-can-i-upload-files-to-a-
	 * server-using-jsp-servlet/) a mano ..
	 * .. risulta necessario. L'alternativa sarebbe cambiare completamente
	 * toolchain, essendo obbligati a usare Cargo oppure TomEE invece di Tomcat 7.x.
	 * 
	 * Per questo motivo, si è preferito utilizzare il metodo proposto per le
	 * piattaforme che impiegano Servlet 3.0.
	 */
	private static String getSubmittedFileName(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1);																								
			}
		}
		return null;
	}

}
