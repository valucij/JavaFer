package hr.fer.zemris.java.custom.collections;

/**
 * Inteface <code>ElementsGetter</code>. It can be implemented as an object
 *  that helps in handling objets from collection, in getting them, and 
 * checking if there is more available elements in collection.
 * 
 * @author Lucija Valentić
 *
 */
public interface ElementsGetter {

/**
 * Abstract method, usually returns next element in collection.
 * Elements getter is an object that goes through collection,
 * so it keeps track on elements that he has passed, and the ones that
 * hasn't.
 */
	Object getNextElement();
/**
 * Abstract method, usually it returns <code>true</code> if there
 * is more elements that this element getter hasn't passed.
 * It returns <code>false</code> otherwise.
 * 
 */
	boolean hasNextElement();
/**
 * Default method <code>void processRemaining(Processor p)</code>
 *  that calls given processor on every element of collection.	
 * @param p
 */
	default void processRemaining(Processor p) {
		
		while(hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
