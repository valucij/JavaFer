package hr.fer.zemris.java.custom.collections;
/**
 * 
 * Interface <code>Collection</code> represents general collection of elements. Has some useful methods that can be used for handling collections. 
 * @author Lucija Valentić
 *
 */
public interface Collection {

/**
 * Method <code>isEmpty</code> checks if the collection is empty or not.
 * 	
 * @return true if collection is empty, false if collection is not empty
 */
	default boolean isEmpty() {
		if(this.size() == 0) {
			return true;
		}else {
			return false;
		}
	}
/**
 * Abstract method, usually returns how many elements there are 
 * in collection. 
 * 
 */
	 public int size();
/**
 * Abstract method, usually adds new element in collection.
 */
	public void add(Object value);
/**
 * Abstract method, usually checks if given element is in the collection.
 * It returns <code>true</code> if it is, and <code>false</code>
 * otherwise.
 */
	public boolean contains(Object value);
/**
 * Abstract method, usually removes given element from collection,
 * if that kind of element exists. If it doesn's, it does nothing.
 * 
 */
	public boolean remove(Object value);

/**
 * Abstract method, usually returns new array with all elements 
 * from this collection.
 */
	public Object[] toArray();
/**
 * Default method, calls processor on each element of current collection. Use ElementsGetter 
 * for getting elements from the collection.
 * ElemenetsGetter is an object that helps in getting elements from the collection.
 * 
 * @param processor 
 */
	default public void forEach(Processor processor) {
		ElementsGetter getter = createElementsGetter();
		
		for(int i = 0, n = size(); i < n; i++) {
			Object help = getter.getNextElement();
			processor.process(help);
		}
	}
/**
 * Abstract method, usually puts all elements from given 
 * collection in this collection.
 */
	default void addAll(Collection other) {
		
		
/**
 * Local class <code>Processor</code>. Adds elements in this collection.		
 * 	
 * @author Lucija Valentić
 *
 */
		class LocalProcessor implements Processor {
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
 * 	Abstract method, usually clears this collection, it 
 * deletes all elements from collection.
 * 	
 */
	public void clear();
/**
 * Abstract method, usually it creates new ElementsGetter, an
 * object that helps us go through this collection, something
 * like iterator, but not quite.
 * 	
 * @return Elementsgetter newly created.
 */
	ElementsGetter createElementsGetter();
/**
 * Default method. Checks satisfying elements from current collection using tester, and adds them to given collection.
 * If there is no such elements, it does nothing. Use ElementsGetter for easly getting elements from collection.
 * 	
 * @param col given collection in which we must put satisfying elements
 * @param tester object type Tester with which we test our elements, if they are satisfying or not
 */
	default void addAllSatisfying(Collection col, Tester tester) {
		
		ElementsGetter getter = col.createElementsGetter();
		
		for(int i = 0, n = col.size(); i < n; i++) {
			
			Object help = getter.getNextElement();
			if(tester.test(help)) this.add(help);
		}
		
	}
}
