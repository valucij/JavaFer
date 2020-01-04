package hr.fer.zemris.java.hw17.jvdraw.objects;

/**
 * Listener that is made for geometrical objects, and
 * when something is changed with them 
 * @author Lucija Valentić
 *
 */
public interface GeometricalObjectListener {
	/**
	 * Method that performs some action when given object is
	 * changed
	 * @param o
	 */
	public void geometricalObjectChanged(GeometricalObject o);
}
