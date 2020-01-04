package hr.fer.zemris.math;

/**
 * This class represents one implementation
 * of polynom. This polynom is in form: z0*(z-z1)*(z-z2)*...
 * There is lot of methods in this class that mimic things
 * people can usually do for polynom . Also, all
 * numbers and roots in this polynom are complex, aka
 * in form a + bi
 * 
 * @author Lucija Valentić
 *
 */
public class ComplexRootedPolynomial {

	/**
	 * Represents z0 in polynom
	 */
	private Complex constant;
	/**
	 * Represents roots in polynom, aka 
	 * z1, z2, z3, ...
	 */
	private Complex[] roots;
	
	/**
	 * Constructor
	 * @param constant
	 * @param roots
	 */
	public ComplexRootedPolynomial(Complex constant, Complex...roots) {
		
		this.constant = constant;
		this.roots = new Complex[roots.length];
		
		for(int i = 0, n = roots.length; i < n; i++) {
			this.roots[i] = roots[i];
		}
	}
	
	/**
	 * Method calculates value of complex point
	 * z in this polynom .
	 * @param z
	 * @return complex
	 */
	public Complex apply(Complex z) {
		
		Complex result = constant;
		
		for(int i = 0, n = roots.length; i < n; i++) {
			result = result.multiply(z.sub(roots[i]));
		}
		
		return result;
		
	}
	
	/**
	 * Method returns new polynom, but in form
	 * zn*z^n + z(n-1)*z^(n-1)+....z1*z^1 + z0
	 * @return complex polynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		
		ComplexPolynomial result = new ComplexPolynomial(constant);
		
		for(int i = 0, n = roots.length; i < n; i++) {
			result = result.multiply(new ComplexPolynomial(roots[i].neg(), Complex.ONE));
		}

		return result;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
	
		StringBuilder sb = new StringBuilder();
		
		sb.append("(" + constant.toString() + ")");
		
		for(int i = 0, n = roots.length; i < n; i++) {
			sb.append("*(z-(" + roots[i].toString() + "))");
		}
		
		return sb.toString();
		
	}
	/**
	 * For given complex number z and given treshold, 
	 * it calculates the closest root (of this polynom) to given
	 * z, but distance between them has to be inside of the treshold
	 * @param z
	 * @param treshold
	 * @return
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
	
		int result = -1;
		double minDistance = Double.MAX_VALUE;
		
		for(int i = 0, n = roots.length; i < n; i++) {
			
			double distance = z.sub(roots[i]).module();
			
			if( distance < treshold && distance < minDistance) {
				minDistance = distance;
				result = i;
			}
			
		}
		
		return result;
		
	}
}
