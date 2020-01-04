package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Class implements interface {@link ListModel}. This model is capable of
 * computing prime numbers. It has method {@link #next()}, so when this method
 * is called, next prime is calculated and all listeners are notified, so
 * appropriate action can be performed, and window (our picture) can be updated
 * @author Lucija Valentić
 *
 * @param <T>
 */
public class PrimListModel<T> implements ListModel<T> {

	/**
	 * List of all primes already calculated
	 */
	private List<Integer> list;
	/**
	 * List of {@link ListDataListener}
	 */
	private List<ListDataListener> listeners;
	
	/**
	 * Prime number that was last computed. In the beginning it is
	 * equal to 1
	 */
	private int prim;
	/**
	 * Constructor
	 */
	public PrimListModel() {
		super();
		list = new ArrayList<Integer>();
		listeners = new ArrayList<ListDataListener>();
		this.prim = 1;
	}
	/**
	 * Method calculates next prime number, and notifies
	 * all listeners
	 */
	public void next() {
		
		int number = prim + 1;
		
		for(int i = number; !isPrim(number); i++) {
			number = i;
		}
		
		prim = number;

		list.add( Integer.valueOf(prim));
		
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, list.size(), list.size());
		for(ListDataListener l : listeners) {
			l.intervalAdded(event);
		}
		
	}
	
	/**
	 * Checks if given number <code>x</code> is
	 * prime. Returns <code>true</code> if it is,
	 * <code>false</code> otherwise
	 * @param x
	 * @return boolean
	 */
	private boolean isPrim(int x) {
		
		if(x == 2) {
			return true;
		}
		
		for(int i = 2; i < x; i++){
			
			if(x % i == 0) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @return
	 */
	@Override
	public int getSize() {
		return list.size();
	}

	/**
	 * {@inheritDoc}
	 * @param index
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T getElementAt(int index) {
		return (T)list.get(index);
	}

	/**
	 * {@inheritDoc}
	 * @param l
	 */
	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
		
	}

	/**
	 * {@inheritDoc}
	 * @param l
	 */
	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
		
	}
	
	/**
	 * Returns this.list
	 * @return List<Integer>
	 */
	public List<Integer> getList() {
		return list;
	}
	
	/**
	 * Returns this.prim
	 * @return int next prime
	 */
	public int getPrim(){
		return this.prim;
	}
	
}
