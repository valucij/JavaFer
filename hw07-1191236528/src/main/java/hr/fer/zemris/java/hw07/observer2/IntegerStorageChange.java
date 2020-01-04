package hr.fer.zemris.java.hw07.observer2;

/**
 * This class is used when handling {@link IntegerStorage} objects,
 * and when handling observers (instances of {@link IntegerStorageObserver}).
 * This class was made so observers, when performing its actions, are given more
 * information about instance of {@link IntegerStorage}. With this class, they are
 * given information about new vale, old value, and reference to the instance of {@link IntegerStorage}
 * @author Lucija Valentić
 *
 */
public class IntegerStorageChange {

	/**
	 * Reference to some instance of {@link IntegerStorage}
	 */
	private IntegerStorage istorage;
	/**
	 * Old value of integer in istorage, before the change
	 */
	private int oldValue;
	/**
	 * New value of integer in istorage, after the change
	 */
	private int newValue;
	
	/**
	 * Constructor
	 * @param istorage
	 * @param newValue
	 */
	public IntegerStorageChange(IntegerStorage istorage, int newValue) {
		this.istorage = istorage;
		oldValue = istorage.getValue();
		this.newValue = newValue;
	}

	/**
	 * Returns reference to object, instance of {@link IntegerStorage}
	 * @return this.istorage
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}

	/**
	 * Returns old value of integer of instance of {@link IntegerStorage}, before the change
	 * @return this.oldValue
	 */
	public int getOldValue() {
		return oldValue;
	}

	/**
	 * Returns new value of integer of instance of {@link IntegerStorage}, after the change
	 * @return this.newValue
	 */
	public int getNewValue() {
		return newValue;
	}
	
	
}
