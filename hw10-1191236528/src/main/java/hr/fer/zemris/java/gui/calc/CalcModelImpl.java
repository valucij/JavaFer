package hr.fer.zemris.java.gui.calc;

import java.util.LinkedList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Class implements {@link CalcModel} and all
 * its function. It is used for {@link Calculator}
 * @author Lucija Valentić
 *
 */
public class CalcModelImpl implements CalcModel{

	/**
	 * Number that is current on display
	 */
	private String digits = "";
	/**
	 * Number that is double version of number on display
	 */
	private double number = 0;
	/**
	 * Variable that says whether the model i editable
	 */
	private boolean editable = true;
	/**
	 * Sing before the number
	 */
	private boolean sign = true;//true: +, false: -
	/**
	 * Current operand
	 */
	private double operand = 0;
	/**
	 * Current operator
	 */
	private DoubleBinaryOperator operator = null;
	/**
	 * List of all {@link CalcValueListener}
	 */
	private List<CalcValueListener> list = new LinkedList<CalcValueListener>();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		
		if(l == null) {
			throw new CalculatorInputException();
		}
		list.add(l);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		
		if(l == null) {
			return;
		}
		
		list.remove(l);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getValue() {
		return number;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(double value) {
		
		if(editable == false) {
			throw new CalculatorInputException();
		}
		
		
		sign = (value > 0)? true : false;
		number = value;
		digits = String.valueOf(value);		
		editable = false;
		
		for(CalcValueListener listener : list) {
			listener.valueChanged(this);
		}
		
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEditable() {
		return editable;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		editable = true;
		number = 0;
		digits = "";
		sign = true;
		for(CalcValueListener listener : list) {
			listener.valueChanged(this);
		}
		
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearAll() {
		
		clear();
		operand = 0;
		
		operator = null;
		
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void swapSign() throws CalculatorInputException {
		
		if(editable == false) {
			throw new CalculatorInputException();
		}
		
		sign = (sign == false)? true: false;
		
		number *= -1;
		
		for(CalcValueListener listener : list) {
			listener.valueChanged(this);
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		
		int i = digits.indexOf(".");
		
		if(i != -1 || editable == false || digits.isBlank()) {
			throw new CalculatorInputException();
		}
		
		digits += ".";
		
		for(CalcValueListener listener : list) {
			listener.valueChanged(this);
		}
		
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		
		if(editable == false) {
			throw new CalculatorInputException();
		}
		
		if(digits.equals("0") && digit == 0) {
			return;
		}
			
		digits += digit;
		
		try {
			number = (double) Double.parseDouble(digits);
			number *= (sign)? 1 : -1;
			
		}catch(NumberFormatException ex) {
			throw new CalculatorInputException();
		}
		
		if(!Double.isFinite(number)) {
			throw new CalculatorInputException();
		}
		
		for(CalcValueListener listener : list) {
			listener.valueChanged(this);
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isActiveOperandSet() {
		return operand != 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getActiveOperand() throws IllegalStateException {
		
		if(!isActiveOperandSet()) {
			throw new IllegalStateException();
		}
		
		return operand;
		
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActiveOperand(double activeOperand) {
		
		operand = activeOperand;
		
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearActiveOperand() {
		
		operand = 0;
		editable = true;
		
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		
		return operator;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		
		operator = op;
		
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
	
		
		String s = sign? "" : "-";
		
		if(digits.isBlank() || digits.equals("0") || (number == 0 && !digits.equals("0."))) {
			return s + "0";
		}
		
		if(number == Double.POSITIVE_INFINITY) {
			return "Infinity";
		}else if( number == Double.NEGATIVE_INFINITY) {
			return "-Infinity";
		}
	
		
		int i = digits.indexOf(".");
		
		
		if(i == -1) {
			return String.valueOf(Integer.parseInt(s + digits));
		}else {
			
			if(i + 1 == digits.length() || digits.substring(i + 1).isEmpty()) {
				
				return digits;
			}
			
			return String.valueOf(number);
		}
		
		
	}

}
