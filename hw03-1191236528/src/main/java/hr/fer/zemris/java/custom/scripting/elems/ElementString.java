package hr.fer.zemris.java.custom.scripting.elems;

/**
 * <code>ElementString</code> inherits class <code>Element</code>
 * and represents one object that is a string. Has one read-only variable;
 * 
 * @author Lucija Valentić
 *
 */
public class ElementString extends Element {
	
	private String value;
	
	/**
	 * Constructor	
	 * @param string string which is put in this.value
	 */	
	public ElementString(String string) {
		value = string;
	}
	/**
	 * Method returns contents of ElementString as string
	 * @return string which represents this.value in string form	
	 */
	@Override
	public String asText() {
		return value;
	}
}
