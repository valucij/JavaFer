package hr.fer.zemris.java.gui.layouts;

/**
 * Class that represents position in 2D space. This
 * class is actually pair of two numbers, one represents 
 * row in some table, and other represents column in said
 * table
 * 
 * @author Lucija Valentić
 *
 */
public class RCPosition {

	/**
	 * Represents first number, row in table
	 */
	private int row;
	/**
	 * Represents second number, column in table
	 */
	private int column;
	
	/**
	 * Constructor
	 * @param row
	 * @param column
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	/**
	 * Returns row
	 * @return this.row
	 */
	public int getRow() {
		return row;
	}
	/**
	 * Returns column
	 * @return this.column
	 */
	public int getColumn() {
		return column;
	}
	
	
	
}
