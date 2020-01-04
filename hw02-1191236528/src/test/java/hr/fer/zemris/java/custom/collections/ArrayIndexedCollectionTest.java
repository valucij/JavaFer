package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * Class <code>ArrayIndexedCollectionTest</code> consist of tests for class
 * <code>ArrayIndexedCollection</code>
 * 		
 * @author Lucija Valentić
 *
 */
class ArrayIndexedCollectionTest {
/**
 * Checks if deafault constructor works.
 */
	@Test
	void testDefaultConstructor() {
		ArrayIndexedCollection l1 = new ArrayIndexedCollection();
		
		if(l1.equals(null)) {
			fail();
		}
		
	}
/**
 * Checks if constructor that sets capacity with initialCapacity works, 
 * second constructor.	
 */
	@Test
	void testConstructorInitialCapacity() {
		ArrayIndexedCollection l2 = new ArrayIndexedCollection(10);
		
		if(l2.equals(null)) {
			fail();
		}
		
	}
/**
 * Checks if constructor that fills collection with elements from other 
 * collection, and that sets capacity with sent initialCapacity works.	
 */
	@Test
	void testConstructorInitialAndCollection() {
		ArrayIndexedCollection p1 = new ArrayIndexedCollection(12);
		ArrayIndexedCollection l3 = new ArrayIndexedCollection(10, p1);
		
		if(l3.equals(null)) {
			fail();
		}
		
	}
/**
 * Checks of constructo that fills collection with elements from other
 * collection works.	
 */
	@Test
	void testConstructorCollection() {
		ArrayIndexedCollection p2 = new ArrayIndexedCollection(12);
		p2.add(7);
		ArrayIndexedCollection l4 = new ArrayIndexedCollection(p2);
		
		if(l4.equals(null)) {
			fail();
		}
		
	}
/**
 * Checks if method <code>void add(Object value)</code> throws NullPointerException if we
 * send it to put null pointer in collection.		
 */
	@Test
	void testAddThrowsNullPointerEx() {
		
		ArrayIndexedCollection l5 = new ArrayIndexedCollection(10);
		
		try {
			l5.add(null);
		}catch(NullPointerException ex) {
			return;
		}
		
		fail();
					
	}
/**
 * Checks if new collection, just made with default constructor, is empty. 	
 * 
 */
	@Test
	void testIsEmpty() {
		ArrayIndexedCollection l6 = new ArrayIndexedCollection();
		
		l6.isEmpty();
		
		assertEquals(true,l6.isEmpty());
		
	}
/**
 * Checks if method <code>void add(Object value)</code> works.	
 */
	@Test
	void testAdd() {
		ArrayIndexedCollection l7 = new ArrayIndexedCollection();
		
		l7.add(7);
		
		assertEquals(false, l7.isEmpty());
		
	}
/**
 * Checks if method <code>Object get(int index)</code> works.	
 */
	@Test
	void testGet() {
		ArrayIndexedCollection l8 = new ArrayIndexedCollection();
		
		l8.add(7);
		
		assertEquals(l8.get(0), 7);
	}
/**
 * Checks if method <code>void clear()</code> works.	
 */
	@Test
	void testClear() {
		ArrayIndexedCollection l7 = new ArrayIndexedCollection();
		
		l7.add(7);
		l7.add(42);
		l7.add(54);
		
		assertEquals(l7.get(0), 7);
		assertEquals(l7.get(1), 42);
		assertEquals(l7.get(2), 54);
		
		
		l7.clear();
		
		assertEquals(true, l7.isEmpty());
	}
/**
 * Checks if method <code>void insert(Object value, int position)</code> works.	
 */
	@Test
	void testInsert() {
		ArrayIndexedCollection l8 = new ArrayIndexedCollection();
		
		l8.add(42);
		l8.add(54);
			
		l8.insert(7, 1);	
		
		assertEquals(42,l8.get(0));
		assertEquals(7,l8.get(1));
		assertEquals(54,l8.get(2));
		
	}
/**
 * Checks if method <code>int indexOf(Object value)</code> works.	
 */
	@Test
	void testIndexOf() {
		
		ArrayIndexedCollection l9 = new ArrayIndexedCollection();
		
		l9.add(42);
		
		assertEquals(0,l9.indexOf(42));
		
		assertEquals(-1,l9.indexOf(7));
		
		
	}
/**
 * Checks if method <code>void remove(int position)</code>	works.
 */
	@Test
	void testRemove() {
		
		ArrayIndexedCollection l10 = new ArrayIndexedCollection();
		
		l10.add(42);
		l10.add(7);
		l10.add(54);
		
		l10.remove(1);
		
		assertEquals(54,l10.get(1));
		assertEquals(-1,l10.indexOf(7));
		
	}
/**
 * Checks if constructor that sets capacity throws IllegalArgumentException().	
 */
	@Test
	void testConstructorException() {
		
		try {
			ArrayIndexedCollection l1 = new ArrayIndexedCollection(-1);
		}catch(IllegalArgumentException ex) {return;}
		
		fail();
	}
/**
 * Checks if constructor with argument collection throws NullPointerException().	
 */
	@Test
	void testConstructorNullPointerException() {
		try {
			ArrayIndexedCollection l1 = new ArrayIndexedCollection(null);
		}catch(NullPointerException ex) {return;}
		
		fail();
	}
/**
 * 	Checks if constructor with argument collection and capacity throws NullPointerException().
 */
	@Test
	void testConstructorCapacityNullPointerException() {
		
		try {
			ArrayIndexedCollection l1 = new ArrayIndexedCollection(2,null);
		}catch(NullPointerException ex) {return;}
		
		fail();
	}

	
/**
 * Checks if <code>void add(Object value) throws NullPointerException();	
 */
	@Test
	void testAddException() {
		ArrayIndexedCollection l1 = new ArrayIndexedCollection();
		
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
		ArrayIndexedCollection l1 = new ArrayIndexedCollection();
		
		try {
			l1.get(7);
		}catch(IndexOutOfBoundsException ex) {return;}
		
		fail();
	}
/**
 * Checks if <code>void insert(Object value, int position)</code> throws IndexOutOfBoundsException().	
 */
	@Test
	void testInsertException() {
		ArrayIndexedCollection l1 = new ArrayIndexedCollection();
		
		try {
			l1.insert(7,123);
		}catch(IndexOutOfBoundsException ex) {return;}
		
		fail();
	}
/**
 * Checks if <code>void remove(int index)</code> throws IndexOutOfBoundsException().	
 */
	@Test
	void testRemoveException() {
		ArrayIndexedCollection l1 = new ArrayIndexedCollection();
		
		try {
			l1.remove(7);
		}catch(IndexOutOfBoundsException ex) {return;}
		
		fail();
	}
	/**
	 * Tests if method <code>void addAll(Collection col)</code> works.	
	 */
	@Test
	void testAddAll() {
		ArrayIndexedCollection l1 = new ArrayIndexedCollection();
		ArrayIndexedCollection l2 = new ArrayIndexedCollection();
		
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
		ArrayIndexedCollection l1 = new ArrayIndexedCollection();
		l1.add(7);
		
		assertEquals(true, l1.contains(7));
	}
	
