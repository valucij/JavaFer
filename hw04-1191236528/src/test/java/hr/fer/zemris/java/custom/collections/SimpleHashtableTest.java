package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

class SimpleHashtableTest {

	@Test
	void testSizeOfEmpty() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		
		assertEquals(0, examMarks.size());
	}
	
	@Test
	void testSizeNotEmpty() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		
		examMarks.put("Ivana",2);
		examMarks.put("Petra",4);
		examMarks.put("Josipa",3);
		examMarks.put("Iva",5);
		
		assertEquals(4, examMarks.size());
	}

	@Test
	void testSizeRewritingKey() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		
		examMarks.put("Ivana",2);
		examMarks.put("Petra",4);
		examMarks.put("Josipa",3);
		examMarks.put("Ivana",5);
		
		assertEquals(3, examMarks.size());
	}

	@Test
	void testIsEmpty() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		
		assertTrue(examMarks.isEmpty());
	}

	@Test
	void testIsNotEmpty() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		
		examMarks.put("Ivana",5);
		
		assertFalse(examMarks.isEmpty());
	}

	@Test
	void testPut() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		
		examMarks.put("Ivana", 5);
		examMarks.put("Lucija", 4);
		
		assertFalse(examMarks.isEmpty());
	}

	@Test
	void testKeyCannotBeNull() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		
		try {
			examMarks.put(null, 3);
		}catch(NullPointerException ex) {
			return;
		}
		
		fail();
	}

	@Test
	void testGetEmpty() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		
		assertEquals(null, examMarks.get(7));
	}

	@Test
	void testGetNotEmpty() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		
		examMarks.put("Lucija", 5);
		
		assertEquals(5,examMarks.get("Lucija"));
	}

	@Test
	void testContainsKey() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		
		examMarks.put("Lucija", 5);
		
		assertTrue(examMarks.containsKey("Lucija"));
	}

	@Test
	void testNotContainsKey() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		
		examMarks.put("Lucija", 5);
		
		assertFalse(examMarks.containsKey("Ivana"));
	}

	@Test
	void testContainsValue() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		
		examMarks.put("Lucija", 5);
		
		assertTrue(examMarks.containsValue(5));
	}

	@Test
	void testContainsValueNot() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		
		examMarks.put("Lucija", 5);
		
		assertFalse(examMarks.containsValue(7));
	}

	@Test
	void testRemove() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		
		examMarks.put("Lucija", 5);
		assertTrue(examMarks.containsKey("Lucija"));
		examMarks.remove("Lucija");
		
		assertFalse(examMarks.containsKey("Lucija"));
	}
	
	@Test
	void testToString() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		
		examMarks.put("Lucija", 5);
		assertEquals("[Lucija=5]", examMarks.toString());
		
	}
	
	@Test
	void testSimpleIterator() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		
		String string = "";
		for(SimpleHashtable.TableEntry<String,Integer> pair1 : examMarks) {
			string = string + pair1.getKey() + " " + pair1.getValue() +" ";
		}
		
		assertEquals("Ante 2 Ivana 5 Jasna 2 Kristina 5 ", string);
	}
	

	@Test
	void testRemoveIterator() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		
		
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				iter.remove(); 
			}
		}
		
		assertFalse(examMarks.containsKey("Ivana"));
	}
	

	@Test
	void testRemoveNotWithIteratorException() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		
		
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);	
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();

		try {
			while(iter.hasNext()) {
				SimpleHashtable.TableEntry<String,Integer> pair =  iter.next();			
				if(pair.getKey().equals("Ivana")) {
					examMarks.remove("Ivana");
				}
			}
		}catch(ConcurrentModificationException ex) {
			return;
		}
		
		fail();
	}
	

	@Test
	void testIteratorRemoveTwiceInARow() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		
		
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);

		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				
				try {
					iter.remove();
					iter.remove();
				}catch(IllegalStateException e) {
					return;
				}
				
			}
		}
		
		fail();
	}
	

	@Test
	void testIteratorHasNextTrue() {
		
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		
		
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		
		assertTrue(iter.hasNext());
	}

	@Test
	void testIteratorHasNextFalse() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		
		assertFalse(iter.hasNext());
	}


	@Test
	void testIteratorNext() {

		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		
		
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		
		assertEquals("Ante",iter.next().getKey());
		assertEquals(2,iter.next().getValue());
	}
	

	@Test
	void testIteratorNextException() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		
		try {
			iter.next();
		}catch(NoSuchElementException ex) {
			return;
		}
		fail();
	}

}
