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
public class OperationButton extends JButton{

	/**
	 * Name of a button
	 */
	private String name;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 * @param string
	 */
	public OperationButton(String string) {
		super(string);
		this.name = string;
	}

	/**
	 * Method that is called when button is pressed. This method also gets
	 * instance of {@link Calculator}, so it can display window with an error, if needed
	 * @param model
	 * @param calc
	 */
	public void doAction(CalcModelImpl model, Calculator calc) {
	
		if(!model.isActiveOperandSet()) {
			model.setActiveOperand(Double.valueOf(model.toString()));	
		}else {
			DoubleBinaryOperator operator = model.getPendingBinaryOperation();
			if(operator != null) {
				double result = operator.applyAsDouble(model.getActiveOperand(), Double.valueOf(model.toString()));
				model.setActiveOperand(result);		
			}
		
		}
		
		model.setPendingBinaryOperation(new Operator(name));
		model.clear();
		
	}
}
