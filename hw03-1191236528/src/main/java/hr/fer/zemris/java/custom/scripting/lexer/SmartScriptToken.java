package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.lexer.SmartTokenType;

/**
 * This class represents tokens that are used with SmartScriptLexer. 
 * This class has two only-read variables, and two public methods for 
 * handling them.
 * Also, toString method is added so printing can be more nice.
 * 
 * @author Lucija Valentić
 *
 */

public class SmartScriptToken {
	private SmartTokenType type;
	private String value;
/**
 * Constructor, sets private variable to given values.
 * 	
 * @param type this.type is set to it
 * @param value this.value is set to it
 */
	public SmartScriptToken(SmartTokenType type, Object value) {
		this.type = type;
		
		if(value == null) {
			this.value = "";
		}else {
			this.value = value.toString();
		}
		
	}
/**
 * This method returns this.value;
 * 	
 * @return this.value;
 */
	public String getValue() { return this.value;}
/**
 * This method returns this.type
 * 	
 * @return this.type
 */
	public SmartTokenType getType() { return this.type;}
/**
 * Method for nicer priting tokens.	
 */
	@Override
	public String toString() {
		
		String newstring = "(" + getType() + ", " + getValue() + ")";
		return newstring;
		
		
	}
}
