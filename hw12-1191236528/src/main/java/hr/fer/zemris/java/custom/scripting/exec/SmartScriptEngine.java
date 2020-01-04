package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Stack;
import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This is used for executing smart scripts. Smart script is first read with some parser, 
 * aka {@link SmartScriptParser}, and then document node is created. That document node is passed to instances of
 * this class through constructor, and then every node is visited, therefore script is executed
 * @author Lucija Valentić
 *
 */
public class SmartScriptEngine {

	/**
	 * Internal {@link DocumentNode}
	 */
	private DocumentNode documentNode;
	/**
	 * Internal {@link RequestContext}
	 */
	private RequestContext requestContext;
	/**
	 * Represents stack for handling parameters
	 */
	private ObjectMultistack multistack = new ObjectMultistack();
	
	/**
	 * Constructor
	 * @param documentNode DocumentNode
	 * @param requestContext RequestContext
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		super();
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}
	/**
	 * Internal visitor, made just for this class. Every method is made so
	 * that while visiting nodes, smart script can actully be executed
	 */
	private INodeVisitor visitor = new INodeVisitor() {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTextNode(TextNode node) {
			
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				throw new RuntimeException();
			}
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			
			Element variable = node.getVariable();
			Element initValue = node.getStartExpression();
			Element end = node.getEndExpression();
			Element step = node.getStepExpression();
			
			multistack.push(variable.asText(), new ValueWrapper(initValue.asText()));

			ValueWrapper value = multistack.peek(variable.asText());
			
			while(Integer.parseInt(value.toString()) <= Integer.parseInt(end.asText())) {
				
				for(int i = 0, n = node.numberOfChildren(); i < n; i++) {
					node.getChild(i).accept(visitor);
				}
				
				value.add(step.asText());
				
				if(Integer.parseInt(value.toString()) > Integer.parseInt(end.asText()) ) {
					
					break;
				}
				
				multistack.push(variable.asText(), new ValueWrapper(value.toString()));
				
			}
			
			multistack.pop(variable.asText());
			
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			
			Stack<Object> tempStack = new Stack<Object>();
			
			Element[] tokens = node.getElements();
			
			for(Object o : tokens) {
				
				if(o instanceof ElementConstantDouble || o instanceof ElementConstantInteger) {
					
					tempStack.push(((Element) o).asText());
					
				}
				
				if(o instanceof ElementString) {
					
					String string = ((ElementString) o).asText();
					
					if(string.startsWith("\"")) {
						string = string.substring(1);
					}
					
					if(string.endsWith("\"")) {
						string = string.substring(0, string.length() - 1);
					}
					
					tempStack.push(string);
				}
				
				if(o instanceof ElementVariable) {
					
					ValueWrapper value = multistack.peek(((ElementVariable) o).asText());
				
					tempStack.push(value.toString());
				
				}
				
				if(o instanceof ElementOperator) {
					
					ValueWrapper one = new ValueWrapper(tempStack.pop());
					
					Object two = tempStack.pop();
					
					if(((ElementOperator) o).asText().equals("+")) {
					
						one.add(two);
					}else if(((ElementOperator) o).asText().equals("-")) {
						one.subtract(two);
					}else if(((ElementOperator) o).asText().equals("/")) {
						one.divide(two);
					}else if(((ElementOperator) o).asText().equals("*")) {

						one.multiply(two);
					}
					
					tempStack.push(one.toString());
				}
				
				if(o instanceof ElementFunction) {
					performAction(tempStack, (ElementFunction) o);
				}
			}
			
			for(Object o : tempStack) {
				try {
					requestContext.write(o.toString());
				} catch (IOException e) {
					throw new RuntimeException();
				}
			}
			
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			
			for(int i = 0, n = node.numberOfChildren(); i < n; i++) {
				node.getChild(i).accept(visitor);
			}
			
		}
		
		/**
		 * Method handles function from smart script. Smart script can have
		 * multiple actions, and this method calculates the result of said function
		 * @param tempStack Stack<Object>
		 * @param o ElementFunction
		 */
		private void performAction(Stack<Object> tempStack, ElementFunction o) {
			
			String function = o.asText();
			Object value;
			Object secondValue;
			switch(function) {
			
			case "sin":	
				value = tempStack.pop();
				tempStack.push(Math.sin(Math.toRadians(Double.valueOf(value.toString()))));
				return;
				
				
			case "decfmt":
				
				value = tempStack.pop();//f
				secondValue = tempStack.pop();//x
				
				DecimalFormat decFormat = new DecimalFormat();
				decFormat.applyPattern(value.toString());
				String format = decFormat.format(secondValue);
				
				tempStack.push(format);
				
				return;
			case "dup":
				
				value = tempStack.pop();
				
				tempStack.push(value);
				tempStack.push(value);
				
				return;
			case "swap":
				
				value = tempStack.pop();
				secondValue = tempStack.pop();
				
				
				tempStack.push(value);
				tempStack.push(secondValue);
				
				return;
			case "setMimeType":
				
				value = tempStack.pop();//ili peek()
				
				requestContext.setMimeType(value.toString());
				
				
				return;
			case "paramGet":
				
				value = tempStack.pop();//dv
				secondValue = tempStack.pop();//name
				
				value = value.toString();
				
				Object parValue = requestContext.getParameter(secondValue.toString());
				
				tempStack.push((parValue == null)? value: parValue);
				
				return;
			case "pparamGet":
				
				value = tempStack.pop();//dv
				secondValue = tempStack.pop();//name
				
				value = value.toString();
				
				parValue = requestContext.getPersistentParameter(secondValue.toString());
				
				tempStack.push((parValue == null)? value: parValue);
				
				return;
			case "pparamSet":
				
				value = tempStack.pop();//name
				secondValue = tempStack.pop();//value
				
				requestContext.setPersistentParameter(value.toString(), secondValue.toString());
				
				return;
			case "pparamDel":
				
				value = tempStack.pop();//name
				
				requestContext.removePersistentParameter(value.toString());
				
				return;
			case "tparamGet":
				
				value = tempStack.pop();//dv
				secondValue = tempStack.pop();//name
				
				value = value.toString();
				
				parValue = requestContext.getTemporaryParameter(secondValue.toString());
				
				tempStack.push((parValue == null)? value: parValue);
				
				return;
			case "tparamSet":
				
				value = tempStack.pop();//name
				secondValue = tempStack.pop();//value
				
				requestContext.setTemporaryParameter(value.toString(), secondValue.toString());
				
				return;
			case "tparamDel":
				
				value = tempStack.pop();//ili peek()
				
				requestContext.removeTemporaryParameter(value.toString());
				
				return;
			}
			
		}
		
	};

	
	/**
	 * Method calls visitor on document node.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}
