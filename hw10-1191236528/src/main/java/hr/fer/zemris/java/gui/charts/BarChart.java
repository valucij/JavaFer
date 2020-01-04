package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Instances of this class represents all the informations
 * needed to make a graph using {@link BarChartComponent}.
 * @author Lucija Valentić
 *
 */
public class BarChart {

	/**
	 * Represents list of all the pairs of x-y values.
	 * This values function like pairs in function.
	 * There can me more x with the same value, but with each
	 * x, there can be only one distinct y
	 */
	private List<XYValue> list;
	/**
	 * Name of x axis
	 */
	private String descX;
	/**
	 * Name of y axis
	 */
	private String descY;
	/**
	 * Minimum value that can be produced in the graph
	 */
	private int yMin;
	/**
	 * Maximum value that can be produced in the graph
	 */
	private int yMax;
	/**
	 * Difference between two y values
	 */
	private int diff;
	
	/**
	 * Constructor
	 * @param list xy values
	 * @param descX name of x axis
	 * @param descY name of y axis
	 * @param yMin min value of y
	 * @param yMax max value of y
	 * @param diff difference between y
	 */
	public BarChart(List<XYValue> list, String descX, String descY, int yMin, int yMax, int diff) {
		super();
		this.list = list;
		this.descX = descX;
		this.descY = descY;
		this.yMin = yMin;
		this.yMax = yMax;
		this.diff = diff;
	
		if(yMin < 0 || yMax < yMin) {
			throw new IllegalArgumentException();
		}
		
		for(XYValue value : list) {
			int y = value.getY();
			if(y < yMin) {
				throw new IllegalArgumentException();
			}
		}
		
		
	}

	/**
	 * Method returns this.list
	 * @return List<XYValue> 
	 */
	public List<XYValue> getList() {
		return list;
	}
	/**
	 * Method returns this.descX
	 * @return String
	 */
	public String getDescX() {
		return descX;
	}

	/**
	 * Method returns this.descY
	 * @return String
	 */
	public String getDescY() {
		return descY;
	}

	/**
	 * Method returns this.yMin
	 * @return int
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * Method returns this.yMax
	 * @return int
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * Method returns this.diff
	 * @return int
	 */
	public int getDiff() {
		return diff;
	}
	
	
	
}
