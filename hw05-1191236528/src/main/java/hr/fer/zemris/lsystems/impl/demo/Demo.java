package hr.fer.zemris.lsystems.impl.demo;

import java.awt.Color;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

public class Demo {

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
	
	
	private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
		
		String[] data = new String[] {
				"origin			0.05 0.4",
				"angle			0",
				"unitLength			0.9",
				"",
				"unitLengthDegreeScaler  1.0 / 3.0",
				"command F draw 1",
				"command + rotate 60",
				"command - rotate -60",
				"",
				"axiom F",
				"",
				"production F F+F--F+F"
		};
		
		return provider.createLSystemBuilder().configureFromText(data).build();
		
		
	}
	
	
	
	public static void main(String[] args) {
		
		//LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));
		
	//	LSystemViewer.showLSystem(LSystemBuilderImpl::new);
		
		LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));
		
		
		
		/*
		LSystemViewer.showLSystem(new LSystem() {
			
			public String generate(int level) {
				return "";
			}
			
			public void draw(int level, Painter painter) {
				painter.drawLine(0.1, 0.1, 0.9, 0.1, Color.RED, 1f);
				painter.drawLine(0.9, 0.1, 0.9, 0.9, Color.GREEN, 1f);
				painter.drawLine(0.9, 0.9, 0.1, 0.1, Color.BLUE, 1f);
			}
		});*/

	}

}
