package hr.fer.zemris.java.gui.layouts;

/**
 * Class that represents new exception. This exception can be used
 * when something with {@link CalcLayout} goes wrong.
 * 
 * @author Lucija Valentić
 *
 */
public class CalcLayoutException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 */
	public CalcLayoutException() {
		super();
	}
	
	/**
	 * Constructor
	 * @param string
	 */
	public CalcLayoutException(String string) {
		super(string);
	}

}
