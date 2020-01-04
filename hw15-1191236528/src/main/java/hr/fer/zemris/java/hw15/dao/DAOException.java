package hr.fer.zemris.java.hw15.dao;

/**
 * New exception made when dealing with databases 
 * @author Lucija Valentić
 *
 */
public class DAOException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param message
	 * @param cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 * @param message
	 */
	public DAOException(String message) {
		super(message);
	}
}