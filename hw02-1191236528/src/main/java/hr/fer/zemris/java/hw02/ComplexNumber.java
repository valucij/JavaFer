package hr.fer.zemris.java.hw02;

import java.lang.Math;
/**
 * Class <code>ComplexNumber</code> gives possibility to work with complex
 * numbers in the same way we work with integer numbers, of decimal numbers.
 * Real and imaginary part of complex number can only be double. None of the
 * methods change anything, if they have to change something, then they create
 * new complex numbers and return them.
 * Next example shows how to work with it. 
 * 
 * <pre>
 * 	ComplexNumber c1 = new ComplexNumber(2,3);
 * 	ComplexNumber c2 = c1.add(2.3,4.6);
 * 
 * 	System.out.println(c2);
 * </pre>
 * 
 * @author Lucija Valentić
 *
 */
public class ComplexNumber {
	/**
	 * Real part of complex number
	 */
	private double real;
	/**
	 * Imaginary part of complex number
	 */
	private double imaginary;

	/**
	 * Constructo that creates one complex number. <code>real</code> becomes
	 * real part of complex number, <code>imaginary</code> becomes imaginary part
	 * of complex number.
	 * 
	 * @param real becomes real part of complex number
	 * @param imaginary becomes imaginary part of complex number
	 * 
	 */
	public ComplexNumber(double real, double imaginary) {
		
		this.real = real;
		this.imaginary = imaginary;
				
	}
	/**
	 * Method <code>calculateMagnitude</code> calulates magnitude from given real and
	 * imaginary part of complex number.
	 * 	
	 * @param real real part of complex number
	 * @param imaginary imaginary part of complex number
	 * @return magnitude that is calculated
	 */
	private double calculateMagnitude(double real, double imaginary) {
		
		return Math.sqrt(real * real + imaginary* imaginary);
	}
	/**
	 * Method <code>calculateAngle</code> calculates angle of complex number with
	 * given real and imaginary part
	 * 	
	 * @param real double that represents real part of complex number
	 * @param imaginary double that represents imaginary part of complex number
	 * @return angle double that represents angle of complex number
	 */
	private double calculateAngle(double real, double imaginary) {
		
		double angle = Math.atan(Math.abs(imaginary/real));
		
		//if(real > 0.0 && imaginary > 0.0) {return angle;}
		
		if(real < 0.0 && imaginary > 0.0) {angle = Math.PI - angle;}
		
		if(real < 0.0 && imaginary < 0.0) {angle = Math.PI + angle;}
		
		if(real > 0.0 && imaginary < 0.0) {angle = 2 * Math.PI - angle;}
		
		if(angle < 0.0) {
			angle += Math.PI;
		}
		
		return angle;
		
	}
	
