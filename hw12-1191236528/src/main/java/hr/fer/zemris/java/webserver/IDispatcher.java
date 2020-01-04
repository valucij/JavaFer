package hr.fer.zemris.java.webserver;
/**
 * Interface used with {@link SmartHttpServer}
 * @author Lucija Valentić
 *
 */
public interface IDispatcher {
	/**
	 * Method handles given path and decides what to do with it
	 * @param urlPath String
	 * @throws Exception
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
