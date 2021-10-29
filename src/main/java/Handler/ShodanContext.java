package Handler;

import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import Database.DBConnectionPool;

/*
	Sfruttare i Java Servlet Context per connettersi al database previene la necessità ..
	.. di doversi ricollegare a JDBC ogni qualvolta si desidera lanciare una query.

	La connessione al database viene stabilita all'avvio del webserver.
*/
@WebListener
public class ShodanContext implements ServletContextListener {
	public void contextInitialized(ServletContextEvent event) {	
		try {
			Connection databaseConnection = DBConnectionPool.getConnection();
			event.getServletContext().setAttribute("databaseConnection", databaseConnection);	
			System.out.println("# ShodanContext > Connessione al database stabilita");
		} catch (SQLException e) {
			System.out.println("# ShodanContext > Impossibile generare una connessione al database");
		}
	}
	
	public void contextDestroyed(ServletContextEvent event) {
		event.getServletContext().removeAttribute("databaseConnection");
	}
}

