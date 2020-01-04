package hr.fer.zemris.java.custom.collections;

/**
 * Interface <code>List</code> inherits interface <code>Collection</code> 
 * and adds new methods for handling collections.
 * 
 * @author Lucija Valentić
 *
 */
public interface List<T> extends Collection<T>{
	
	/**
	 * Abstract method, usually returns element on position
	 * index.
	 * 
	 */
	T get(int index);
	/**
	 * Abstract method, usually inserts value, on
	 * given position.
	 * 
	 */
	void insert(T value, int position);
	/**
	 * Abstract method, usually returns position of a object
	 * from collection, or -1 otherwise
	 * 
	 */
	int indexOf(Object value);
	/**
	 * Abstract method, it removes object from the collection
	 * that was on position index
	 * 
	 */
	void remove(int index);
}
