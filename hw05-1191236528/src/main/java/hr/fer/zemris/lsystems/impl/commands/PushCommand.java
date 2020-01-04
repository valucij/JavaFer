package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
/**
 * Class implements {@link Command} interface. 
 * User can use it when he want's to put on top of the stack
 * the same state of a turtle that already 
 * is on top of the stack, but without deleting it first 
 * from the stack
 * 
 * @author Lucija Valentić
 *
 */
public class PushCommand implements Command{

	/**
	 * Method when called puts currentState of
	 * a turtle on top of the stack, without
	 * deleting it first.
	 * 
	 * @param ctx Context
	 * @param painter Painter
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		
		TurtleState newState = ctx.getCurrentState().copy();
		ctx.pushState(newState);
		
	}


}
