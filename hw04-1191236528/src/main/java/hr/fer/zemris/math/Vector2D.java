package hr.fer.zemris.math;

/**
 * Class that represents one Vector2D, or more specifically
 * a point in 2D space. Like with any point, objects type
 * Vector2D can be translated, rescaled, rotated.
 * Next example will show what can be done with Vector2D
 * 
 * <pre>
 * 
 * Vector2D newVector = new Vector2D(1.0, 0.0);
 * newVector.translate(2.0, 4.0); -> newVectors coordinates are now (3.0, 4.0)
 * Vector2D second = newVector.translated(1.0, 0.0); -> second vectors 
 * 								coordinates are (4.0, 4.0), and newVector
 * 								coordinate are (3.0, 4.0)
 * Vector2D third = newVector.copy(); -> third vectors coordinates
 * 										are (3.0, 4.0)
 * 
 * </pre>
 * 
 * @author Lucija Valentić
 *
 */
public class Vector2D {

	/**
	 * Represents value of point on x-axis, type double
	 */
	private double x;
	/**
	 * Represents value of point on y-axis, type double
	 */
	private double y;
	
	/**
	 * Constructor
	 * @param x
	 * @param y
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * Returns this.x;
	 * @return x
	 */
	public double getX() {
		return x;
	}
	/**
	 * Returns this.y
	 * @return y
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Translated this Vector2D by given offset in coordinate plane
	 * @param offset 
	 */
	public void translate(Vector2D offset) {
		x += offset.x;
		y += offset.y;
	}
	/**
	 * Makes new Vector2D that is made by translating this Vector2D
	 * by given offset. This Vector2D is unchanged
	 * @param offset
	 * @return Vector2D
	 */
	public Vector2D translated(Vector2D offset) {
		
		return new Vector2D(x + offset.x, y + offset.y);
		
	}
	/**
	 * Rotates this Vector2D by given angle in coordinate plane
	 * @param angle
	 */
	public void rotate(double angle) {
		
		x = radius() * Math.cos(angle + existingAngle());
		y = radius() * Math.sin(angle + existingAngle());
		
		
	}
	
	/**
	 * Makes new Vector2D that is made by rotating this Vector2D
	 * by given angle. This Vector2D is unchanged
	 * @param angle
	 * @return Vector2D
	 */
	public Vector2D rotated(double angle) {
	
		Vector2D vector = this.copy();
		vector.rotate(angle);
		
		return vector;
		
	}
	/**
	 * Rescales this Vector2D with given scaler
	 * @param scaler
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}
	
	/**
	 * Returns new Vector2D that is made by rescaling this Vector2D.
	 * This Vector2D is left unchanged
	 * @param scaler
	 * @return Vector2D
	 */
	public Vector2D scaled(double scaler) {
		
		return new Vector2D(x * scaler, y * scaler);
	}
	
	/**
	 * Returns a copy of this Vector2D. This Vector2D is left
	 * unchanged.
	 * @return Vector2D
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
	/**
	 * Private method that returns radius of a circle
	 * on which Vector2D lives.
	 * @return double that represents radius
	 */
	private double radius() {
		return Math.sqrt(x * x + y * y);
	}
	/**
	 * Private method that returns angle of this Vector2D,
	 * or more specifically, where this Vector2D is on the circle,
	 * based on the angle
	 * @return angle
	 */
	private double existingAngle() {
		
		if(x == 0.0 && y == 0.0) {
			return 0.0;
		} else if(x == 0) {
			return Math.acos((double) y/radius());
		}else {
			return Math.acos((double) x/radius());
		}
	}
}
