package hr.fer.zemris.java.hw05.db;

/**
 * Enum class that is used when creating
 * tokens as their type. Tokens can be of multiple
 * types. JMBAG -> represent a token that 
 * symbolizes word 'jmbag', so classes that
 * use this lexer can know that after this token, there
 * is some operator and string of numbers that represents
 * jmbag. LAST_NAME -> represent a token that 
 * symbolizes word 'lastName', so classes that
 * use lexer that creates token with this token type
 * can know that after this token, there
 * is some operator and string of letters that represents
 * last name. FIRST_NAME -> represent a token that 
 * symbolizes word 'firstName', so classes that
 * use lexer that creates token with this token type
 * can know that after this token, there
 * is some operator and string of letters that represents
 * first name. AND -> represent a token that 
 * symbolizes word 'and' (written in any form of
 * upper or lower class letters), so classes that
 * use lexer that creates token with this token type
 * can know that after this token, there
 * is something more after it. EOF -> represents a token
 * that is made by lexer when string that has been observed is at the end.
 * OPERATOR -> represent a token that 
 * symbolizes some kind operator, like: <, <=, >, >=, =, !=, LIKE;
 *  so classes that use lexer that creates token with this token type
 * can know that after this token, there
 * is some string of letters or numbers that represents
 * first name/last name/jmbag. STRING -> represent a token that 
 * symbolizes string of letters/numbers, so classes that
 * use lexer that creates token with this token type
 * can know that this token's value is some sort of 
 * string literal.
 * 
 * @author Lucija Valentić
 *
 */
public enum QueryTokenType {

	/**
	 * Represents jmbag token
	 */
	JMBAG,
	/**
	 * Represents last name token
	 */
	LAST_NAME,
	/**
	 * Represents first name token
	 */
	FIRST_NAME,
	/**
	 * Represents and token
	 */
	AND,
	/**
	 * Represents eof token
	 */
	EOF,
	/**
	 * Represent operator token
	 */
	OPERATOR,
	/**
	 * Represent string token
	 */
	STRING
}
