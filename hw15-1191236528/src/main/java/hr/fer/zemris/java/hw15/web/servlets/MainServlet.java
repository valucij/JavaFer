package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.crypto.Crypto;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Class extends class {@link HttpServlet}. This class
 * through its method, allows user to login in, see
 * other registered users, and maybe, if user is logged in, to
 * log out. This class with its method prepares all for that actions
 * 
 * @author Lucija Valentić
 *
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<BlogUser> u = DAOProvider.getDAO().getAllUsers();
		
		if(u != null && !u.isEmpty()) {
			
			Map<String, String> users = new HashMap<String, String>();
			
			for(BlogUser user : u) {
				users.put(user.getNick(), user.getFirstName() + " " + user.getLastName());
			}
			
			req.setAttribute("users", users);
		}
	
		req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(req.getSession().getAttribute("currentUserId") != null) {
			doGet(req, resp);
			return;
		}
		
		String username = req.getParameter("user");
		
		req.setAttribute("usernameForm", username);
		
		String password = req.getParameter("pass");
		String hashPass = null;
		
		if(password == null || password.isBlank()) {
			req.setAttribute("loginError", "Unesena je kriva lozinka i/ili username!");
			doGet(req, resp);
			return;
		}
		
		if(username == null || username.isBlank()) {
			req.setAttribute("loginError", "Unesena je kriva lozinka i/ili username!");
			doGet(req, resp);
			return;
		}
		
		try {
			hashPass = Crypto.calculatePassword(password);
		} catch (NoSuchAlgorithmException e) {
			req.setAttribute("loginError", "Unesena je kriva lozinka i/ili username!");
			doGet(req, resp);
			return;
		}
		
		BlogUser user = DAOProvider.getDAO().getBlogUserByNick(username);
		
		if(user == null) {
			req.setAttribute("loginError", "Unesena je kriva lozinka i/ili username!");
			doGet(req, resp);
			return;
		}
		
		if(!user.getPasswordHash().equals(hashPass)) {
			req.setAttribute("loginError", "Unesena je kriva lozinka i/ili username!");
			doGet(req, resp);
			return;
		}
		
		req.getSession().setAttribute("currentUserId", user.getId());
		req.getSession().setAttribute("currentUserName", user.getFirstName());
		req.getSession().setAttribute("currentUserLastName", user.getLastName());
		req.getSession().setAttribute("currentUserNick", user.getNick());
		
		
		
		doGet(req, resp);
	}
	
}
