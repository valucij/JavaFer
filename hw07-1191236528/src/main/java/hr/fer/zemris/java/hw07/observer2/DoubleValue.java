package hr.fer.zemris.java.hw07.observer2;

/**
 * Class implements interface {@link IntegerStorageObserver}. 
 * This class writes out double value of new value of {@link IntegerStorage},
 * but has its own counter, so when counter hits zero, it doesn't write out
 * anything anymore.
 * 
 * @author Lucija Valentić
 *
 */

public class DoubleValue implements IntegerStorageObserver{

	/**
	 * Represents internal counter
	 */
	private int count;
	
	/**
	 * Constructor
	 * @param count
	 */
	public DoubleValue(int count) {
		this.count = count;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorageChange) {
		
		if(count > 0) {
			
			System.out.println("Double value: " + getDouble(istorageChange.getNewValue()));
			
			count--;
		}
		
	}
	
	/**
	 * Private function that returns double value of given number n
	 * @param n
	 * @return
	 */
	private int getDouble(int n) {
		return n * 2;
	}

	/**
	 * Returns this.count
	 * @return this.count
	 */
	public int getCount() {
		return this.count;
	}
}
