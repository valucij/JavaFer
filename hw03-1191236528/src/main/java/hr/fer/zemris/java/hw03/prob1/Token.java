package hr.fer.zemris.java.hw03.prob1;
/**
 * Class <code>Token</code> represents tokens.
 *
 * @author Lucija Valentić
 *
 */
public class Token {

	private TokenType type;
	private Object value;
/**
 * Constructor 
 * 	
 * @param type argument TokenType, this.type is set to it
 * @param value Object, this.value is set to it
 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
/**
 * Gets this.value	
 * @return this.value
 */
	public Object getValue() { return this.value;}
/**
 * Gets this.type	
 * @return this.type
 */
	public TokenType getType() { return this.type;}
/**
 * Method <code>toString</code> overrides <code>String toString()</code> so tokens can be printed like it is showed in text of homework ->
 * (TokenType, Object value)
 * 
 * @return newstring String that shows how token should be printed, how it looks like.	
 */
	@Override
	public String toString() {
		
		String newstring = "(" + getType() + ", " + getValue() + ")";
		return newstring;
		
		
	}
}
