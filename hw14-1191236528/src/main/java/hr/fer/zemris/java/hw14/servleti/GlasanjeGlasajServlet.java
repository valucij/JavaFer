package hr.fer.zemris.java.hw14.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * This class extends {@link HttpServlet}. It's job is to
 * update voting results in voting app.
 * 
 * @author Lucija Valentić
 *
 */
@WebServlet(name="gl", urlPatterns = {"/servleti/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id = req.getParameter("id");
		
		long optionID = Long.valueOf(id);
		
		DAOProvider.getDao().addVoteToPollOptionById(optionID);
		
		PollOption option = DAOProvider.getDao().getPollOptionById(optionID);
		
		long pollID = option.getPollId();
		
		req.setAttribute("pollID", pollID);
		
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID="+pollID);
	}
}
