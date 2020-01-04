package hr.fer.zemris.math;

/**
 * Class represent one complex polynom. That means
 * that all numbers and factors are complex numbers.
 * This polynom is in the form: zn*z^n + z(n-1)*z^(n-1)+....z1*z^1 + z0
 * There is lot of methods in this class that mimic things
 * people can usually do for polynom.
 * @author Lucija Valentić
 *
 */
public class ComplexPolynomial {

	/**
	 * Represents factors of this polynom
	 */
	private Complex[] factors;
	
	/**
	 * Constructor
	 * @param factors
	 */
	public ComplexPolynomial(Complex...factors) {
		
		this.factors = factors;
		
	}
	/**
	 * Returns order of this polynom
	 * @return short
	 */
	public short order() {
		return (short)(factors.length - 1);
	}
	
	/**
	 * Method returns new complex polynom, that
	 * polynom is result of multipling of this
	 * polynom and the given one.
	 * @param p
	 * @return complex polynomial
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		
		short m = p.order();
		short n = order();
		
		Complex[] result = new Complex[m + n + 1];
		
		for(int i = 0; i <= m + n ; i++) {
			
			Complex c = new Complex();	
			for(int k = 0; k <= i; k++) {

					
					while(i - k > m) {
						k++;
					}
				
					if(k > n) {
						break;
					}
	
					c = c.add(factors[k].multiply(p.factors[i-k]));	
	
			}
			
			result[i] = c;
			
		}
		
		return new ComplexPolynomial(result);
	}
	
	/**
	 * Method returns derivate of this vector. Returned
	 * polynom is newly created
	 * @return
	 */
	public ComplexPolynomial derive() {
		
		Complex[] derivate = new Complex[order()];
		
		for(int i = 0, n = order(); i < n; i++) {
			
			derivate[i] = factors[i + 1].multiply(new Complex(i + 1, 0));
			
		}
		
		return new ComplexPolynomial(derivate);
	}
	
	/**
	 * Method calculates value of complex point
	 * z in this polynom .
	 * @param z
	 * @return complex
	 */
	public Complex apply(Complex z) {
		
		Complex result = factors[order()];
		
		for(int i = order() - 1; i >= 0; i--) {
			result = result.multiply(z).add(factors[i]);
		}
		
		return result;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
	
		StringBuilder sb = new StringBuilder();
		
		for(int i = order(), n = factors.length, j = n - 1; i > 0; i--, j--) {
			sb.append("(" + factors[i].toString() + ")" + "*z^" + j + "+");
		}
		
		sb.append("(" + factors[0] + ")");
		
		return sb.toString();
	}
	
	
}
