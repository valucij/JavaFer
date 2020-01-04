package hr.fer.zemris.java.hw17.trazilica;

/**
 * 
 * Class extends {@link RuntimeException}. It is used for problems
 * in classes located in package hr.fer.zemris.java.hw17.trazilica.  
 * @author Lucija Valentić
 *
 */
public class SearchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public SearchException() {
		super();
	}

	/**
	 * Constructor
	 */
	public SearchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor
	 */
	public SearchException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 */
	public SearchException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 */
	public SearchException(Throwable cause) {
		super(cause);
	}
}
