package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Class that is solution for task 4 from homework
 * @author Lucija Valentić
 *
 */
public class StudentDemo {

	/**
	 * Main method that is called in the beginning of the program,
	 * it writes out solutions for those 8 tasks from homework
	 * @param args arguments of command line
	 */
	public static void main(String[] args) {
		
		List<String> lines = null;
		
		try {
			lines = Files.readAllLines(Paths.get("src/main/resources/studentdatabase.txt"));
		} catch (IOException e) {
			System.err.println("Couldn't open file.");
			return;
		}
		
		List<StudentRecord> records = convert(lines);

		for(int i = 1; i < 9; i++) {
			System.out.println("Zadatak " + i);
			System.out.println("=========");
			ispisZadatka(records, i);
			System.out.println("");
		}
	}
	
	/**
	 * Private method that writes out solution of some task. Which task will be 
	 * performed is determined by given integer i.
	 * @param records
	 * @param i
	 */
	private static void ispisZadatka(List<StudentRecord> records, int i) {
		
		switch(i) {
		
		case 1:
		
			long broj = vratiBodovaViseOd25(records);
			System.out.println(broj);
			return;
			
		case 2:
			
			long broj5 = vratiBrojOdlikasa(records);
			System.out.println(broj5);
			return;
			
		case 3:
			
			List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
			odlikasi.forEach(System.out::println);
			return;
			
		case 4:
			
			List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
			odlikasiSortirano.forEach(System.out::println);
			return;
			
			
		case 5:
			
			List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
			nepolozeniJMBAGovi.forEach(System.out::println);
			return;
			
		case 6:
			
			Map<Integer, List <StudentRecord> > mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
			
			mapaPoOcjenama.forEach( (integer, s) -> {
				System.out.println(integer);
				s.forEach(System.out::println);
			} );
			
			return;
			
		case 7:
			
			Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
			
			mapaPoOcjenama2.forEach( (integer1, integer2) -> System.out.format(" %s: %s%n", integer1, integer2));
			
			return;
			
		case 8:
		
			Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
			
			prolazNeprolaz.forEach( (b, list) -> {
				
				System.out.println(b);
				list.forEach(System.out::println);
				
			}  );
			
			return;
		}
		
	}

	/**
	 * Creates map with keys true/false, and values are lists of students who passed with the key true
	 * , that is students who failed with the key false.
	 * @param records
	 * @return map
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		
		return records.stream().collect(Collectors.partitioningBy( r -> r.getGrade() > 1));
	}

	/**
	 * Creates map whose keys are grades, and values are number of students with said grades.
	 * @param records
	 * @return map
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		
		return records.stream().collect(Collectors.toMap( StudentRecord::getGrade , StudentRecord::getJmbag, (lastSeen, j) ->{
		
			Map<Integer,List<StudentRecord>> map = records.stream().collect(Collectors.groupingBy(StudentRecord::getJmbag));
			int grade = map.get(j).get(0).getGrade();
			return (int) records.stream().filter(s -> s.getGrade() == grade).count();
			
		}  ));
	}

	/**
	 * Creates map whose keys are grades, and values are lists with all students with that grade
	 * @param records
	 * @return map
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
	
		return records.stream().collect(Collectors.groupingBy(StudentRecord::getGrade));
	}

	/**
	 * Creates list of jmbags of students that failed class, list is sorted based on jmbag.
	 * @param records
	 * @return list
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		
		return records.stream().filter( s -> s.getGrade() == 1)
				.sorted((s1, s2) -> (s1.getJmbag() - s2.getJmbag() ))
				.map( s -> s.properFormat(s.getJmbag())).collect(Collectors.toList());
	}

	/**
	 * Creates list of students that have grade 5, but said list is sorted in a way that
	 * first student has most points, second less points that the first one, and so on.
	 * @param records
	 * @return list
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		
		return records.stream().filter( s -> s.getGrade() == 5)
				.sorted((s1, s2) -> 
				(int)Math.floor((Double.valueOf(s2.getZi() ) + Double.valueOf(s2.getLab()) + Double.valueOf(s2.getMi()))
						- (Double.valueOf(s1.getZi() ) + Double.valueOf(s1.getLab()) + Double.valueOf(s1.getMi()))))
				.collect(Collectors.toList());
	}

	/**
	 * Creates list of students that have 5 
	 * @param records
	 * @return list
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		
		return records.stream().filter( s -> s.getGrade() == 5).collect(Collectors.toList());
	}

	/**
	 * Returns number of students that have 5.
	 * @param records
	 * @return long
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		
		return records.stream().filter( s -> s.getGrade() == 5).collect(Collectors.toList()).size();
	}

	/**
	 * Returns number of students that have more points than 25.
	 * @param records
	 * @return long 
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		
		return records.stream().filter( s -> (Double.valueOf(s.getZi() ) + Double.valueOf(s.getLab()) + Double.valueOf(s.getMi()))> 25)
		.collect(Collectors.toList()).size();
	}

	/**
	 * Method that creates list of student records from given 
	 * list of strings. Each string has one student record written all together,
	 * separated with tabulator, and this method extracts specific information
	 * for each student.
	 * @param list
	 * @return list
	 */
	private static List<StudentRecord> convert(List<String> list){
		
		List<StudentRecord> students = new LinkedList<>();
		
		for(String string : list) {
		
			String[] values = string.split("[\t]");
			students.add(new StudentRecord(Integer.valueOf(values[0]), values[1], values[2], values[3], values[4], values[5], Integer.valueOf(values[6])));
		}
		
		return students;
		
		 
	}

}
