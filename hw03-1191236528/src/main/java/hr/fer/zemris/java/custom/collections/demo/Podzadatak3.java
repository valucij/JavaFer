package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.*;
/**
 * Test example in task 3. Throws exception.
 * @author Lucija Valentić
 *
 */
public class Podzadatak3 {
/**
 * Main method that is called in the beginning of the program
 * @param args arguments of command line
 */
	public static void main(String[] args) {
		
		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		col.clear();
		System.out.println("Jedan element: " + getter.getNextElement());
	}
	
	
}
