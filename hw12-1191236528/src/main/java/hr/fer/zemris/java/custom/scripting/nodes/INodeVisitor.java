package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Interface that is useful for going through nodes and 
 * generally using Visitor design patter
 * @author Lucija Valentić
 *
 */
public interface INodeVisitor {
	/**
	 * Method that is performed when {@link TextNode} is visited
	 * @param node TextNode
	 */
	public void visitTextNode(TextNode node);
	/**
	 * Method that is performed when {@link ForLoopNode} is visited
	 * @param node ForLoopNode
	 */
	public void visitForLoopNode(ForLoopNode node);
	/**
	 * Method that is performed when {@link EchoNode} is visited
	 * @param node EchoNode
	 */
	public void visitEchoNode(EchoNode node);
	/**
	 * Method that is performed when {@link DocumentNode} is visited
	 * @param node DocumentNode
	 */
	public void visitDocumentNode(DocumentNode node);
}
