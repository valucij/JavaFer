package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.*;


public class ProblemOneDemo {

	public static void main(String[] args) {
		
		ArrayIndexedCollection<String> col1 = new ArrayIndexedCollection<String>();
		
		col1.add("Hans Landa");
		//col1.add(7); error, can't add int in collection with strings
		
		System.out.printf("%s%nIf there is no element 7 (an int) in collection with strings, next number should be -1: %d%n", 
				col1.get(0),col1.indexOf(7));
		
		ArrayIndexedCollection<Integer> col2 = new ArrayIndexedCollection<Integer>();
		
		col2.add(7);
		
		System.out.printf("%d%nIf there is no sring \"Hans Landa\" in collection with integers, next word should be false: %s%n", 
				col2.get(0), col2.remove("Hans Landa"));
		
		System.out.println(col2.contains("Hans Landa"));
		System.out.println(col2.contains(7));
		
		
	}

}
