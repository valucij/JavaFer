package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

//import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection.ListNode;

/**
 * Class <code>LinkedListIndexedCollection</code> represents list-backed
 *  collection of objects. It implements interface <code>List</code>. It also
 * keeps track if something is changed in collection, if something is added
 *  or removed etc.
 * 
 * 
 * @author Lucija Valentić
 *
 */
public class LinkedListIndexedCollection<T> implements List<T>{
	/**
	 * Private static class <code>ListNode</code> where we keep elements from this list.
	 */
	private static class ListNode<S>{
		ListNode<S> next;
		ListNode<S> previous;
		S value;
	}
	
	private int size;
	private ListNode<T> first;
	private ListNode<T> last;
	private long modificationCount;

	/**
	 * Default constructor, sets references to null pointers, size to 0, 
	 * and modificationCout to 0.
	 */
	public LinkedListIndexedCollection() {
		size = 0;
		first = null;
		last = null;
		modificationCount = 0;
	}
	/**
	 * Constructor in which we copy elements from other collection in this 
	 * collection, and that's how we make this collection.
	 * 	
	 * @param c given collection from which we copy elements
	 * @throws NullPointerException() if given collection is null
	 */
	public LinkedListIndexedCollection(Collection<T> c) {
		if(c.equals(null)) {throw new NullPointerException();}
		
		addAll(c);
	}
	/**
	 * Method <code>size</code> tells us how many elements there are in collection.
	 * 
	 * @return size of collection, number of elements in collection	
	 */
	public int size() {
		return this.size;
	}
		
	/**
	 * Method <code>add</code> adds element <code>value</code> in this collection,
	 *  at the end of the list. If given value is <code>null</code>,
	 * the it throws NullPointerException(). If something is added, modificationCount
	 *  is increased.
	 * 
	 * @param value Object element which me must put at the end of the list 
	 * @throws NullPointerException() if given value is <code>null</code>	
	 */
	
	public void add(T value) {
		
		if(value.equals(null)) {
			throw new NullPointerException();
		}
		
		
		ListNode<T> newnode = new ListNode<T>();
				
		newnode.value = value;
		newnode.previous = this.last;
		newnode.next = null;
		
		if(this.size == 0) {
			
			this.first = newnode; 
			this.last = newnode;
			
		}else {
			
			this.last.next = newnode;
			this.last = newnode;
			
		}
			
		modificationCount++;
		this.size++;
	}
	
