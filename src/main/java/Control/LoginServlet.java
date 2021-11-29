package Control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Model.User;
import Service.UserService;
import Utils.PasswordHasher;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 5201749135928085764L;

	protected void doPost(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
	
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if(username == null || password == null) {
			String failureMessage = "";

			/*
				TC_Login
				-> TC_LoginFailed4
			*/
			failureMessage += (username == null) ? "Username: campo obbligatorio.\n" : "";

			/*
				TC_Login
				-> TC_LoginFailed2
			*/
			failureMessage += (password == null) ? "Password: campo obbligatorio.\n" : "";

			out.print(failureMessage);
			System.out.println("# LoginServlet > Tentativo di login fallito (value(s) == null).");
			response.setStatus(400);
			return;
		}

		Connection db = (Connection) request.getServletContext().getAttribute("databaseConnection");
		UserService service = new UserService(db);
		int id = service.getIdByUsername(username);
			
		if(id != -1) {
			User user = service.getUser(id); 

			if(user.getPassword().equals(PasswordHasher.hash((password)))) {
				/*
					TC_Login
					-> TC_LoginOK
				*/
				response.setStatus(200);	
				response.addCookie(new Cookie("user_session", request.getSession().getId()));
				request.getSession().setAttribute("user_metadata", user);
					
				System.out.println("# LoginServlet > URL Rewriting: " + response.encodeURL("index.jsp"));
					
				user.setSession(request.getSession().getId());
					
				try {
					new UserService(db).updateUser(user);
				} catch(SQLException e) {
					e.printStackTrace();
				}
					
				if(request.getParameter("cookie").equals("false"))
					out.print(response.encodeURL(""));
				
				System.out.println("# LoginServlet > Login effettuato!");
				System.out.println("# LoginServlet > Tentativo di creazione del cookie: [user_session = " + request.getSession().getId() + "]");
				
				return;
			} else {
				/*
					TC_Login
					-> TC_LoginFailed1
				*/
				out.print("La password &egrave; errata!");
				System.out.println("# LoginServlet > Tentativo di login fallito (password errata).");
					
				response.setStatus(400);
				return;
			}
		} else {
			/*
				TC_Login
				-> TC_LoginFailed3
			*/
			out.print("L'username &egrave; errato o inesistente!");
			System.out.println("# LoginServlet > Tentativo di login fallito (username errato o inesistente).");
				
			response.setStatus(400);
			return;
		}
		
	}
}
