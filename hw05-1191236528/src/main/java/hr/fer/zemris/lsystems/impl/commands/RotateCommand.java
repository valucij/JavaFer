package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
/**
 * Class implements {@link Command}. This class
 * is used when we want to change direction of
 * a turtle (a point that helps us draw things).
 * 
 * @author Lucija Valentić
 *
 */
public class RotateCommand implements Command{

	/**
	 * Represents an angle by how much we
	 * must rotate direction of a turtle
	 */
	private double angle;
	
	/**
	 * Constructor
	 * @param angle
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}
	
	/**
	 * When called, this method rotates direction of a turtle.
	 * Current state of a turtle gets from the stack, and that
	 * it returns new state with new direction on top of
	 * the stack
	 * 
	 * @param ctx Context
	 * @param painter Painter
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		
		TurtleState state = ctx.getCurrentState().copy();
		ctx.popState();
				
		state.getCurrentDirection().rotate(angle);
		ctx.pushState(state);		
	
	}
	
}
