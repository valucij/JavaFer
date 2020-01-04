package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;


/**
 * Class <code>ArrayIndexedCollection</code> represents resizable array-backed
 *  collection of objects. It implements class <code>List</code>. It keeps
 * track if something is changed in collection, if something is added or removed 
 * etc.		
 * 
 * @author Lucija Valentić
 *
 */
public class ArrayIndexedCollection implements List{
	/**
	 * How many elements there is in collection.
	 */
	private int size;
	/**
	 * Array with elements in collection
	 */
	private Object elements[];
	/**
	 * Int that keeps track if there has been some modification
	 * in collection
	 */
	private long modificationCount;
	/**
	 * Default capacity of collection.
	 */
	private static final int DEFAULT_CAPACITY = 16;
	
/**
 * Default constructor. Sets capacity to 16, size to 0.	
 */
	
	public ArrayIndexedCollection() {
		
		this.size = 0;
		this.elements = new Object[DEFAULT_CAPACITY];
	}
/**
 * Method <code>size</code> returns number of elements in collection.		
 */
	@Override
	public int size() {
		return this.size;
	}
/**
 * Constructor that sets capacity to <code>initialCapacity</code>, size to 0.
 * 		
 * @param initialCapacity, capacity should be set to it
 * @throws IllegalArgumentException() if initialCapacity is below 1
 */
	public ArrayIndexedCollection(int initialCapacity) {
		
		if(initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		
		this.size = 0;
		this.elements = new Object[initialCapacity];
	}
/**
 * Constructor that puts elements from collection <code>other</code> in this 
 * collection.	
 * 	
 * @param other collection from which we should take elements and put them
 * 		  in this collection
 * @throws NullPointerException if sent collection is empty
 */
	public ArrayIndexedCollection(Collection other){
			
		this(other.size());
		addAll(other);
		
	}
/**
 * Constructo that sets capacity of this collection to initialCapacity, 
 * and puts elements from collection <code>other</code>	in this collection.
 * Makes resizes if needded.
 * 	
 * @param initialCapacity sets capacity to this
 * @param other collection whose elements we want to put in this collection
 * @throws NullPointerException if other collection is empty,
 * 			 IllegalArgumentException if capacity is below 1
 */
	public ArrayIndexedCollection(int initialCapacity, Collection other)throws NullPointerException {
		
		this(initialCapacity);
		
		if(other == null) {
			throw new NullPointerException();
		}
		
		if(initialCapacity < other.size()) {
						
			this.elements = new Object[other.size()];
			size = other.size();
		}
		
		addAll(other); 
		
		
	}
	
	
/**
 * Method <code>rescaleArray</code> doubles the capacity of collection,
 *  so more elements can be put in.
 * 		
 * @param capacity doubles the capacity of collection by this number
 */
	private void rescaleArray(int capacity) {
		
		if(capacity < 0) {
			throw new IllegalArgumentException();
		}
				
		Object novi[] = new Object[capacity * 2];
		
		for(int i = 0, n = capacity; i < n; i++) {
			
			novi[i] = this.elements[i];
		}
		this.elements = novi;
		
		
	}
/**
 * Method <code>add</code> adds object <code>value</code> in collection. 
 * If collection is to small to hold it, it resize itself. It overrides 
 * method <code>add()</code> from class Collection.
 * modificationCount is increased.
 * 
 * @throws NullPointerException() if value is null				
 */
	@Override
	public void add(Object value) {
		
		if(value == null) {
			throw new NullPointerException();
		}
		
		if(this.size == elements.length) {
			this.rescaleArray(elements.length);
		}
		
		modificationCount++;
		this.elements[this.size] = value;
		this.size++;
		
	}
	
/**
 * Method <code>get</code> 	returns object that is stored at
 *  position <code>index</code>. Checks if index is valid. 
 * 		
 * @param index number on which we should find element in this collection
 * @return Object on position <code>index</code> if found, or null if collection
 * is emtpy
 * 
 * @throws 	IndexOutOfBoundsException() id given <code>index</code> is less
 * than 0, or more than last possible index in this collection	
 */
	public Object get(int index) {
	
		
		if(index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException();
		}
		
		if(this.size == 0) {
			return null;
		}
		
		return this.elements[index];
	}

/**
 * 	Method <code>clear</code> removes all elements from collection.
 * 	It overrides method <code>clear()</code> from class Collection.	
 * modificationCount is increased.
 */
	@Override
	public void clear() {
		
		for(int i = 0, n = this.size; i < n; i++) {
			this.elements[i] = null;
		}
		modificationCount++;
		this.size = 0;
	}
/**
 * Method <code>insert</code> inserts element <code>value</code>
 *  i position <code>position</code> in this collection. If <code>value</code> is
 * inserted, then modificationCount is increased.
 * 			
 * @param value elements that needs to be placed in this collection
 * @param position place in collection in which should new element be placed
 * @throws IndexOutOfBoundsException() if sent position is less than 0,
 * 		  more than size of this collection
 */
	public void insert(Object value, int position) {
		
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		
		if(value == null) {
			throw new NullPointerException("Can't insert null in collection.");
		}
		int flag = 0;
		modificationCount++;
		if(this.size == elements.length) {
			this.rescaleArray(elements.length);
		}
		
		for(int i = this.size; i > 0; i--) {
			
			if(position == i) {
				this.elements[i] = value;
				flag = 1;
				break;
			}else {
				this.elements[i] = this.elements[i - 1];
			}
		}
		
		if(flag == 0) {
			this.elements[0] = value;
		}
		
		this.size++;
	}
/**
 * Method <code>indexOf</code> returns position of element <code>value</code>
 *  in collection. If element <code>value</code> not found, or sent Object
 * is null, returns -1.
 * 				
 * @param value element that needs to be found in this collection
 * @return -1 if element not found in collection, or if sent 
 * 			Object <code>value</code> is null; else returns position of 
 * 			that element in this collection	
 */
	public int indexOf(Object value) {
		
		if(value == null) {
			return -1;
		}
		
		for(int i = 0, n = this.size; i < n; i++) {
			
			if(value.equals(this.elements[i])) {
				return i;
			}
		}
		return -1;
	}
/**
 * Method <code>remove</code> removes elements with position 
 * <code>index</code> from collection. If element is removed, 
 * then modificationCount is increased.
 * 			
 * @param index position on which should element in this collection be removed
 * @throws IndexOutOfBoundsException() if sent index is less than 0, or more
 * 		  than possible index in collection
 */
	public void remove(int index) {
		
		if(index < 0 || index > this.size -1) {
			throw new IndexOutOfBoundsException();
		}
		
		modificationCount++;
		for(int i = index, n = this.size - 1; i < n; i++) {
			
			this.elements[i] = this.elements[i + 1];
		}
		
		this.elements[this.size - 1] = null;
		
		this.size--;
	}
	
/**
 * Method <code>boolean remove(Object value)</code> is removed from 
 * collection if that kind of element exits in collection. 
 * Return true if it is removed, false otherwise.
 * This method uses other method <code> void remove(int index)</code> 
 * for removing elements from collection.
 * 
 * @param value object that needs to be removed if it is in collection
 * @return <code>true</code> if element is remove, <code>false</code> otherwise
 */
	public boolean remove(Object value) {
		int index = indexOf(value);
		
		if(index == -1) {
			return false;
		}else {
			remove(index);
			return true;
		}
	}
/**
 * Method <code>boolean contains(Object value)</code> checks if
 *  collection contains given object <code>value</code>.
 * 
 * @param value Object that needs to be checked if it is in the collection
 * @return true if collection contains <code>value</code>, false if it doesn't 	
 */
	public boolean contains(Object value) {
		int index = indexOf(value);
		
		if(index == -1) {
			return false;
		}
		
		return true;
	}
	
/**
 * Method <code>Object[] toArray()</code> copy all elements from this
 *  collection to array of objects, and returns it.
 * 
 * @return object[] array of objects from the collecton.	
 */	
	public Object[] toArray() {
		
		Object[] array = new Object[size()];
		
		
		for(int i = 0, n = size();i < n; i++) {
			array[i] = this.get(i);
		}
		
		return array;
	}
/**
 * Method <code>ArrayElementsGetter</code> creates new ElementsGetter.
 * 
 * @return newgetter ElementsGetter newly created	
 */	
	@Override
	public ArrayElementsGetter createElementsGetter() {
		
		ArrayElementsGetter newgetter = new ArrayElementsGetter(this);
		return newgetter;
	}
/**
 * Private static class <code>ArrayElementsGetter</code> that represents 
 * object for easly getting objects from collection.
 * 		
 * @author Lucija Valentić
 *
 */	
	 private static class ArrayElementsGetter implements ElementsGetter{
		/**
		 * Int that represents current position of element
		 * that this ElementGetter observes.
		 */
		private int index;
		/**
		 * Collection that is a copy of collection from
		 * this class, so we can do with it whatever we want,
		 * without disturbing outside collection
		 */
		ArrayIndexedCollection collection;
		/**
		 * Int that keeps track how many modifications have been
		 * made in collection before creating this ElementsGetter
		 */
		private long savedModificationCount;
/**
 * Constructor
 * 	
 * @param col this collection so we can keep track on elements in it
 */
		
		ArrayElementsGetter(ArrayIndexedCollection col){
			index = -1;
			collection = col;
			savedModificationCount = col.modificationCount;
		}
/**
 * Method <code>Object getNextElement()</code> gets next element from
 *  collection, and remembers which elements are already gotten before, and
 *  which ones are available. If there are no available elements or if there
 *   are some modification that happend before this method was called,
 *  it throws exception.
 * 
 * @return object element from the list
 * @throws ConcurrentModificationException() if there were some 
 * 			modifications made on collection before this method was called
 * @throws NoSuchElementsException() if there is no more available elements	
 */		
		public Object getNextElement() {
				
			if(index + 1< collection.size() && savedModificationCount == collection.modificationCount) {
				index++;
				return collection.elements[index];
			}else if(index +1 >= collection.size() && savedModificationCount == collection.modificationCount){
				throw new NoSuchElementException();
			}else {
				throw new ConcurrentModificationException();
			}
		}
/**
 * Method <code>boolean hasNextElement()</code> checks if there are 
 * more available elments in collection.
 * 
 * @return <code>true</code> if there is more available elements 
 * in collection, <code>false</code> if there is not	
 */		
		public boolean hasNextElement() {
			
			if(index + 1 < collection.size()) {
				return true;
			}else {
				return false;
			}
			
		}
		
		
	}
	
	
}
