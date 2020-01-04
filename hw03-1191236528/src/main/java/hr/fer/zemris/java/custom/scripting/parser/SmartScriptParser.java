package hr.fer.zemris.java.custom.scripting.parser;

import java.util.Arrays;
import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;
/**
 * Class <code>SmartScriptParser</code> is used for creating DocumentNode from
 * given strings. It uses SmartScriptLexer, class that works with given
 * string and adds tokens to each word, and then parser from that
 * made tag is making new DocumentNode, that can be later reproduced back into
 * text/string.Ofcourse, this class also makes sure that text is valid,
 * if it's not, then it throws exception. For example, 
 * for loop needs to be closed, variables in for tag needs to be valid - first
 * needs to be variable followed by two or three number/string/variable nodes.
 * In the next example it is showed how this class can be used
 *  index
 *  Input > "This is sample text \r\n {$FOR i 1 10 1 $} \r\n {$=i$} -th time"
 *  'Output of parser' (just to show what it does)>
 *  
 *  DocumentNode - TextNode (This is sample text. \r\n)
 *  			 - ForLoopNode (i 1 10 1) - TextNode(\r\n)
 *  									  - EchoNode(i)
 *  			 - TextNode(i-th time)	
 *  
 * @author Lucija Valentić
 *
 */
public class SmartScriptParser {

