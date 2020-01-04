package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Class <code>DocumentNode</code> represents documentnode. It inherits class
 * <code>Node</code>. We can use it when reading from documents and creating
 * trees full of nodes from that document. It is an empty class.
 * 
 * @author Lucija Valentić
 *
 */
public class DocumentNode extends Node {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "";
	}

}
