package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * Class to see if {@link QueryParser} works.
 * 
 * @author Lucija Valentić
 *
 */
class QueryParserTest {

	@Test
	void test() {
		QueryParser parser = new QueryParser("jmbag = \"23456812\" ");

		assertTrue(parser.isDirectQuery());
		assertEquals("23456812", parser.getQueriedJMBAG());
		assertEquals(1, parser.getQuery().size());
		
		QueryParser parser2 = new QueryParser("jmbag = \"23456812\" and lastName>\"J\"");

		assertEquals(2, parser2.getQuery().size());
		
		assertFalse(parser2.isDirectQuery());
		
		try {
			parser2.getQueriedJMBAG();
		}catch(IllegalStateException ex) {
			
			return;
		}
		
		fail();
		
	}

}
