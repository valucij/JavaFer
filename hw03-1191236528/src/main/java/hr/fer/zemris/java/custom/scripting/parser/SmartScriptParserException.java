package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Exception thrown while working with SmartScriptParser if something
 * goes wrong.
 * 
 * @author Lucija Valentić
 *
 */
public class SmartScriptParserException extends RuntimeException {
	/**
	 * Default constructor.	
	 */
		public SmartScriptParserException() { super();}
	/**
	 * Constructor with a message. When we want to signal when exception is made.	
	 * @param message string that signals additional information what happend
	 */
		public SmartScriptParserException(String message) {super(message);}
		
		private static final long serialVersionUID = 1L;
		
}
