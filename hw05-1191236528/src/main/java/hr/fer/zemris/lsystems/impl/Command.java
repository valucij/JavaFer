package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Interface that is used when drawing
 * LSystem. It can be implemented
 * so those classes do thing like 
 * drawing, skipping, scaling, rotating etc.
 * 
 * @author Lucija Valentić
 *
 */
public interface Command {
	/**
	 * Abstract method, usually when called,
	 * does the thing what the class was implemented
	 * for
	 * 
	 * @param ctx
	 * @param painter
	 */
	void execute(Context ctx, Painter painter);
}
