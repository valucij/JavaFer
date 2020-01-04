package hr.fer.zemris.java.hw13.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * This class implements {@link ServletContextListener} and it's job
 * is in the beginning of a program to save current time
 * 
 * @author Lucija Valentić
 *
 */
@WebListener
public class AppInfoListener implements ServletContextListener{

	/**
	 * Represents internal context
	 */
	private ServletContext context = null;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		context = sce.getServletContext();
		context.setAttribute("time", System.currentTimeMillis());
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {		
	}


	
	
}

