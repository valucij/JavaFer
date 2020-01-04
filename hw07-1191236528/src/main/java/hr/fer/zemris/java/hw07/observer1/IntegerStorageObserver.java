package hr.fer.zemris.java.hw07.observer1;

/**
 * Interface that is used when working with {@link IntegerStorage}.
 * Value of stored integer in some instance of {@link IntegerStorage}
 * can change, so user wants to maybe do something with that information.
 * Then, new observers are made, they implement this interface, and they 
 * perform action that some other user writes for them
 * 
 * @author Lucija Valentić
 *
 */
public interface IntegerStorageObserver {
	/**
	 * Method is called when change of value in {@link IntegerStorage}
	 * occurs. This method usually do some action
	 * that has to do with a name of the class that implements this interface.
	 * Usually, it can write out some message, or it can count.
	 * @param istorage
	 */
	public void valueChanged(IntegerStorage istorage);
}
