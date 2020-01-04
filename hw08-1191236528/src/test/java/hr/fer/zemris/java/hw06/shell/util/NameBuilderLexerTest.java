package hr.fer.zemris.java.hw06.shell.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class NameBuilderLexerTest {

	@Test
	void testLexerWithoutSpaces() {
		NameBuilderLexer lexer = new NameBuilderLexer ("gradovi-${2}-${1,03}.txt");
		
		assertEquals(NameBuilderTokenType.TEXT, lexer.nextToken().getType());
		assertEquals("gradovi-", lexer.getToken().getValue());
		
		assertEquals(NameBuilderTokenType.GROUP1, lexer.nextToken().getType());
		assertEquals("2", lexer.getToken().getValue());
		
		assertEquals(NameBuilderTokenType.TEXT, lexer.nextToken().getType());
		assertEquals("-", lexer.getToken().getValue());
		
		assertEquals(NameBuilderTokenType.GROUP2, lexer.nextToken().getType());
		assertEquals("1,03", lexer.getToken().getValue());
		
		assertEquals(NameBuilderTokenType.TEXT, lexer.nextToken().getType());
		assertEquals(".txt", lexer.getToken().getValue());
		
		assertEquals(NameBuilderTokenType.EOF, lexer.nextToken().getType());
		
	}


	@Test
	void testLexerWithSpaces() {
		NameBuilderLexer lexer = new NameBuilderLexer ("gradovi-${2}-${1    ,03   }.txt");
		
		assertEquals(NameBuilderTokenType.TEXT, lexer.nextToken().getType());
		assertEquals("gradovi-", lexer.getToken().getValue());
		
		assertEquals(NameBuilderTokenType.GROUP1, lexer.nextToken().getType());
		assertEquals("2", lexer.getToken().getValue());
		
		assertEquals(NameBuilderTokenType.TEXT, lexer.nextToken().getType());
		assertEquals("-", lexer.getToken().getValue());
		
		assertEquals(NameBuilderTokenType.GROUP2, lexer.nextToken().getType());
		assertEquals("1    ,03   ", lexer.getToken().getValue());
		
		assertEquals(NameBuilderTokenType.TEXT, lexer.nextToken().getType());
		assertEquals(".txt", lexer.getToken().getValue());
		
		assertEquals(NameBuilderTokenType.EOF, lexer.nextToken().getType());
		
	}

}
