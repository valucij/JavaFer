package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.nodes.*;

class SmartScriptParserTest {

	@Test
	void testOnlyText() {
		SmartScriptParser parser = new SmartScriptParser("Test");
		
		DocumentNode node = parser.getDocumentNode();
		TextNode newNode = (TextNode) node.getChild(0);
		
		assertEquals("Test",newNode.getText());
	}
	
	@Test
	void testEchoNode() {
		
		SmartScriptParser parser = new SmartScriptParser(" Text {$= i$}");
		
		DocumentNode node = parser.getDocumentNode();
		
		
		if(node.getChild(1) instanceof EchoNode) {
			return;
		}
		fail();
		
	}
	
	@Test
	void testForLoopNode() {
		
		SmartScriptParser parser = new SmartScriptParser(" Text {$for i 2 3 $} {$END$}");
		
		DocumentNode node = parser.getDocumentNode();
		
		
		if(node.getChild(1) instanceof ForLoopNode) {
			return;
		}
		fail();
		
	}
	
	@Test
	void testForLoopNotClosedException() {
		
		try {
		
			SmartScriptParser parser = new SmartScriptParser(" Text {$for i 2 3 $} ");
		}catch(SmartScriptParserException ex) {
			return;
		}
		fail();
	}
	
	@Test
	void testNumberOfChildrenDocumentNode() {
		SmartScriptParser parser = new SmartScriptParser(" Text {$for i 2 3 $} {$END$}");
		
		DocumentNode node = parser.getDocumentNode();
		
		assertEquals(2,node.numberOfChildren());
	}

}
