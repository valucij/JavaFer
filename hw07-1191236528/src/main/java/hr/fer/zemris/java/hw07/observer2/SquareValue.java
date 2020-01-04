package hr.fer.zemris.java.hw07.observer2;

/**
 * 
 * Class implements interface {@link IntegerStorageObserver}.
 * This class writes out square of the new value of {@link IntegerStorage}
 * 
 * @author Lucija Valentić
 *
 */
public class SquareValue implements IntegerStorageObserver{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorageChange) {
		System.out.println("Provided new value " + istorageChange.getNewValue() +
				", square is " + getSquare(istorageChange.getNewValue()));
		
	}
	
	/**
	 * Private method that returns square of given number n
	 * @param n
	 * @return int
	 */
	private int getSquare(int n) {
		return n * n;
	}
	
}
