package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Class extends {@link HttpServlet}. This class allows
 * users to add new comments to some blog entries. Actions
 * are allowed only if some user is logged in. User 
 * can add comments to its own blog entry if he
 * chooses to.
 * 
 * @author Lucija Valentić
 *
 */
@WebServlet("/servleti/comment")
public class CommentServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Internal map of errors
	 */
	Map<String, String> errors = new HashMap<String, String>();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id = req.getParameter("blogID");
		req.setAttribute("blogID", id);
		req.getRequestDispatcher("/WEB-INF/pages/AddComment.jsp").forward(req, resp);;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String message = req.getParameter("message");
		String usersEmail = req.getParameter("usersEmail");
		errors.clear();
		
		if(message == null || message.isBlank()) {
			errors.put("messageError", "Poruka je obavezno polje");
		}
		
		BlogComment comment = new BlogComment();
		
		BlogUser user = DAOProvider.getDAO().getBlogUserByNick(String.valueOf(req.getSession().getAttribute("currentUserNick")));
		
		if(req.getSession().getAttribute("currentUserId") != null) {
			comment.setUsersEMail(user.getEmail());
		}else if(usersEmail == null || usersEmail.isBlank()) {
			errors.put("usersEmailError", "E-mail je obavezno polje");
		}else {
			comment.setUsersEMail(usersEmail);
		}
		
		if(!errors.isEmpty()) {
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("/WEB-INF/pages/AddComment.jsp").forward(req, resp);;
			return;
		}
		
		
		comment.setMessage(message);
		comment.setPostedOn(new Date(new Date().getTime()));
		
		Long id = Long.parseLong(req.getParameter("blogID"));
		
		BlogEntry entry = DAOProvider.getDAO().getBlogEntryById(id);
		
		comment.setBlogEntry(entry);
		
		DAOProvider.getDAO().addNewComment(comment);
		req.setAttribute("message", "Komentar uspješno dodan");
		req.getRequestDispatcher("/WEB-INF/pages/Message.jsp").forward(req, resp);;
		
	}
}
