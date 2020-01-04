package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
/**
 * Class checks if class {@link ComparisonOperators} works.
 * 
 * @author Lucija Valentić
 *
 */
class ComparisonOperatorsTest {

	@Test
	void testLess() {
		assertTrue(ComparisonOperators.LESS.satisfied("A", "B"));
		assertFalse(ComparisonOperators.LESS.satisfied("Zadatak", "Test"));	
	}

	@Test
	void testLessOrEquals() {
		assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("A", "B"));
		assertFalse(ComparisonOperators.LESS_OR_EQUALS.satisfied("Zadatak", "Test"));
		assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("A", "A"));
		assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("Test", "Test"));
	}

	@Test
	void testGreater() {
		assertFalse(ComparisonOperators.GREATER.satisfied("A", "B"));
		assertTrue(ComparisonOperators.GREATER.satisfied("Zadatak", "Test"));	
		
	}
	@Test
	void testGreaterOrEquals() {
		assertFalse(ComparisonOperators.GREATER_OR_EQUALS.satisfied("A", "B"));
		assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Zadatak", "Test"));
		assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("A", "A"));
		assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Test", "Test"));
		
	}
	@Test
	void testEquals() {
		assertTrue(ComparisonOperators.EQUALS.satisfied("A", "A"));
		assertTrue(ComparisonOperators.EQUALS.satisfied("Test", "Test"));
		
	}
	@Test
	void testNotEquals() {
		assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("Test", "A"));
		assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("B", "Test"));
		
	}
	@Test
	void testLike() {
		
		assertTrue(ComparisonOperators.LIKE.satisfied("AAAA", "AA*AA"));
		assertTrue(ComparisonOperators.LIKE.satisfied("AAAA", "*AA"));
		assertTrue(ComparisonOperators.LIKE.satisfied("AAAA", "AAAA"));
		assertTrue(ComparisonOperators.LIKE.satisfied("AAAA", "AAAA*"));
		assertFalse(ComparisonOperators.LIKE.satisfied("AAA", "AA*AA"));
		assertFalse(ComparisonOperators.LIKE.satisfied("Zagreb", "Aba*"));
	}

}
