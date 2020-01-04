package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class that is used for keeping states of a turtle
 * in the stack. With this class, LSystems
 * can be drawn, because they can understand
 * what, where and how something has to be shown.
 * 
 * @author Lucija Valentić
 *
 */
public class Context {

	/**
	 * Stack in which states of a turtles
	 * are being kept
	 */
	private ObjectStack<TurtleState> stack = new ObjectStack<>();
	
	/**
	 * Returns state from the top
	 * of the stack
	 * 
	 * @return stack.peek()
	 */
	public TurtleState getCurrentState() {
		
		return stack.peek();
	}
	
	/**
	 * Puts given state in the stack,
	 * on top of the stack
	 * 
	 * @param state
	 */
	public void pushState(TurtleState state) {
		
		stack.push(state);
	}
	
	/**
	 * Removes one state from 
	 * top of the stack
	 */
	public void popState() {
		stack.pop();
	}
}
