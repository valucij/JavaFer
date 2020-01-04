package hr.fer.zemris.java.hw07.observer1;

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
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Provided new value " + istorage.getValue() +
				", square is " + getSquare(istorage.getValue()));
		
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
