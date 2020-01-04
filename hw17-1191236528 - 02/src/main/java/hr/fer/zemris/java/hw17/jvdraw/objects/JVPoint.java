package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.geom.Point2D;

/**
 * Class extends {@link Point2D}. It represents
 * geometrical point with few extra options
 * @author Lucija Valentić
 *
 */
public class JVPoint extends Point2D {

	/**
	 * X component
	 */
	private double x;
	/**
	 * Y component
	 */
	private double y;
	
	/**
	 * Constructor
	 * @param x
	 * @param y
	 */
	public JVPoint(double x, double y) {
		setLocation(x, y);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getX() {
		return this.x;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getY() {
		return this.y;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * Returns distance between two points
	 * @param other
	 * @return double
	 */
	public double distance(JVPoint other) {
		return Math.sqrt( Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
	}

}
