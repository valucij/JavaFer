package hr.fer.zemris.java.gui.calc.util;

import java.util.List;
import java.util.function.DoubleBinaryOperator;
/**
 * Class was made so it can be used as help to custom made buttons.
 * Such buttons are {@link NumberButton}, {@link MathButton} etc.
 * Class provides methods that creates new binary operators, or 
 * return some value; this class also helps in managing calculator.
 * 
 * @author Lucija Valenitć
 *
 */
public class Util {

	/**
	 * Represents whether funcion buttons are "normal", aka "sin", "cos"
	 * etc, or "inverse" aka "arcsin", "arccos", etc
	 */
	private static boolean inv = false;
	
	/**
	 * Changes mode of {@link MathButton}. Those buttons don't show
	 * "sin", "cos" etc. anymore, but "arcsin", "arccos" etc
	 * @param buttons
	 */
	public static void changeInv(List<MathButton> buttons) {
		
		inv = inv? false : true;
		
		for(MathButton b : buttons) {
			
			String name = inv? b.getInverse() : b.getOriginal();
			b.setName(name);
			b.setText(name);
			b.changeInv();
		}
		
	}
	/**
	 * Creates new binary operator, that can either add, multiply, 
	 * subtract, or divide numbers. What kind of operator is made depends on given string
	 * @param string
	 * @return DoubleBinaryOperator
	 */
	public static DoubleBinaryOperator operatorFactory (String string) {
		return new Operator(string);
	}
	
	/**
	 * Returns number from a given string
	 * @param string
	 * @return
	 */
	public static int number (String string) {
		return Integer.valueOf(string);
	}
	/**
	 * Creates new binary operator, that can either caluculate sin of numbers,
	 * or cos, or etc. What kind of operator is made depends on given string
	 * @param string
	 * @return DoubleBinaryOperator
	 */
	public static MathOperationOperator functionFactory(String string, boolean inv) {	
		return new MathOperationOperator(string, inv); 
	}
	
}
