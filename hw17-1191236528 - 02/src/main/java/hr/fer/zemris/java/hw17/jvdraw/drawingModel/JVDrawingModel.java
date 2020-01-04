package hr.fer.zemris.java.hw17.jvdraw.drawingModel;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectListener;
import hr.fer.zemris.java.hw17.jvdraw.util.Util;
/**
 * Concrete implementation of interface {@link DrawingModel}
 * 
 * @author Lucija Valentić
 *
 */
public class JVDrawingModel implements DrawingModel, GeometricalObjectListener{
	
	/**
	 * List of geometrical objects
	 */
	private List<GeometricalObject> objects = new ArrayList<GeometricalObject>();
	/**
	 * List of listeners
	 */
	private List<DrawingModelListener> listeners = new ArrayList<DrawingModelListener>();
	/**
	 * Flag that shows whether something has changed
	 */
	private boolean modifiedFlag = false;

	/**
	 * Constructor
	 */
	public JVDrawingModel() {
		super();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSize() {
		return objects.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GeometricalObject getObject(int index) {
		if(objects.size() == 0) {
			return null;
		}
		return objects.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(GeometricalObject object) {
		
		object.addGeometricalObjectListener(this);
		objects.add(object);
		modifiedFlag = true;
		
		for(DrawingModelListener l : listeners) {
			l.objectsAdded(this, objects.indexOf(object), objects.indexOf(object));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(GeometricalObject object) {
		
		int index = objects.indexOf(object);
		objects.remove(object);
		modifiedFlag = true;
		
		for(DrawingModelListener l : listeners) {
			l.objectsRemoved(this, index, index);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		
		remove(object);
		objects.add(offset, object);
		
		for(DrawingModelListener l : listeners) {
			l.objectsChanged(this, offset, offset);
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int indexOf(GeometricalObject object) {
		return objects.indexOf(object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		
		int last = objects.size() - 1;
		objects.clear();
		modifiedFlag = true;
		
		for(DrawingModelListener l : listeners) {
			l.objectsRemoved(this, 0, last);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearModifiedFlag() {
		modifiedFlag = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isModified() {
		return modifiedFlag;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		
		int i = objects.indexOf(o);
		modifiedFlag = true;
		
		for(DrawingModelListener l : listeners) {
			l.objectsChanged(this, i, i);
		}
		
		Util.canvasRepaint();
	}
	/**
	 * Returns list of geometrical objects
	 * @return List<Geometrical objects>
	 */
	public List<GeometricalObject> getObjects(){
		return objects;
	}

}