	/**
	 * Creates new complex number from magnitude and angle.
	 * 	
	 * @param magnitude magnitude of complex number
	 * @param angle angle of complex number
	 * @return complex number that is calculated from magnitude and angle
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		
		ComplexNumber newnumber = new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
		
		return newnumber;
	}
	
	
	/**
	 * Creates new complex number from just given imaginary part. Real part is 0.0.
	 * 	
	 * @param imaginary double which becomes imaginary part of new complex number
	 * @return complex number that is made with imaginary part
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		
		ComplexNumber newnumber = new ComplexNumber(0.0,imaginary);
		return newnumber;
	}
	
	/**
	 * Creates new complex number from just given real part. Imaginary part is 0.0.
	 * 	
	 * @param real double which becomes real part of new complex number
	 * @return complex number that is made with real part
	 */
	public static ComplexNumber fromReal(double real) {
		
		ComplexNumber newnumber = new ComplexNumber(real, 0.0);
		
		return newnumber;
		
	}
	/**
	 * Method <code>getReal</code> returns real part of complex number
	 * 	
	 * @return real double that is real part of complex number
	 */
	public double getReal() {
		return real;
	}
	/**
	 * Method <code>getImaginary</code> returns imagianry part of complex number
	 * 	
	 * @return imaginary double that is imaginary part of complex number
	 */
	public double getImaginary() {
		return imaginary;
	}
	/**
	 * Method <code>getMagnitude</code> returns magnitude of complex number	
	 * 
	 * @return magnitude double that is magnitude of complex number
	 */
	public double getMagnitude() {
		return calculateMagnitude(real, imaginary);
	}
	/**
	 * Method <code>getAngle</code> returns angle of complex number
	 * 	
	 * @return angle double that is angle of complex number
	 */
	public double getAngle() {
		return calculateAngle(real, imaginary);
	}
	/**
	 * Method <code>add</code> adds given complex number with this complex number.
	 * 	
	 * @param c given complex number, which we must add with this
	 * @return newnumber complex number that is calculated
	 */
	public ComplexNumber add(ComplexNumber c) {
		
		ComplexNumber newnumber = new ComplexNumber(c.getReal(),c.getImaginary());
		
		newnumber.real+= this.real;
		newnumber.imaginary+= this.imaginary;
		
		return newnumber;
		
	}
	/**
	 * Method <code>sub</code> subtracts from this complex number given complex number.	
	 * 
	 * @param c given complex number which we must subtrack from this one
	 * @return newnumber complex number that is calculated
	 */
	public ComplexNumber sub(ComplexNumber c) {
		
		ComplexNumber newnumber = new ComplexNumber(this.real, this.imaginary);
		
		newnumber.real-= c.getReal();
		newnumber.imaginary-= c.getImaginary();
		
		return newnumber;
		
	}
	/**
	 * Method <code>mul</code> multiplies this complex number with given.
	 * 	
	 * @param c given complex number that we have to multiply with this one
	 * @return newnumber complex number that is calculated
	 */
	public ComplexNumber mul(ComplexNumber c) {
		
		ComplexNumber newnumber = new ComplexNumber(1.0, 1.0);
		
		newnumber.real = c.getReal() * this.real - c.getImaginary() * this.imaginary;
		newnumber.imaginary = c.getReal() * this.imaginary + c.getImaginary() * this.real;
		
		return newnumber;
	}
	/**
	 * Method <code>changeImaginarySign</code> changes sign in front of imaginary
	 * part of complex number.
	 * 	
	 * @param c given complex number that we must change
	 * @return newnumber complex number with changed imaginary part sign
	 */
	public ComplexNumber changeImaginarySign(ComplexNumber c) {
		
		ComplexNumber newnumber = new ComplexNumber(c.real, -c.imaginary);
		
		return newnumber;
		
	}
	
