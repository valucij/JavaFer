package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Class tests if class {@link ValueWrapper} works how it should
 * @author Lucija Valentić
 *
 */
class ValueWrapperTest {

	@Test
	void testWrapperNullElement() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());
		
		assertEquals(0, v1.getValue());
		assertEquals(null, v2.getValue());
		assertTrue( v1.getValue() instanceof Integer);
	}
	
	@Test
	void testWrapperDoubleAndIntegerElement() {
	
		ValueWrapper v1 = new ValueWrapper("1.2E1");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.add(v2.getValue());
		
		assertEquals(13.0, v1.getValue());
		assertEquals(1, v2.getValue());
		assertTrue( v1.getValue() instanceof Double);
		assertTrue( v2.getValue() instanceof Integer);
		
	}
	
	@Test
	void testWrapperIntegerAddElement() {
		
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.add(v2.getValue());
		
		assertEquals(13, v1.getValue());
		assertEquals(1, v2.getValue());
		assertTrue( v1.getValue() instanceof Integer);
		assertTrue( v2.getValue() instanceof Integer);
		
	}
	
	@Test
	void testWrapperIntegerMultiplyElement() {
		
		ValueWrapper v1 = new ValueWrapper("7");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(6));
		v1.multiply(v2.getValue());
		
		assertEquals(42, v1.getValue());
		assertEquals(6, v2.getValue());
		assertTrue( v1.getValue() instanceof Integer);
		assertTrue( v2.getValue() instanceof Integer);
		
	}
	
	@Test
	void testWrapperIntegerDivideElement() {
		
		ValueWrapper v1 = new ValueWrapper("42");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(6));
		v1.divide(v2.getValue());
		
		assertEquals(7, v1.getValue());
		assertEquals(6, v2.getValue());
		assertTrue( v1.getValue() instanceof Integer);
		assertTrue( v2.getValue() instanceof Integer);
		
	}

	
	@Test
	void testWrapperIntegerSubtractElement() {
		
		ValueWrapper v1 = new ValueWrapper("42");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(35));
		v1.subtract(v2.getValue());
		
		assertEquals(7, v1.getValue());
		assertEquals(35, v2.getValue());
		assertTrue( v1.getValue() instanceof Integer);
		assertTrue( v2.getValue() instanceof Integer);
		
	}
	
	@Test
	void testWrapperIntegerAndNullAddElement() {
		
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(7));
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());
		
		assertEquals(7, v1.getValue());
		assertEquals(null, v2.getValue());
		assertTrue( v1.getValue() instanceof Integer);
		assertFalse( v2.getValue() instanceof Integer);
		
	}
	
	@Test
	void testWrapperRuntimeException() {
		
		ValueWrapper v1 = new ValueWrapper("Ankica");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		
		try{
			v1.add(v2.getValue());	
		}catch(RuntimeException ex) {
			return;
		}
		fail();
	}
	
	@Test
	void testBooleanFirstRuntimeException() {
	
		ValueWrapper v1 = new ValueWrapper(Boolean.valueOf(true));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(5));
		
		try{
			v1.add(v2.getValue());	
		}catch(RuntimeException ex) {
			return;
		}
		fail();		
	}
	
	@Test
	void testBooleanSecondRuntimeException() {
	
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(5));
		ValueWrapper v2 = new ValueWrapper(Boolean.valueOf(true));
		
		try{
			v1.add(v2.getValue());	
		}catch(RuntimeException ex) {
			return;
		}
		fail();
			
	}
	
	@Test 
	void testTwoNullCompare() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		
		int compare = v1.numCompare(v2.getValue());
		
		assertEquals(0, compare);
		
	}
	
	@Test
	void testCompareIntegerAndNull() {
		
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(7));
		ValueWrapper v2 = new ValueWrapper(null);
		
		int compare = v1.numCompare(v2.getValue());
		
		assertEquals(1, compare);
		
	}
	
	@Test
	void testCompareDoubleAndNull() {
		
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(42.7));
		
		int compare = v1.numCompare(v2.getValue());
		
		assertEquals(-1, compare);
		
	}
	
	@Test
	void testCompareStringsFirstSmaller() {
		
		ValueWrapper v1 = new ValueWrapper("Lucija");
		ValueWrapper v2 = new ValueWrapper("Valentić");
		
		int compare = v1.numCompare(v2.getValue());
		
		if(compare < 0) {
			return;
		}
		
		fail();
		
	}
	
	@Test
	void testCompareEqualStrings() {
		
		ValueWrapper v1 = new ValueWrapper("Lucija");
		ValueWrapper v2 = new ValueWrapper("Lucija");
		
		int compare = v1.numCompare(v2.getValue());
		
		if(compare == 0) {
			return;
		}
		
		fail();
		
	}
	
	@Test
	void testCompareStringsFirstBigger() {
		
		ValueWrapper v1 = new ValueWrapper("Valentić");
		ValueWrapper v2 = new ValueWrapper("Lucija");
		
		int compare = v1.numCompare(v2.getValue());
		
		if(compare > 0 ) {
			return;
		}
		
		fail();
		
	}
	
	@Test
	void testAddStringsRuntimeException() {
		
		ValueWrapper v1 = new ValueWrapper("Valentić");
		ValueWrapper v2 = new ValueWrapper("Lucija");
		
		try {
			v1.add(v2.getValue());
		}catch(RuntimeException ex) {
			return;
		}
		
		fail();
		
	}
	
	@Test
	void testCompareStringsAndIntegerRuntimeException() {
		
		ValueWrapper v1 = new ValueWrapper("Valentić");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(7));
		
		try {
			v1.numCompare(v2.getValue());
		}catch(RuntimeException ex) {
			return;
		}
		
		fail();
		
	}
	
}
