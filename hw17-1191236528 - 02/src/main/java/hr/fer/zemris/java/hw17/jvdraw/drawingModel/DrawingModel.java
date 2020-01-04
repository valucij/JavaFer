package hr.fer.zemris.java.hw17.jvdraw.drawingModel;

import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;

/**
 * Drawing model, class that keeps list of all
 * geometrical objects made/drawn 
 * @author Lucija Valentić	
 *
 */
public interface DrawingModel {
	/**
	 * Returns how many geometrical objects are there
	 * @return int
	 */
	public int getSize();
	/**
	 * Returns geometrical object on given index
	 * @param index
	 * @return GeometricalObject
	 */
	public GeometricalObject getObject(int index);
	/**
	 * Adds new geometrical object to list
	 * @param object
	 */
	public void add(GeometricalObject object);
	/**
	 * Removes geometrical object from list
	 * @param object
	 */
	public void remove(GeometricalObject object);
	/**
	 * Changes order of geometrical objects from list
	 * @param object
	 * @param offset
	 */
	public void changeOrder(GeometricalObject object, int offset);
	/**
	 * Returns index of geometrical object
	 * @param object
	 * @return int
	 */
	public int indexOf(GeometricalObject object);
	/**
	 * Removes all geometrical objects from list
	 */
	public void clear();
	/**
	 * Changes modifiedFlag into false
	 */
	public void clearModifiedFlag();
	/**
	 * Returns this.modifiedFlag
	 * @return boolean
	 */
	public boolean isModified();
	/**
	 * Adds {@link DrawingModelListener}
	 * @param l
	 */
	public void addDrawingModelListener(DrawingModelListener l);
	/**
	 * Removes {@link DrawingModelListener}
	 * @param l
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}
