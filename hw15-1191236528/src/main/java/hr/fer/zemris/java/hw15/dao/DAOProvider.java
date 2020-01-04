package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.dao.jpa.JPADAOImpl;

/**
 * Class provides object to deal with database
 * 
 * @author Lucija Valentić
 *
 */
public class DAOProvider {

	/**
	 * Internal dao
	 */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Returns this.dao
	 * @return DAO
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}