package hr.fer.zemris.java.hw07.observer1;

import java.util.*;

/**
 * Class that represents one storage for some integer.
 * This class also has internal list of observers, more specifically, 
 * list of objects that are notify when change of value happens, and then, they are
 * called to perform actions that are implemented with them. These objects
 * are instances of {@link IntegerStorageObserver}.
 * 
 * @author Lucija Valentić
 *
 */
public class IntegerStorage {

	/**
	 * Represents value of stored integer
	 */
	private int value;
	/**
	 * List of internal observers
	 */
	private List<IntegerStorageObserver> observers;
	/**
	 * Constructor
	 * @param initialValue
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}
	
	/**
	 * Adds new given observer into list of all observers.
	 * @param observer
	 */
	public void addObserver(IntegerStorageObserver observer) {
		
		if(observers == null) {
			observers = new ArrayList<IntegerStorageObserver>();
		}
		observers.add(observer);
	}
	
	/**
	 * Removes given observer from list of  all observers
	 * @param observer
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		
		observers.remove(observer);
	}
	
	/**
	 * Removes all observers from list of observers
	 */
	public void clearObservers() {
		observers.clear();
	}
	
	/**
	 * Return value of a stored integer
	 * @return this.value
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Changes this.value into given value. Then
	 * method goes through list of observers and calls them
	 * to perform their action. Also, if one of the observer is 
	 * instance of {@link DoubleValue} observer, and if counter of
	 * that observer has hit 0, then that observer is removed from the list.
	 * 
	 * @param value
	 */
	public void setValue(int value) {
		
		if(this.value != value) {
			this.value = value;
			
			if(observers != null) {
				
				List<IntegerStorageObserver> copyObservers = new ArrayList<>(observers);
				
				for(IntegerStorageObserver observer : copyObservers) {
					observer.valueChanged(this);
					
					if(observer instanceof DoubleValue && ((DoubleValue) observer).getCount() == 0) {
						removeObserver(observer);
					}
				}
				
				
				
			}
		}
	}
}