	/**
	 * Method <code>div</code> divides this complex number with given one.
	 * 	
	 * @param c given complex number with which we divide this one
	 * @return newnumber complex number that is calculated
	 */
	public ComplexNumber div(ComplexNumber c) {
		//nazivnik - denominator
		//brojnik - numerator
		
		if(c.getReal() == 0.0 && c.getImaginary() == 0.0) {
			throw new IllegalArgumentException();
		}
		
		ComplexNumber numerator = new ComplexNumber(this.real, this.imaginary);
		ComplexNumber denominator = new ComplexNumber(c.getReal(), c.getImaginary());
		
		
		ComplexNumber numerator1 = numerator.mul(changeImaginarySign(c));
		ComplexNumber denominator1 = denominator.mul(changeImaginarySign(c));
		
		ComplexNumber newnumber = new ComplexNumber((numerator1.getReal())/(denominator1.getReal()), (numerator1.getImaginary())/(denominator1.getReal()));
		
		return newnumber;
	}
	/**
	 * Method <code>power</code> raises this complex number on given power <code>n</code>.
	 * 	
	 * @param n we raise this complex power on n
	 * @return newnumber complex number that is calculated
	 */
	public ComplexNumber power(int n) {
		
		if(n < 0) {
			throw new IllegalArgumentException();
		}
		
		double magnitude = this.getMagnitude();
		double radius = Math.pow(magnitude, n);
				
		ComplexNumber newnumber = new ComplexNumber(radius * Math.cos(this.getAngle() * n), radius * Math.sin(this.getAngle() * n));
		
		return newnumber;
	}
	/**
	 * Method <code>root</code> calculates n-th root of complex number.
	 * 	
	 * @param n number that shows which root we should calculate
	 * @return numebers[n] array of complex numbers with n-th roots of complex numbers
	 */
	public ComplexNumber[] root(int n) {
		
		if(n <= 0) {
			throw new IllegalArgumentException();
		}
		
		ComplexNumber numbers[] = new ComplexNumber[n];
				
		double radius = this.getMagnitude() * this.getMagnitude();
		
		int help = n;
		while(help > 0) {
			radius = Math.sqrt(radius);
			help--;
		}
		
		for(int i = 0; i < n; i++) {
			
			double phiAngle = (this.calculateAngle(real, imaginary) + 2 * Math.PI * i) /n;
			numbers[i] = new ComplexNumber(radius * Math.cos(phiAngle),radius * Math.sin(phiAngle));
			
		}
		
		return numbers;
		
	}
	/**
	 * Method <code>toString</code> "changes" this complex number to string, so
	 * when we want to write complex number on command line, we can. Written form
	 * is this: a+bi; or a-bi; where a is real part, and b is imaginary part.
	 * 
	 * @return newstring string that represents complex number in written form	
	 */
	@Override
	public String toString() {
		
		if(imaginary == 0.0) {
			
			return String.format("%.2f", real);
		}else if(real == 0.0) {
			
			if(imaginary == 1.0 || imaginary == -1.0) {
				String sign = imaginary > 0 ? "" : "-";
				
				return sign + "i";
			}else {
				return String.format("%.2fi", imaginary);
			}
			
		}else {
			String sign = imaginary > 0 ? "+" : "-";
			
			return String.format("%.2f%s%.2fi", real, sign, Math.abs(imaginary));
		}
		
	}
	/**
	 * Method <code>parsingRealPart</code> extracts just real part of complex
	 * from given string. If something goes wrong, or there is no valid real parts,
	 * it throws exceptions. Sometimes, we don't have to find real part, but
	 * then we return 0.0 to signal it.
	 * 	
	 * @param s given string from which we must extract real part of complex number
	 * @return returnvalue double that represents real part of complex number, or
	 * 		  0.0 if we didn't find real part
	 * @throws NumberFormatException is we cannot extract real part from string
	 */
	private static double parsingRealPart(String s) throws NumberFormatException{
		
		double returnvalue = 0.0;
		
		try {
			
			returnvalue = Double.parseDouble(s);
			return returnvalue;
			
		}catch(NumberFormatException ex) { }
		
		int indexlastplus = s.lastIndexOf("+");
		int indexlastminus = s.lastIndexOf("-");
		
		
		if(indexlastminus + 1 == indexlastplus || indexlastplus + 1 == indexlastminus) {
			if(indexlastminus != -1 && indexlastplus != -1)
			throw new NumberFormatException();
		}
		
		if(indexlastplus == 0 && indexlastminus != -1) {
			
			returnvalue = Double.parseDouble(s.substring(0, indexlastminus));
			return returnvalue;
		}
		
		if(indexlastplus != -1) {
			
			String newString = s.substring(indexlastplus);
			if(isMaybeComplex(newString) && s.substring(0, indexlastplus).isEmpty()) {
		
				return 0.0;
			}
			
			returnvalue = Double.parseDouble(s.substring(0, indexlastplus));
			return returnvalue;
				
		}
		
		if(indexlastminus != -1) {
			
			if(s.subSequence(0, indexlastminus).equals("")) {
				return 0.0;
			}
			
			returnvalue = Double.parseDouble(s.substring(0, indexlastminus));
			return returnvalue;
							
		}
	
		
		return 0.0;
		
		
	}
	/**
	 * Method that checks if characters between last plus and last 'i' can be number double,
	 * because that means that given string consists of imaginary part of complex number
	 * 
	 * @param string
	 * @return true if it is imaginary part, and false if it's not
	 */
	public static boolean isMaybeComplex(String string) {
		
		int indexCharacterI = string.lastIndexOf('i');
		
		if(indexCharacterI == -1) {
			return false;
		}
		
		if(indexCharacterI == 1) {
			return true;
		}
		
		String newString = string.substring(1, indexCharacterI);
		try {
			
			Double.parseDouble(newString);
			
		}catch(NumberFormatException ex) {
			return false;
		}
		return true;
	}
	
