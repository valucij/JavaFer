package hr.fer.zemris.java.hw14.servleti;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * This class extends {@link HttpServlet}. It it called 
 * "in the beginning" of a voting webapp. This class prepares list
 * of all bands user can vote on, and also prepare results, meaning,
 * in text file where results are kept, in the beginning of app ( but
 * only first time when app is started on user's computer, 
 * this doesn't happen if server is (re)started!), results are initialized, every band
 * gets 0 votes.
 * Then everything is sent to "/WEB-INF/pages/glasanjeIndex.jsp", and user can vote.
 * 
 * @author Lucija Valentić
 *
 */
@WebServlet(name="g", urlPatterns = {"/servleti/glasanje"})
public class GlasanjeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		DAOProvider.getDao().doesExistPollTableAndCreate();
		
		long pollId = Long.valueOf(req.getParameter("pollID"));
		
		req.getSession().setAttribute("pollID", pollId);
		
		Poll poll = DAOProvider.getDao().getPollById(pollId);
		
		String title = poll.getName();
		String message = poll.getMessage();
		
		req.setAttribute("title", title);
		req.setAttribute("message", message);
		
		req.setAttribute("pollID", pollId);
		
		
		List<PollOption> options = DAOProvider.getDao().getPollOptionsByIdPoll(pollId);
		
		List<String> id = new ArrayList<String>();
		List<String> bandNames = new ArrayList<String>();
		
		for(PollOption o : options) {
			id.add(String.valueOf(o.getId()));
			bandNames.add(String.valueOf(o.getOptionTitle()));
		}
		
		req.setAttribute("id", id);
		req.setAttribute("bandNames", bandNames);
		
		
		// Pošalji ih JSP-u...
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