	/**
	 * Tests if method <code>boolean remove(Object value)</code> works
	 * 
	 */
	@Test
	void testBooleanRemove() {
		ArrayIndexedCollection l1 = new ArrayIndexedCollection();
		
		l1.add("test");
		
		assertTrue(l1.remove("test"));
	}
	
	/**
	 * Test if method <code>Object[] toArray()</code> works.
	 */
	@Test
	void testToArray() {
		ArrayIndexedCollection l1 = new ArrayIndexedCollection();
		
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
		ArrayIndexedCollection l1 = new ArrayIndexedCollection();
		
		l1.add("7");
		
		Tester tester = new Tester();
		l1.forEach(tester);
		
		int number = Integer.valueOf((String)l1.get(0));
		
		assertNotEquals(42, l1.get(0));
		assertEquals(7,number);
	}
	/**
	 * Tests if method <code>int size()</code> works
	 */
	@Test
	void testSize() {
		ArrayIndexedCollection l1 = new ArrayIndexedCollection();
		
		l1.add(7);
		l1.add(42);
		
		assertEquals(2,l1.size());
	}
}
/**
 * Class <code>Tester</code> extends class <code>Processor</code> and helps in testing methods from 
 * class ArrayIndexedCollection and LinkedListIndexedCollection.
 * 
 * @author Lucija Valentić
 *
 */
	class Tester extends Processor{
		
		public void proces(Object value) {
			Integer.getInteger((String)value);
		}
	}
