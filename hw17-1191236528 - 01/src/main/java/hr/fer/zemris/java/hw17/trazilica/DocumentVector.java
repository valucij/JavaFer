package hr.fer.zemris.java.hw17.trazilica;

/**
 * Class represents n-dimensional vector. The only
 * reason it is called DocumentVector is because
 * it is used with methods in package hr.fer.zemris.java.hw17.trazilica.
 * It has all method like any other n-dimensional vector (only the 
 * ones that are needed in other classes).
 * 
 * @author Lucija Valentić
 *
 */
public class DocumentVector {

	/**
	 * Array od double values
	 */
	private double[] vector;
	
	/**
	 * Constructor
	 * @param vocabularySize
	 */
	public DocumentVector(int vocabularySize) {
		vector = new double[vocabularySize];
	}
	
	/**
	 * Constructor
	 * @param v
	 */
	public DocumentVector(double[] v) {
		this.vector = v;
	}
	
	/**
	 * Returns norm of this vector
	 * @return double
	 */
	public double norm() {
		
		double sum = 0;
		
		for(double d : this.vector) {
			sum += d * d; 
		}
		
		return Math.sqrt(sum);
		
	}
	
	/**
	 * Returns new vector. Newly returned vector
	 * is this vector, but normalized
	 * @return DocumentVector
	 */
	public DocumentVector normalized() {
		return scale((double)1/norm());
	}

	/**
	 * Returns new vector. Newly returned vector
	 * is this vector, but multiplied with given 
	 * value
	 * @param s double
	 * @return DocumentVector
	 */
	public DocumentVector scale(double s) {
		
		double[] v = this.vector;
		
		for(int i = 0, n = v.length; i < n; i++) {
			v[i] *= s;
		}
		
		return new DocumentVector(v);
	}
	
	/**
	 * Returns new vector. Newly returned vector
	 * is this vector plus given vector other
	 * @param other DocumentVector
	 * @return DocumentVector
	 */
	public DocumentVector add(DocumentVector other) {
		
		double[] v = this.vector;
		
		for(int i = 0, n = v.length; i < n; i++) {
			v[i] += other.vector[i];
		}
		
		return new DocumentVector(v);
			
	}

	/**
	 * Returns new vector. Newly returned vector is 
	 * this vector minus given vector other.
	 * @param other DocumentVector
	 * @return DocumentVector
	 */
	public DocumentVector sub(DocumentVector other) {
		
		double[] v = this.vector;
		
		for(int i = 0, n = v.length; i < n; i++) {
			v[i] -= other.vector[i];
		}
		
		return new DocumentVector(v);
			
	}
	/**
	 * Returns new vector. Newly returned vector
	 * is this vector multiply by given vector other
	 * @param other DocumentVector
	 * @return DocumentVector
	 */
	public DocumentVector multiply(DocumentVector other) {
		
		double[] v = this.vector;
		
		for(int i = 0, n = v.length; i < n; i++) {
			v[i] *= other.vector[i];
		}
		
		return new DocumentVector(v);
		
	}
	
	/**
	 * Returns scalar product of this and given vector
	 * @param other DocumentVector
	 * @return double
	 */
	public double dot(DocumentVector other) {
		
		double result = 0;
		
		for(int i = 0, n = other.vector.length; i < n; i++) {
			result += other.vector[i] * this.vector[i];
		}
		
		return result;
	}
	
	/**
	 * Returns cos of an angle between this and given vector
	 * @param other DocumentVector
	 * @return double
	 */
	public double cosAngle(DocumentVector other) {
		return (double)dot(other)/(norm()*other.norm());
	}
	
	/**
	 * Returns this.vector
	 * @return double[]
	 */
	public double[] getVector() {
		return this.vector;
	}
	
	/**
	 * Returns specific value from vector. Throws exception
	 * if given index is out of bounds 
	 * @param i int
	 * @return double
	 * @throws IndexOutOfBoundsException
	 */
	public double getDoubleOnIndex(int i) {
		
		if(i< 0 || i > vector.length) {
			throw new IndexOutOfBoundsException();
		}
		
		return vector[i];
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		boolean flag = true;
		
		sb.append("(");
		
		for(double d : this.vector) {
			
			if(flag) {
				flag = false;
			}else {
				sb.append(", ");
			}
			sb.append(String.valueOf(d));
			
		}
		
		sb.append(")");
		return sb.toString();
	}
	
}
