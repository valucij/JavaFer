package hr.fer.zemris.java.gui.calc.util;

import java.util.function.DoubleBinaryOperator;

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
public class OtherButton extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Name of a button
	 */
	private String name;
	
	/**
	 * Constructor
	 * @param string
	 */
	public OtherButton(String string) {
		super(string);
		this.name = string;
	}
	
	/**
	 * Method that is called when button is pressed. This method also gets
	 * instance of {@link Calculator}, so it can perform action on internal stack
	 * from {@link Calculator}. 
	 * @param model
	 * @param calc
	 */
	public void doAction(CalcModelImpl model, Calculator calc) {
		
		switch(name) {
		
		case "=":
			
			if(!model.isActiveOperandSet()) {
				calc.errorMessage("There is no active operand");
				return;
			}
			
			double operand = model.getActiveOperand();
			double newOperand = Double.valueOf(model.toString());
			DoubleBinaryOperator operator = model.getPendingBinaryOperation();
			
			if(operator == null) {
				calc.errorMessage("There is no active operator");
				return;
			}
			double result = operator.applyAsDouble(operand, newOperand);
			model.setValue(result);
			model.setActiveOperand(result);
			model.setPendingBinaryOperation(null);
			
			return;
		case ".":
			try {
				model.insertDecimalPoint();
			}catch(CalculatorInputException ex) {
				calc.errorMessage("Can't add \".\"");
				return;
			}
			return;
		case "+/-":
			try {
				model.swapSign();
				//model.clear();
			}catch(CalculatorInputException ex) {
				calc.errorMessage("Can't swap signs");
				return;
			}
			return;
		case "clr":
			model.clear();
			return;
		case "res":
			model.clearAll();
			return;
		case "push":
			calc.getStack().push(Double.valueOf(model.toString()));
			return;
		case "pop":
			if(calc.getStack().isEmpty()) {
				calc.errorMessage("Stack is empty");
			}else {
				calc.getStack().pop();
			}
			return;
		
		}
		
	}
}