	/**
	 * Method <code>get</code> returns element from collection on position 
	 * <code>index</code>. If index is invalid, it throws IndexOutOfBoudException().
	 * 
	 * @param index int represents position of element we want to get
	 * @return value object from collection, on given position
	 * @throws IndexOutOfBoundsException() if index is less then 0 or more then size - 1
	 * 
	 */
	public T get (int index) {
		
		if(index < 0 || index > size - 1 ) {
			throw new IndexOutOfBoundsException();
		}
		
		if(index < this.size/2) {
			
			ListNode<T> help = this.first;
			
			while(index > 0) {
				
				help = help.next;
				index--;
			}
			
			return help.value;
			
		}else {
			ListNode<T> help = this.last;
			index = this.size - 1 - index;
			
			while(index > 0) {
				help = help.previous;
				index--;
			}
			
			return help.value;
		}
		
	}
	/**
	 * Method <code>clear</code> removes all elements from this collection. 
	 * Collection is "new and fresh". modificationCount is increased.
	 */
	public void clear() {
		
		if(this.size == 0) {return;}
		
		for(ListNode<T> i = this.last; i != this.first; ) {
			
			i.next = null;
			i.value = null;
			ListNode<T> j = i.previous;
			i.previous = null;
			i = j;
		}
		modificationCount++;
		this.size = 0;
		this.first = null;
		this.last = null;
	}
	/**
	 * Method <code>indexOf</code> returns index in collection of given element.
	 *  If element is not in the list, not found, ther it returns -1. Also, if
	 * <code>null</code> is sent as an argument, method returns -1 as well.
	 * 	
	 * @param value Object which we want to find in collection
	 * @return index int represents position of given element in this collection,
	 * 		   or -1 if given element is null, or if there is no given element
	 * 		  in this collection
	 */
	public int indexOf(Object value) {
		
		if(value.equals(null)) {return -1;}
		
		int counter = 0;
		
		for(ListNode<T> i = this.first; i != null; i = i.next,counter ++) {
			
			if(i.value.equals(value)) {return counter;}
		}
		
		return -1;
	}
	/**
	 * Method <code>insert</code> inserts given object in given position in this list.
	 *  If position is invalid, then it throws IndexOutOfBoundsException().
	 * modificationCount is increased.	
	 * 
	 * @param value Object element that we must put in this list
	 * @param position int position in list in which we must put this element
	 */
	public void insert(T value, int position) {
		
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		
		ListNode<T> newnode = new ListNode<T>();
		newnode.value = value;
		modificationCount++;
		
		if(position == size) {
			newnode.previous = this.last;
			newnode.next = null;
			
			if(size == 0) {
				
				this.first = newnode;
				this.last = newnode;
				
			}else {
				
				this.last.next = newnode;
				this.last = newnode;
			}
			
			this.size++;
			return;
		}
		
		if(position == 0) {
			newnode.previous = null;
			newnode.next = this.first;
			
			if(size == 0) {
				
				this.first = newnode;
				this.last = newnode;
				
			}else {
				
				this.first.previous = newnode;
				this.first = newnode;
			}
			
			this.size++;
			return;
		}
		
		ListNode<T> i = this.first;
		
		for(int help = position; help > 0; help--) {
			
			i = i.next;
		}
		

		newnode.next = i;
		newnode.previous = i.previous;
		i.previous.next = newnode;
		i.previous = newnode;
		this.size++;
		
		return;
	
	}
	/**
	 * Method <code>remove</code> removes element on given index. 
	 * If index is invalid, then it throws IndexOutOfBoundsException().
	 *  modificationCount is increased if something is removed.
	 * 	
	 * @param index int position in list from which we must remove element
	 */
	public void remove(int index) throws IndexOutOfBoundsException{
		
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		
		modificationCount++;
		
		if(index == 0) {
			ListNode<T> help = this.first;
			help = help.next;
			
			help.previous = null;
			this.first.value = null;
			this.first.next = null;
			this.first = help;
			this.size--;
			
			return;
		}
		
		if(index == size -1) {
			ListNode<T> help = this.last.previous;
			
			this.last.previous = null;
			this.last.value = null;
			help.next = null;
			this.last = help;
			this.size--;
			
			return;
		}
		
		ListNode<T> i = this.first;
		
		for(int help = index; help > 0; help--) {
			
			i = i.next;
		}
		
		ListNode<T> help1 = i.previous;
		ListNode<T> help2 = i.next;
		
		i.next = null;
		i.previous = null;
		i.value = null;
		
		help1.next = help2;
		help2.previous = help1;
		
		this.size--;
		
		return;
		
	}
	/**
	 * Method <code>boolean remove(Object value)</code> removes 
	 * Object <code>value</code> if that elements exists in collection,
	 *  and return true. If it doesn't exists it returns false.
	 * 
	 * @param value Object that needs to be removed from the collection.
	 * @return <code>true </code> if element is removed, <code>false</code> 
	 * 			if it is not	
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
	 * Method <code>boolean contains(Object value)</code> checks if collection 
	 * contains given object <code>value</code>.
	 * 
	 * @param value Object that needs to be checked if it is in the collection
	 * @return true if collection contains <code>value</code>, false if it doesn't 	
	 */
	public boolean contains(Object value) {
		int index = indexOf(value);
		
		if(index == -1) {
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * Method <code>Object[] toArray()</code> copy all elements from this 
	 * collection to array of objects, and returns it.
	 * 
	 * @return object[] array of objects from the collection.	
	 */
	@SuppressWarnings("unchecked")
	public T[] toArray() {
		
		T[] array =(T[]) new Object[size()];
		
		
		for(int i = 0, n = size();i < n; i++) {
			array[i] = this.get(i);
		}
		
		return array;
	}
	/**
	 * Method <code>ListElementsGetter</code> creates new ElementsGetter.
	 * 
	 * @return newgetter ElementsGetter newly created	
	 */
	@Override
	public ListElementsGetter<T> createElementsGetter() {
	
		ListElementsGetter<T> newgetter = new ListElementsGetter<T>(this);
		return newgetter;
	}
	
	/**
	 * Private static class <code>ListElementsGetter</code> that represents 
	 * object for easly getting objects from collection.
	 * 		
	 * @author Lucija Valentić
	 *
	 */
	private static class ListElementsGetter<S> implements ElementsGetter<S>{
		
		private ListNode<S> node;
		private LinkedListIndexedCollection<S> helpCol;
		private int index;
		private int helpsize;
		private long savedModificationCount;
		/**
		 * Constructor
		 * 	
		 * @param col this collection so we can keep track on elements in it
		 */
		ListElementsGetter(LinkedListIndexedCollection<S> col){
			node = col.first;
			helpCol = col;
			index = 0;
			helpsize = col.size();
			savedModificationCount = col.modificationCount;
		}
		/**
		 * Method <code>Object getNextElement()</code> gets next element from collection,
		 *  and remembers which elements are already gotten before, and
		 * which ones are available. If there are no available elements or if there 
		 * are some modification that happend before this method was called,
		 *  it throws exception.
		 * 
		 * @return object element from the list
		 * @throws ConcurrentModificationException() if there were some modifications 
		 * 		   made on collection before this method was called
		 * @throws NoSuchElementsException() if there is no more available elements	
		 */
		@SuppressWarnings("unchecked")
		public S getNextElement() {
			
			if(index < helpsize && savedModificationCount == helpCol.modificationCount) {
				index++;
				S returnvalue = node.value;
				node = node.next;
				return returnvalue;
				
			}else if(index >= helpsize && savedModificationCount == helpCol.modificationCount) {
				throw new NoSuchElementException();
			}else {
				throw new ConcurrentModificationException();	
			}
			
		}
		/**
		 * Method <code>boolean hasNextElement()</code> checks if there are more available 
		 * elments in collection.
		 * 
		 * @return <code>true</code> if there is more available elements in collection,
		 * 		   <code>false</code> if there is not	
		 */
		public boolean hasNextElement() {
			if(index < helpsize) return true;
			else return false;
		}
		
		
	}
	
}
