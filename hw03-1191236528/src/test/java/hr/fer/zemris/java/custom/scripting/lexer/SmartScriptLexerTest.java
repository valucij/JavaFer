package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

class SmartScriptLexerTest {

	@Test
	void testOnlyText() {
		
		SmartScriptLexer lexer = new SmartScriptLexer("Test ");
		assertEquals(SmartTokenType.TEXT,lexer.nextToken().getType());
		
	}
	
	@Test
	void testOnlyTag(){

		SmartScriptLexer lexer = new SmartScriptLexer("{$ For $} ");
		assertEquals(SmartTokenType.TAGNAME,lexer.nextToken().getType());
	}
	
	@Test
	void testTextAndTag() {

		SmartScriptLexer lexer = new SmartScriptLexer("Test {$ For $} ");
		assertEquals(SmartTokenType.TEXT,lexer.nextToken().getType());
		assertEquals(SmartTokenType.TAGNAME,lexer.nextToken().getType());
	}
	
	@Test
	void testExceptionNotClosedTag() {
		
		SmartScriptLexer lexer = new SmartScriptLexer(" {$ for ");
		SmartScriptToken newToken = lexer.nextToken();
		
		try {
			newToken = lexer.nextToken();
		}catch(SmartLexerException ex) {
			return;
		}
		fail();
	}
	
	@Test
	void testEndingString() {
		
		SmartScriptLexer lexer = new SmartScriptLexer("Test");
		SmartScriptToken newToken = lexer.nextToken();
		
		assertEquals(SmartTokenType.EOF,lexer.nextToken().getType());
	}
	
}
