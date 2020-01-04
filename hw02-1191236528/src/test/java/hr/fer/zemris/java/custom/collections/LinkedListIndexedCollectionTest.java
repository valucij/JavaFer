package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Class <code>LinkedListIndexedCollectionTest</code> contains tests that check if methos from <code>LinkedListIndexedCollection</code> works.
 * @author lucijaval
 *
 */
class LinkedListIndexedCollectionTest {

/**
 * Checks if deafault constructor works.
 */
	@Test
	void testDefaultConstructor() {
		LinkedListIndexedCollection l1 = new LinkedListIndexedCollection();
		
		if(l1.equals(null)) {
			fail();
		}
		
	}
/**
 * 	Checks if constructor that creates list with another collection works.
 */
	@Test
	void testConstructoAnotherCollection() {
		LinkedListIndexedCollection l1 = new LinkedListIndexedCollection();
		LinkedListIndexedCollection l2 = new LinkedListIndexedCollection(l1);
		
		if(l2.equals(null)) {
			fail();
		}
		
	}
/**
 * Checks if <code>boolean isEmpty()</code> works.	
 */
	@Test
	void testIsEmpty() {
		LinkedListIndexedCollection l1 = new LinkedListIndexedCollection();
		
		assertEquals(true, l1.isEmpty());
	}
	
/**
 * Checks if <code>void add(Object value)</code> works.		
 */
	@Test
	void testAdd() {
		LinkedListIndexedCollection l1 = new LinkedListIndexedCollection();
		
		l1.add(7);
				
		assertEquals(false, l1.isEmpty());
		
	}
/**
 * Checks if <code>Object get(int index)</code> works.		
 */
	@Test
	void testGet() {
		LinkedListIndexedCollection l1 = new LinkedListIndexedCollection();
		
		l1.add(7);
		
		assertEquals(7,l1.get(0));
	}
	
/**
 * Checks if <code>void clear()</code> works.		
 */
	
	@Test
	void testClear() {
		LinkedListIndexedCollection l1 = new LinkedListIndexedCollection();
		
		l1.add(7);
		l1.add(42);
		
		l1.clear();
		
		assertEquals(true, l1.isEmpty());
		
	}
/**
 * Checks if <code>int indexOf(Object value)</code> works.		
 */
	@Test
	void testIndexOf() {
		LinkedListIndexedCollection l1 = new LinkedListIndexedCollection();
		
		l1.add(7);
		l1.add(42);
		l1.add(54);
		
		assertEquals(0, l1.indexOf(7));
		assertEquals(1, l1.indexOf(42));
		assertEquals(2, l1.indexOf(54));
		
	}
/**
 * Checks if <code>void insert(Object value, int position)</code> works.		
 */
	@Test
	void testInsert() {
		LinkedListIndexedCollection l1 = new LinkedListIndexedCollection();
		
		l1.insert(7, 0);
		l1.insert(42, 1);
		l1.insert(54, 1);
		
		assertEquals(0, l1.indexOf(7));
		assertEquals(1, l1.indexOf(54));
		assertEquals(2, l1.indexOf(42));
		
	}
/**
 * Checks if <code>int size()</code> works.		
 */
	@Test
	void testSize() {
		
		LinkedListIndexedCollection l1 = new LinkedListIndexedCollection();
		
		l1.insert(7, 0);
		l1.insert(42, 1);
		l1.insert(54, 1);
		
		assertEquals(3,l1.size());
	}
/**
 * Checks if <code>void remove(Object value)</code> works.		
 */
	@Test
	void testRemove() {
		
		LinkedListIndexedCollection l1 = new LinkedListIndexedCollection();
		
		l1.add(7);
		l1.add(42);
		l1.add(54);
		
		l1.remove(1);
		
		assertEquals(-1,l1.indexOf(42));
		assertEquals(54,l1.get(1));
		assertEquals(7,l1.get(0));
		assertEquals(2,l1.size());
		
	}
/**
 * Checks if constructor with argument collection throws NullPointerException();	
 */
	@Test
	void testConstructorThrowsException() {
		
		try {
			LinkedListIndexedCollection l1 = new LinkedListIndexedCollection(null);
		}catch(NullPointerException ex) {return;}
		
		fail();
		
			
	}
/**
 * Checks if <code>void add(Object value)</code> throws NullPointerException().	
 */
	@Test
	void testAddThrowsException() {
		LinkedListIndexedCollection l1 = new LinkedListIndexedCollection();
		
		try {
			l1.add(null);
		}catch(NullPointerException ex) {return;}
		
		fail();
		
	}
/**
 * Checks if <code>Object get(int index)</code> throws IndexOutOfBoundsException(). 	
 */
	@Test
	void testGetException() {
		LinkedListIndexedCollection l1 = new LinkedListIndexedCollection();
		
		try {
			l1.get(10);
		}catch(IndexOutOfBoundsException ex) {return;}
		
		fail();
		
	}
/**
 * Checks if <code>void insert(Object value, int position)</code> throws IndexOutOfBoundsException().	
 */
	@Test
	void testInsertException() {
		LinkedListIndexedCollection l1 = new LinkedListIndexedCollection();
		
		try {
			l1.insert(10,10);
		}catch(IndexOutOfBoundsException ex) {return;}
		
		fail();
		
	}
/**
 * Checks if <code>void remove(int index)</code> throws IndexOutOfBoundsException().	
 */
	@Test
	void testRemoveException() {
		LinkedListIndexedCollection l1 = new LinkedListIndexedCollection();
		
		try {
			l1.remove(10);
		}catch(IndexOutOfBoundsException ex) {return;}
		
		fail();
		
	}
	/**
	 * Tests if method <code>void addAll(Collection col)</code> works.	
	 */
	@Test
	void testAddAll() {
		LinkedListIndexedCollection l1 = new LinkedListIndexedCollection();
		LinkedListIndexedCollection l2 = new LinkedListIndexedCollection();
		
		l1.add(7);
		l1.add(42);
		l1.add(52);
		
		l2.addAll(l1);
		
		assertEquals(7,l2.get(0));
		assertEquals(42,l2.get(1));
		assertEquals(52,l2.get(2));
		
		
	}
	
	/**
	 * Tests if method <code>boolean contains(Object value)</code> works.
	 */
	@Test
	void testCointains() {
		LinkedListIndexedCollection l1 = new LinkedListIndexedCollection();
		l1.add(7);
		
		assertEquals(true, l1.contains(7));
	}
	
	/**
	 * Tests if method <code>boolean remove(Object value)</code> works
	 * 
	 */
	@Test
	void testBooleanRemove() {
		LinkedListIndexedCollection l1 = new LinkedListIndexedCollection();
		
		l1.add("test");
		
		assertTrue(l1.remove("test"));
	}
	
	/**
	 * Test if method <code>Object[] toArray()</code> works.
	 */
	@Test
	void testToArray() {
		LinkedListIndexedCollection l1 = new LinkedListIndexedCollection();
		
		l1.add(7);
		l1.add(42);
		Object[] newArray = l1.toArray();
		Object[] trueArray = {7,42};
		
		assertArrayEquals(trueArray, newArray);
	}
	
	/**
	 * Tests if method <code>void forEach(Processor proces)</code> works
	 */
	@Test
	void testForEach() {
		LinkedListIndexedCollection l1 = new LinkedListIndexedCollection();
		
		l1.add("7");
		
		Tester tester = new Tester();
		l1.forEach(tester);
		
		int number = Integer.valueOf((String)l1.get(0));
		
		assertNotEquals(42, l1.get(0));
		assertEquals(7,number);
	}
		
}

