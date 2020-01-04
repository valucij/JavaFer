package hr.fer.zemris.java.custom.collections;
/**
 * Class <code>EmptyStackException</code> represents new exception. We use it when we are handling stack, and illegal action wants to be performed,
 * but the stack is empty.
 * 
 * @author Lucija Valentić
 *
 */
public class EmptyStackException extends RuntimeException{
	/**
	 * Default constructor.	
	 */
	public EmptyStackException() { super();}
	/**
	 * Constructor with a message. When we want to signal when exception is made.	
	 * @param message string that signals additional information what happend
	 */
	public EmptyStackException(String message) {super(message);}
	
	private static final long serialVersionUID = 1L;
	
	
}
