package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;

/**
 * This class represents collection of prime numbers.  
 * Collection doesn't include any arrays or lists for keeping
 * the prime numbers, but each prime number is calculated only
 * when needed. When this collection
 * is made, user has to specify how many numbers he wants, and that
 * many numbers will be made. This collection doesn't offer
 * too much functions, but it is used for accumulating prime numbers, and
 * then passing through them and observing them. Next example shows
 * how this class can be used.
 * 
 * <pre>
 * 
 * EXAMPLE 1:
 * 
 * PrimesCollection primes = new PrimesCollection(2);
 *		
 *		for(Integer prime : primes) {
 *			for(Integer prime2 : primes) {
 *				System.out.println("Got prime pair: " + prime + ", " + prime2);
 *			}
 *		}
 *
 *	Output > Got prime pair: 2, 2
 *			 Got prime pair: 2, 3
 *			 Got prime pair: 3, 2
 *	   		 Got prime pair: 3, 3

 *
 * EXAMPLE 2:
 * 
 * PrimesCollection primes = new PrimesCollection(5);
 *		
 *		for(Integer prime : primes) {
 *			System.out.println("Got prime: " + prime);
 *		}
 *
 *	Output > Got prime: 2
 *			 Got prime: 3
 *			 Got prime: 5
 *		  	 Got prime: 7
 *			 Got prime: 11
 * </pre>
 * 
 * @author Lucija Valentić
 */
public class PrimesCollection implements Iterable<Integer>{

	/**
	 * Says how much there are primes in this collection
	 */
	private int size = 0;
	
	/**
	 * Constructor receives how many numbers
	 * (primes) should be in this collection
	 * @param number
	 */
	public PrimesCollection(int number) {
		
		this.size = number;
		
	}

	/**
	 * {@inheritDoc} Type here is Integer.
	 */
	@Override
	public Iterator<Integer> iterator() {
		
		return this.new IteratorImpl();
	}	
	
	/**
	 * Private class implements {@link Iterator}, and it is
	 * made so this collection can be passed through with for loop
	 * 
	 * @author Lucija Valentić
	 *
	 */
	private class IteratorImpl implements Iterator<Integer>{
		/**
		 * Represents how many primes has been already passed through,
		 * how many integers has been already observed
		 */
		private int passedPrimes;
		/**
		 * Reference to a current prime
		 */
		private int currentPrime;
		/**
		 * Constructor
		 */
		public IteratorImpl() {
			passedPrimes = 0;
			currentPrime = 2;
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return passedPrimes != size;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Integer next() {
			
			int returnValue = nextPrime(currentPrime);
			passedPrimes++;
			currentPrime = returnValue + 1;
			return returnValue;
			 
		}
		
		/**
		 * Returns next prime number after given number n
		 * @param n
		 * @return int
		 */
		private int nextPrime(int n) {
			
			for(int i = n; ; i++) {
			
				if( isPrime(i)) {
					return i;
				}
			}
		}
		/**
		 * Checks if given number is a prime. If it is, returns
		 * <code>true</code>, otherwise <code>false</code>.
		 * @param x
		 * @return <code>true</code> if given number is prime, 
		 * 			<code>false</code> otherwise
		 */
		private boolean isPrime(int x) {
			
			if( x == 2) {
				return true;
			}
			
			for(int i = 2; i < x; i++) {
				if(x % i == 0) {
					return false;
				}
			}
			
			return true;
			
		}
		
		
	}

}
