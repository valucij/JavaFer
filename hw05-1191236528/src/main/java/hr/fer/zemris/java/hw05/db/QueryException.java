package hr.fer.zemris.java.hw05.db;

/**
 * New exception, we use it when we are working
 * with QueryParser or QueryLexer when something
 * goes wrong
 * 
 * @author Lucija Valentić
 *
 */
public class QueryException extends RuntimeException {

	/**
	 * Constructor
	 */
	public QueryException() {
		super();
	}
	/**
	 * Constructor
	 * @param message
	 */
	public QueryException(String message) {
		super(message);
	}

	
	private static final long serialVersionUID = 1L;

	
}
