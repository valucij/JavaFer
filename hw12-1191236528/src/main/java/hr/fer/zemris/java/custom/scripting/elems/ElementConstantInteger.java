package hr.fer.zemris.java.custom.scripting.elems;

/**
 * <code>ElementConstantInteger</code> inherits class <code>Element</code>
 * and represents one object that is a integer. Has one read-only variable;
 * 
 * @author Lucija Valentić
 *
 */
public class ElementConstantInteger extends Element {
	
	private int value;
	/**
	 * Constructor	
	 * @param integer integer which is put in this.value
	 */	
	public ElementConstantInteger(int integer) {
		value = integer;
	}
	/**
	 * Method returns contents of ElementConstantInteger as string
	 * @return string which represents this.value in string form	
	 */	
	@Override
	public String asText() {
		
		return String.valueOf(value);
	}
	/**
	 * Returns this.value
	 * @return int
	 */
	public int returnValue() {
		return value;
	}
}
