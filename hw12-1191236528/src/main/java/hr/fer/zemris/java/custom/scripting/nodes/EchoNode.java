package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.*;

/**
 * Class inherits class <code>Node</code>. It represents echo nodes.
 * 
 * @author Lucija Valentić
 *
 */
public class EchoNode extends Node {
	
	private Element[] elements;
	/**
	 * Constructor 
	 * @param string 
	 */
	public EchoNode(Element[] elements) {
		
		this.elements = elements;	
		
	}
	
	/**
	 * Returns information from echo node in string form
	 * @return string
	 */
	@Override
	public String toString() {
		
		String string = "";
		for(int i = 0, n = elements.length; i < n; i++) {
			
			if(elements[i] instanceof ElementFunction) {
				string = string + " @" + elements[i].asText();
			}else {
				string = string + " " +elements[i].asText();
			}
		}
		
		return string;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
		
	}

	/**
	 * Returns this.elements
	 * @return Element[]
	 */
	public Element[] getElements() {
		return elements;
	}
	
	
	
}
