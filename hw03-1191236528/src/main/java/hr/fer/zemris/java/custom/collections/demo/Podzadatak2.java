package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.*;

/**
 * Class <code>Podzadatak2</code> is class that checks if newly added perks in 
 * interface Collection, ArrayIndexedCollection and LinkedListIndexedCollection
 * work.
 * 
 * @author Lucija Valentić
 *
 */
public class Podzadatak2 {

	public static void main(String[] args) {
		
		Collection col = new ArrayIndexedCollection(); 
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		
		ElementsGetter getter = col.createElementsGetter();
		
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		//System.out.println("Jedan element: " + getter.getNextElement()); throws exception
		
		System.out.println("");
		
		Collection col1 = new ArrayIndexedCollection(); // npr. new ArrayIndexedCollection();
		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");
		ElementsGetter getter1 = col1.createElementsGetter();
		
		System.out.println("Ima nepredanih elemenata: " + getter1.hasNextElement());
		System.out.println("Ima nepredanihelemenata: " + getter1.hasNextElement());
		System.out.println("Ima nepredanihelemenata: " + getter1.hasNextElement());
		System.out.println("Ima nepredanihelemenata: " + getter1.hasNextElement());
		System.out.println("Ima nepredanihelemenata: " + getter1.hasNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		
		System.out.println("Ima nepredanih elemenata: " + getter1.hasNextElement());
		
		System.out.println("Ima nepredanih elemenata: " + getter1.hasNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter1.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter1.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter1.hasNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		
		
		System.out.println("Ima nepredanih elemenata: " + getter1.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter1.hasNextElement());
		
		System.out.println("");
		
		Collection col2 = new ArrayIndexedCollection(); // npr. new ArrayIndexedCollection();
		
		col2.add("Ivo");
		col2.add("Ana");
		col2.add("Jasna");
		ElementsGetter getter2 = col2.createElementsGetter();
		System.out.println("Jedan element " + getter2.getNextElement());
		System.out.println("Jedan element " + getter2.getNextElement());
		System.out.println("Jedan element " + getter2.getNextElement());
		
		System.out.println("");
		
		Collection col3 = new ArrayIndexedCollection(); // npr. new ArrayIndexedCollection();
		col3.add("Ivo");
		col3.add("Ana");
		col3.add("Jasna");
		ElementsGetter getter3 = col3.createElementsGetter();
		ElementsGetter getter4 = col3.createElementsGetter();
		
		System.out.println("Jedan element je: " + getter3.getNextElement());
		System.out.println("Jedan element je: " + getter3.getNextElement());
		System.out.println("Jedan element je: " + getter4.getNextElement());
		System.out.println("Jedan element je: " + getter3.getNextElement());
		System.out.println("Jedan element je: " + getter4.getNextElement());
		
		System.out.println("");
		Collection col10 = new ArrayIndexedCollection();
		Collection col20 = new ArrayIndexedCollection();
		col10.add("Ivo");
		col10.add("Ana");
		col10.add("Jasna");
		col20.add("Jasmina");
		col20.add("Štefanija");
		col20.add("Karmela");
		ElementsGetter getter10 = col10.createElementsGetter();
		ElementsGetter getter20 = col10.createElementsGetter();
		ElementsGetter getter30 = col20.createElementsGetter();
		
		System.out.println("Jedan element je: " + getter10.getNextElement());
		System.out.println("Jedan element je: " + getter10.getNextElement());
		System.out.println("Jedan element je: " + getter20.getNextElement());
		System.out.println("Jedan element je: " + getter30.getNextElement());
		System.out.println("Jedan element je: " + getter30.getNextElement());
		
	}

}
