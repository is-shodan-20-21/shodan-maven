package Control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Service.UserService;
import Utils.PasswordHasher;

@WebServlet("/SignInServlet")
public class SignInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		Connection db = (Connection) request.getServletContext().getAttribute("databaseConnection");
		
		UserService service = new UserService(db);
		PrintWriter out = response.getWriter();
		
		int id = service.getIdByUsername(request.getParameter("username"));
		boolean emailUsed = service.findUserByEmail(request.getParameter("email"));
		
		if(id == -1){
			if(!emailUsed) {
				if(request.getParameter("password").equals(request.getParameter("password2"))) {
					service.insertUser(request.getParameter("username"), PasswordHasher.hash(request.getParameter("password")), request.getParameter("email"));
					
					out.print("Utente creato con successo!");
					System.out.println("# SignInServlet > Creazione utente effettuata con successo (username: " + request.getParameter("username"));
					
					response.setStatus(200);
					return;
				} else {
					out.print("Le password non coincidono!");
					System.out.println("# SignInServlet > E' stato effettuato un tentativo fallimentare di registrazione.");
					
					response.setStatus(400);
					return;
				}
			} else {
				out.print("Questa email &egrave; gi&agrave; in uso!!");
				System.out.println("# SignInServlet > E' stato effettuato un tentativo di creazione di un utente gia' esistente {err: email}.");
			
				response.setStatus(400);
				return;	
			}
		} else {
			out.print("Questo nome utente &egrave; gi&agrave; in uso!");
			System.out.println("# SignInServlet > E' stato effettuato un tentativo di creazione di un utente gia' esistente {err: username}.");
			
			response.setStatus(400);
			return;
		}
	}

}
