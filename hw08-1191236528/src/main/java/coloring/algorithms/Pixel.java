package coloring.algorithms;

import java.util.Objects;

import marcupic.opjj.statespace.coloring.FillApp;
/**
 * Class that represents one pixel on the screen. It is used 
 * for {@link FillApp}
 * @author Lucija Valentić
 *
 */
public class Pixel {

	/**
	 * Reference to the x coordinate of a pixel
	 */
	public int x;
	/**
	 * Reference to the y coordinate of a pixel
	 */
	public int y;
	
	/**
	 * Constructor
	 * @param x
	 * @param y
	 */
	public Pixel(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
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
		if (!(obj instanceof Pixel))
			return false;
		Pixel other = (Pixel) obj;
		return x == other.x && y == other.y;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
