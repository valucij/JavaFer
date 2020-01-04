package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * Class <code>ArrayIndexedCollection</code> represents resizable array-backed collection of objects. It inherits class <code>Collection</code>		
 * 
 * @author Lucija Valentić
 *
 */
public class ArrayIndexedCollection extends Collection{
	/**
	 * How many elements there is in elements[]
	 */
	private int size;
	/**
	 * Array of objects
	 */
	private Object elements[];
	
	
	
	/**
	 * Default constructor. Sets capacity to 16, size to 0.	
	 */
	
	public ArrayIndexedCollection() {
		
		this.size = 0;
		this.elements = new Object[16];
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
	 * Constructor that puts elements from collection <code>other</code> in this collection.	
	 * 	
	 * @param other collection from which we should take elements and put them
	 * 		  in this collection
	 * @throws NullPointerException if sent collection is empty
	 */
	
	public ArrayIndexedCollection(Collection other) throws NullPointerException {

		this(other.size());
		addAll(other);
	}
	/**
	 * Constructor that sets capacity of this collection to initialCapacity, and puts elements from collection <code>other</code>	in this collection.
	 * Makes resizes if needded.
	 * 	
	 * @param initialCapacity sets capacity to this
	 * @param other collection whose elements we want to put in this collection
	 * @throws NullPointerException if other collection is empty, IllegalArgumentException if capacity is below 1
	 */
	public ArrayIndexedCollection(int initialCapacity, Collection other)throws NullPointerException, IllegalArgumentException {
		
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
	 * Method calls given procesor on every element in this collection
	 * @param procesor type Processor that is used in method
	 */
	@Override
	public void forEach(Processor procesor) {
		
		for(int i = 0, n = size(); i < n; i++) {
			procesor.process(elements[i]);
		}
	}
	/**
	 * Method copys all elements from this collection in new array, and returns that array.
	 * 
	 */
	@Override
	public Object[] toArray() {
		
		Object[] newarray = Arrays.copyOf(elements, size());
		return newarray;
	}
	/**
	 * Method removes object <code>value</code>, and returns true. If that objects doesn't exist
	 * in this collection, it does nothing and returns false
	 * @param value object that needs to be removed if it exists
	 */
	@Override
	public boolean remove(Object value) {
		
		int index = indexOf(value);
		
		if(index == -1) {return false;}
		
		remove(index);
		return true;
	}
	
	
	/**
	 * Method <code>rescaleArray</code> doubles the capacity of collection, so more elements can be put in.
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
	 * Method <code>add</code> adds object <code>value</code> in collection. If collection is to small to hold it, it resize itself. It
	 * overrides method <code>add()</code> from class Collection.
	 * 
	 * @throws NullPointerException() if value is null				
	 */
	@Override
	public void add(Object value) {
		
		if(value.equals(null)) {
			throw new NullPointerException();
		}
		
		if(this.size == elements.length) {
			this.rescaleArray(elements.length);
		}
		
		
		this.elements[this.size] = value;
		this.size++;
		
	}
	
	/**
	 * Method <code>get</code> 	returns object that is stored at position <code>index</code>. Checks if index is valid. 
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
	 * 	Method <code>clear</code> removes all elements from collection.	It overrides method <code>clear()</code> from class Collection.	
	 */
	@Override
	public void clear() {
		
		for(int i = 0, n = this.size; i < n; i++) {
			this.elements[i] = null;
		}
		
		this.size = 0;
	}
	/**
	 * Method returns checks if object <code>value</code> exists in collection, and returns true if
	 * it does, false if it doesn't.
	 * @return true if element exists in collection, false otherwise
	 */
	@Override
	public boolean contains(Object value) {
		
		if(indexOf(value) == -1) return false;
		
		return true;
	}
	/**
	 * Method <code>insert</code> inserts element <code>value</code> i position <code>position</code> in this collection.
	 * 			
	 * @param value elements that needs to be placed in this collection
	 * @param position place in collection in which should new element be placed
	 * @throws IndexOutOfBoundsException() if sent position is less than 0,
	 * 		  more than size of this collection
	 */
	public void insert(Object value, int position) {
		
		int flag = 0;
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		
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
	 * Method <code>indexOf</code> returns position of element <code>value</code> in collection. If element <code>value</code> not found, or sent Object
	 * is null, returns -1.
	 * 				
	 * @param value element that needs to be found in this collection
	 * @return -1 if element not found in collection, or if sent Object <code>value</code>
	 * 		  is null; else returns position of that element in this collection	
	 */
	public int indexOf(Object value) {
		
		if(value.equals(null)) {
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
	 * Method <code>remove</code> removes elements with position <code>index</code> from collection. 
	 * 			
	 * @param index position on which should element in this collection be removed
	 * @throws IndexOutOfBoundsException() if sent index is less than 0, or more
	 * 		  than possible index in collection
	 */
	public void remove(int index) {
		
		if(index < 0 || index > this.size -1) {
			throw new IndexOutOfBoundsException();
		}
		
		for(int i = index, n = this.size - 1; i < n; i++) {
			
			this.elements[i] = this.elements[i + 1];
		}
		
		this.elements[this.size - 1] = null;
		
		this.size--;
	}
}
