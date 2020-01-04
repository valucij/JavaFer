package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.*;

/**
 * Class that test examples in task 4.
 * 
 * @author Lucija Valentić
 *
 */
public class Podzadatak4 {

	/**
	 * Main method that is called in the beginning of a program
	 * @param args arguments of command line
	 */
	public static void main (String[] args) {
		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		getter.getNextElement();
		getter.processRemaining(System.out::println);
	}
}
