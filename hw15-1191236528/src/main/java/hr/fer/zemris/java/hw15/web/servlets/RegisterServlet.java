package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
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
 * Class extends {@link HttpServlet}. This class
 * allows user, with its method, to register himself.
 * If he enters something invalid, registration won't
 * be successful, and he will have to enter it again. If
 * everything is valid, then message about successful registration
 * will be shown to user.
 * 
 * @author Lucija Valentić
 *
 */
@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet {

	/**
	 * Internal map of errors
	 */
	private Map<String, String> errors = new HashMap<String, String>();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		BlogUser user = new BlogUser();
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String password = req.getParameter("password");
		String nick = req.getParameter("username");
		String email = req.getParameter("email");
		errors.clear();
		
		if(firstName == null || firstName.isBlank()) {
			errors.put("firstName", "Ime je obavezno polje");
		}else {
			user.setFirstName(firstName);
		}
		
		if(lastName == null || lastName.isBlank()) {
			errors.put("lastName", "Prezime je obavezno polje");
		}else {
			user.setLastName(lastName);
		}
		
		if(nick == null || nick.isBlank()) {
			errors.put("username", "Username je obavezno polje");
		}else if(doesNickExists(nick)) {
			errors.put("username", "Korisnik sa upisanim usernameom već postoji");
		}else {
			user.setNick(nick);
		}
		
		if(email == null || email.isBlank()) {
			errors.put("email", "E-mail je obavezno polje");
		}else {
			user.setEmail(email);
		}
	
		if(password == null || password.isBlank()) {
			errors.put("password", "Lozinka je obavezno polje");
		}else {
			try {
				user.setPasswordHash(Crypto.calculatePassword(password));
			} catch (NoSuchAlgorithmException e) {
				errors.put("password", "Ponovno upiši lozinku");
			}
		}
		
		if(!errors.isEmpty()) {
			req.setAttribute("errors", errors);
			doGet(req, resp);
			return;
		}
		
		DAOProvider.getDAO().registerNewUser(user);
		req.setAttribute("message", "Uspješna registracija!");
		req.getRequestDispatcher("/WEB-INF/pages/Message.jsp").forward(req, resp);
			
	}

	/**
	 * Checks if user with given nick exists. Returns <code>true</code>
	 * if it does, <code>false</code> otherwise
	 * @param nick
	 * @return boolean
	 */
	private boolean doesNickExists(String nick) {
		return DAOProvider.getDAO().getBlogUserByNick(nick) != null;
	}
	
}
