package hr.fer.zemris.java.hw02.demo;

import hr.fer.zemris.java.hw02.*;
/**
 * Class <code>ComplexDemo</code> serves just to show how complex numbers work, 
 * and what can somebody do with them.
 * 
 * @author Lucija Valentić
 *
 */
public class ComplexDemo {

	/**
	 * Main method that is called in the beginning of program.
	 * 	
	 * @param args arguments of command line
	 */
	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2,3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57))
				.div(c2).power(3).root(2)[1];
		
		System.out.println(c3);
		
		ComplexNumber c10 = new ComplexNumber(2,1);
		ComplexNumber c20 = new ComplexNumber(-2,1);
		ComplexNumber c30 = new ComplexNumber(2,-1);
		ComplexNumber c40 = new ComplexNumber(-2,-1);
		
		
		System.out.println(c10.getAngle());
		System.out.println(c20.getAngle());
		System.out.println(c30.getAngle());
		System.out.println(c40.getAngle());
		
		ComplexNumber c21 = ComplexNumber.parse("+2.71+3.51i");
		ComplexNumber c22 = ComplexNumber.parse("+2.71");
		ComplexNumber c23 = ComplexNumber.parse("+i");
		System.out.println(c21);
		System.out.println(c22);
		System.out.println(c23);
		
	}
}
