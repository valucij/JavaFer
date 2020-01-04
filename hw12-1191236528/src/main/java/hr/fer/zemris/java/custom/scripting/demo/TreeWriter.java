package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * 
 * Method creates new tree from some document, and then 
 * it writes out content of that document from created document node
 * @author Lucija Valentić
 *
 */
public class TreeWriter {

	/**
	 * Main method, called in the beginning of a program
	 * @param args arguments of command line
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		if(args.length != 1) {
			throw new RuntimeException();
		}
		
		String docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
	}
	/**
	 * Class implements {@link INodeVisitor}.
	 * Method visits each node of some documentNode, and 
	 * performs some action. 
	 * @author Lucija Valentić
	 *
	 */
	public static class WriterVisitor implements INodeVisitor{

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.toString());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print(node.toString());
			
			for(int i = 0, n = node.numberOfChildren(); i < n; i++) {
				node.getChild(i).accept(this);
			}
			
			System.out.print("{$END$}");
			
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print("{$=" + node.toString() + "$}");
			
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			
			for(int i = 0, n = node.numberOfChildren(); i < n; i++) {
				
				node.getChild(i).accept(this);
			}
			
			
		}
		
	}

}
