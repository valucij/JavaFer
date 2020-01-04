package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * Class to see if {@link QueryLexer} works.
 * 
 * @author Lucija Valentić
 *
 */
class QueryLexerTest {

	@Test
	void testLexerJmbag() {
		QueryLexer lexer1 = new QueryLexer(" jmbag >= \"883182397\" ");
		
		assertEquals(QueryTokenType.JMBAG,lexer1.nextToken().getType());
		assertEquals(QueryTokenType.OPERATOR, lexer1.nextToken().getType());
		assertEquals(">=", lexer1.getToken().getValue());
		assertEquals(QueryTokenType.STRING, lexer1.nextToken().getType());
		assertEquals("883182397", lexer1.getToken().getValue());
		assertEquals(QueryTokenType.EOF, lexer1.nextToken().getType());
	}

	@Test
	void testLexerLastName() {
		QueryLexer lexer1 = new QueryLexer(" lastName LIKE \"Valentić\" ");
		
		assertEquals(QueryTokenType.LAST_NAME,lexer1.nextToken().getType());
		assertEquals(QueryTokenType.OPERATOR, lexer1.nextToken().getType());
		assertEquals("LIKE", lexer1.getToken().getValue());
		assertEquals(QueryTokenType.STRING, lexer1.nextToken().getType());
		assertEquals("Valentić", lexer1.getToken().getValue());
		assertEquals(QueryTokenType.EOF, lexer1.nextToken().getType());
	}
	
	@Test
	void testLexerFirstName() {
		QueryLexer lexer1 = new QueryLexer(" firstName != \"Lucija\" ");
		
		assertEquals(QueryTokenType.FIRST_NAME,lexer1.nextToken().getType());
		assertEquals(QueryTokenType.OPERATOR, lexer1.nextToken().getType());
		assertEquals("!=", lexer1.getToken().getValue());
		assertEquals(QueryTokenType.STRING, lexer1.nextToken().getType());
		assertEquals("Lucija", lexer1.getToken().getValue());
		assertEquals(QueryTokenType.EOF, lexer1.nextToken().getType());
	}
}
