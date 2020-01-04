package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
/**
 * 'Main' class that represent base for all graph nodes
 * @author Lucija Valentić
 *
 */
public class Node {

	private ArrayIndexedCollection children;
	
	/**
	 * Method adds new child node to this node. If it is called first time
	 * then makes new ArrayIndexedCollection variable and stores it in
	 * private variable children
	 * @param child node that needs to be added as a child
	 */
	public void addChildNode(Node child) {
		
		if(children == null) {
			children = new ArrayIndexedCollection();
		}
		
		children.add(child);
	}
	/**
	 * Returns how many children node has.
	 * 
	 * @return int number of children of this node
	 */
	public int numberOfChildren() {
		
		if(children == null) {
			
			return 0;
		}
		
		return children.size();
	}
	/**
	 * Return child from this node from specific position. If the child is not 
	 * on the position, exception is thrown.
	 * 
	 * @param index position from which we must return child
	 * @return node child of a node
	 * @throws IndexOutOfBoundsException if given index that represents
	 * 		  position is illegal.
	 */
	public Node getChild(int index)throws IndexOutOfBoundsException {
		
		return (Node)children.get(index);
	}
	
	
}
