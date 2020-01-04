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
public class ArrayIndexedCollection<T> implements List<T>{

	private int size;
	private static final int DEFAULT_CAPACITY = 16;
	private T elements[];
	private long modificationCount;
		
	/**
	 * Default constructor. Sets capacity to 16, size to 0.	
	 */
		@SuppressWarnings("unchecked")
	public ArrayIndexedCollection() {
		
		this.size = 0;
		this.elements = (T[]) new Object[DEFAULT_CAPACITY];
	}
	/**
	 * Method <code>size</code> returns number of elements in collection.		
	 */
	@Override
	public int size() {
		return this.size;
	}
	/**this.capacity
	 * Constructor that sets capacity to <code>initialCapacity</code>, size to 0.
	 * 		
	 * @param initialCapacity, capacity should be set to it
	 * @throws IllegalArgumentException() if initialCapacity is below 1
	 */
		@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity) {
		
		if(initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		
		this.size = 0;
		this.elements = (T[])new Object[initialCapacity];
	}
	/**
	 * Constructor that puts elements from collection <code>other</code> in this 
	 * collection.	
	 * 	
	 * @param other collection from which we should take elements and put them
	 * 		  in this collection
	 * @throws NullPointerException if sent collection is empty
	 */
	public ArrayIndexedCollection(Collection<T> other) throws NullPointerException {
			
		this(other.size());
		addAll(other);
		
	}
	/**
	 * Constructor that sets capacity of this collection to initialCapacity, 
	 * and puts elements from collection <code>other</code>	in this collection.
	 * Makes resizes if needed.
	 * 	
	 * @param initialCapacity sets capacity to this
	 * @param other collection whose elements we want to put in this collection
	 * @throws NullPointerException if other collection is empty,
	 * 			 IllegalArgumentException if capacity is below 1
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity, Collection<T> other)throws NullPointerException, IllegalArgumentException {
		
		this(initialCapacity);
		
		if(other == null) {
			throw new NullPointerException();
		}
		
		if(initialCapacity < other.size()) {
						
			this.elements = (T[])new Object[other.size()];
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
	@SuppressWarnings("unchecked")
	private void rescaleArray(int capacity) {
		
		if(capacity < 0) {
			throw new IllegalArgumentException();
		}
				
		T[] novi = (T[]) new Object[capacity * 2];
		
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
	public void add(T value) {
		
		
		if(value.equals(null)) {
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
	public T get(int index) {
	
		
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
	public void insert(T value, int position) {
		
		int flag = 0;
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		
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
		
		if(index == -1) return false;
		
		return true;
	}
	
	/**
	 * Method <code>Object[] toArray()</code> copy all elements from this
	 *  collection to array of objects, and returns it.
	 * 
	 * @return object[] array of objects from the collecton.	
	 */	
	@SuppressWarnings("unchecked")
	public T[] toArray() {
		
		T[] array = (T[])new Object[size()];
		
		
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
	public ArrayElementsGetter<T> createElementsGetter() {
		
		ArrayElementsGetter<T> newgetter = new ArrayElementsGetter<T>(this);
		return newgetter;
	}
	/**
	 * Private static class <code>ArrayElementsGetter</code> that represents 
	 * object for easly getting objects from collection.
	 * 		
	 * @author Lucija Valentić
	 *
	 */	
	 private static class ArrayElementsGetter<S> implements ElementsGetter<S>{
		
		private int index;
		private ArrayIndexedCollection<S> node;
		private long savedModificationCount;
	/**
	 * Constructor
	 * 	
	 * @param col this collection so we can keep track on elements in it
	 */
		
		ArrayElementsGetter(ArrayIndexedCollection<S> col){
			index = -1;
			node = col;
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
		@SuppressWarnings("unchecked")
		public S getNextElement() {
				
			if(index + 1< node.size() && savedModificationCount == node.modificationCount) {
				index++;
				return node.elements[index];
			}else if(index +1 >= node.size() && savedModificationCount == node.modificationCount){
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
			
			if(index + 1 < node.size()) return true;
			else return false;
		}
		
		
	}
	
	 
	
}
