package hr.fer.zemris.java.hw17.jvdraw.drawingModel;

/**
 * Listener that is made for {@link DrawingModel}. It has
 * methods that are performed when geometrical objects are
 * added, removed or changed
 * @author Lucija Valentić
 *
 */
public interface DrawingModelListener {
	/**
	 * Method that is called when some geometrical object is added
	 * @param source
	 * @param index0
	 * @param index1
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);
	/**
	 * Method that is called when some geometrical object is removed
	 * @param source
	 * @param index0
	 * @param index1
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);
	/**
	 * Method that is called when some geometrical object is changed
	 * @param source
	 * @param index0
	 * @param index1
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
