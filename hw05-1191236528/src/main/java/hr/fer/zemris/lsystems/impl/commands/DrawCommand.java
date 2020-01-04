package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;
/**
 * Class implements {@link Command} interface, and 
 * is used when we want to draw something. This class
 * has method that, when executed, draws a line in the
 * way user says to draw
 * 
 * @author Lucija Valentić
 *
 */
public class DrawCommand implements Command{

	/**
	 * Represents double that says
	 * how much we must scale drawn line
	 */
	private double step;
	
	/**
	 * Constructor
	 * 
	 * @param step
	 */
	public DrawCommand(double step) {
		this.step = step;
	}
	
	/**
	 * Method that draws a line in the way user wants it.
	 * Given painter paints that line, and informations
	 * about start and end point of that line are in 
	 * the given ctx, that is made by user.
	 * @param ctx Context
	 * @param painter Painter
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		
		TurtleState state = ctx.getCurrentState().copy();
		ctx.popState();
		
		
		painter.drawLine(state.getCurrentPosition().getX(), state.getCurrentPosition().getY(), 
				state.getCurrentPosition().getX() + state.getCurrentShift() * step * state.getCurrentDirection().getX(), 
				state.getCurrentPosition().getY() + state.getCurrentShift() * step * state.getCurrentDirection().getY(),
				state.getCurrentColor(), 1f);
		
		Vector2D newPosition = new Vector2D(state.getCurrentPosition().getX() + state.getCurrentShift() * step * state.getCurrentDirection().getX(), 
				state.getCurrentPosition().getY() + state.getCurrentShift() * step * state.getCurrentDirection().getY());
		
		TurtleState newState = new TurtleState(newPosition, state.getCurrentDirection(), state.getCurrentColor(), state.getCurrentShift());
		
	
		ctx.pushState(newState);
	}

}
