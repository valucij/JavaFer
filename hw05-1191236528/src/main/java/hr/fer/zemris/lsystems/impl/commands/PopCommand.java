package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
/**
 * Class implements {@link Command} interface.
 * It is used when it is needed to remove
 * currentState of a turtle from
 * the stack
 * 
 * @author Lucija Valentić
 *
 */
public class PopCommand implements Command{
	
	/**
	 * Method when called, removes currentState
	 * of a turtle from the stack
	 * 
	 * @param ctx Context
	 * @param painter Painter
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}
	
	
}