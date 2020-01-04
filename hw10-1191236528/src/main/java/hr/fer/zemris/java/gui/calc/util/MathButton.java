package hr.fer.zemris.java.gui.calc.util;

import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.Calculator;

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
public class MathButton extends JButton{

	/**
	 * Name of a button
	 */
	private String original;
	/**
	 * Inverted name of a button
	 */
	private String inverse;
	/**
	 * Represets if the name of a button is inverted or not
	 */
	private boolean inv;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor
	 * @param string
	 */
	public MathButton(String string) {
		super(string);
		this.original = string;
		this.inv = false;
		
		switch(string) {
		
		case "sin":
			inverse = "arcsin";
			break;
		case "cos":
			inverse = "arccos";
			break;
		case "tan":
			inverse = "atan";
			break;
		case "ctg":
			inverse = "arcctg";
			break;
		case "1/x":
			inverse = "x";
			break;
		case "ln":
			inverse = "e^x";
			break;
		case "log":
			inverse = "10^x";
			break;
		case "x^n":
			inverse = "x^(1/n)";
			break;
			
		}
		
	}
	
	/**
	 * Changes inversion of a name (this.inv)
	 */
	public void changeInv() {
		inv = inv? false : true;
	}
	
	/**
	 * Returns this.original
	 * @return String
	 */
	public String getOriginal() {
		return original;
	}
	/**
	 * Returns this.inverse
	 * @return String
	 */
	public String getInverse() {
		return inverse;
	}
	/**
	 * Method that is called when button is pressed. This method also gets
	 * instance of {@link Calculator}, so it can display window with error if
	 * needed
	 * @param model
	 * @param calc
	 */
	public void doAction(CalcModelImpl model, Calculator calc) {
	
		if(!original.equals("x^n")) {
			
			double activeOperand = model.getActiveOperand();
			MathOperationOperator o = new MathOperationOperator(original, inv);
			
			double result = o.apply(Double.valueOf(model.toString()));
			model.setValue(result);
			
			model.clearActiveOperand();
			model.setActiveOperand(activeOperand);
			return;
			
		}
		
		if(!model.isActiveOperandSet()) {
			model.setActiveOperand(Double.valueOf(model.toString()));	
		}else {
			DoubleBinaryOperator operator = model.getPendingBinaryOperation();
			if(operator != null) {
			
				double result = operator.applyAsDouble(model.getActiveOperand(), Double.valueOf(model.toString()));
				model.setActiveOperand(result);
			}
			
		}
		
		if(inv) {
			model.setPendingBinaryOperation(new Operator(inverse));
		}else {
			model.setPendingBinaryOperation(new Operator(original));	
		}
		
		model.clear();
		
	}
	
}
