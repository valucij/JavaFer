package hr.fer.zemris.java.custom.scripting.elems;

/**
 * <code>ElementOperator</code> inherits class <code>Element</code>
 * and represents one object that is a operator. Has one read-only variable;
 * 
 * @author Lucija Valentić
 *
 */

public class ElementOperator extends Element {

	private String symbol;
	
	/**
	 * Constructor	
	 * @param string string which is put in this.symbol
	 */	
	public ElementOperator(String string) {
		symbol = string;
	}
	/**
	 * Method returns contents of ElementSymbol as string
	 * @return string which represents this.value in string form	
	 */
	@Override
	public String asText() {
		return symbol;
	}
}
