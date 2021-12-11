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

		Connection db = (Connection) request.getServletContext().getAttribute("databaseConnection");
		UserService service = new UserService(db);
		int id = service.getIdByUsername(username);

		if(id != -1){
			User user = service.getUser(id); 
			if(user.getPassword().equals(PasswordHasher.hash((password)))){
				response.setStatus(200);	
				response.addCookie(new Cookie("user_session", request.getSession().getId()));
				request.getSession().setAttribute("user_metadata", user);
					
				System.out.println("# LoginServlet > URL Rewriting: " + response.encodeURL("index.jsp"));
				System.out.println("# LoginServlet > TC_LoginOK > Login effettuato");
					
				user.setSession(request.getSession().getId());
					
				try {
					new UserService(db).updateUser(user);
				} catch(SQLException e) {
					e.printStackTrace();
				}
					
				if(request.getParameter("cookie").equals("false"))
					out.print(response.encodeURL(""));
			}else{
				System.out.println("# LoginServlet > TC_LoginFailed1 > Password errata");
				response.setStatus(400);
				return;
			}
		}else{
			System.out.println("# LoginServlet > TC_LoginFailed2 > Utente non esistente");
			response.setStatus(400);
			return;
		}						
	}
}
