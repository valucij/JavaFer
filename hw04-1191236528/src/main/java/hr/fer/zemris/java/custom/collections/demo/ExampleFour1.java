package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

public class ExampleFour1 {

	public static void main(String[] args) {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
	
	
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
	
		
		Integer kristinaGrade = examMarks.get("Kristina");
		System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes: 5

		System.out.println("Number of stored pairs: " + examMarks.size()); // writes: 4
	}

}
