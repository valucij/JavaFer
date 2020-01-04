package hr.fer.zemris.java.hw07.observer1;

/**
 * Class that shows how {@link IntegerStorage} and
 * {@link IntegerStorageObserver} can be used. 
 * @author Lucija Valentić
 *
 */
public class ObserverExample {

	/**
	 * Main method that is called in the beginning of a program
	 * @param args arguments of command line
	 */
	public static void main(String[] args) {
		
		IntegerStorage istorage = new IntegerStorage(20);
		
		IntegerStorageObserver observer = new SquareValue();
		
		istorage.addObserver(observer);
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		
		/*istorage.removeObserver(observer);
		
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(5));
		*/
		
		istorage.removeObserver(observer);
		
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));
		
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
		
	}

}
