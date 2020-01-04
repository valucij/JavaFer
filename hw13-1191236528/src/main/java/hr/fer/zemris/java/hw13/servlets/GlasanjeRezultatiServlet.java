package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class extends {@link HttpServlet}. It's job is to
 * prepare all informations for "/WEB-INF/pages/glasanjeRez.jsp".
 * Informations that are needed are: voting results, sorted of course, 
 * names of the bend, and links of the song from bands that got
 * most votes.
 * 
 * @author Lucija Valentić
 *
 */
@WebServlet(name="glr", urlPatterns={"/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		// Pročitaj rezultate iz /WEB-INF/glasanje-rezultati.txt
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
			
		Map<String, String> mapRezultati = GlasanjeUtil.returnMapResults(fileName);
		
		List<String> winnerIds = GlasanjeUtil.sortByValueAndReturnWinners(mapRezultati);
		
		req.setAttribute("winnerIds", winnerIds);
		
		req.setAttribute("mapRezultati", mapRezultati);
		
		fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		
		Map<String, String> mapBandInformations = GlasanjeUtil.returnMapIdAndBandName(fileName);
		Map<String, String> idAndLinks = GlasanjeUtil.returnMapIdAdLinks(fileName);
		
		List<String> winners = new ArrayList<String>();
		
		for(String s : winnerIds) {
			winners.add(idAndLinks.get(s));
		}
		
		req.setAttribute("mapBandInformations", mapBandInformations);
		req.setAttribute("winners", winners);
		
		// Pošalji ih JSP-u...
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
		
	}
	
	

}
