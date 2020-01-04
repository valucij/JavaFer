package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
/**
 * Class implements {@link Command} interface.
 * It is used when the color of the currentState
 * of a turtle needs to change.
 * 
 * @author Lucija Valentić
 *
 */
public class ColorCommand implements Command{

	/**
	 * Represents to which color 
	 * currentState color needs to 
	 * change
	 */
	private Color color;
	
	/**
	 * Constructor
	 * @param color
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}
	
	/**
	 * Method when called changes color
	 * of a currentState, and puts that new changed
	 * state on top of the stack
	 * 
	 * @param ctx Context
	 * @param painter Painter
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		
		TurtleState state = ctx.getCurrentState().copy();
		ctx.popState();
		
		ctx.pushState(new TurtleState(state.getCurrentPosition(), state.getCurrentDirection(), color, state.getCurrentShift()));
		
	}

}
