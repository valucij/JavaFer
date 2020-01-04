package hr.fer.zemris.java.custom.scripting.exec;

/**
 * This is new exception that can be used when working with class
 * {@link ObjectMultistack}, and when something goes wrong
 * @author Lucija Valentić
 *
 */
public class ObjectStackException extends RuntimeException {

	/**
	 * Constructor
	 */
	public ObjectStackException() {
		super();
	}
	
	/**
	 * Constructor
	 * @param message
	 */
	public ObjectStackException(String message) {
		super(message);
	}
	
	private static final long serialVersionUID = 1L;

}
