package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Class <code>ComplexNumberTest</code> consists of test to check if methods
 * from ComplexNumbers work.
 * 
 * @author Lucija Valentić
 *
 */
class ComplexNumberTest {
/**
 * Checks if constructor of complex numbers works.
 */
	@Test
	void test1() {
		
		ComplexNumber c1 = new ComplexNumber(2,3);
		
		if(c1.equals(null)) {
			fail();
		}
	}
/**
 * Checks if method <code>double getReal()</code> works.	
 */
	
	@Test
	void test16() {
		ComplexNumber c2 = new ComplexNumber(2,3);
				
		assertEquals(2.0, c2.getReal());
				
	}
	
/**
 * Checks if method <code>double getImaginary()</code> works.	
 */
	
	@Test
	void test17() {
		ComplexNumber c2 = new ComplexNumber(2,3);
				
		assertEquals(3.0, c2.getImaginary());
				
	}	
/**
 * Checks if method <code>ComplexNumber add(ComplexNumber c)</code> works.	
 */
	@Test
	void test2() {
		ComplexNumber c2 = new ComplexNumber(2,3);
		ComplexNumber c3 = new ComplexNumber(1,2);
		ComplexNumber c4 = c3.add(c2);
		
		assertEquals(3.0, c4.getReal());
		assertEquals(5.0, c4.getImaginary());
		
	}
/**
 * Checks if method <code>ComplexNumber sub(ComplexNumber c)</code> works.	
 */
	@Test
	void test3() {
		ComplexNumber c5 = new ComplexNumber(7,8);
		ComplexNumber c6 = new ComplexNumber(1,4);
		ComplexNumber c7 = c5.sub(c6);
		
		assertEquals(6.0, c7.getReal());
		assertEquals(4.0, c7.getImaginary());
		
	}

/**
 * Checks if method <code>ComplexNumber mul(ComplexNumber c)</code> works.	
 */	
	@Test
	void test4() {
		
		ComplexNumber c8 = new ComplexNumber(2,1);
		ComplexNumber c9 = new ComplexNumber(3,4);
		ComplexNumber c10 = c9.mul(c8);
		
		assertEquals(2.0, c10.getReal());
		assertEquals(11.0, c10.getImaginary());
	}

/**
 * Checks if method <code>ComplexNumber div(ComplexNumber c)</code> works.	
 */	
	@Test
	void test5() {
		ComplexNumber c1 = new ComplexNumber(5,1);
		ComplexNumber c2 = new ComplexNumber(2,3);
		ComplexNumber c3 = c1.div(c2);
		
		assertEquals(1.0, c3.getReal());
		assertEquals(-1.0, c3.getImaginary());
	}

/**
 * Checks if method <code>ComplexNumber changeImaginarySign(ComplexNumber c)</code> works.	
 */	
	@Test
	void test6() {
		ComplexNumber c1 = new ComplexNumber(5,1);
		
		c1 = c1.changeImaginarySign(c1);
		
		assertEquals(-1,c1.getImaginary());
		
	}

/**
 * Checks if method <code>double getAngle()</code> works.	
 */	
	@Test
	void test7() {
		ComplexNumber c1 = new ComplexNumber(1,1);
		
		assertEquals(Math.PI/4,c1.getAngle());
	}

/**
 * Checks if method <code>double getMagnitude()</code> works.	
 */	
	@Test
	void test8() {
		ComplexNumber c1 = new ComplexNumber(1,1);
		
		assertEquals(Math.sqrt(2),c1.getMagnitude());
	}

/**
 * Checks if method <code>ComplexNumber power(ComplexNumber c)</code> works.	
 */	
	@Test
	void test9() {
		ComplexNumber c1 = new ComplexNumber(2.0,0.0);
		
		c1 = c1.power(2);
		
		assertEquals(4, c1.getReal());
		
	}

/**
 * Checks if method <code>ComplexNumber root(ComplexNumber c)</code> works.	
 */	
	@Test
	void test10() {
		ComplexNumber c1 = new ComplexNumber(4, 0.0);
		
		ComplexNumber[] numbers = c1.root(2);
		
		assertEquals(2,numbers[0].getReal());
		assertEquals(0,numbers[0].getImaginary());

		
	}

/**
 * Checks if method <code>ComplexNumber fromReal(double real)</code> works.	
 */	
	@Test
	void test11() {
		
		ComplexNumber c2 = ComplexNumber.fromReal(1.0);
		
		assertEquals(1, c2.getReal());
		assertEquals(0, c2.getImaginary());
	}

/**
 * Checks if method <code>ComplexNumber fromImaginary(double imaginary)</code> works.	
 */	
	@Test
	void test12() {
		ComplexNumber c1 = ComplexNumber.fromImaginary(1.0);
		
		assertEquals(0, c1.getReal());
		assertEquals(1, c1.getImaginary());
	}

/**
 * Checks if method <code>String toString()</code> works.	
 */
	@Test
	void test13() {
		
		ComplexNumber c1 = new ComplexNumber(1.0,3.0);
		
		assertEquals("1.00+3.00i", c1.toString());
	}

/**
 * Checks if method <code>ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle)</code> works.	
 */
	@Test
	void test14() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(2.0, 0.0);
		
		assertEquals(2, c1.getReal());
	}

/**
 * Checks if <code>ComplexNumber div(ComplexNumber c)</code> throws IllegalArgumentsException;	
 */
	@Test
	void testDivException() {
		ComplexNumber c1 = new ComplexNumber(1.0,1.0);
		ComplexNumber c2 = new ComplexNumber(0.0,0.0);
		
		try {
			c1.div(c2);
		}catch(IllegalArgumentException ex) {return;}
		
		fail();
	}
/**
 * Checks if <code>ComplexNumber power(int n)</code> throws IllegalArgumentException().	
 */
	@Test
	void testPowerException() {
		ComplexNumber c1 = new ComplexNumber(1.0,1.0);
				
		try {
			c1.power(-5);
		}catch(IllegalArgumentException ex) {return;}
		
		fail();
	}
/**
 * Checks if <code>ComplexNumber root(int n)</code> throws IllegalArgumentException().		
 */
	@Test
	void testRootException() {
		ComplexNumber c1 = new ComplexNumber(1.0,1.0);
		
		try {
			c1.root(-5);
		}catch(IllegalArgumentException ex) {return;}
		
		fail();
	}
/**
 * Checks if <code>ComplexNumber parse(String s)</code> throws NumberFormatException().
 */
	@Test
	void testParseException() {
		
		try {
			ComplexNumber c1 = ComplexNumber.parse("abc");
		}catch(NumberFormatException ex) {return;}
		
		fail();
	}
/**
 * Checks if <code>Complex Number parse(String s)</code> works.	
 */
	@Test
	void testParse() {
		ComplexNumber c1 = ComplexNumber.parse("2.3-3.2i");
		
		assertEquals(2.3, c1.getReal());
		assertEquals(-3.2, c1.getImaginary());
	}
	
}
