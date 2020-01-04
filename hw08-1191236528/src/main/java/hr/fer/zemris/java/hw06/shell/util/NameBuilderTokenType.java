package hr.fer.zemris.java.hw06.shell.util;

/**
 * Represents {@link NameBuilderToken} types
 * @author Lucija Valentić
 *
 */
public enum NameBuilderTokenType {

	/**
	 * Text part
	 */
	TEXT,
	/**
	 * Group part, but with only one number
	 */
	GROUP1,
	/**
	 * Group part, but with two number and ',' in between
	 */
	GROUP2,
	/**
	 * Ending
	 */
	EOF
}
