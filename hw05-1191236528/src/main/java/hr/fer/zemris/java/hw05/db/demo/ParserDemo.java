package hr.fer.zemris.java.hw05.db.demo;

import hr.fer.zemris.java.hw05.db.QueryParser;
/**
 * Class to check if QueryParser works.
 * @author Lucija Valentić
 *
 */
public class ParserDemo {

	public static void main(String[] args) {
		QueryParser parser = new QueryParser("jmbag = \"23456812\" ");

		System.out.println("isDirectQuery() " + parser.isDirectQuery());//true
		System.out.println("jmbag was: " + parser.getQueriedJMBAG());//23456812
		System.out.println("size: " + parser.getQuery().size());//1
		
		QueryParser parser2 = new QueryParser("jmbag = \"23456812\" and lastName>\"J\"");

		System.out.println("isDirectQuery() " + parser2.isDirectQuery());//false
		try {
			System.out.println("jmbag was: " + parser2.getQueriedJMBAG());//exception
		}catch(IllegalStateException ex) {
			System.out.println("Očekivana greška.");
		}
		
		System.out.println("size: " + parser2.getQuery().size());//2
		
				
	}

}
