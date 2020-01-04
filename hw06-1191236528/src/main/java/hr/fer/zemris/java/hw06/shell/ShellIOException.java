package hr.fer.zemris.java.hw06.shell;

/**
 * New exception, it is used when working with programs
 * from package hr.fer.zemris.java.hw06.shell.
 * 
 * @author Lucija Valentić
 *
 */
public class ShellIOException extends RuntimeException {

	/**
	 * Constructor
	 */
	public ShellIOException() {
		super();
	}
	/**
	 * Constructor
	 * @param string
	 */
	public ShellIOException(String string) {
		super(string);
	}
	
	private static final long serialVersionUID = 1L;

}
