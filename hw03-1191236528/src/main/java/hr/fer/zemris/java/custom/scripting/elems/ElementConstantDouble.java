package hr.fer.zemris.java.custom.scripting.elems;

/**
 * <code>ElementConstantDouble</code> inherits class <code>Element</code>
 * and represents one object that is a double. Has one read-only variable;
 * 
 * @author Lucija Valentić
 *
 */
public class ElementConstantDouble extends Element {
	
	private double value;
	/**
	 * Constructor	
	 * @param doubleNumber integer which is put in this.value
	 */
	public ElementConstantDouble(double doubleNumber) {
		value = doubleNumber;
	}
	/**
	 * Method returns contents of ElementConstantDouble as string
	 * @return string which represents this.value in string form	
	 */
	@Override
	public String asText() {
		return String.valueOf(value);
	}
}
