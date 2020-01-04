package hr.fer.zemris.java.custom.collections;

/**
 * Interface <code>List</code> inherits interface <code>Collection</code> 
 * and adds new methods for handling collections.
 * 
 * @author Lucija Valentić
 *
 */
public interface List extends Collection{
	
	/**
	 * Abstract method, usually returns element from
	 * given position. If given position is invalid, 
	 * then it throws exception.
	 * 
	 */
	Object get(int index);
	/**
	 * Abstract method, usually inserts element in given
	 * position. If object is null, or position is invalid
	 * it throws exception.
	 * 
	 */
	void insert(Object value, int position);
	/**
	 * Abstract method, usually it returns index of 
	 * given object, if that object exists in collection.
	 * If it doesn't, it returns -1.
	 * 
	 */
	int indexOf(Object value);
	/**
	 * Abstract method, usually removes object from given position.
	 * If position is invalid, it throws exception.
	 * 
	 */
	void remove(int index);
}
