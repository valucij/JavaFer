package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Class that has methods that deal with blog entries, adding
 * new blog entries, editing blog entries, and adding new comments.
 * 
 * @author Lucija Valentić
 *
 */
public class AuthorUtil {

	/**
	 * Internal map of errors
	 */
	public static Map<String, String> errors = new HashMap<String, String>();
	
	/**
	 * Method gathers all blog entries from some user (user id is given through
	 * parameter). It then shows to the user list of all blogs from some user. List of
	 * blogs are actually links, and user can click on them and see actually blog entries
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void handleListOfBlogEntries(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String[] pathInfo = req.getPathInfo().substring(1).split("/");
		
		BlogUser user = DAOProvider.getDAO().getBlogUserByNick(pathInfo[0]);
		
		List<BlogEntry> blogEntries = DAOProvider.getDAO().getBlogEntriesByAuthorId(user.getId());
		
		if(blogEntries != null && !blogEntries.isEmpty()) {
			req.setAttribute("blogEntries", blogEntries);
		}
		
		if(req.getSession().getAttribute("currentUserId") != null && req.getSession().getAttribute("currentUserNick").equals(pathInfo[0])) {
			req.setAttribute("addNew", true);
		}
		
		req.getRequestDispatcher("/WEB-INF/pages/ListOfBlogs.jsp").forward(req, resp);;
	}

	/**
	 * Method prepares blog that was determinated by blogID that was
	 * sent through parameter. If user is logged in and 
	 * blog entries belongs to the user, then special button is prepared, and
	 * user can choose to edit it. This method in the end shows actual
	 * blog entry to the user
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void handleBlogEntryByID(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String[] pathInfo = req.getPathInfo().substring(1).split("/");
		String lastString = pathInfo[pathInfo.length - 1];
		
		Long id = Long.parseLong(lastString);
		
		BlogEntry entry = DAOProvider.getDAO().getBlogEntryById(id);
		
		if(entry == null) {
			req.setAttribute("message", "Pogreška! Zatražili ste stranicu koja je zabranjena!");
			req.getRequestDispatcher("/WEB-INF/pages/Message.jsp").forward(req, resp);
			return;
		}
		
		req.setAttribute("blogEntry", entry);
		
		List<BlogComment> comments = DAOProvider.getDAO().getCommentsByBlogId(id);
		
		if(comments != null && !comments.isEmpty()) {
			req.setAttribute("comments", comments);
		}
		
		if(req.getSession().getAttribute("currentUserId") != null && req.getSession().getAttribute("currentUserNick").equals(pathInfo[0])) { // ako je ulogirani korisnik, baš vlasnik tog bloga
			req.setAttribute("edit", true);
		}
		
		BlogUser user = DAOProvider.getDAO().getBlogUserByNick(pathInfo[0]);
		
		if(user == null || entry.getBlogUser().getId() != user.getId()) { // ili user uopće ne postoji s tim nickom, ili taj blog nije od tog usera
			req.setAttribute("message", "Pogreška! Zatražili ste stranicu koja je zabranjena!");
			req.getRequestDispatcher("/WEB-INF/pages/Message.jsp").forward(req, resp);
			return;
		}
		
		req.getRequestDispatcher("/WEB-INF/pages/Blog.jsp").forward(req, resp);;
	}

	/**
	 * Method checks if everything was typed in correctly by the user.
	 * If there was some mistakes, user has to add blog entry again. 
	 * in the end, this method allows user to add new blog entry.
	 * This action is only possible is some user is logged in
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void addNewBlogEntry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String title = req.getParameter("title");
		String text = req.getParameter("textAll");
		errors.clear();
		
		if(title == null || title.isBlank()) {
			errors.put("titleError", "Naslov je obavezno polje");
		}
		
		if(text == null || text.isBlank()) {
			errors.put("textError", "Sadržaj je obavezno polje");
		}
		
		if(!errors.isEmpty()) {
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("/WEB-INF/pages/NewBlog.jsp").forward(req, resp);
			return;
		}
		
		BlogEntry entry = new BlogEntry();
		
		entry.setCreatedAt(new Date(new Date().getTime()));
		
		Long id = Long.parseLong(String.valueOf(req.getSession().getAttribute("currentUserId")));
		
		BlogUser user = DAOProvider.getDAO().getBlogUser(id);
		
		entry.setBlogUser(user);
		entry.setLastModifiedAt(new Date(new Date().getTime()));
		entry.setText(text);
		entry.setTitle(title);
		
		DAOProvider.getDAO().addNewEntry(entry);
		req.setAttribute("message", "Blog uspješno dodan!");
		 
		req.getRequestDispatcher("/WEB-INF/pages/Message.jsp").forward(req, resp);
		
	}

	/**
	 * Method allows user to edit some blog entry, determinate by
	 * blogID sent through parameter. This action is only allowed
	 * if the user is logged in and if blog entry belongs
	 * to him. Otherwise, user will see message about error. This method just prepares
	 * jsp-file for editing
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void prepareforEditBlogEntry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String[] pathInfo = req.getPathInfo().substring(1).split("/");
		
		BlogUser user = DAOProvider.getDAO().getBlogUserByNick(pathInfo[0]);
		
		if(user == null || req.getSession().getAttribute("currentUserId") == null || 
				req.getSession().getAttribute("currentUserId") != null && !user.getNick().equals(req.getSession().getAttribute("currentUserNick"))) {
			req.setAttribute("message", "Pogreška! Zatražili ste stranicu koja je zabranjena!");
			req.getRequestDispatcher("/WEB-INF/pages/Message.jsp").forward(req, resp);
			return;
		}
		
		Long blogID = Long.parseLong(req.getParameter("blogID"));
		
		BlogEntry entry = DAOProvider.getDAO().getBlogEntryById(blogID);
		
		if(entry == null || entry.getBlogUser().getId() != user.getId()) {
			req.setAttribute("message", "Pogreška! Zatražili ste stranicu koja je zabranjena!");
			req.getRequestDispatcher("/WEB-INF/pages/Message.jsp").forward(req, resp);
			return;
		}
		
		req.setAttribute("blogEntry", entry);
		
		req.getSession().setAttribute("forEdit", true);
		req.getRequestDispatcher("/WEB-INF/pages/EditBlog.jsp").forward(req, resp);
		
	}


	/**
	 * Method allows user to edit some blog entry, determinate by
	 * blogID sent through parameter. This action is only allowed
	 * if the user is logged in and if blog entry belongs
	 * to him. Otherwise, user will see message about error. This method
	 * is actual method that allows user to edit blog entry
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void editBlogEntry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String title = req.getParameter("title");
		String text = req.getParameter("textAll");
		
		if(title == null || title.isBlank()) {
			errors.put("titleError", "Naslov je obavezno polje");
		}
		
		if(text == null || text.isBlank()) {
			errors.put("textError", "Sadržaj je obavezno polje");
		}
		
		if(!errors.isEmpty()) {
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("/WEB-INF/pages/EditBlog.jsp").forward(req, resp);
			return;
		}
		
		Long id = Long.parseLong(req.getParameter("blogID"));
		
		BlogEntry entry = DAOProvider.getDAO().getBlogEntryById(id);
		
		entry.setLastModifiedAt(new Date(new Date().getTime()));
		entry.setText(text);
		entry.setTitle(title);
		
		DAOProvider.getDAO().changeExistingEntry(entry);
		
		req.setAttribute("message", "Blog je uspješno promjenjen");
		req.getSession().removeAttribute("forEdit");
		req.getRequestDispatcher("/WEB-INF/pages/Message.jsp").forward(req, resp);
	}
	
}
