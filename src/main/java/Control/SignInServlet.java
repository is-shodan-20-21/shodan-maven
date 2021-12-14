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

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String repeatPassword = request.getParameter("password2");
		String email = request.getParameter("email");

		boolean usernamePattern = username.matches("^[A-Za-z][A-Za-z0-9_]{3,29}$");
		boolean passwordPattern = password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,}$");
		boolean emailPattern = email.matches("^\\S+@\\S+$");

		UserService service = new UserService(db);
		
		//PARAMETRO USERNAME
		if(username.length() > 0){
			if(usernamePattern){
				int id = service.getIdByUsername(username);
				if(id == -1){
					if(password.length() > 0){
						if(passwordPattern){
							if(repeatPassword.length() > 0){
								if(password.equals(repeatPassword)){
									if(email.length() > 0){
										if(emailPattern){
											boolean emailUsed = service.findUserByEmail(email);
											if(!emailUsed){
												service.insertUser(username, PasswordHasher.hash(password), email);				
												System.out.println("# SignInServlet > TC_RegistrazioneOK > Registrazione avvenuta con successo");
												out.print("Registrazione avvenuta con successo");
												response.setStatus(200);
												return;
											}else{
												System.out.println("# SignInServlet > TC_RegistrazioneFailed1 > E-mail già esistente");
												out.print("E-mail gia' esistente");
											}
										}else{				
											out.print("Formato e-mail non valido");							
											System.out.println("# SignInServlet > TC_RegistrazioneFailed2 > Formato e-mail non valido");
										}
									}else{			
										out.print("Email: campo obbligatorio");							
										System.out.println("# SignInServlet > TC_RegistrazioneFailed3 > Email: campo obbligatorio");
									}
								}else{
									out.print("Le password non coincidono");									
									System.out.println("# SignInServlet > TC_RegistrazioneFailed4 > Le password non coincidono");
								}
							}else{
								out.print("Ripeti password: campo obbligatorio");
								System.out.println("# SignInServlet > TC_RegistrazioneFailed5 > Ripeti password campo obbligatorio");
							}
						}else{
							out.print("Formato password non valido");
							System.out.println("# SignInServlet > TC_RegistrazioneFailed6 > Formato password non valido");
						}
					}else{
						out.print("Password: campo obbligatorio");
						System.out.println("# SignInServlet > TC_RegistrazioneFailed7 > Password campo obbligatorio");
					}
				}else{
					out.print("Username esistente");
					System.out.println("# SignInServlet > TC_RegistrazioneFailed8 > Username già esistente");
				}
			}else{
				out.print("Formato username non valido");
				System.out.println("# SignInServlet > TC_RegistrazioneFailed9 > Formato username non valido");
			}
		}else{
			out.print("Username: campo obbligatorio");
			System.out.println("# SignInServlet > TC_RegistrazioneFailed10 > Username - campo obbligatorio");
		}

		response.setStatus(400);
	}
}
