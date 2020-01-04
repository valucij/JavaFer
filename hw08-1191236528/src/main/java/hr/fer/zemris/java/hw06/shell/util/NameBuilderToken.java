package hr.fer.zemris.java.hw06.shell.util;

/**
 * Class represents token, used in {@link NameBuilderLexer}
 * and {@link NameBuilderParser}
 * @author Lucija Valentić
 *
 */
public class NameBuilderToken {

	/**
	 * Represents token type
	 */
	private NameBuilderTokenType type;
	/**
	 * Represents value of a token
	 */
	private String value;
	
	/**
	 * Constructor
	 * @param type
	 * @param value
	 */
	public NameBuilderToken(NameBuilderTokenType type, String value) {
		this.type = type;
		this.value = value;
	}
	/**
	 * Returns type of a token
	 * @return this.type
	 */
	public NameBuilderTokenType getType() {
		return type;
	}
	
	/**
	 * Returns value of a token
	 * @return this.value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "(" + type.toString() + ", " + value + ")";
	}
}
