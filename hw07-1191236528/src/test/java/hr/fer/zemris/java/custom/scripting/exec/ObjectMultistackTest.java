package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * Tests to see if class {@link ObjectMultistack} works
 * @author Lucija Valentić
 *
 */
class ObjectMultistackTest {

	@Test
	void testMultistackPush() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		try {
			multistack.push("year", year);
			return;
		}catch(ObjectStackException ex) {
			fail();
		}	
	}
	
	@Test
	void testMultistackPeek() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		
		multistack.push("year", year);
		assertEquals(2000, multistack.peek("year").getValue());
	}
	
	@Test
	void testMultistackPop1() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		try {
			multistack.pop("year");
			
		}catch(ObjectStackException ex) {
			fail();
		}
	}
	@Test
	void testMultistackPop2() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
	
		multistack.push("year", new ValueWrapper(Integer.valueOf(1900)));
		assertEquals(1900, multistack.peek("year").getValue());
		
		multistack.pop("year");
		
		assertEquals(2000, multistack.peek("year").getValue());
	}

}
