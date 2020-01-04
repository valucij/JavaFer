package hr.fer.zemris.java.custom.scripting.lexer;
/**
 * Enumeration for SmartLexerStates
 * @author Lucija Valentić
 *
 */
public enum SmartLexerState {
	/**
	 * Lexer state in which every string od characters is text
	 */
	TEXT,
	/**
	 * Lexer state in which every string of characters can be 
	 * different thing, like number, variable, function, etc.
	 */
	TAG	
}
