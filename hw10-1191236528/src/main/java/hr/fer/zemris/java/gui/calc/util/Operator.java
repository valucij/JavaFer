package hr.fer.zemris.java.gui.calc.util;

import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;


/**
 * Class implements interface {@link DoubleBinaryOperator} so it
 * can use its function, but so it can be more editable. This class is used when
 * user needs to perform operations on numbers, and such
 * operations are adding, subtracting, dividing and multipling.
 * Then, operator this instance
 * is created, and later, when needed, it can be used
 * @author Lucija Valentić
 *
 */
public class Operator implements DoubleBinaryOperator{

	/**
	 * Name of a operator, also decides which operation
	 * will be associated with this operator
	 */
	private String operator;
	
	/**
	 * Constructor
	 * @param operator
	 */
	public Operator(String operator) {
		super();
		this.operator = operator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double applyAsDouble(double left, double right) {
		
		switch(operator) {
		case "+":
			return left + right;
		case "-":
			return left - right;
		case "/":
			if(right == 0 || right == Double.NEGATIVE_INFINITY || right == Double.NEGATIVE_INFINITY || right == Double.NaN) {
				throw new CalculatorInputException("Can't divide with that number");
			}
			return left / right;
		case "*":
			return left * right;
		case "x^n":
			return Math.pow(left, right);
		case "x^(1/n)":
			return Math.pow(left, (double)1/right);
		
		}
		return right;
		
	}
	

}
