package hr.fer.zemris.java.hw05.db;

import java.util.*;

/**
 * Class that makes list of conditional expressions
 * from given string, that user has given. It uses 
 * QueryLexer. This class makes sure that query is in the 
 * right form. Right form is:
 * lastName/firstName/jmbag + operator + stringLiteral
 * After that, there can be word 'and', but after
 * that has to come again lastName/.... . If that
 * comes without word 'and' (in any form), exception is thrown.
 * 
 * 
 * @author Lucija Valentić
 *
 */
public class QueryParser {

	/**
	 * Represents list of conditional expressions
	 */
	private List<ConditionalExpression> query;
	
	/**
	 * Constructor, also calls method for parsing
	 * given string
	 * @param text
	 */
	public QueryParser(String text) {
		LinkedList<ConditionalExpression> newQuery = new LinkedList<ConditionalExpression>();
		query = newQuery;
		parse(text);	
	}
	/**
	 * Method that parses given string in a way that is
	 * describes in the javadoc of a class
	 * @param text
	 */
	private void parse(String text) {
		QueryToken token = null;
		QueryLexer lexer = new QueryLexer(text);
		token = lexer.nextToken();
		
		while(token == null || !token.getType().equals(QueryTokenType.EOF)) {
			
				IfieldValueGetter getter = makeGetter(lexer);
				token = lexer.nextToken();
				IComparisonOperator operator = makeOperator(lexer);
				token = lexer.nextToken();
				String string = makeStringLiteral(lexer);
				token = lexer.nextToken();
				
				query.add(new ConditionalExpression(getter, string, operator));
			
				//ako je sljedeći token tipa AND, onda poslije njega mora sljediti neki atribut;
				//inače, ako taj sljeći token nije tipa EOF, nije kraj stringa i query je kriv
				if(token.getType().equals(QueryTokenType.AND)) {
					token = lexer.nextToken();
					if( !token.getType().equals(QueryTokenType.JMBAG) && !token.getType().equals(QueryTokenType.FIRST_NAME) && !token.getType().equals(QueryTokenType.LAST_NAME)) {
						throw new QueryException();
					}
				}else if(!token.getType().equals(QueryTokenType.EOF)) {
					throw new QueryException();
				}
		}
		
	}
	
	/**
	 * Makes some kind of getter, type IfieldValueGetter, based 
	 * on type of token. If token type is not JMBAG, LAST_NAME or
	 * FIRST_NAME, exception is being thrown
	 * 
	 * @param lexer
	 * @return IfieldValueGetter
	 * @throws QueryException() if token type is not
	 * 		JMBAG, LAST_NAME or FIRST_NAME
	 */
	private IfieldValueGetter makeGetter(QueryLexer lexer) {
		
		QueryToken token = lexer.getToken();
		
		
		if(token.getType().equals(QueryTokenType.JMBAG)) {
			return FieldValueGetters.JMBAG;
		}else if(token.getType().equals(QueryTokenType.LAST_NAME)) {
			return FieldValueGetters.LAST_NAME;
		}else if(token.getType().equals(QueryTokenType.FIRST_NAME)) {
			return FieldValueGetters.FIRST_NAME;
		}else {
			throw new QueryException();
		}
	}
	
	/**
	 * Makes some kind of operator, type IComparisonOperator,
	 * based on type of token. If token value is not some
	 * valid operator, exception is thrown.
	 * 
	 * @param lexer
	 * @return IComparisonOperator
	 * @throws QueryException() if token value is not
	 * 			valid operator
	 */
	private IComparisonOperator makeOperator(QueryLexer lexer) {
		
		QueryToken token = lexer.getToken();
		String string = token.getValue();
		
		if(string.equals("=")) {
			return ComparisonOperators.EQUALS;
		}else if(string.equals("<")) {
			return ComparisonOperators.LESS;
		}else if(string.equals("<=")) {
			return ComparisonOperators.LESS_OR_EQUALS;
		}else if(string.equals(">")) {
			return ComparisonOperators.GREATER;
		}else if(string.equals(">=")) {
			return ComparisonOperators.GREATER_OR_EQUALS;
		}else if(string.equals("!=")) {
			return ComparisonOperators.NOT_EQUALS;
		}else if(string.equals("LIKE")) {
			return ComparisonOperators.LIKE;
		}else {
			throw new QueryException();
		}
		
	}
	
	/**
	 * Makes some kind of string literal, based on given token.
	 * String is made from token value. If token type is
	 * not STRING, exception is thrown.
	 * @param lexer
	 * @return String
	 * @throws QueryException() if token type is not STRING
	 */
	private String makeStringLiteral(QueryLexer lexer) {
		
		QueryToken token = lexer.getToken();
		
		if(!token.getType().equals(QueryTokenType.STRING)) {
			throw new QueryException();
		}
		
		return token.getValue();
	}
		
	/**
	 * Check if given query is a direct one (just one condition,
	 * it has to be in a form jmbag= "xxx"). If the query is 
	 * a direct one, returns <code>true</code>, <code>false</code>
	 * otherwise.
	 * @return <code>true</code> if query is direct, <code>false</code>
	 * 		otherwise
	 */
	public boolean isDirectQuery() {
		return query.size() == 1 
				&& query.get(0).getComparisonOperator().equals(ComparisonOperators.EQUALS)
				&& query.get(0).getFieldGetter().equals(FieldValueGetters.JMBAG);
	}
	
	/**
	 *If query is direct, returns string literal from
	 * saved conditional expression; if it's not, 
	 * throws exception
	 * @return string literal from saved conditional expression
	 * @throws IllegalStateException() if query is not direct
	 */
	public String getQueriedJMBAG() {
	
		if(query.size() != 1) {
			throw new IllegalStateException();
		}
		return query.get(0).getStringLiteral();
	}
	
	/**
	 * Returns list of conditional expressions
	 * @return this.query
	 */
	public List<ConditionalExpression> getQuery(){
		return query;
	}
}
