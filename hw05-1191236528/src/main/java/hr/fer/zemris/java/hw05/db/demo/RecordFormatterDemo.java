package hr.fer.zemris.java.hw05.db.demo;

import java.util.*;
import hr.fer.zemris.java.hw05.db.*;

/**
 * Class for testing if RecordFormatter works.
 * @author Lucija Valentić
 *
 */
public class RecordFormatterDemo {

	public static void main(String[] args) {
		
		LinkedList<StudentRecord> list = new LinkedList<>();
		
		list.add(new StudentRecord("0000000001", "Akšamović", "Marin", "2"));
		list.add(new StudentRecord("0000000002", "Bakamović", "Petra", "2"));
		list.add(new StudentRecord("0000000003", "Bosnić", "Andrea", "2"));
		list.add(new StudentRecord("0000000004", "Božić", "Bojan", "2"));
		
		List<String> output = RecordFormatter.format(list);
		
		output.forEach(System.out::println);
		
		LinkedList<StudentRecord> list2 = new LinkedList<>();
		
		List<String> output2 = RecordFormatter.format(list2);
		
		output2.forEach(System.out::println);
	}
}
