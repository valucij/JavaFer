package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.Test;
/**
 * Class tests if class {@link StudentDatabase} works.
 * @author Lucija Valentić
 *
 */
class StudentDatabaseTest {

	@Test
	void testForJmbag() {
		StudentDatabase data = new StudentDatabase(getDatabase());
		assertEquals(null, data.forJMBAG("8323284"));
		assertEquals(new StudentRecord("0000000001","Akašimović","Marin","2"), data.forJMBAG("0000000001"));
	}
	
	@Test
	void testFilter() {
		StudentDatabase data = new StudentDatabase(getDatabase());
		assertFalse(data.filter(z -> {return true;}).isEmpty());
		assertTrue(data.filter(z -> {return false;}).isEmpty());
	}

	private List<String> getDatabase(){
		
		LinkedList<String> list = new LinkedList<>();
		list.add("0000000001	Akšamović	Marin	2");
		list.add("0000000002	Bakamović	Petra	3");
		list.add("0000000003	Bosnić	Andrea	4");
		
		return list;
		
	}
}
