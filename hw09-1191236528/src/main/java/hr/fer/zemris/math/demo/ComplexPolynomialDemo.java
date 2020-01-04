package hr.fer.zemris.math.demo;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class checks if classes {@link ComplexPolynomial} and
 * {@link ComplexRootedPolynomial} work.
 * @author Lucija Valentić
 *
 */
public class ComplexPolynomialDemo {

	/**
	 * Main method called in the beginning of the program
	 * @param args arguments of command line
	 */
	public static void main(String[] args) {
		
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG);
		ComplexPolynomial cp = crp.toComplexPolynom();
			
		System.out.println("crp: " + crp);
		System.out.println("cp " + cp);
		System.out.println("cp.derive(): " + cp.derive());
		
		ComplexRootedPolynomial c = new ComplexRootedPolynomial(Complex.ONE, new Complex(1,0), new Complex(-1,0), new Complex(0,1), new Complex(0,-1));
		Complex number = new Complex(-2.5, 1.2);
		
		System.out.println(c.apply(number));
		
		
	}
	
}
