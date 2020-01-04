package hr.fer.zemris.java.hw07.demo2;

/**
 * Class that shows how {@link PrimesCollection} can be used.
 * @author Lucija Valentić
 *
 */
public class PrimesDemo1 {

	/**
	 * Main method called in the beginning of a program.
	 * @param args arguments of a command line
	 */
	public static void main(String[] args) {
		
		PrimesCollection primes = new PrimesCollection(5);
		
		for(Integer prime : primes) {
			System.out.println("Got prime: " + prime);
		}

	}

}
