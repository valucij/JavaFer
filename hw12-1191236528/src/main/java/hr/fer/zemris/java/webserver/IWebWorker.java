package hr.fer.zemris.java.webserver;

/**
 * Interface, usually used with {@link SmartHttpServer}. This interface
 * can be implemented as workers, and they are used to generate some html documents,
 * or to handle sent parameters (sent from form, or written by hand), and to redirect
 * the "view" to appropriate smart script. The result is usually, if implemented right, 
 * that user, in his interface, see some html document
 * @author Lucija Valentić
 *
 */
public interface IWebWorker {
	/**
	 * This is main and only method in this interface. This method 
	 * is the one that handles all the work that worker has to do. Usually, 
	 * description of the work is given in description of class of that worker.
	 * Because this method uses context, and usually it does something with context, 
	 * it is not impossible for this method not to throw Exception
	 * @param context RequestContext
	 * @throws Exception
	 */
	public void processRequest(RequestContext context) throws Exception;
}
