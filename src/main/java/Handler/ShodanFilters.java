package Handler;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Model.User;
import Service.UserService;

@WebFilter(urlPatterns = 
	{ 
		"/index.jsp", 
		"/app.jsp"
	}
)
public class ShodanFilters implements Filter {

	@Override
	public void doFilter(
		ServletRequest request, 
		ServletResponse response, 
		FilterChain chain
	) throws IOException, ServletException {
		HttpServletRequest hRequest = (HttpServletRequest) request;
		HttpServletResponse hResponse = (HttpServletResponse) response;
		
		String uri = hRequest.getRequestURI();
		String encoding = "";
		User user;
		
		System.out.println("# ShodanFilters > URI > " + uri);
		
		Connection db = (Connection) hRequest.getServletContext().getAttribute("databaseConnection");
		
		/*
			E' possibile utilizzare Shodan anche se l'utente ha disabilitato completamente i cookie.
			In questo caso, l'URL viene modificato e ad esso viene aggiunto l'ID della JSESSION corrente.

			L'ID in questione è il medesimo salvato nella tabella {SESSIONS} all'accesso.
			La riga di accesso viene distrutta al logout.
		*/
		if(uri.contains("jsessionid")) {
			String jsession = uri.substring(uri.lastIndexOf("=") + 1);
			System.out.println("# ShodanFilters > URL Rewriting > " + jsession);
		
			if(new UserService(db).getUserBySession(jsession) != null) {
				user = new UserService(db).getUserBySession(jsession);
				
				System.out.println("# ShodanFilters > Utente: " + user.toString());
			} else
				user = null;
			
			encoding = ";jsessionid=" + jsession;
		} else
			user = (User) hRequest.getSession().getAttribute("user_metadata");
		
		chain.doFilter(hRequest, hResponse);
		
		if(user != null)
			System.out.println("# ShodanFilters > Routing di [" + user.getId() + "] " + user.getName());
		
		// Prima che Shodan fosse effettivamente one-page, la seguente parte doveva essere decommentata.

		/*
			if(uri.contains("/index.jsp") || uri.contains("Shodan/;jsessionid=") || uri.equals("/Shodan/")) {
				if(user != null)
					hResponse.sendRedirect("app.jsp" + encoding);
			} else if(uri.contains("/app.jsp")) {
				if(user == null)
					hResponse.sendRedirect("index.jsp" + encoding);
			} else if(uri.contains("/admin.jsp")) {
				if(user == null || !user.isAdmin())
					hResponse.sendRedirect("app.jsp" + encoding);
			}
		*/

		// Gli utenti non possono accedere alla Index una volta loggati.
		if(uri.contains("/index.jsp") && user != null)
			hResponse.sendRedirect("app.jsp" + encoding);
	}

	public void destroy() {
		return;
	}

	public void init(javax.servlet.FilterConfig config) {
		return;
	}

}