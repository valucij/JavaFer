package hr.fer.zemris.java.custom.collections;

/**
 * Inteface <code>ElementsGetter</code>. It can be implemented as an object
 *  that helps in handling objets from collection, in getting them, and 
 * checking if there is more available elements in collection.
 * 
 * @author Lucija Valentić
 *
 */
public interface ElementsGetter<S> {

/**
 * Abstract method.T
 */
	<T> T getNextElement();
/**
 * Abstract method
 * 
 */
	boolean hasNextElement();
/**
 * Default method <code>void processRemaining(Processor p)</code>
 *  that calls given processor on every element of collection.	
 * @param p
 */
	default void processRemaining(Processor<S> p) {
		
		while(hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
