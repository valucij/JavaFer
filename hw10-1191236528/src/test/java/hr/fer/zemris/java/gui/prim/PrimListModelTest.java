package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class PrimListModelTest {

	@Test
	void testValidPrimes() {
		PrimListModel<Integer> model = new PrimListModel<Integer>();
		
		List<Integer> integers = new ArrayList<Integer>();
		
		integers.add(2);
		integers.add(3);
		integers.add(5);
		
		for(int i = 0; i < 3; i++) {
			model.next();
		}
		
		assertEquals(integers, model.getList());
		
	}
	
	@Test
	void testGetElementOnIndex() {
		
		PrimListModel<Integer> model = new PrimListModel<Integer>();
		
		List<Integer> integers = new ArrayList<Integer>();
		
		integers.add(2);
		integers.add(3);
		integers.add(5);
		
		for(int i = 0; i < 3; i++) {
			model.next();
		}
		
		assertEquals(3, model.getElementAt(1));
	}

	@Test
	void testGetSize() {
		
		PrimListModel<Integer> model = new PrimListModel<Integer>();
		
		List<Integer> integers = new ArrayList<Integer>();
		
		integers.add(2);
		integers.add(3);
		integers.add(5);
		
		for(int i = 0; i < 3; i++) {
			model.next();
		}
		
		assertEquals(3, model.getSize());
	}
	
	@Test
	void testGetPrime() {
		
		PrimListModel<Integer> model = new PrimListModel<Integer>();
		
		List<Integer> integers = new ArrayList<Integer>();
		
		model.next();
		integers.add(2);
		assertEquals(2, model.getPrim());
		model.next();
		integers.add(3);
		assertEquals(3, model.getPrim());
		model.next();
		integers.add(5);
		assertEquals(5, model.getPrim());
			
		
	}
}
