package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Class in which, when we run the program,
 * can choose from the file examples of some
 * fractals, and chosen fractal will be drawn.
 * 
 * @author Lucija Valentić
 *
 */
public class Glavni3 {

	public static void main(String[] args) {
		
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);

	}

}
