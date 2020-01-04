package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * This class extends {@link HttpServlet}. It's job is
 * to set attribute for background color for "/index.jsp". If
 * nothing is sent, then default color is white.
 * 
 * @author Lucija Valentić
 *
 */
@WebServlet(name="s", urlPatterns = {"/setcolor"})
public class SetColorServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String color = req.getParameter("col");
		
		if(color == null || color.isBlank()) {
			req.getSession().setAttribute("pickedBgColor", "White");	
		}else {
			req.getSession().setAttribute("pickedBgColor", color);
		}
		
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
		
	}
	
}
