package hr.fer.zemris.java.hw05.db;

/**
 * Class that represents lexer that extracts
 * words from given string query. Since we know
 * how this query must look like, this lexer
 * is quite simple. When we encounter certain
 * letter, we can already know what kind of
 * word that is. Of course, so we can be sure
 * there is no mistake, we also check one more time
 * if that word is valid or not. For example,
 * if lexer encounters letter 'l', it knows that
 * next word must be lastName. Of course, 
 * we check one more time if that word is indeed 
 * 'lastName'; if it's not, exception is thrown.
 *  
 * @author lucijaval
 *
 */
public class QueryLexer {

	/**
	 * Keeps given string(from constructor)
	 *  in array of chars
	 */
	private char data[];
	/**
	 * Represents token just returned
	 */
	private QueryToken token;
	/**
	 * Position of the next character we must evaluate
	 */
	private int currentIndex;
	
	/**
	 * Constructor
	 * 
	 * @param query
	 */
	public QueryLexer(String query) {
		data = query.toCharArray();
		token = null;
		currentIndex = 0;
	}
	
	/**
	 * Method goes through array of chars that was
	 * made from given string from constructor,
	 * and based on characters/words, it makes
	 * appropriate token with appropriate 
	 * token type and value
	 * @return token
	 */
	public QueryToken nextToken() {
		
		nextNotBlank();
		
		if(currentIndex == data.length) {
			return new QueryToken(QueryTokenType.EOF,"");
		}
		
		if(token != null && token.getType() == QueryTokenType.EOF) {
			throw new QueryException();
		}
		
		if(data[currentIndex] == 'j') {
			token = createJmbagToken();
			return token;
		}else if(data[currentIndex] == 'l') {
			token = createLastNameToken();
			return token;
		}else if(data[currentIndex] == 'f') {
			token = createFirstNameToken();
			return token;
		}else if(Character.toUpperCase(data[currentIndex]) == 'A') {
			token = createAndToken();
			return token;
		}else if(data[currentIndex] == '"') {
			token = createStringToken();
			return token;
		}else if(!Character.isLetter(data[currentIndex]) && !Character.isDigit(data[currentIndex])) {
			token = createOperatorToken();
			return token;
		}else if(data[currentIndex] == 'L') {
			token = createLikeToken();
			return token;
		}else {
			throw new QueryException();
		}
	}
	
	/**
	 * Creates jmbag token
	 * @return jmbag token
	 * @throws QueryException() if attribute jmbag
	 * 		was the last one in the string, or
	 * 		it wasn't written jmbag in this exact way
	 */
	private QueryToken createJmbagToken() {
				
		try {
			
			if(!String.copyValueOf(data, currentIndex, 5).equals("jmbag")) {
				
				throw new QueryException();
			}
			
		}catch(IndexOutOfBoundsException ex){
			throw new QueryException();
		}
		
		currentIndex +=5;
		return new QueryToken(QueryTokenType.JMBAG,"");
	}
	/**
	 * Creates lastName token
	 * @return lastName token
	 * @throws QueryException() if attribute lastName
	 * 		was the last one in the string, or
	 * 		it wasn't written lasttName in this exact way
	 */
	private QueryToken createLastNameToken() {
		
		try {
			
			if(!String.copyValueOf(data, currentIndex, 8).equals("lastName")) {
				throw new QueryException();
			}
			
		}catch(IndexOutOfBoundsException ex){
			throw new QueryException();
		}
		
		currentIndex += 8;
		return new QueryToken(QueryTokenType.LAST_NAME,"");
	}
	/**
	 * Creates firstName token
	 * @return firstName token
	 * @throws QueryException() if attribute firstName
	 * 		was the last one in the string, or
	 * 		it wasn't written firstName in this exact way
	 */
	private QueryToken createFirstNameToken() {
		
		try {
			
			if(!String.copyValueOf(data, currentIndex, 9).equals("firstName")) {
				throw new QueryException();
			}
			
		}catch(IndexOutOfBoundsException ex){
			throw new QueryException();
		}
		
		
		
		currentIndex += 9;
		return new QueryToken(QueryTokenType.FIRST_NAME,"");
	}
	
	/**
	 * Creates AND token
	 * @return AND token
	 * @throws QueryException() if attribute AND
	 * 		was the last one in the string, or
	 * 		it wasn't written AND (in any way), 
	 * 		but some other word starting with letter 'A'
	 */
	private QueryToken createAndToken() {
		
		try {
			
			if(!String.copyValueOf(data, currentIndex, 3).toUpperCase().equals("AND")) {
				throw new QueryException();
			}
			
		}catch(IndexOutOfBoundsException ex){
			throw new QueryException();
		}
		
		currentIndex += 3;
		return new QueryToken(QueryTokenType.AND,"");
	}
	
	/**
	 * Creates Operator token
	 * @return operator token
	 * @throws QueryException() if operator wasn't 
	 * 		valid operator
	 */
	private QueryToken createOperatorToken() {
		
		String string = "";
		while(!Character.isLetter(data[currentIndex]) && !Character.isDigit(data[currentIndex]) && !Character.isWhitespace(data[currentIndex]) && data[currentIndex] != '"') {
			string = string + String.copyValueOf(data, currentIndex, 1);
			currentIndex++;
		}
		
		if( !string.equals("<") && !string.equals("<=") && !string.equals(">") && !string.equals(">=") && !string.equals("=") && !string.equals("!=")) {
			throw new QueryException();
		}
		return new QueryToken(QueryTokenType.OPERATOR, string);
	}
	
	/**
	 * Creates string literal token (we shortened it 
	 * with string)
	 * @return string literal token
	 */
	private QueryToken createStringToken() {
		String string = "";
		currentIndex++;
		
		while(data[currentIndex] != '"') {
				
			string = string + data[currentIndex];
			currentIndex++;
		}
		
		currentIndex++;
		return new QueryToken(QueryTokenType.STRING,string);
	}
	
	/**
	 * Creates LIKE token
	 * @return LIKE token
	 * @throws QueryException() if operator LIKE
	 * 		was the last one in the string, or
	 * 		it wasn't written LIKE in this exact way
	 */
	private QueryToken createLikeToken() {
		
		try {
			
			if(!String.copyValueOf(data, currentIndex, 4).equals("LIKE")) {
				throw new QueryException();
			}
			
		}catch(IndexOutOfBoundsException ex){
			throw new QueryException();
		}
		
		currentIndex += 4;
		return new QueryToken(QueryTokenType.OPERATOR, "LIKE");
	}
	
	/**
	 * Moves this.currentIndex to a position
	 * of a next character that is not whitespace
	 */
	private void nextNotBlank() {
		
		while(currentIndex < data.length && Character.isWhitespace(data[currentIndex])) {
			currentIndex++;
		}
	}
	
	/**
	 * Return token from this object
	 * @return this.token
	 */
	public QueryToken getToken() {
		return token;
	}
}
