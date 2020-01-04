package hr.fer.zemris.java.custom.scripting.elems;

/**
 * <code>ElementVariabler</code> inherits class <code>Element</code>
 * and represents one object that is a variable. Has one read-only variable;
 * 
 * @author Lucija Valentić
 *
 */

public class ElementVariable extends Element {
	private String name;
	
	/**
	 * Constructor	
	 * @param string string which is put in this.value
	 */
	public ElementVariable(String string) {
		name = string;
	}
	/**
	 * Method returns contents of ElementVariable as string
	 * @return string which represents this.value in string form	
	 */	
	@Override
	public String asText() {
		return name;
	}
	

}