	/**
	 * DocumentNode that has nodes in it, and by using it,
	 * we can recreate parsed text completely.
	 */
	DocumentNode documentNode;
	/**
	 * Stack that is used to help us in creating tree of nodes.
	 */
	ObjectStack stack;
	
	
	/**
	 * Constructor. It calls method parse() and sends given string to it
	 * @param string
	 */
	public SmartScriptParser(String string) {
		parse(string);
	}
	/**
	 * Method that makes tree of nodes from given string using lexer.
	 * Based on tokens that lexer returns, it creates nodes of different types.
	 *  There are three type of nodes - TextNode, ForLoopNode
	 * and EchoNode. Everything is a text, and TextNode is created, until
	 * we encounter token that symbolizes opening of a tag.  We differ
	 * two tags - FOR and ECHO, and we called different functions for making
	 * EchoNode and ForLoopNode. Also when we make ForLoopNode, we have to 
	 * make its children. If we come to the ending of a string without 
	 * encountering END token, then exception is thrown. Tag type ECHO doesn't
	 * have to be closed like FOR tag.
	 * 
	 * @param lexer SmartScriptLexer
	 */
	private void parse(String string) {
			
		SmartScriptLexer lexer = new SmartScriptLexer(string);
		
		documentNode = new DocumentNode();
		stack = new ObjectStack();
		stack.push(documentNode);
		
		while(true) {
			
			SmartScriptToken token = lexer.nextToken();
			
			/*Next if-else check tokens, they make difference between TEXT, TAGNAME, EOF and TAGEND tokens,
			 * so appropriate action can be made*/
			
			/*If next encountered token is TEXT TOKEN*/
			if(token.getType().equals(SmartTokenType.TEXT)) {
				
				TextNode node = new TextNode(token.getValue());
				Node help = (Node)stack.peek();
				help.addChildNode(node);
				
				continue;
				
			}else if(token.getType().equals(SmartTokenType.TAGNAME)) {/*If we encounter beginning of a tag, we check if the tag is for or echo*/
				
				if(token.getValue().equals("=")) {
					EchoNode node = makeEchoNode(lexer);
					Node help = (Node)stack.peek();
					help.addChildNode(node);
		
				}else if(token.getValue().length() == 3) {
					
					if(!isFor(token.getValue())) {
						throw new SmartScriptParserException();
					}
					
					lexer = makeForTag(lexer, token.getValue());
					lexer = makeForChildren(lexer);
				
					continue;
					
				}else {
					throw new SmartScriptParserException();
				}
				
			}else if(token.getType().equals(SmartTokenType.EOF)) {/*We are at the end of string that needs to be parsed*/
				
				if(stack.size() != 1) {
					throw new SmartScriptParserException();
				}
				
				return;
				
			}else if(token.getType().equals(SmartTokenType.ENDTAG)){/*If we left the tag*/
				
				continue;
			}else {
				throw new SmartScriptParserException(); /*If we are parsing string, and we found nothinf valid*/
			}
			
			
		}
	}
	/**
	 * Method returns this.documentnode
	 * 
	 * @return documentnode
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
	/**
	 * Method makes echo tag. We are in side of tag, and
	 * because we are creating echo tag, every token until ending
	 * of a tag is variable or number or function or string.
	 * Based on that, we fill array with that elements, and in the end, we create
	 * EchoNode with that array.
	 * 
	 * @param lexer SmartScriptLexer
	 * @return echotag newly created
	 */
	private EchoNode makeEchoNode(SmartScriptLexer lexer) {
		
		SmartScriptToken token = lexer.nextToken();
		Element[] elements = {};
		
		while(!token.getType().equals(SmartTokenType.ENDTAG)) {
			
			
			if(token.getType().equals(SmartTokenType.VARIABLE)) {
			
				ElementVariable help = new ElementVariable(token.getValue());
				elements= putInArray(elements,help);
				
			}else if(token.getType().equals(SmartTokenType.NUMBER)) {

				try {
					int newNumber = Integer.parseInt(token.getValue());
					ElementConstantInteger expression= new ElementConstantInteger(newNumber);
					elements= putInArray(elements,expression);
					
				}catch(NumberFormatException ex) {
					
					double newNumber = Double.parseDouble(token.getValue());
					ElementConstantDouble expression = new ElementConstantDouble(newNumber);
					elements= putInArray(elements,expression);
				}
				
			}else if(token.getType().equals(SmartTokenType.STRING)) {
				ElementString help = new ElementString(token.getValue());
				elements= putInArray(elements,help);
				
			}else if(token.getType().equals(SmartTokenType.FUNCTION)) {
				ElementFunction help = new ElementFunction(token.getValue());
				elements= putInArray(elements,help);
			}else if(token.getType().equals(SmartTokenType.OPERATOR)) {
				ElementOperator help = new ElementOperator(token.getValue());
				elements= putInArray(elements,help);
				
			}else {
				throw new SmartScriptParserException("Invalid token type in echo node");
			}
			
			token = lexer.nextToken();
			
		}	
		
		EchoNode node = new EchoNode(elements);
		return node;
	}
	/**
	 * Method that puts given object type element in given array
	 * of object type element. Used while creating EchoNode.
	 * 
	 * @param elements
	 * @param element
	 * @return elements array with newly added element
	 */
	private Element[] putInArray(Element[] elements, Element element) {
		
		if(elements.length == 0) {
			elements = new Element[1];
			elements[0] = element;
		}else {
			elements = Arrays.copyOf(elements, elements.length + 1);
			elements[elements.length - 1] = element;
		}
		
		return elements;
	}
	/**
	 * Makes multiple nodes, ForLoopNode and EchoNode, with the help 
	 * od function that make that kind of nodes. How they are exactly created is
	 * decribed before those function. In the end of for loop
	 * it just goes back to creating nodes outside of for loop.
	 * If for loop is not closed, it throws exception.
	 * 
	 * @param lexer SmartScriptException
	 * @return lexer that was used for making tokens from string
	 */
	private SmartScriptLexer makeForChildren(SmartScriptLexer lexer) {
		
		
		while(true) {
			
			SmartScriptToken token  = lexer.nextToken();
			
			if(token.getType().equals(SmartTokenType.EOF)) {
				throw new SmartScriptParserException();
			}
			
			if(token.getType().equals(SmartTokenType.TEXT)) {
			
				TextNode node = new TextNode(token.getValue());
				
				Node help = (Node)stack.peek();
				help.addChildNode(node);
				
			}else if(token.getType().equals(SmartTokenType.TAGNAME) && !token.getValue().equals("END")) {
				
				if(token.getValue().equals("=")) {
			
					EchoNode node = makeEchoNode(lexer);
					Node help = (Node)stack.peek();
					help.addChildNode(node);
				
				}else {
					throw new SmartScriptParserException();
				}
			}else if(token.getType().equals(SmartTokenType.TAGNAME) && token.getValue().equals("END")) {
		
				stack.pop();
		
				return lexer;
			}else {
				throw new SmartScriptParserException();
			}
			
		}
	}
	/**
	 * Method just makes ForLoopNode. It checks if everything inside of
	 * a FOR tag is correct. If there are too many, or too little arguments,
	 * if first argument is not a variable, or next ones are not
	 * number, string or variable, then it throws and exception.
	 * Valid number of arguments after first variable is 2 or 3.
	 * 
	 * @param lexer SmartScriptLexer
	 * @param string given string from which we are creating for tag name
	 * @return lexer that was used
	 * @throws SmartScriptParserException if for tag is invalid
	 */
	private SmartScriptLexer makeForTag(SmartScriptLexer lexer, String string) {
		
		SmartScriptToken token1 = lexer.nextToken();
		
		
		if(!token1.getType().equals(SmartTokenType.VARIABLE)) {
			throw new SmartScriptParserException();
		}
		
		SmartScriptToken token2 = lexer.nextToken();
		
		if( token2.getType().equals(SmartTokenType.ENDTAG) || token2.getType().equals(SmartTokenType.EOF) || token2.getType().equals(SmartTokenType.FUNCTION) || token2.getType().equals(SmartTokenType.OPERATOR) ) {
			throw new SmartScriptParserException();
		}
		
		SmartScriptToken token3 = lexer.nextToken();
		
		if( token3.getType().equals(SmartTokenType.ENDTAG) || token3.getType().equals(SmartTokenType.EOF) || token3.getType().equals(SmartTokenType.FUNCTION) || token3.getType().equals(SmartTokenType.OPERATOR) ) {
			throw new SmartScriptParserException();
		}
		
		SmartScriptToken token4 = lexer.nextToken();
		
		if( token4.getType().equals(SmartTokenType.EOF) || token4.getType().equals(SmartTokenType.FUNCTION) || token4.getType().equals(SmartTokenType.OPERATOR)) {
			throw new SmartScriptParserException();
		}
		
		if(token4.getType().equals(SmartTokenType.ENDTAG)) {
			
			ElementVariable variable = new ElementVariable( token1.getValue());
			Element startExpression = returnElementExpression(token2);
			Element endExpression = returnElementExpression(token3);
			Element stepExpression = new ElementString("");
			
			ForLoopNode node = new ForLoopNode(variable, startExpression, endExpression, stepExpression);
			Node help = (Node)stack.peek();
			help.addChildNode(node);
			stack.push(node);
			
			return lexer;
		}
		
		if( token4.getType().equals(SmartTokenType.EOF) || token4.getType().equals(SmartTokenType.FUNCTION) || token4.getType().equals(SmartTokenType.OPERATOR)) {
			throw new SmartScriptParserException();
		}
		
		SmartScriptToken token5 = lexer.nextToken();
		

		if(token5.getType().equals(SmartTokenType.ENDTAG)) {
			
			ElementVariable variable = new ElementVariable( token1.getValue());
			Element startExpression = returnElementExpression(token2);
			Element endExpression = returnElementExpression(token3);
			Element stepExpression = returnElementExpression(token4);
			
			ForLoopNode node = new ForLoopNode(variable, startExpression, endExpression, stepExpression);
			Node help = (Node)stack.peek();
			help.addChildNode(node);
			stack.push(node);
			
			return lexer;
		}else {
			throw new SmartScriptParserException();
		}
		
		
	}
	/**
	 * Method checks what kind of next argument is based on its token,
	 * and then it returns Element that is best suited. We are using it 
	 * for creating ForLoopNode.
	 * 
	 * @param token
	 * @return Element
	 */
	private Element returnElementExpression( SmartScriptToken token) {
		
		if(token.getType().equals(SmartTokenType.NUMBER)) {
			
			try {
				int newNumber = Integer.parseInt(token.getValue());
				ElementConstantInteger expression= new ElementConstantInteger(newNumber);
				return expression;
				
			}catch(NumberFormatException ex) {
				
				double newNumber = Double.parseDouble(token.getValue());
				
				ElementConstantDouble expression = new ElementConstantDouble(newNumber);
				return expression;
			}
			
		}else if(token.getType().equals(SmartTokenType.STRING)) {
			
			ElementString expression = new ElementString(token.getValue());
			return expression;
		}else {
			ElementVariable expression = new ElementVariable(token.getValue());
			return expression;
		}
		
		
	}
	/**
	 * Checks if a nametag is FOR. Used when creating ForLoopNode
	 * @param string
	 * @return true if the name is for, false otherwise
	 */
	private boolean isFor(String string) {
		
		String help1 = string.substring(0,1);
		String help2 = string.substring(1,2);
		String help3 = string.substring(2);
		
		if( (help1.equals("F") || help1.equals("f")) && ( help2.equals("O") || help2.equals("o") ) && ( help3.equals("R") || help3.equals("r"))   ) {
			return true;
		}
		
		return false;
	}
	
	
}
