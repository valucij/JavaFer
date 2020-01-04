package hr.fer.zemris.java.gui.charts;

/**
 * Newly made exception that is used when working with
 * {@link BarChart}, {@link BarChartComponent} and {@link BarChartDemo}
 * if something goes wrong. It extends class {@link RuntimeException}
 * 
 * @author Lucija Valentić
 *
 */
public class IllegalDataException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public IllegalDataException() {
		super();
		
	}

	/**
	 * Constructor
	 * @param message
	 */
	public IllegalDataException(String message) {
		super(message);
		
	}
	
	

}
