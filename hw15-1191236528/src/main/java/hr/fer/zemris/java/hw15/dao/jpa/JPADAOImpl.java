package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Class implements {@link DAO}. It provides
 * methods to deal with database, and to retrieve data
 * @author Lucija Valentić
 *
 */
public class JPADAOImpl implements DAO {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BlogEntry> getBlogEntriesByAuthorId(Long id) throws DAOException {
		
		EntityManager em = JPAEMProvider.getEntityManager();		
		BlogUser blogUser = em.find(BlogUser.class, id);
		
		List<BlogEntry> entries = em.createQuery("select b from BlogEntry as b where b.blogUser=:be", BlogEntry.class).
				setParameter("be", blogUser).getResultList();
		
		return entries;
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogUser getBlogUser(Long id) throws DAOException {
		
		BlogUser user = JPAEMProvider.getEntityManager().find(BlogUser.class, id);
		
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BlogComment> getCommentsByBlogId(Long id) throws DAOException {
		
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		
		List<BlogComment> blogComments = JPAEMProvider.getEntityManager().createQuery("select b from BlogComment as b where b.blogEntry=:be", BlogComment.class).
				setParameter("be", blogEntry).getResultList();
		
		return blogComments;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BlogUser> getAllUsers() throws DAOException {
		
		List<BlogUser> users = JPAEMProvider.getEntityManager().createQuery("select user from BlogUser user", BlogUser.class).getResultList();
		
		return users;
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerNewUser(BlogUser user) throws DAOException {
		
		System.out.println(user.getEmail());
		System.out.println(user.getFirstName());
		System.out.println(user.getLastName());
		System.out.println(user.getNick());
		System.out.println(user.getPasswordHash());
		
		JPAEMProvider.getEntityManager().persist(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addNewEntry(BlogEntry entry) throws DAOException {
		JPAEMProvider.getEntityManager().persist(entry);
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changeExistingEntry(BlogEntry entry) throws DAOException {
	
		EntityManager em = JPAEMProvider.getEntityManager();
		em.merge(entry);
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addNewComment(BlogComment comment) throws DAOException {
		JPAEMProvider.getEntityManager().persist(comment);
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogUser getBlogUserByNick(String nick) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		List<BlogUser> user = em.createQuery("select b from BlogUser as b where b.nick=:ni", BlogUser.class).
		setParameter("ni", nick).getResultList();
		
		if(user == null || user.isEmpty()) {
			return null;
		}else {
			return user.get(0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogEntry getBlogEntryById(Long id) throws DAOException {
		
		List<BlogEntry> entries = JPAEMProvider.getEntityManager().createQuery("select b from BlogEntry as b where id=:be", BlogEntry.class)
				.setParameter("be", id).getResultList();
		
		if(entries == null || entries.isEmpty()) {
			return null;
		}
		
		return entries.get(0);
	}



}