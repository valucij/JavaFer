package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FactorialTest {

/**
 * <code>test1</code> provjerava izračunava li metoda
 * <code>fact</code> faktorijelu dobro.
 */
	@Test
	public void test1() {
		
		long n = Factorial.fact(5);
		assertEquals(120, n);
	}
/**
 * <code>test2</code> provjerava da li metoda 
 * <code>fact</code> throw-a IllegalArgumentException
 * ako se pošalje neki cijeli broj od kojeg se ne 
 * može izračunati faktorijela.	
 */
	@Test
	public void test2() {
		
		try{
			Factorial.fact(-1);
		}catch(IllegalArgumentException ex){
			return;
		}
		fail();
	}

}
