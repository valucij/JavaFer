package hr.fer.zemris.java.hw03;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.custom.scripting.nodes.*;

import java.nio.file.Files;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;


/**
 * Class that tests if SmartScriptParser works properly. It uses examples
 * from the homework.
 * 
 * @author Lucija Valentić
 *
 */
public class SmartScriptTester {
	/**
	 * Method that creates string from given DocumentNode
	 * 
	 * @param node DocumentNode from which we must create string
	 * @return sb newly created string
	 */
	public static String createOriginalDocumentBody(DocumentNode node) {
		
		String sb = "";
		
		for(int i = 0, n = node.numberOfChildren(); i < n; i++) {
			
			
			
			if(node.getChild(i).numberOfChildren() == 0) {
				
				if(node.getChild(i) instanceof EchoNode) {
					
					EchoNode help = (EchoNode)node.getChild(i);
					
					sb = sb + "{$=" + help.toString() + "$}";
					
				}else {
					TextNode help = (TextNode)node.getChild(i);
					sb = sb + help.getText();
				}
				
			}else {//fornodeloop je 
				
				ForLoopNode forNode = (ForLoopNode)node.getChild(i);
				
				sb = sb + "{$FOR " + forNode.getVariable().asText() + " " + forNode.getStartExpression().asText() + " " + forNode.getEndExpression().asText() + " " + forNode.getStepExpression().asText() + "$}"	;
				
				for(int j = 0, m = node.getChild(i).numberOfChildren(); j < m; j++) {
					
					Node help = node.getChild(i).getChild(j);
					
					if(help instanceof EchoNode) {
						
						EchoNode echo = (EchoNode)help;
						
						sb = sb + "{$=" + echo.toString() + "$}";
						
					}else {
						TextNode echo = (TextNode)help;
						sb = sb + echo.getText();
					}
					
				}
				
				sb = sb + "{$END$}";
			}
				
		}
		
		return sb;
		
	}
	
/**
 * Main method called in the beginning of a program.	
 * @param args arguments of a command line
 */
	public static void main(String[] args) {
				
		
		/* in command line you should put input: src/test/java/document1.txt */
		String docBody;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			
			System.out.println("Error while loading document");
			return;
		}
		
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
		
			System.out.println("Unable to parse document!");
			System.exit(-1);
		
		} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocumentNode();
		
		String originalDocumentBody = createOriginalDocumentBody(document);
		
		System.out.println(originalDocumentBody); // should write something like original
		// content of docBody
		
		/*
		System.out.printf("%n%n");
		
		System.out.println("ACTUAL TEST");
		
		String docBody2 = "This is sample text.\n" + 
				"{$ FOR i 1 10 1 $}\n" + 
				"This is {$= i $}-th time this message is generated.\n" + 
				"{$END$}\n" + 
				"{$FOR i 0 10 2 $}\n" + 
				"sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n" + 
				"{$END$}";
		
		SmartScriptParser parser2 = new SmartScriptParser(docBody2);
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBody2 = createOriginalDocumentBody(document2);
		SmartScriptParser parser3 = new SmartScriptParser(originalDocumentBody2);
		DocumentNode document3 = parser3.getDocumentNode();
		String secondDocumentBody2 = createOriginalDocumentBody(document3);
		
		System.out.printf("%n");
		System.out.println("Original Document Body:");
		System.out.println(originalDocumentBody2);
		
		System.out.printf("%n%n");
		
		System.out.println("Second Document Body:");
		
		System.out.println(secondDocumentBody2);
		
		
		*/
		

	}
	
	
}
