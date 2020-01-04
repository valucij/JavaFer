package hr.fer.zemris.java.custom.collections.demo;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

public class ExampleFour6 {

	public static void main(String[] args) {

		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();

		try {
			while(iter.hasNext()) {
				SimpleHashtable.TableEntry<String,Integer> pair =  iter.next();			
				if(pair.getKey().equals("Ivana")) {
					examMarks.remove("Ivana");
				}
			}
		}catch(ConcurrentModificationException ex) {
			System.out.println("Očekivana greška jer se metoda remove "
					+ "pozvala izvana, dok se prolazilo kroz mapu pomoću iteratora.");
		}
		
		
	}

}
