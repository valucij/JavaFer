package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Class that provides methods for retrieving data from
 * database
 * 
 * @author Lucija Valentić
 *
 */
public interface DAO {
	/**
	 * Returns all blogs from author, which is determined by received id
	 * @param id
	 * @return List<BlogEntry>
	 * @throws DAOException
	 */
	public List<BlogEntry> getBlogEntriesByAuthorId(Long id) throws DAOException;
	/**
	 * Returns blog user determinated by given id, or <code>null</code>
	 * if the user doesn't exist.
	 * @param id
	 * @return BlogUser or null
	 * @throws DAOException
	 */
	public BlogUser getBlogUser(Long id) throws DAOException;
	/**
	 * Returns list of all blog comments from some blog, determinated
	 * by given id. List can be empty or null if there are no comments
	 * @param id
	 * @return List<BlogComment>
	 * @throws DAOException
	 */
	public List<BlogComment> getCommentsByBlogId(Long id) throws DAOException;
	/**
	 * Returns list of all registered users. It can be null or empty there is no
	 * registered users
	 * @return List<BlogUser>
	 * @throws DAOException
	 */
	public List<BlogUser> getAllUsers() throws DAOException;
	/**
	 * Method registers new blog user, given by user. It was made sure
	 * that this parameter user is valid
	 * @param user
	 * @throws DAOException
	 */
	public void registerNewUser(BlogUser user) throws DAOException;
	/**
	 * Adds new entry into database. It was made sure that given entry
	 * is valid one.
	 * @param entry
	 * @throws DAOException
	 */
	public void addNewEntry(BlogEntry entry) throws DAOException;
	/**
	 * Method changes blog entry, determinated by given entry. It was
	 * made sure that given entry is valid one.
	 * @param entry
	 * @throws DAOException
	 */
	public void changeExistingEntry(BlogEntry entry) throws DAOException;
	/**
	 * Method adds new comment into database. It was made sure that
	 * given comment is valid one.
	 * @param comment
	 * @throws DAOException
	 */
	public void addNewComment(BlogComment comment) throws DAOException;
	/**
	 * Returns blog user determinated by given nick. It return null
	 * if that user doesn't exit.
	 * @param nick
	 * @return BlogUser
	 * @throws DAOException
	 */
	public BlogUser getBlogUserByNick(String nick) throws DAOException;
	/**
	 * Returns blog entry determinated by given id. Returns null if
	 * that blog entry doesn't exist
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public BlogEntry getBlogEntryById(Long id) throws DAOException;
}