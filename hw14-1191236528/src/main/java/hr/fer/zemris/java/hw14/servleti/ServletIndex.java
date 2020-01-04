package hr.fer.zemris.java.hw14.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class extends {@link HttpServlet}. Its job is to redirect
 * servlet mapped to "/index.html" to "/servleti/index.html"
 * 
 * @author Lucija Valentić
 *
 */
@WebServlet(name="in", urlPatterns = {"/index.html"})
public class ServletIndex extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.sendRedirect(req.getContextPath() + "/servleti/index.html");
	}
}
