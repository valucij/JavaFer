package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * Checks if class {@link FieldValueGetters} works.
 * @author Lucija Valentić
 *
 */
class FieldValueGettersTest {

	@Test
	void testLastName() {
		
		assertEquals("Valentić", FieldValueGetters.LAST_NAME.get(new StudentRecord("02132424","Lucija","Valentić","5")));
	}

	@Test
	void testFirstName() {
		assertEquals("Lucija", FieldValueGetters.FIRST_NAME.get(new StudentRecord("02132424","Lucija","Valentić","5")));
	}

	@Test
	void testJmbag() {
		assertEquals("02132424", FieldValueGetters.JMBAG.get(new StudentRecord("02132424","Lucija","Valentić","5")));
	}

}
