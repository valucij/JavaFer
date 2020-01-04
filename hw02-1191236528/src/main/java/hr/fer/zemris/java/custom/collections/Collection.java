package hr.fer.zemris.java.custom.collections;
/**
 * 
 * Class <code>Collection</code> represents some general collection od elements.
 * Other classes can inherit this class.
 * 
 * @author Lucija Valentić
 *
 */
public class Collection {
	/**
	 * Deafult constructor. Does nothing.
	 */
	protected Collection() { }
	/**
	 * Method <code>isEmpty</code> checks if the collection is empty or not.
	 * 	
	 * @return true if collection is empty, false if collection is not empty
	 */
	public boolean isEmpty() {
		if(this.size() == 0) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * Method implemented here does nothing. Usually, method <code>size</code>
	 * returns size of collection, number of elements in collection.	
	 * 
	 * @return 0 always
	 */
	public int size() {
		return 0;
	}
	/**
	 * Method implemented here does nothing. Usually, method <code>add</code>
	 * adds passed object in collection. 
	 * 	
	 * @param value argument that should be put in collection
	 */
	public void add(Object value) {
		return;
	}
	/**
	 * Method implemented here does nothing. Usually, method <code>contains</code>
	 * checks if collection contains value or not. Returns true if collection 
	 * contains value, false in other case.
	 * 	
	 * @param value object that needs to be checked if it is in collection or not
	 * @return false always
	 */
	public boolean contains(Object value) {
		return false;
	}
	/**
	 * Method implemented here does nothing. Usually, method <code>remove</code>
	 * removes object with value <code>value</code> if collection contains it,
	 * and return true.
	 * 	
	 * @param value that needs to be removed from collection if collection contains it
	 * @return false always
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Method implemented here throws UnsupportedOperationException. Usually, 
	 * method <code>toArray</code> makes new array and fills it with elements
	 * from this collection. 
	 * 
	 * @throw UnsupportedOperationException()
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	/**
	 * Method implemented here does nothing. Usually, method <code>forEach</code>
	 * calls <code>processor.process</code> for each element of this collection. 
	 * 
	 * @param processor 
	 */
	public void forEach(Processor processor) { }
	/**
	 * Method implemented here defines local processor class. Usually,
	 * method <code>addAll</code> adds into current collection elements of
	 *  given collection. That collection remains unchanged.
	 *  
	 * @param other collection whose elements usually should be added in this
	 * 		 collection
	 */
	public void addAll(Collection other) {
		
			
	/**
	 * Local class <code>Processor</code>. Adds elements in this collection.		
	 * 	
	 * @author Lucija Valentić
	 *
	 */
		class LocalProcessor extends Processor {
		/**
		 * Method <code>process</code> adds elements in this collection and
		 * on other collection calls method <code>forEach</code>.		
		 * 			
		 * @param value object that needs to be added in collection
		 */
			public void process(Object value) {	
				
				add(value);
			}
			
		}
		
		LocalProcessor local = new LocalProcessor();
		
		other.forEach(local);
	}
	/**
	 * 	Method implemented here does nothing. Usually, method <code>clear</code>
	 * removes all elements from collection.
	 * 	
	 */
	public void clear() { }
}
