package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeration for SmartTokenType; there are 9 different types of SmartTokens.
 * 
 * @author Lucija Valentić
 *
 */
public enum SmartTokenType {
	/**
	 * String of characters that make text
	 */
	TEXT,
	/**
	 * String of characters that make a tagname
	 */
	TAGNAME,
	/**
	 * Empty string, represents ending of a tag
	 */
	ENDTAG,
	/**
	 * String of characters that make variable
	 */
	VARIABLE,
	/**
	 * String of characters that make number
	 */
	NUMBER,
	/**
	 * String of characters that make string
	 */
	STRING,
	/**
	 * String of characters that represents ending of a observed string
	 */
	EOF,
	/**
	 * String of characters that make operator
	 */
	OPERATOR,
	/**
	 * String of characters that make function
	 */
	FUNCTION
}
