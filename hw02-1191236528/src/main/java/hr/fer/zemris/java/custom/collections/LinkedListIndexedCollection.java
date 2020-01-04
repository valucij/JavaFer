package hr.fer.zemris.java.custom.collections;

/**
 * Class <code>LinkedListIndexedCollection</code> represents list-backed collection of objects. It inherits class <code>Collection</code>
 * 
 * 
 * @author Lucija Vakentić
 *
 */
public class LinkedListIndexedCollection extends Collection{
	/**
	 * Private static class <code>ListNode</code> where we keep elements from this list.
	 */
	private static class ListNode{
		ListNode next;
		ListNode previous;
		Object value;
	}
	/**
	 * How many elements there are in collection
	 */
	private int size;
	/**
	 * Reference to first element in collection
	 */
	private ListNode first;
	/**
	 * Reference to last element in collection
	 */
	private ListNode last;

	/**
	 * Default constructor, sets references to null pointers, and size to 0.
	 */
	public LinkedListIndexedCollection() {
		size = 0;
		first = null;
		last = null;
	}
	/**
	 * Constructor in which we copy elements from other collection in this collection, and that's how we make this collection.
	 * 	
	 * @param c given collection from which we copy elements
	 * @throws NullPointerException() if given collection is null
	 */
	public LinkedListIndexedCollection(Collection c) {
		
		if(c.equals(null)) {throw new NullPointerException();}
		
		addAll(c);
	}
	/**
	 * Method <code>size</code> tells us how many elements there are in collection.
	 * 
	 * @return size of collection, number of elements in collection	
	 */
	@Override
	public int size() {
		return this.size;
	}
	/**
	 * Method calls given procesor on every element in this collection
	 * @param procesor type Processor that is used in method
	 */
	@Override
	public void forEach(Processor procesor) {
		
		ListNode help = first;
		
		for(int i = 0, n = size(); i < n; i++) {
			procesor.process(help.value);
			help = help.next;
		}
		
	}
	/**
	 * Method copys all elements from this collection in new array, and returns that array.
	 * 
	 */
	@Override
	public Object[] toArray() {
		
		Object[] newarray = new Object[size()];
		ListNode help = this.first;
		
		for(int i = 0, n = size(); i < n; i++) {
			newarray[i] = help.value;
			help = help.next;
		}
		
		return newarray;
	}
	/**
	 * Method removes object <code>value</code>, and returns true. If that objects doesn't exist
	 * in this collection, it does nothing and returns false
	 * @param value object that needs to be removed if it exists
	 */
	public boolean remove(Object value) {
		
		if(indexOf(value) == -1) return false;
		
		remove(indexOf(value));
		return true;
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
	 * Method <code>add</code> adds element <code>value</code> in this collection, at the end of the list. If given value is <code>null</code>,
	 * the it throws NullPointerException().
	 * 
	 * @param value Object element which me must put at the end of the list 
	 * @throws NullPointerException() if given value is <code>null</code>	
	 */
	@Override
	public void add(Object value) {
		
		if(value.equals(null)) {
			throw new NullPointerException();
		}
		
		ListNode newnode = new ListNode();
				
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
			
		
		this.size++;
	}
	
	/**
	 * Method <code>get</code> returns element from collection on position <code>index</code>. If index is invalid, it throws IndexOutOfBoudException().
	 * 
	 * @param index int represents position of element we want to get
	 * @return value object from collection, on given position
	 * @throws IndexOutOfBoundsException() if index is less then 0 or more then size - 1
	 * 
	 */
	public Object get (int index) {
		
		if(index < 0 || index > size - 1 ) {
			throw new IndexOutOfBoundsException();
		}
		
		if(index < this.size/2) {
			
			ListNode help = this.first;
			
			while(index > 0) {
				
				help = help.next;
				index--;
			}
			
			return help.value;
			
		}else {
			ListNode help = this.last;
			index = this.size - 1 - index;
			
			while(index > 0) {
				help = help.previous;
				index--;
			}
			
			return help.value;
		}
		
	}
	/**
	 * Method <code>clear</code> removes all elements from this collection. Collection is "new and fresh".	
	 */
	public void clear() {
		
		if(this.size == 0) {return;}
		
		for(ListNode i = this.last; i != this.first; ) {
			
			i.next = null;
			i.value = null;
			ListNode j = i.previous;
			i.previous = null;
			i = j;
		}
		
		this.size = 0;
		this.first = null;
		this.last = null;
	}
	/**
	 * Method <code>indexOf</code> returns index in collection of given element. If element is not in the list, not found, ther it returns -1. Also, if
	 * <code>null</code> is sent as an argument, method returns -1 as well.
	 * 	
	 * @param value Object which we want to find in collection
	 * @return index int represents position of given element in this collection, or -1 if given element is null, or if there is no given element
	 * 		  in this collection
	 */
	public int indexOf(Object value) {
		
		if(value.equals(null)) {return -1;}
		
		int counter = 0;
		
		for(ListNode i = this.first; i != null; i = i.next,counter ++) {
			
			if(i.value.equals(value)) {return counter;}
		}
		
		return -1;
	}
	/**
	 * Method <code>insert</code> inserts given object in given position in this list. If position is invalid, then it throws IndexOutOfBoundsException().
	 * 	
	 * @param value Object element that we must put in this list
	 * @param position int position in list in which we must put this element
	 */
	public void insert(Object value, int position) {
		
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		
		ListNode newnode = new ListNode();
		newnode.value = value;
		
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
		
		ListNode i = this.first;
		
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
	 * Method <code>remove</code> removes element on given index. If index is invalid, then it throws IndexOutOfBoundsException();
	 * 	
	 * @param index int position in list from which we must remove element
	 */
	public void remove(int index) throws IndexOutOfBoundsException{
		
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		
		if(size() == 1) {
			clear();
			this.size --;
			return;
		}
		
		if(index == 0) {
			ListNode help = this.first;
			help = help.next;
			
			help.previous = null;
			this.first.value = null;
			this.first.next = null;
			this.first = help;
			this.size--;
			
			return;
		}
		
		if(index == size -1) {
			ListNode help = this.last.previous;
			
			this.last.previous = null;
			this.last.value = null;
			help.next = null;
			this.last = help;
			this.size--;
			
			return;
		}
		
		ListNode i = this.first;
		
		for(int help = index; help > 0; help--) {
			
			i = i.next;
		}
		
		ListNode help1 = i.previous;
		ListNode help2 = i.next;
		
		i.next = null;
		i.previous = null;
		i.value = null;
		
		help1.next = help2;
		help2.previous = help1;
		
		this.size--;
		
		return;
		
	}
	
}
