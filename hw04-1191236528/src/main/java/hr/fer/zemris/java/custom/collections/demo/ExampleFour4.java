package hr.fer.zemris.java.custom.collections.demo;

import java.util.Iterator;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

public class ExampleFour4 {

	public static void main(String[] args) {
		
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				iter.remove(); // sam iterator kontrolirano uklanja trenutni element
			}
		}
		
		System.out.println("Ispis nakon uklanjanja Ivane s iteratorom: " + examMarks);
		System.out.println("Veličina nakon uklanjanja Ivane s iteratorom: " + examMarks.size());
	
	}

}
