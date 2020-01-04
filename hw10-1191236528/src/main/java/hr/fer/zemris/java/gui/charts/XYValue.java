package hr.fer.zemris.java.gui.charts;

/**
 * Class represents one pair of numbers. Pair consists
 * of x and of y value. 
 * @author Lucija Valentić
 *
 */
public class XYValue {

	/**
	 * Represents x value
	 */
	private int x;
	/**
	 * Represents y value
	 */
	private int y;
	
	/**
	 * Constructor
	 * @param x new int value
	 * @param y new int value
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	/**
	 * Returns this.x
	 * @return int
	 */
	public int getX() {
		return x;
	}
	/**
	 * Returns this.y
	 * @return int
	 */
	public int getY() {
		return y;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
}
