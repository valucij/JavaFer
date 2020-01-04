package hr.fer.zemris.java.custom.collections;

/**
 * Class <code>ObjectStack</code> represents stack, and it can be worked with it like with any other stack of elements. It keeps elements using
 * class ArrayIndexesCollection.
 * 
 * @author Lucija Valentić
 *
 */

public class ObjectStack {
	/**
	 * Private collection that keeps elements in stack
	 */
	private ArrayIndexedCollection elementsinstack;
	/**
	 * Default constructor, prepares stack for use. Makes new collection of elements using default constructor of ArrayIndexedCollection.	
	 */
	public ObjectStack() {
		elementsinstack = new ArrayIndexedCollection();
	}
	/**
	 * Checks if the stack is empty.
	 * 	
	 * @return true if stack is empty, else returns false.
	 */
	public boolean isEmpty() {
		
		if(elementsinstack.isEmpty()) {return true;}
		
		return false;
	}	
	/**
	 * Method <code>size</code> returns number of elements in stack.
	 * 	
	 * @return number of elements in stack
	 */
	public int size() {
		
		return elementsinstack.size();
	}
	/**
	 * Method <code>push</code>	puts element <code>value</code> in stack if possible.
	 * 
	 * @param value element that needs to be put in stack
	 * @throws NullPointerException() if given element is null
	 */
	public void push(Object value) {
		
		if(value.equals(null)) {
			throw new NullPointerException();
		}
		
		elementsinstack.add(value);
		
	}
	/**
	 * Method <code>pop</code> removes last element from stack (on top of the stack), and returns it. It pays attention if the stack is empty or not. 
	 * If it is empty, returns EmptyStackException()
	 * 	
	 * @return element from the top od the stack
	 * @throws EmptyStackException() if stack is empty
	 */
	public Object pop() {
		
		if(isEmpty()) {
			throw new EmptyStackException();
		}
		
		Object newobject = peek();
		
		elementsinstack.remove(elementsinstack.size()-1);
		
		return newobject;
	}
	/**
	 * Method <code>peek</code> returns element from the top of the stack if possible. It pays attention if the stack is empty; in that case it
	 * throws EmptyStackException().
	 * 	
	 * @return element on top of the stack
	 * @throws EmptyStackException() if the stack is empty
	 */
	public Object peek() {
		
		if(isEmpty()) {
			throw new EmptyStackException();
		}
		
		Object newobject = elementsinstack.get(elementsinstack.size()-1);
		
		return newobject;
	}
	/**
	 * Method <code>clear</code> removes all the elements from the stack.	
	 */
	public void clear() {
		
		elementsinstack.clear();
	}
	
}
