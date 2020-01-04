package searching.algorithms;

import java.util.Objects;

import searching.slagalica.KonfiguracijaSlagalice;

/**
 * Represents one particular transition node. It is similar to
 * the actual {@link Node}, but it is simplified to only have
 * state and cost in it, because it is more simple to use this class
 * in {@link SearchUtil} and in algorithms.
 * @author Lucija Valentić
 *
 * @param <S>, later S is {@link KonfiguracijaSlagalice}
 */
public class Transition<S> {

	/**
	 * Represents state
	 */
	private S state;
	/**
	 * Represents cost
	 */
	private double cost;
	
	/**
	 * Constructor
	 * @param state
	 * @param cost
	 */
	public Transition(S state, double cost) {
		this.state = state;
		this.cost = cost;
	}

	/**
	 * Returns state
	 * @return this.state
	 */
	public S getState() {
		return state;
	}

	/**
	 * Returns cost
	 * @return this.cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(state);
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
		if (!(obj instanceof Transition))
			return false;
		Transition<S> other = (Transition<S>) obj;
		return Objects.equals(state, other.state);
	}

	
}
