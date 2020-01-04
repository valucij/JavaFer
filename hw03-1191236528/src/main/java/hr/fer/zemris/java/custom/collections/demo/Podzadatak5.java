package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.*;

/**
 * Class <code>EvenIntegerTester</code> implements interface <code>Tester</code>,
 *  has one method that check if given object is even or not.
 * 
 * @author Lucija Valentić
 *
 */
class EvenIntegerTester implements Tester{
	
	/**
	 * Method <code>test</code> checks if the given object is even or not.
	 * 
	 * @return <code>true</code> if given object is even, <code>false</code>
	 *  if given object is not even
 	 */
	public boolean test(Object obj) {
		if(!(obj instanceof Integer)) return false;
		Integer i = (Integer) obj;
		return i % 2 == 0;
	}
}

public class Podzadatak5 {
/**
 * Main method that is called on starting the program.
 * 
 * @param args arguments of command line
 */
	public static void main(String[] args) {	
		
		Tester t = new EvenIntegerTester();
		System.out.println(t.test("Ivo"));
		System.out.println(t.test(22));
		System.out.println(t.test(3));
		
		Collection col10 = new LinkedListIndexedCollection();
		Collection col20 = new ArrayIndexedCollection();
		
		System.out.println(" ");
		col10.add(2);
		col10.add(3);
		col10.add(4);
		col10.add(5);
		col10.add(6);
		
		col20.add(12);
		
		col20.addAllSatisfying(col10, new EvenIntegerTester());
		
		col20.forEach(System.out::println);
		
		System.out.println(" ");
		
		
		
	}

}
