package searching.slagalica;

import java.util.Arrays;

/**
 * This class is actually object that represents one array of integer. 
 * This class is written so that we can write methods to help us handle
 * array of 9 integers. That array has 9 digits, and all digits
 * are distinct. Every digit in the array is : 0,1,2,3,4,5,6,7 and 8. 
 * There is also a method that tells us where the 0 is at, because zero is
 * actually a special character that represents blank
 * @author Lucija Valentić
 *
 */
public class KonfiguracijaSlagalice {

	/**
	 * Internal int array that represent one configuration of
	 * a {@link Slagalica}
	 */
	private int[] polje;
	
	/**
	 * Constructor
	 * @param polje
	 */
	public KonfiguracijaSlagalice(int[] polje) {
		this.polje = polje;
	}

	/**
	 * Returns copy of internal array
	 * this.polje
	 * @return int array
	 */
	public int[] getPolje() {
		return polje.clone();
	}
	
	/**
	 * Returns position of a number zero in 
	 * internal array this.polje
	 * @return int
	 */
	public int indexOfSpace() {
		
		for(int i = 0, n = polje.length; i < n; i++) {
			if(polje[i] == 0) {
				return i;
			}
		}
		
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0, j = 0; i < 3; i++, j += 3) {
		
			if(polje[j] == 0) {
				sb.append("* ");
			}else {
				sb.append(polje[j] + " ");
			}
			
			if(polje[j+1] == 0) {
				sb.append("* ");
			}else {
				sb.append(polje[j+1] + " ");
			}
			if(polje[j+2] == 0) {
				sb.append("*");
			}else {
				sb.append(polje[j+2]);
			}
			
			if(i != 2) {
				sb.append("\n");	
			}
			
		}
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(polje);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof KonfiguracijaSlagalice))
			return false;
		KonfiguracijaSlagalice other = (KonfiguracijaSlagalice) obj;
		return Arrays.equals(polje, other.polje);
	}
	
	
	
}
