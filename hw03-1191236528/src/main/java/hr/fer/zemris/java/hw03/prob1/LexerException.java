package hr.fer.zemris.java.hw03.prob1;

/**
 * Class <code>LexerException</code> represents new exception. We use
 *  it when there is problem with lexer. It inherits 
 *  <code>RuntimeException()</code>
 * 
 * @author Lucija Valentić
 *
 */
public class LexerException extends RuntimeException {
/**
 * Constructor
 */
	public LexerException() { super();}
/**
 * Constructor that writes message.	
 * @param message
 */
	public LexerException(String message) {super(message);}
	
	private static final long serialVersionUID = 1L;
		
}
