package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

/**
 * This Class was used while writing a code for class <code>SimpleHashtable</code>.
 * It consists of examples from homework, as well as examples from myself, to
 * check if something needs fixing. If you want to check examples from homework,
 * use different demo.
 * 
 * @author Lucija Valentić
 *
 */

public class ProblemFourDemo {

	/**
	 * Main method that is called in the beginning of a program.
	 * 
	 * @param args arguments of command line
	 */
	public static void main(String[] args) {
		
		
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		
		//System.out.println((double)75/100);
		//System.out.println((double)(75/100));
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		Integer ivanaGrade = examMarks.get("Ivana");
		System.out.println("Ivana's exam grade is before fixing it: " + ivanaGrade); //writes 2
		examMarks.put("Ivana", 5); //overwrites old grade for Ivana
		
		Integer kristinaGrade = examMarks.get("Kristina");
		Integer ivanaGrade2 = examMarks.get("Ivana");
		System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes: 5
		System.out.println("Ivana's exam grade is after: " + ivanaGrade2); // writes: 2
		System.out.println("Number of stored pairs: " + examMarks.size()); // writes: 4
		System.out.println("");
		System.out.println("Ovo je ispis: " + examMarks);//[]
		
		
		/*examMarks.clear();
		System.out.println("Ovo je ispis nakon clear : " + examMarks);
		System.out.println("Ovo je duljina, veličina nakon clear: " + examMarks.size());
		*/
		
		
		examMarks.remove("Jasna");	
		System.out.println("Ovo je ispis nakon micanja \"Jasne\": " + examMarks);
		System.out.println("Ovo je duljina, veličina nakon micanja \"Jasne\": " + examMarks.size());
		
		examMarks.remove("Kristina");	
		System.out.println("Ovo je ispis nakon micanja \"Kristine\": " + examMarks);
		System.out.println("Ovo je duljina, veličina nakon micanja \"Kristine\": " + examMarks.size());
		
		examMarks.remove("Ante");	
		System.out.println("Ovo je ispis nakon micanja \"Ante\": " + examMarks);
		System.out.println("Ovo je duljina, veličina nakon micanja \"Ante\": " + examMarks.size());
		
		examMarks.remove("Ivana");	
		System.out.println("Ovo je ispis nakon micanja \"Ivane\": " + examMarks);
		System.out.println("Ovo je duljina, veličina nakon micanja \"Ivana\": " + examMarks.size());
		
	}

}
