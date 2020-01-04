package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;
/**
 * Class in which we are drawing Koch curve,
 * LSystem is configurated with 
 * LSystemBuilderProvider
 * 
 * @author Lucija Valentić
 *
 */
public class Glavni1 {

	/**
	 * Method that makes configuration of
	 * a LSystem, and returns said LSystem
	 * 
	 * @param provider
	 * @return LSystem
	 */
	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
		return provider.createLSystemBuilder()
				.registerCommand('F', "draw 1")
				.registerCommand('+', "rotate 60")
				.registerCommand('-', "rotate -60")
				.setOrigin(0.05, 0.4)
				.setAngle(0)
				.setUnitLength(0.9)
				.setUnitLengthDegreeScaler(1.0/3.0)
				.registerProduction('F', "F+F--F+F")
				.setAxiom("F")
				.build();
	}
	
	/**
	 * Main method called in the beginning of a program.
	 * 
	 * @param args arguments of command line
	 */
	public static void main(String[] args) {
		
		LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));

		
	}
}
