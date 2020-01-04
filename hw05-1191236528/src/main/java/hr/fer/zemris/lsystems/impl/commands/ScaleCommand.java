package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * This class implements {@link Command}. It has methods
 * that are used when we want to scale current moving shift
 * of a point (we call it turtle) that is responsible
 * for actually providing information about how to
 * draw something (if we want to draw it with {@link DrawCommand}).
 * 
 * @author Lucija Valentić
 *
 */
public class ScaleCommand implements Command{

	/**
	 * Represents double that says for
	 * how much we must scale current moving
	 * shift
	 */
	private double factor;
	
	/**
	 * Constructor
	 * @param factor
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}
	
	/**
	 * Method, when called, scales turtle's current moving
	 * shift with this.factor.
	 * 
	 * @param ctx Context
	 * @param painter Painter
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		
		TurtleState state = ctx.getCurrentState().copy();
		ctx.popState();
		
		double newShift = state.getCurrentShift() * factor;
		TurtleState newState = new TurtleState(state.getCurrentPosition(),state.getCurrentDirection(),state.getCurrentColor(),newShift);
		
		ctx.pushState(newState);
	}

}
