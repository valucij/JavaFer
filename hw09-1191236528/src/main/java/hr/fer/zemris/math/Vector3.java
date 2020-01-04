package hr.fer.zemris.math;

/**
 * Class represents Vector, dimension of the vector is 3.
 * Every method in this class allows user to do some operations
 * with vector that he might learn in linear algebra. User
 * can add vectors together, multiplies them and etc. 
 * @author Lucija Valentić
 *
 */
public class Vector3 {

	/**
	 * X component
	 */
	private double x;
	/**
	 * Y component
	 */
	private double y;
	/**
	 * Z component
	 */
	private double z;
	
	/**
	 * Constructor
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector3(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Returns norm of this vector, or its 'size'
	 * @return double
	 */
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * Returns new vector that is this vector, but
	 * normalize, aka norm = 1
	 * @return Vector3
	 */
	public Vector3 normalized() {
		return scale((double)1/norm());
	}
	
	/**
	 * Returns new Vector, adds this vector with given one
	 * @param other
	 * @return Vector2
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}

	/**
	 * Returns new Vector, subtracts given vector from this one
	 * @param other
	 * @return Vector2
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}
	
	/**
	 * Method multiplies two vectors together, this product
	 * is actually scalar product of vectors.
	 * @param other
	 * @return double
	 */
	public double dot(Vector3 other) {
		return (x * other.x + y * other.y + z * other.z);
	}
	
	/**
	 * Method multiplies vectors together, but this product
	 * is what is in linear algebra known as vector product.
	 * Method returns new vector that is vertical with both
	 * this and given vector
	 * @param other
	 * @return vector
	 */
	public Vector3 cross(Vector3 other) {
		 return new Vector3( y * other.z - z * other.y, 
				 			 z * other.x - x * other.z,
				 			 x * other.y - y * other.x);
	}
	
	/**
	 * Method multiplies this vector with given scalar.
	 * Method returns new vector, and doesn't modify current one
	 * @param s
	 * @return vector
	 */
	public Vector3 scale(double s) {
		return new Vector3(x * s, y * s, z * s);
	}
	
	/**
	 * Method returns cos of the angle between this and given vector.
	 * Formula that is used for that is: if a and b are vectors,
	 * then cos(a,b) = (a*b)/(|a|*|b|)
	 * @param other
	 * @return double
	 */
	public double cosAngle(Vector3 other) {
		return (double)dot(other)/(norm()*other.norm());
		
	}
	
	/**
	 * Returns new array representation of this vector
	 * @return
	 */
	public double[] toArray(){
		double[] v = {x, y, z};
		return v;
	}
	/**
	 * Returns x component of this vector
	 * @return this.x
	 */
	public double getX() {
		return x;
	}
	/**
	 * Returns y component of this vector
	 * @return this.y
	 */
	public double getY() {
		return y;
	}
	/**
	 * Returns z component of this vector
	 * @return this.z
	 */
	public double getZ() {
		return z;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format("(%.6f, %.6f, %.6f)", x, y, z);
	}
}
