package hr.fer.zemris.java.custom.collections;

/**
 * Interface <code>Processor</code>. It can be implemented to be capable
 *  of performing some actions on passed object.
 *  
 * @author Lucija Valentić
 *
 */

public interface Processor {
/**
 * Abstract method, usually it does something on given 
 * object.
 */
	public void process(Object value);
}
