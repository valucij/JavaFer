package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.*;
/**
 * Class test example in task 6
 * 
 * @author Lucija Valentić
 *
 */

public class Podzadatak6 {

	/**
	 * Main method that is called in the beginning of a program.
	 * @param args arguments of command line
	 */
	public static void main(String[] args) {
		
		List col1 = new ArrayIndexedCollection();
		List col2 = new ArrayIndexedCollection();
		
		col1.add("Ivana");
		col2.add("Jasna");
		
		Collection col3 = col1;
		Collection col4 = col2;
		
		col1.get(0);
		col2.get(0);
		
		col1.forEach(System.out::println);
		col2.forEach(System.out::println);
		col3.forEach(System.out::println);
		col4.forEach(System.out::println);
	}
	
}
