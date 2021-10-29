package Handler;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Model.Role;
import Model.User;
import Service.HasRoleService;
import Service.UserService;
import Service.ViewService;

/*
    Il sistema di ruoli di Shodan prevede quattro posizioni:
    - Ospite, default
    - Utente, ottenuto una volta loggati a Shodan
    - Articolista, cioè un admin che gestisce le notizie del blog
    - Cataloghista, cioè un admin che gestisce il catalogo di giochi

    Un attore può ricoprire più di un ruolo se loggato.

    La tabella {VIEWS} associa i ruoli a dei componenti e determina quale componente mostrare ..
    .. all'attore, in funzione dei suoi ruoli per l'appunto.

    L'handler ShodanViews si occupa della logica dietro all'operazione di associamento sopracitata.
*/
@WebServlet("/ShodanViews")
public class ShodanViews extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    private User user;

    /*
        Per aggiungere una nuova view personalizzata, è sufficiente ..
        .. modificare il seguente Enum, e aggiungere la corrispondente entry ..
        .. nella tabella {VIEWS} del database.
    */
    public enum RequestedView {
        MAIN,
        NAV,
        ADMIN
    }

    protected void doGet(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		Connection db = (Connection) request.getServletContext().getAttribute("databaseConnection");
		RequestedView requestedView = RequestedView.valueOf(request.getParameter("view"));

		if(request.getParameter("cookie").equals("false"))
			user = new UserService(db).getUserBySession(request.getParameter("jsession"));
		else
			user = (User) request.getSession().getAttribute("user_metadata");

        Role role = null;

        if(request.getParameter("requestedRole") == null)
            role = user != null ? new HasRoleService(db).getMainRole(user.getRoles()) : new Role(-1, "GUEST");
        else
            role = new Role(user.getId(), request.getParameter("requestedRole"));    

        String path = new ViewService(db).getView(role, requestedView);

        System.out.println("# ShodanViews > View ottenuta (" + role.getRoleName() + ", " + requestedView + ", " + path + ")");

        response.getWriter().print(path);
    }
}
