package hr.fer.zemris.java.hw05.db;

import java.util.*;

/**
 * Class whose task is to draw table with
 * student records that are given in form of a list.
 * If list is empty, nothing is drawn, and appropriate
 * message is written. Cause we already know the type of
 * informations in student records, this formatter is rather simple.
 * We just have to check max length of name and last name, so
 * our table can be resized according to that.
 * 
 * @author Lucija Valentić
 *
 */
public class RecordFormatter {

	/**
	 * Receives list of student records and 'draws' a table with
	 * those records in it. It also makes sure to resize it 
	 * appropriately, so it doesn't look like this ->
	 * 
	 * +======+======+=======+====+
	 * | 2332 | Mae  | Lfieh | 22 |
	 * | 1323123| Djfwio | ejwjpw | 232|
	 * +======+======+=======+====+
	 * 
	 * @param list
	 * @return list of strings, each string represents
	 * 			one row in a table
	 */
	public static List<String> format(List<StudentRecord> list){
		
		if(list == null) {
			return null;
		}
		
		LinkedList<String> drawRecords = new LinkedList<String>();
		String string = "";
		if(list.size() == 0) {
			drawRecords.add("Records selected: 0");
			return drawRecords;
		}
		
		int maxName = sizeMaxName(list);
		int maxLastName = sizeMaxLastName(list);
		
		string = drawBorder(maxName, maxLastName);
		drawRecords.add(string);
		
		for(int i = 0, n = list.size(); i < n; i++) {
		
			string = "| " + list.get(i).getJmbag() 
					+ " | " + forFirstOrLastName(list.get(i).getLastName(), maxLastName)
					+ " | " + forFirstOrLastName(list.get(i).getFirstName(), maxName) 
					+ " | " + list.get(i).getFinalGrade() + " |";
			
			drawRecords.add(string);
			
		}
		
		string = drawBorder(maxName, maxLastName);
		drawRecords.add(string);
		drawRecords.add("Records selected: " + list.size() );
		return drawRecords;
	}
	
	/**
	 * Writes appropriate cell in the table, for last or first name
	 * and making sure it is resized according to length of the name or 
	 * last name with biggest length
	 * 
	 * @param name
	 * @param lenght
	 * @return string that represents one cell in a table
	 */
	private static String forFirstOrLastName(String name, int lenght) {
		String string = name;
		
		int diff = lenght - name.length();
		
		for(int i = 0; i < diff; i++) {
			string = string + " ";
		}

		return string;
	}
	/**
	 * Checks list of student records, and returns max length
	 * of first name
	 * 
	 * @param list
	 * @return max length of first name
	 */
	private static int sizeMaxName(List<StudentRecord> list) {
		
		int max = 0;
		
		for(StudentRecord record : list) {
			if(record.getFirstName().length() > max) {
				max = record.getFirstName().length();
			}
		}
		
		return max;
	}
	
	/**
	 * Checks list of student records, and returns max length
	 * of last name
	 * 
	 * @param list
	 * @return max length of last name
	 */
	private static int sizeMaxLastName(List<StudentRecord> list) {
		
		int max = 0;
		
		for(StudentRecord record : list) {
			if(record.getLastName().length() > max) {
				max = record.getLastName().length();
			}
		}
		return max;
	}
	/**
	 * Draws border of a table, making sure to resize it
	 * accordingly to max name size and max last name size.
	 * 
	 * @param maxName
	 * @param maxLastName
	 * @return string that represents border of a table
	 */
	private static String drawBorder(int maxName, int maxLastName) {
		String string = "+";
		
		for(int i = 0; i < 12; i++) {
			string = string + "=";
		}
		string = string + "+";
		
		for(int i = 0; i < maxLastName + 2; i++) {
			string = string + "=";
		}
		
		string = string + "+";
		
		for(int i = 0; i < maxName + 2; i++) {
			string = string + "=";
		}
		string = string + "+===+";
		
		return string;
	}
	
	
}
