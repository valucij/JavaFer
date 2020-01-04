package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
/**
 * Main class for Database. In main method of this class,
 * you can search through database provided in text document named
 * "datoteka.txt". Next example shows how you can do that.
 * 
 * <pre>
 *  INPUT: > query jmbag = "0000000003"
 *	
 *  OUTPUT: Using index for record retrieval.
 *  +============+========+========+===+
 *  | 0000000003 | Bosnić | Andrea | 4 |
 *  +============+========+========+===+
 *  Records selected: 1
 *  
 *  INPUT: > query jmbag = "0000000003" AND lastName LIKE "B*"
 *  
 *  OUTPUT:
 *  +============+========+========+===+
 *	| 0000000003 | Bosnić | Andrea | 4 |
 *	+============+========+========+===+
 *	Records selected: 1
 *	
 *	INPUT:> query jmbag = "0000000003" AND lastName LIKE "L*"
 *	OUTPUT:Records selected: 0
 *	
 *	INPUT:> query lastName LIKE "B*"
 *	OUTPUT:
 *	+============+===========+===========+===+
 *	| 0000000002 | Bakamović | Petra     | 3 |
 *	| 0000000003 | Bosnić    | Andrea    | 4 |
 *	| 0000000004 | Božić     | Marin     | 5 |
 *	| 0000000005 | Brezović  | Jusufadis | 2 |
 *	+============+===========+===========+===+
 *	Records selected: 4
 *	
 *	INPUT:> query lastName LIKE "Be*"
 *	OUTPUT:Records selected: 0
 *	
 *	INPUT:> exit
 *	OUTPUT:Goodbye!
 * 
 * </pre>
 * 
 * @author Lucija Valentić
 *
 */
public class StudentDB {
	/**
	 * Main method called in the beginning of a program
	 * @param args arguments of a command line
	 */
	public static void main(String[] args) {
		
		List<String> lines;
		
		try {
			lines = Files.readAllLines(Paths.get("./database.txt"),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			return;
		}
		
		StudentDatabase db = new StudentDatabase(lines);
		Scanner sc = new Scanner(System.in);
		String string = "";
		while(true) {
			
			System.out.printf("> ");
			
			if(sc.hasNext()) {
				string = sc.nextLine();
			}
			
			
			if(string.equals("exit")) {
				System.out.println("Goodbye!");
				sc.close();
				return;
			}
			

			if( string.substring(0).length() < 5 || !string.substring(0,5).equals("query") || string.substring(5).isBlank()) {
			
				//sc.close();
				System.out.println("Invalid command");
			}else {

				List<String> output = null;
				try {

					SelectRecords trueRecords = new SelectRecords(string.substring(5), db);
					List<StudentRecord> records = trueRecords.getRecords();
					output = RecordFormatter.format(records);	
				}catch(QueryException ex){
					System.out.println("Invalid command");
				}
				
				try {
					output.forEach(System.out::println);
				}catch(NullPointerException ex) {}
			}
			
		}		
		
		
	}
	
	
}
