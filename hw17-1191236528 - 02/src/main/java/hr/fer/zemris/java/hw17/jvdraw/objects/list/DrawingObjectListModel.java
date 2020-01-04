package hr.fer.zemris.java.hw17.jvdraw.objects.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import hr.fer.zemris.java.hw17.jvdraw.drawingModel.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawingModel.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;

/**
 * Class extends class {@link AbstractListModel}.
 * It is for showing list of geometrical objects
 * @author Lucija Valentić
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Drawing model
	 */
	private DrawingModel model;
	
	/**
	 * List of all listeners
	 */
	private List<ListDataListener> listeners;
	
	/**
	 * Constructor
	 * @param model
	 */
	public DrawingObjectListModel(DrawingModel model) {
		model.addDrawingModelListener(this);
		this.model = model;
		listeners = new ArrayList<ListDataListener>();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSize() {
		return model.getSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		
		for(ListDataListener l : listeners) {
			l.intervalAdded(new ListDataEvent(source, 0, index0, index1));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		
		if(index0 < 0) {
			index0 = 0;
		}
		
		if(index1 < 0) {
			index1 = 0;
		}
		
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index0, index1);
		for(ListDataListener l : listeners) {
			l.intervalAdded(event);
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		for(ListDataListener l : listeners) {
			l.contentsChanged(new ListDataEvent(source, 0, index0, index1));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

}
