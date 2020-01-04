package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;
/**
 * Test to see if method <code> LSystemBuilderImpl.generate()</code> works.
 * 
 * @author Lucija Valentić
 *
 */
class LSystemBuilderGenerateTest {

	@Test
	void test() {
		
		LSystemBuilderImpl lsb = new LSystemBuilderImpl();
		lsb.setAxiom("F");
		lsb.registerProduction('F', "F+F--F+F");
		
		LSystem ls = lsb.build();
		
		assertEquals("F", ls.generate(0));
		assertEquals("F+F--F+F", ls.generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", ls.generate(2));
	}

}
