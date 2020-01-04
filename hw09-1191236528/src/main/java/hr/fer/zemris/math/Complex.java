package hr.fer.zemris.math;

import java.util.LinkedList;
import java.util.List;

public class Complex {

	/**
	 * Imaginary part of complex number
	 */
	private double im;
	/**
	 * Real part of complex number
	 */
	private double re;
	/**
	 * Represents complex zero, (normal zero)
	 */
	public static final Complex ZERO = new Complex(0, 0);
	/**
	 * Number one
	 */
	public static final Complex ONE = new Complex(1, 0);
	/**
	 * Number minus one
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/**
	 * Imaginary number i
	 */
	public static final Complex IM = new Complex(0, 1);
	/**
	 * Imaginary number -i
	 */
	public static final Complex IM_NEG = new Complex(0, -1);
	
	/**
	 * Constructor
	 * @param re
	 * @param im
	 */
	public Complex(double re, double im) {
		super();
		this.im = im;
		this.re = re;
	}

	/**
	 * Constructor
	 */
	public Complex() {
		super();
		this.im = 0;
		this.re = 0;
	}
	
	/**
	 * Returns module of complex number
	 * @return double
	 */
	public double module() {
		return Math.sqrt(re * re + im * im);
	}
	
	/**
	 * Returns new complex number, it multiplies this
	 * complex number with given one.
	 * @param c
	 * @return complex number
	 */
	public Complex multiply(Complex c) {
	
		return new Complex(re * c.re - im * c.im, im * c.re + re * c.im);
	}

	/**
	 * Returns new complex number, it divides this
	 * complex number with given one.
	 * @param c
	 * @return complex number
	 */
	public Complex divide(Complex c) {
		
		if(c == ZERO) {
			throw new IllegalArgumentException();
		}
		
		double denominator = Math.pow(c.module(), 2);
		
		return new Complex((double) (re * c.re + im * c.im)/denominator, 
						   (double) (im * c.re - re * c.im)/denominator);
	}
	

	/**
	 * Returns new complex number, it adds this
	 * complex number to given one.
	 * @param c
	 * @return complex number
	 */
	public Complex add(Complex c) {
	
		return new Complex(re + c.re, im + c.im);
	}

	/**
	 * Returns new complex number, it subtract given
	 * complex number from this complex number.
	 * @param c
	 * @return complex number
	 */
	public Complex sub(Complex c) {
		return new Complex(re - c.re, im - c.im);
	}
	

	/**
	 * Returns new complex number, negative form
	 * of this complex number
	 * @param c
	 * @return complex number
	 */
	public Complex neg() {
		return new Complex(-re, -im);
	}
	
	/**
	 * Returns new complex number, sets this
	 * complex number to the power of given n. If
	 * n is negative number, exception is thrown
	 * @param c
	 * @return complex number
	 * @throws IllegalArgumentsException()
	 */
	public Complex power(int n) {
		 
		if(n < 0) {
			throw new IllegalArgumentException();
		}
		
		double r = Math.pow(module(), n);
		double angle = getAngle();
		
		return new Complex( r * Math.cos(n * angle), r * Math.sin(n * angle));		
	}
	
	/**
	 * Returns angle of this complex number. Angle
	 * returned is always between 0 and 2Pi
	 * @return double
	 */
	private double getAngle() {
		
		if(re == 0) {
			if(im == 0) {
				return 0;
			}
			return im < 0 ? 3 * ((double) Math.PI/2) : (double) Math.PI/2;
		}
		
		double angle = Math.atan(Math.abs(im/re));
		
		if(re > 0.0 && im > 0.0) {}
		
		if(re < 0.0 && im > 0.0) {angle = Math.PI - angle;}
		
		if(re < 0.0 && im < 0.0) {angle = Math.PI + angle;}
		
		if(re > 0.0 && im < 0.0) {angle = 2 * Math.PI - angle;}
		
		if(angle < 0) {
			angle += 2 * Math.PI;
		}
		
		return angle;
		
	}
	/**
	 * Returns list of n-roots of this complex number.
	 * If given n is negative, exception is thrown
	 * @param n
	 * @return List<Complex>
	 * @throws IllegalArgumentException()
	 */
	public List<Complex> root(int n){
		
		if(n < 0) {
			throw new IllegalArgumentException();
		}
		
		List<Complex> list = new LinkedList<>();
		
		double r = Math.pow(module(), (double)1/n);
		double angle = getAngle();
		
		for(int i = 0; i < n; i++) {
			double x = Math.cos( (double)(angle + 2*i*Math.PI)/n);
			double y = Math.sin( (double)(angle + 2*i*Math.PI)/n);
			list.add(new Complex(r * x, r * y));
		}
		
		return list;
		
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
	
		if(im < 0) {
			return re + "-" + "i" + (im * -1);
		}
		return re + "+" + "i" + im;
	}
}
