package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.*;
/**
 * Class inherits <code>Node</code> and represents for node, node created
 * from beginning of for loop.
 * @author Lucija Valentić
 *
 */
public class ForLoopNode extends Node {
	
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;
	/**
	 * Constructor
	 * 
	 * @param variable
	 * @param startExpression
	 * @param endExpression
	 * @param stepExpression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	/**
	 * Returns information from node, specifically variable
	 *  
	 * @return variable 
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	/**
	 * Returns information from node, specifically startExpression
	 *  
	 * @return startExpression
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	/**
	 * Returns information from node, specifically endExpression
	 *  
	 * @return endExpression
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	/**
	 * Returns information from node, specifically stepExpression
	 *  
	 * @return stepExpression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	
}