	/**
	 * Method <code>parsingImaginaryPart</code> exctracts imaginary part, if
	 * possible from given string. If there is no imaginary part, it is
	 * signalized by returning 0.0. If it is not possible to extract imaginary part
	 * because something is written wrong, or there is too much 'i', or
	 * number that should become imaginary part can't be parsed in double, 
	 * this method throws NumberFormatException.
	 * 
	 * 	
	 * @param s given string from which we try to extract imaginary part of complex number
	 * @return returnvalue double that represents imaginary part of complex number,
	 * 		 or 0.0 if there is no error, but there is no imaginary part there
	 * @throws NumberFormatException if somethings wrong, number that should be
	 * 		  imaginary part cannot be parsed in double
	 */
	private static double parsingImaginaryPart(String s) throws NumberFormatException{
		
		double returnvalue = 0.0;
		
		int indexlasti = s.lastIndexOf("i");
		int indexlastplus = s.lastIndexOf("+");
		int indexlastminus = s.lastIndexOf("-");
		
		if(indexlasti == -1) {
			returnvalue = Double.parseDouble(s);
			return 0.0;
		}
		
		if(indexlasti != -1 && !s.substring(s.length()-1).equals("i")) {

			throw new NumberFormatException();
		}
		
		
		if(indexlastminus + 1 == indexlastplus || indexlastplus + 1 == indexlastminus) {
			if(indexlastminus != -1 && indexlastplus != -1)
			throw new NumberFormatException();
		}
		
		if(indexlasti == 0) {
			
					
			return 1.0;
		}
		
		
		if(indexlastminus != -1 && indexlastminus + 1 == indexlasti) {
			return -1.0;
		}
		
		if(indexlastplus != 1 && indexlastplus + 1 == indexlasti) {
			return 1.0;
		}
		
		
		try {
			
			returnvalue = Double.parseDouble(s.substring(0,indexlasti));
			return returnvalue;
			
		}catch(NumberFormatException ex) { }
		
		
		
		if(indexlastplus > indexlastminus && indexlastplus != -1) {
			
			if(indexlastplus + 1 == indexlasti) {return 1.0;}
			
			returnvalue = Double.parseDouble(s.substring(indexlastplus, indexlasti));
			return returnvalue;
		}
		
		if(indexlastminus > indexlastplus && indexlastminus != -1) {
			
			if(indexlastminus + 1 == indexlasti) {return -1.0;}
			
			returnvalue = Double.parseDouble(s.substring(indexlastminus, indexlasti));
			return returnvalue;
			
		}
		
				
		return 0.0;
	}
	/**
	 * Method <code>parse</code> from given string s trys to extract complex number.
	 * It calls other method to help it, and real part and imaginary part
	 * of complex number is calculated separately.
	 * 	
	 * @param s given string from which we try to extract complex number
	 * @return newnumber extracted complex number from string
	 * @throws NumberFormatException if complex number cannot be extracted from string,
	 * 		 because there is no complex number in string
	 */
	public static ComplexNumber parse(String s) throws NumberFormatException{
		
		ComplexNumber newnumber = new ComplexNumber(0.0,0.0);
			
			
		newnumber.real = parsingRealPart(s);			
		newnumber.imaginary = parsingImaginaryPart(s);
					
		
		if(newnumber.real == 0.0 && newnumber.imaginary == 0.0) {
			throw new NumberFormatException();
		}
		
		return newnumber;
	}
	
	
	
}
