package hr.fer.zemris.java.hw14.servleti;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;

/**
 * Class extends {@link HttpServlet}. This servlet generates html
 * document that shows every poll in database, and user can click on them.
 * 
 * @author Lucija Valentić
 *
 */
@WebServlet(name="i", urlPatterns = {"/servleti/index.html"})
public class ServletIndexHTML extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<Poll> listPolls = DAOProvider.getDao().getAllPolls();
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter writer = resp.getWriter();
		
		writer.println("<h1>Dostupne ankete:</h1>");
		
		writer.println("<ol>");
		for(Poll p : listPolls) {
			writer.println("<li><a href=\"glasanje?pollID=" + p.getId() + " \">" + p.getName() + "</a></li>");
		}
		writer.println("</ol>");
		
	}

}
