package hr.fer.zemris.java.hw06.crypto;
/**
 * New exception, made to use when something in methods in package
 * hr.fer.zemris.java.hw06.crypto goes wrong
 * 
 * @author Lucija Valentić
 *
 */
public class CryptoException extends RuntimeException {

	/**
	 * Constructor
	 */
	public CryptoException() {
		super();
	}
	/**
	 * Constructor
	 * @param string
	 */
	public CryptoException(String string) {
		super(string);
	}
	
	
	private static final long serialVersionUID = 1L;

}
