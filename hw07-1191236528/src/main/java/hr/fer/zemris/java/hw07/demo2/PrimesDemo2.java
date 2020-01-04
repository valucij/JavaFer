package hr.fer.zemris.java.hw07.demo2;

/**
 * Class that shows how {@link PrimesCollection} can be used.
 * @author Lucija Valentić
 *
 */
public class PrimesDemo2 {

	/**
	 * Main method called in the beginning of a program.
	 * @param args arguments of a command line
	 */
	public static void main(String[] args) {
		
		PrimesCollection primes = new PrimesCollection(2);
		
		for(Integer prime : primes) {
			for(Integer prime2 : primes) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}

	}

}
