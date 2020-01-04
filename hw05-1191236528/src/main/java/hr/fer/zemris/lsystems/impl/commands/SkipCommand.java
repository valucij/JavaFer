package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;
/**
 * Class implements {@link Command} interface. 
 * It does somethin similarly as {@link DrawCommand}, but
 * it doesn't draw a line.
 * 
 * @author Lucija Valentić
 *
 */
public class SkipCommand implements Command {
	
	/**
	 * Represents for how much skipped line
	 * has to be scaled
	 */
	private double step;
	
	/**
	 * Constructor
	 * 
	 * @param step
	 */
	public SkipCommand(double step) {
		this.step = step;
	}
	
	/**
	 * Method that with given information about
	 * start and end point of a line, skips said
	 * line. Informations are given in ctx , and are
	 * provided by user
	 * 
	 * @param ctx Context
	 * @param painter Painter
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		
		TurtleState state = ctx.getCurrentState().copy();
		ctx.popState();
		
		Vector2D newPosition = new Vector2D(state.getCurrentPosition().getX() + state.getCurrentShift() * step * state.getCurrentDirection().getX(), 
				state.getCurrentPosition().getY() + state.getCurrentShift() * step * state.getCurrentDirection().getY());
		
		TurtleState newState = new TurtleState(newPosition, state.getCurrentDirection(), state.getCurrentColor(), state.getCurrentShift());
		
	
		ctx.pushState(newState);
	}

	
}
