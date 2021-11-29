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
		PrintWriter out = response.getWriter();

		String failureMessage = "";

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String repeatPassword = request.getParameter("password2");
		String email = request.getParameter("email");

		boolean usernamePattern = username.matches("^[A-Za-z][A-Za-z0-9_]{7,29}$");
		boolean passwordPattern = password.matches("/^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,}$/");
		boolean repeatPasswordPattern = repeatPassword.matches("/^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,}$/");
		boolean emailPattern = email.matches("^\\S+@\\S+$");

		/*
		 	Error Handling
		*/

		// Uno o più dati sono nulli
		boolean nullable = username == null || password == null || repeatPassword == null || email == null;

		// Uno o più dati non rispettano il formato richiesto
		boolean mismatch = !usernamePattern || !passwordPattern || !repeatPasswordPattern || !emailPattern;

		if(nullable || mismatch) {
			/*
				TC_Login
				-> TC_RegistrazioneFailed12
			*/
			failureMessage += (username == null) ? "Username - campo obbligatorio.\n" : "";

			/*
				TC_Login
				-> TC_RegistrazioneFailed8
			*/
			failureMessage += (password == null) ? "Password - campo obbligatorio.\n" : "";

			/*
				TC_Login
				-> TC_RegistrazioneFailed6
			*/
			failureMessage += (repeatPassword == null) ? "Ripeti password - campo obbligatorio.\n" : "";

			/*
				TC_Login
				-> TC_RegistrazioneFailed3
			*/
			failureMessage += (email == null) ? "Email - campo obbligatorio.\n" : "";

			/*
				TC_Login
				-> TC_RegistrazioneFailed11
			*/
			failureMessage += (email != null && !usernamePattern) ? "Formato username non valido.\n" : "";

			/*
				TC_Login
				-> TC_RegistrazioneFailed7
			*/
			failureMessage += (password != null && !passwordPattern) ? "Formato password non valido.\n" : "";

			/*
				TC_Login
				-> TC_RegistrazioneFailed5
			*/
			failureMessage += (repeatPassword != null && !repeatPasswordPattern) ? "Formato ripeti password non valido.\n" : "";

			/*
				TC_Login
				-> TC_RegistrazioneFailed2
			*/
			failureMessage += (email != null && !emailPattern) ? "Formato email non valido.\n" : "";

			out.print(failureMessage);
			System.out.println("# LoginServlet > Tentativo di login fallito: alcuni dati non risultano validi.");
			System.out.println("# LoginServlet > Errore/i: " + failureMessage);

			response.setStatus(400);
			return;
		} else {
			UserService service = new UserService(db);

			int id = service.getIdByUsername(username);
			boolean emailUsed = service.findUserByEmail(email);
				
			if(id == -1) {
				if(!emailUsed) {
					if(password.equals(repeatPassword)) {
						/*
							TC_Registrazione
							-> TC_RegistrazioneOK
						*/
						service.insertUser(request.getParameter("username"), PasswordHasher.hash(password), email);
						
						out.print("Registrazione avvenuta con successo!");						
						System.out.println("# SignInServlet > Creazione utente effettuata con successo (username: " + request.getParameter("username"));
						
						response.setStatus(200);
						return;
					} else {
						/*
							TC_Registrazione
							-> TC_RegistrazioneFailed4
						*/
						out.print("Le password non coincidono.");
						System.out.println("# SignInServlet > E' stato effettuato un tentativo fallimentare di registrazione.");
						
						response.setStatus(400);
						return;
					}
				} else {
					/*
						TC_Registrazione
						-> TC_RegistrazioneFailed1
					*/
					out.print("E-mail gi&agrave; esistente.");
					System.out.println("# SignInServlet > E' stato effettuato un tentativo di creazione di un utente gia' esistente {err: email}.");
				
					response.setStatus(400);
					return;	
				}
			} else {
				/*
					TC_Registrazione
					-> TC_RegistrazioneFailed10
				*/
				out.print("Username gi&agrave; esistente.");
				System.out.println("# SignInServlet > E' stato effettuato un tentativo di creazione di un utente gia' esistente {err: username}.");
					
				response.setStatus(400);
				return;
			}
		}
	}
}
