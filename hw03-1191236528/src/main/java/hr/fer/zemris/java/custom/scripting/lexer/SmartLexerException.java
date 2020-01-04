package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * New exception, SmartLexerException. It is called when using SmartScriptLexer,
 *  when something goes wrong
 * @author Lucija Valentić
 *
 */

public class SmartLexerException extends RuntimeException {

	/**
	 * Constructor
	 */
		public SmartLexerException() { super();}
	/**
	 * Constructor that writes message.	
	 * @param message
	 */
		public SmartLexerException(String message) {super(message);}
		
		private static final long serialVersionUID = 1L;
}
