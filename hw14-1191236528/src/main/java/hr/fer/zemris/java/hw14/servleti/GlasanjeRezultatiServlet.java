package hr.fer.zemris.java.hw14.servleti;

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
@WebServlet(name="glr", urlPatterns={"/servleti/glasanje-rezultati"})
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
	
		Long pollID = Long.valueOf(req.getParameter("pollID"));
		
		Map<String, String> mapRezultati = GlasanjeUtil.returnMapResults(pollID);
		
		List<String> winnerIds = GlasanjeUtil.sortByValueAndReturnWinners(mapRezultati);
		
		req.setAttribute("winnerIds", winnerIds);
		
		req.setAttribute("mapRezultati", mapRezultati);
		
		req.setAttribute("pollID", pollID);
		
		
		Map<String, String> mapBandInformations = GlasanjeUtil.returnMapIdAndBandName(pollID);
		Map<String, String> idAndLinks = GlasanjeUtil.returnMapIdAdLinks(pollID);
		
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
