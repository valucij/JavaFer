package hr.fer.zemris.java.hw05.db;
/**
 * Newly made exception that can be used when working
 * with database, and when something goes wrong
 * @author Lucija Valentić
 *
 */
public class DatabaseException extends RuntimeException {

	/**
	 * Constructor
	 */
	public DatabaseException() {
		super();
	}
	/**
	 * Construcotr
	 * @param message
	 */
	public DatabaseException(String message) {
		super(message);
	}
	
	private static final long serialVersionUID = 1L;

}
