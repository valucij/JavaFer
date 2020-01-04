package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Class extends {@link HttpServlet}. This servlet is used when 
 * handling blog entries, editing, adding new entry, or comments. 
 * Methods redirect action to appropriate method that deals with that action.
 * Method that actually handle actions are in class {@link AuthorUtil}.
 * 
 * @author Lucija Valentić
 *
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String[] pathInfo = req.getPathInfo().substring(1).split("/");
		String lastString = pathInfo[pathInfo.length - 1];
		
		if(lastString.startsWith("new")) {
			AuthorUtil.addNewBlogEntry(req, resp);
		}else if(lastString.startsWith("edit")) {
			if(req.getSession().getAttribute("forEdit") == null) {
				AuthorUtil.prepareforEditBlogEntry(req, resp);
			}else {
				AuthorUtil.editBlogEntry(req,resp);	
			}
			
		}else if(lastString.startsWith("comment")) {
			
			String blogID = req.getParameter("blogID");
			resp.sendRedirect(req.getContextPath() + "/servleti/comment?blogID=" + blogID);
		}else if(lastString.startsWith("main")) {
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
		}else if(lastString.startsWith("logout")) {
			resp.sendRedirect(req.getContextPath() + "/servleti/logout");
		}

	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String[] pathInfo = req.getPathInfo().substring(1).split("/");
		String lastString = pathInfo[pathInfo.length - 1];
		
		if(lastString.startsWith("new")) {
			
			BlogUser user = DAOProvider.getDAO().getBlogUserByNick(pathInfo[0]);
			
			if(req.getSession().getAttribute("currentUserId") == null || 
					req.getSession().getAttribute("currentUserId") != null && !user.getNick().equals(req.getSession().getAttribute("currentUserNick"))) {
				req.setAttribute("message", "Pogreška! Zatražili ste stranicu koja je zabranjena!");
				req.getRequestDispatcher("/WEB-INF/pages/Message.jsp").forward(req, resp);
				return;
			}
			
			req.getRequestDispatcher("/WEB-INF/pages/NewBlog.jsp").forward(req, resp);
		}else if(lastString.startsWith("edit")) {
			AuthorUtil.prepareforEditBlogEntry(req, resp);
		}else {
			try {
				Long.parseLong(lastString);
				AuthorUtil.handleBlogEntryByID(req,resp);
			}catch(NumberFormatException ex) {
				AuthorUtil.handleListOfBlogEntries(req, resp);	
			}	
		}
		
		
	}
}
