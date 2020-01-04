package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.objects.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.objects.visitor.GeometricalObjectVisitor;

/**
 * Abstract class, it represents some geometrical objects. What
 * object is made, depends on classes that extends this class
 * @author Lucija Valentić
 *
 */
public abstract class GeometricalObject {
	/**
	 * List of listeners
	 */
	protected List<GeometricalObjectListener> listeners = new ArrayList<GeometricalObjectListener>();
	
	/**
	 * Accepts visitor and performs some action, depending what
	 * is implemented in visitors methods
	 * @param v
	 */
	public abstract void accept(GeometricalObjectVisitor v);
	/**
	 * Creates new editor for specific geometrical object
	 * @return
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();
	
	/**
	 * Adds {@link GeometricalObjectListener} into list
	 * @param l
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
	}
	/**
	 * Removes {@link GeometricalObjectListener} from list
	 * @param l
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}
	/**
	 * Method that returns geometrical object in string form, but
	 * in the way suited for saving into file
	 * @return String
	 */
	public abstract String saveToString();
	/**
	 * Moves object up for one pixel, if it can
	 */
	public abstract void moveUp();
	/**
	 * Moves object down for one pixel, if it can
	 * @param windowWidth 
	 */
	public abstract void moveDown(double windowWidth);
}
