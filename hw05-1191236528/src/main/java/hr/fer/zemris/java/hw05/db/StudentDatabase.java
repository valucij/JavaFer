package hr.fer.zemris.java.hw05.db;

import java.util.*;
/**
 * Class that keeps database of StudentRecord. 
 * In constructor, it receives List of strings,
 * each representing one line in some text
 * file, in which are written StudentRecord. 
 * Each line is one StudenRecord.
 * So with this class, we are making a list
 * of StudentRecords and instances of this class
 * will represent database with which we will work,
 * and presumably search for specific StudentRecord. 
 * 
 * @author Lucija Valentić
 *
 */
public class StudentDatabase {
	
	/**
	 * List that keeps all students from database
	 */
	private List<StudentRecord> students;	
	/**
	 * Map that keeps all students from database
	 * but they are all mapped with their jmbag,
	 * so if we know jmbag, we can easly get some student,
	 * and not search the whole database for that student.
	 */
	private Map<String, StudentRecord> index;	
	
	/**
	 * Constructor, from given database, it creates
	 * list of StudentRecords.
	 * 
	 * @param database
	 */
	public StudentDatabase(List<String> database) {
		
		TreeMap<String, StudentRecord> newIndex = new TreeMap<>();
		LinkedList<StudentRecord> listStudents = new LinkedList<StudentRecord>();
		
		
		for(String line : database) {
		
			StudentRecord newStudent = extractStudentRecord(line);
			
			if((newStudent.getFinalGrade().compareTo("1") < 0 || newStudent.getFinalGrade().compareTo("5") > 0)
					|| (listStudents.contains(newStudent)) ) {
				throw new DatabaseException("Invalid input in database");
			}else {
				listStudents.add(newStudent);
				newIndex.put(newStudent.getJmbag(), newStudent);
			}
		}
		
		students = listStudents;	
		index = newIndex;
	
	}
	
	/**
	 * Public method that returns student with appropriate jmbag
	 * that is the same as given one.
	 * 
	 * @param jmbag
	 * @return student with given jmbag, or <code>null</code> if
	 * that student doesn't exist.
	 */
	public StudentRecord forJMBAG(String jmbag) {	
				
		return index.get(jmbag);
		
	}
	
	/**
	 * Public method that fills a new list with
	 * students from database that satisfy
	 * some kind of criteria given in filter
	 * 
	 * @param filter
	 * @return List<StudentRecord>, it can also be empty,
	 * 			or have only one element
	 */
	public List<StudentRecord> filter(IFilter filter){
		
		LinkedList<StudentRecord> newList = new LinkedList<>();
		
		for(StudentRecord student : students) {
			
			if(filter.accepts(student)) {
				newList.add(student);
			}
		}
		
		return newList;
	}
	
	/**
	 * Private method that from given string line extracts
	 * informations of student and returns new StudentRecord
	 * made from those informations.
	 * 
	 * @param line
	 * @return StudentRecord
	 */
	private StudentRecord extractStudentRecord(String line) {
				
		char[] charLine = line.toCharArray();
		int i = 0;
		int j;
		String jmbag;
		String lastName = "";
		String stringOne;
		String stringTwo;
		String firstName = "";
		String grade;
		
		i = nextNotBlank(line, i);
		j = i;
		while(Character.isDigit(charLine[j])) {
			j++;
		}
		
		
		jmbag = String.copyValueOf(charLine, i, j - i);
		
		i = nextNotBlank(line,j);
		j = i;
		while(!Character.isWhitespace(charLine[j])) {
			j++;
		}
		
		stringOne = String.copyValueOf(charLine, i, j - i);
		
		i = nextNotBlank(line, j);
		j = i;
		
		while(!Character.isWhitespace(charLine[j])) {
			j++;
		}
		
		
		stringTwo = String.copyValueOf(charLine, i, j - i);
		
		i = nextNotBlank(line, j);
		
		if(!Character.isDigit(charLine[i]) && charLine[i] != '-') {
			//ako se studentovo prezime sastoji od dva dijela
			
			j = i;
			
			while(!Character.isWhitespace(charLine[j])) {
				j++;
			}
			
			lastName = stringOne + " " + stringTwo;
			firstName = String.copyValueOf(charLine, i, j - i); 
			i = nextNotBlank(line, j);
			
		}else {
			//ako student ima samo jedno ime i samo jedno prezime
			lastName = stringOne;
			firstName = stringTwo;
		}
		
		grade = String.copyValueOf(charLine, i, line.length() - i);
		
		return new StudentRecord(jmbag,firstName,lastName,grade);
	}
	
	/**
	 * Private method that searches next position where
	 * there is some character other that white space in given string line.
	 * Search starts from given index 
	 * @param line
	 * @param index
	 * @return index of a next character that is not white space
	 * 			of some sorts.
	 */
	private int nextNotBlank(String line, int index) {
		char[] charLine = line.toCharArray();
		
		while(Character.isWhitespace(charLine[index]) ){
			index++;
		}
		
		return index;
	}
	
}
