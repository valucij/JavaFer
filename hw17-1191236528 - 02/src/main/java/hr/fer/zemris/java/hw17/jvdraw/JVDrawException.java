package hr.fer.zemris.java.hw17.jvdraw;

/**
 * New exception. It can be used when error occurs while
 * dealing with classes from package hr.fer.zemris.java.hw17.jvdraw
 * @author Lucija Valentić
 *
 */
public class JVDrawException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public JVDrawException() {
		super();
	}

	/**
	 * Constructor
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public JVDrawException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor
	 * @param message
	 * @param cause
	 */
	public JVDrawException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 * @param message
	 */
	public JVDrawException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param cause
	 */
	public JVDrawException(Throwable cause) {
		super(cause);
	}
	

}
