package hr.fer.zemris.java.gui.calc.util;

import java.util.function.UnaryOperator;

/**
 * Class implements interface {@link UnaryOperator} so it
 * can use its function, but so it can be more editable. This class is used when
 * user needs to perform operations on numbers, and such
 * operations are actually mathematical functions. Then, operator this instance
 * is created, and it can be used as unary. 
 * @author Lucija Valentić
 *
 */
public class MathOperationOperator implements UnaryOperator<Double>{

	/**
	 * Name of operator, also decides which operation will be
	 * associated with this operator
	 */
	private String operation;
	/**
	 * Represents inverted mode of a {@link MathButton}
	 */
	private boolean inv;
	/**
	 * Flag; tells us whether this operator is
	 * unary or binary
	 */
	private boolean isDoubleOp;
	
	/**
	 * Constructor
	 * @param operation
	 * @param inv
	 */
	public MathOperationOperator(String operation, boolean inv) {
		super();
		this.operation = operation;
		this.inv = inv;
		isDoubleOp = false;
		
		if(operation.equals("x^n") || operation.equals("")) {
			isDoubleOp = true;
		}
	}

	/**
	 * Returns this.isDoubleOp
	 * @return boolean
	 */
	public boolean isDouble() {
		return isDoubleOp;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double apply(Double t) {
		if(inv == false) {//ako nije invertirano
			
			switch(operation) {
			case "sin":
				return Math.sin(t);
			case "cos":
				return Math.cos(t);
			case "tan":
				return Math.tan(t);
			case "ctg":
				return Math.atan(t);
			case "1/x":
				return (double) 1/t;
			case "ln":
				return Math.log(t);
			case "log":
				return Math.log10(t);
			}
			
		}else {
			switch(operation) {
			case "sin":
				return Math.asin(t);
			case "cos":
				return Math.acos(t);
			case "tan":
				return Math.tan(t);
			case "ctg":
				return Math.atan(t);
			case "1/x":
				return (double) t;
			case "ln":
				return Math.exp(t);
			case "log":
				return Math.pow(10, t);
			
			}
		}
		return t;
	}
}
