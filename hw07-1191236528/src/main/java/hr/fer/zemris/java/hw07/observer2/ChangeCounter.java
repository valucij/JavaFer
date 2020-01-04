package hr.fer.zemris.java.hw07.observer2;

/**
 * Class implements interface {@link IntegerStorageObserver}. This class
 * counts how many times value of {@link IntegerStorage} has been changed.
 * 
 * @author Lucija Valentić
 *
 */
public class ChangeCounter implements IntegerStorageObserver{

	/**
	 * Represents counter
	 */
	private int counter;
	
	/**
	 * Constructor
	 */
	public ChangeCounter() {
		this.counter = 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorageChange) {
		this.counter++;
		
		System.out.println("Number of value changes since tracking: " + this.counter);
		
	}

}
