package hr.fer.zemris.java.custom.collections;

/**
 * Interface <code>Processor</code>. It can be implemented to be capable
 *  of performing some actions on passed object.
 *  
 * @author Lucija Valentić
 *
 */

public interface Processor<S> {
/**
 * Abstract method, usually when called it process given object.
 */
	public void process(S value);
}
