package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * Tests if class {@link Util} works properly.
 * 
 * @author Lucija Valentić
 *
 */
class UtilTest {

	@Test
	void testByteToHex() {
		byte[] bytes = {01, -82, 34};
		
		assertEquals("01ae22", Util.bytetohex(bytes));
	}

	
	@Test
	void testHexToByte() {
		String string = "01aE22";
		byte[] bytes = {1, -82, 34};
		
		assertArrayEquals(bytes, Util.hextobyte(string));
		
	}
}
