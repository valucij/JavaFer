package hr.fer.zemris.java.gui.calc.util;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Class extends class {@link JButton}. This class acts the same as
 * {@link JButton}, but with few extra options. This class was made beacuse there
 * are many buttons that need the same function, but they are different in just one action.
 * Objects of this class are used in {@link Calculator}, and when they are pressed, method
 * {@link #doAction(CalcModelImpl, Calculator)} is called. What kind of action is performed
 * depends on {@link #name} that is given in constructor
 *	
 * @author Lucija Valentić
 *
 */
public class NumberButton extends JButton{

	/**
	 * Name of a button
	 */
	private int number;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 * @param name
	 */
	public NumberButton(String name) {
		super(name);
		this.number = Integer.valueOf(name);
	}
 
	/**
	 * Method is called when button is pressed. Number that is pressed
	 * is written in display of a calculator
	 * @param model
	 */
	public void doAction(CalcModelImpl model, Calculator calc) {
		
		try {
			model.insertDigit(number);
		}catch(CalculatorInputException ex) {
			calc.errorMessage("Press \"res\" before entering digits");
		}
		
	}
	 
}
