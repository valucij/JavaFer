package hr.fer.zemris.java.custom.collections;
/**
 * 
 * Interface <code>Collection</code> represents general collection of elements. Has some useful methods that can be used for handling collections. 
 * @author Lucija Valentić
 *
 */
public interface Collection<T> {
	
	/**
	 * Method <code>isEmpty</code> checks if the collection is empty or not.
	 * 	
	 * @return true if collection is empty, false if collection is not empty
	 */
	default boolean isEmpty() {
		return this.size() == 0;
		}
	/**
	 * Abstract method, usually returns how many elements there is in collection.
	 * 
	 */
	 public int size();
	/**
	 * Abstract method, usually adds new element in the collection.
	 */
	public void add(T value);
	/**
	 * Abstract method, usually checks if the element is in the collection.
	 * Returns true if it does, and false otherwise.
	 */
	public boolean contains(Object value);
	/**
	 * Abstract method, usually removes object from the collection, if that 
	 * kind of object exists in the collection.
	 */
	public boolean remove(Object value);

	/**
	 * Abstract method, usually converts this collection in the array.
	 * 
	 */
	public T[] toArray();
	/**
	 * Default method, calls processor on each element of current collection. Use ElementsGetter 
	 * for getting elements from the collection.
	 * ElemenetsGetter is an object that helps in getting elements from the collection.
	 * 
	 * @param processor 
	 */
	default public void forEach(Processor<? super T> processor) {
		ElementsGetter<T> getter = createElementsGetter();
		
		for(int i = 0, n = size(); i < n; i++) {
			T help = getter.getNextElement();
			processor.process(help);
		}
	}
	/**
	 * Abstract method, usually adds all the elements from given
	 * collection to this collection, and also it has local
	 * class that is called on every element of other collection.
	 */
	default void addAll(Collection<? extends T> other) {
	/**
	 * Local class <code>Processor</code>. Adds elements in this collection.		
	 * 	
	 * @author Lucija Valentić
	 *
	 */		
		class LocalProcessor<S extends T> implements Processor<S> {
	/**
	 * Method <code>process</code> adds elements in this collection and
	 * on other collection calls method <code>forEach</code>.		
	 * 			
	 * @param value object that needs to be added in collection
	 */
			public void process(S  value) {	
				
				add(value);
			}
			
		}
		
		LocalProcessor<T> local = new LocalProcessor<>();
		
		other.forEach(local);
	}
	/**
	 * 	Abstract method, usually deletes every element in collection.
	 * 	
	 */
	public void clear();
	/**
	 * Abstract method.
	 * 	
	 * @return Elementsgetter newly created.
	 */
	ElementsGetter<T> createElementsGetter();
	/**
	 * Default method. Checks satisfying elements from current collection using tester, and adds them to given collection.
	 * If there is no such elements, it does nothing. Use ElementsGetter for easily getting elements from collection.
	 * 	
	 * @param col given collection in which we must put satisfying elements
	 * @param tester object type Tester with which we test our elements, if they are satisfying or not
	 */
	default void addAllSatisfying(Collection<T> col, Tester<? super T> tester) {
		
		ElementsGetter<T> getter = col.createElementsGetter();
		
		for(int i = 0, n = col.size(); i < n; i++) {
			
			T help = getter.getNextElement();
			if(tester.test(help)) this.add(help);
		}
		
	}
}
