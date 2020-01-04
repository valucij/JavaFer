package hr.fer.zemris.java.custom.scripting.elems;

/**
 * <code>ElementFunction</code> inherits class <code>Element</code>
 * and represents one object that is a function. Has one read-only variable;
 * 
 * @author Lucija Valentić
 *
 */

public class ElementFunction extends Element {

	private String name;
	
	/**
	 * Constructor	
	 * @param string string which is put in this.name
	 */	
	public ElementFunction(String string) {
		name = string;
	}
	/**
	 * Method returns contents of ElementFunction as string
	 * @return string which represents this.value in string form	
	 */
	@Override
	public String asText() {
		return name;
	}
}
