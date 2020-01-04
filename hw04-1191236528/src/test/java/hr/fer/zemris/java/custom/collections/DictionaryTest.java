package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DictionaryTest {

	@Test
	void testIsEmpty() {
		Dictionary<Integer, Integer> dictionary = new Dictionary<Integer, Integer>();
		
		assertTrue(dictionary.isEmpty()); 
	}
	@Test
	void testIsNotEmpty() {
		Dictionary<Integer, Integer> dictionary = new Dictionary<Integer, Integer>();
		dictionary.put(7, 42);
		
		assertTrue(!dictionary.isEmpty()); 
	}
	
	@Test
	void testSizeEmpty() {
		Dictionary<Integer, Integer> dictionary = new Dictionary<Integer, Integer>();
		
		assertEquals(0,dictionary.size()); 
	}
		
	@Test
	void testSizeSomeSameKeysIntegers() {
		Dictionary<Integer, Integer> dictionary = new Dictionary<Integer, Integer>();
		
		dictionary.put(7, 42);
		dictionary.put(5, 42);
		dictionary.put(6, 42);
		dictionary.put(7, 423);
		assertEquals(3,dictionary.size()); 
	}
	
	@Test
	void testSizeAllDifferentKeysIntegers() {
		Dictionary<Integer, Integer> dictionary = new Dictionary<Integer, Integer>();
		
		dictionary.put(7, 42);
		dictionary.put(5, 42);
		dictionary.put(6, 42);
		dictionary.put(12, 423);
		assertEquals(4,dictionary.size()); 
	}
	
	@Test
	void testGetEmptyIntegers() {
		Dictionary<Integer, Integer> dictionary = new Dictionary<Integer, Integer>();
		
		assertEquals(null,dictionary.get(7)); 
	}
	
	@Test
	void testGetExistingIntegers() {
		Dictionary<Integer, Integer> dictionary = new Dictionary<Integer, Integer>();
		dictionary.put(7, 42);
		
		assertEquals(42, dictionary.get(7)); 
	}
	
	@Test
	void testGetNotExistingIntegers() {
		Dictionary<Integer, Integer> dictionary = new Dictionary<Integer, Integer>();
		dictionary.put(7, 42);
		
		assertEquals(null, dictionary.get(231)); 
	}
	
	@Test
	void testPutAllDifferentKeysIntegers() {
		Dictionary<Integer, Integer> dictionary = new Dictionary<Integer, Integer>();
		
		dictionary.put(7, 42);
		dictionary.put(5, 54);
		dictionary.put(6, 76);
		
		assertEquals(42, dictionary.get(7));
		assertEquals(54, dictionary.get(5));
		assertEquals(76, dictionary.get(6));

	}
	
	@Test
	void testPutSomeSameKeysIntegers() {
		Dictionary<Integer, Integer> dictionary = new Dictionary<Integer, Integer>();
		
		dictionary.put(7, 42);
		dictionary.put(5, 54);
		dictionary.put(6, 76);
		
		assertEquals(42, dictionary.get(7));
		assertEquals(54, dictionary.get(5));
		assertEquals(76, dictionary.get(6));
		
		dictionary.put(7, 24);
		dictionary.put(5, 45);
		dictionary.put(6, 67);
		
		assertEquals(24, dictionary.get(7));
		assertEquals(45, dictionary.get(5));
		assertEquals(67, dictionary.get(6));

	}
	
	
	
	@Test
	void testSizeSomeSameKeysStringMixIntegers() {
		Dictionary<String, Integer> dictionary = new Dictionary<String, Integer>();
		
		dictionary.put("7", 42);
		dictionary.put("5", 42);
		dictionary.put("6", 42);
		dictionary.put("7", 423);
		assertEquals(3,dictionary.size()); 
	}
	
	@Test
	void testSizeAllDifferentKeysStringMixIntegers() {
		Dictionary<String, Integer> dictionary = new Dictionary<String, Integer>();
		
		dictionary.put("7", 42);
		dictionary.put("5", 42);
		dictionary.put("6", 42);
		dictionary.put("12", 423);
		assertEquals(4,dictionary.size()); 
	}
	
	@Test
	void testGetEmptyStringMixIntegers() {
		Dictionary<String, Integer> dictionary = new Dictionary<String, Integer>();
		
		assertEquals(null,dictionary.get(7)); 
	}
	
	@Test
	void testGetExistingStringMixIntegers() {
		Dictionary<String, Integer> dictionary = new Dictionary<String, Integer>();
		dictionary.put("7", 42);
		
		assertEquals(42, dictionary.get("7")); 
	}
	
	@Test
	void testGetNotExistingStringMixIntegers() {
		Dictionary<String, Integer> dictionary = new Dictionary<String, Integer>();
		dictionary.put("7", 42);
		
		assertEquals(null, dictionary.get("235")); 
	}
	
	@Test
	void testPutAllDifferentKeysStringMixIntegers() {
		Dictionary<String, Integer> dictionary = new Dictionary<String, Integer>();
		
		dictionary.put("7", 42);
		dictionary.put("5", 54);
		dictionary.put("6", 76);
		
		assertEquals(42, dictionary.get("7"));
		assertEquals(54, dictionary.get("5"));
		assertEquals(76, dictionary.get("6"));

	}
	
	@Test
	void testPutSomeSameKeysStringMixIntegers() {
		Dictionary<String, Integer> dictionary = new Dictionary<String, Integer>();
		
		dictionary.put("7", 42);
		dictionary.put("5", 54);
		dictionary.put("6", 76);
		
		assertEquals(42, dictionary.get("7"));
		assertEquals(54, dictionary.get("5"));
		assertEquals(76, dictionary.get("6"));
		
		dictionary.put("7", 24);
		dictionary.put("5", 45);
		dictionary.put("6", 67);
		
		assertEquals(24, dictionary.get("7"));
		assertEquals(45, dictionary.get("5"));
		assertEquals(67, dictionary.get("6"));

	}
	
	@Test
	void testClear() {
		Dictionary<String, Integer> dictionary = new Dictionary<String, Integer>();
		
		dictionary.put("7", 42);
		dictionary.put("5", 54);
		dictionary.put("6", 76);
		
		assertEquals(3,dictionary.size());
		dictionary.clear();
		assertEquals(0, dictionary.size());
		
	}
	
	

}
