package hr.fer.zemris.java.hw05.db;

/**
 * Class that represents token that are
 * used in QueryLexer and QueryParser
 * for validating some input string from user.
 * 
 * @author Lucija Valentić
 *
 */
public class QueryToken {

	/**
	 * Represents type of a token
	 */
	private QueryTokenType type;
	/**
	 * Represent value of a token
	 */
	private String value;
	
	/**
	 * Constructor
	 * 
	 * @param type
	 * @param value
	 */
	public QueryToken(QueryTokenType type, String value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Returns type of a token
	 * @return this.type
	 */
	public QueryTokenType getType() {
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
		
		return "(" + type +"," + value +")";
	}
}
