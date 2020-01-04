package searching.algorithms;

import java.util.Objects;

import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;

/**
 * Class represents one node, it has reference to current states, and
 * to parents states. ALso it has reference to the cost of switching 
 * between states. This node is used in {@link Slagalica}, more specifically,
 * in {@link SearchUtil} for searching the right configuration.
 * @author Lucija Valentić
 *
 * @param <S>, later it represents {@link KonfiguracijaSlagalice}
 */
public class Node<S> {

	/**
	 * Represents current state
	 */
	private S currentState;
	/**
	 * Represents node of a parent, the one
	 * that keeps state of a node that is the one 
	 * before current one; between them, the cost is 1
	 */
	private Node<S> parent;
	/**
	 * Represents the cost
	 */
	private double cost;
	
	/**
	 * Constructor
	 * @param parent
	 * @param state
	 * @param cost
	 */
	public Node(Node<S> parent, S state, double cost) {
		this.currentState = state;
		this.parent = parent;
		this.cost = cost;
	}
	
	/**
	 * Returns current state
	 * @return this.state
	 */
	public S getState() {
		return currentState;
	}
	
	/**
	 * Returns cost
	 * @return this.cost
	 */
	public double getCost() {
		return cost;
	}
	
	/**
	 * Returns parent of a current node
	 * @return this.parent
	 */
	public Node<S> getParent(){
		return parent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(currentState);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Node))
			return false;
		Node<S> other = (Node<S>) obj;
		return Objects.equals(currentState, other.currentState);
	}
	
	
}
