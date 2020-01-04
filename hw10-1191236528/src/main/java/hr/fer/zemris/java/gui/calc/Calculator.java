package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import javax.swing.*;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.util.*;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Class extends {@link JFrame}. This class is made so when
 * main program is run, calculator is shown. Instances of this object 
 * define one calculator that resembles old Windows calculators. Simple
 * operations can be performed, but no advanced operations are allowed.
 * Numbers can be added together, subtracted, multiplied, and also
 * user can find sin or cos of some number
 * 
 * @author Lucija Valentić
 *
 */
public class Calculator extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Internal stack
	 */
	private Stack<Double> stack;
	
	public Stack<Double> getStack() {
		return stack;
	}

	/**
	 * Constructor
	 */
	public Calculator() {
		
		setLocation(500, 500);
		setSize(1000, 500);
		setTitle("Calculator");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		stack = new Stack<Double>();
		initGUI();
	}
	
	/**
	 * Initialize the whole scene as described above
	 */
	private void initGUI() {
		
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout());
		
		CalcModelImpl model = new CalcModelImpl();
	
		JLabel display = new JLabel("0");
		display.setOpaque(true);
		
		display.setBackground(Color.decode("#FFFF99"));
		display.setHorizontalAlignment(SwingConstants.RIGHT);
		
		CalcValueListener listener = new CalcValueListener() {
			@Override
			public void valueChanged(CalcModel model) {
			
				display.setName(model.toString());
				display.setText(model.toString());
			}
		};
		
		model.addCalcValueListener(listener);
			

		ActionListener numberAction = a -> {
			NumberButton b = (NumberButton)a.getSource();
			b.doAction(model, this);
		};
		
		ActionListener mathAction = a -> {
			MathButton b = (MathButton)a.getSource();
			b.doAction(model, this);
		};
		
		ActionListener operationAction = a ->{
			OperationButton b = (OperationButton)a.getSource();
			b.doAction(model, this);
		};
		
		ActionListener other = a ->{
			OtherButton b = (OtherButton)a.getSource();
			b.doAction(model, this);
		};
		
		
		//number buttons
		int[] numbers = {0,1,2,3,4,5,6,7,8,9};
		
		List<NumberButton> nB = new LinkedList<NumberButton>();
		
		for(int i : numbers) {
			nB.add(new NumberButton(String.valueOf(i)));
		} 
		
		for(NumberButton b : nB) {
			b.addActionListener(numberAction);
		}
		
		//function buttons
		String[] math= {"sin", "cos", "tan", "ctg", "1/x", "log", "ln","x^n"};
		List<MathButton> mB = new LinkedList<MathButton>();
		
		for(String string : math) {
			mB.add(new MathButton(string));
		}
		
		for(MathButton b : mB) {
			b.addActionListener(mathAction);
		}
		
		
		//operations buttons
		String[] operations = {"/", "*", "-", "+"};
		List<OperationButton> oB = new LinkedList<OperationButton>();
		for(String string : operations) {
			oB.add(new OperationButton(string));
		}
		
		
		for(OperationButton b : oB) {
			b.addActionListener(operationAction);
		}
		
		List<OtherButton> otherB = new LinkedList<OtherButton>();
		
		OtherButton equals = new OtherButton("=");
		otherB.add(equals);
		equals.setBackground(Color.decode("#61A6D4"));
		
		
		OtherButton dot = new OtherButton(".");
		otherB.add(dot);
		dot.setBackground(Color.decode("#61A6D4"));
		
		
		OtherButton sign = new OtherButton("+/-");
		otherB.add(sign);
		sign.setBackground(Color.decode("#61A6D4"));
		
		
		OtherButton clear = new OtherButton("clr");
		otherB.add(clear);
		clear.setBackground(Color.decode("#61A6D4"));
		
		
		OtherButton res = new OtherButton("res");
		otherB.add(res);
		res.setBackground(Color.decode("#61A6D4"));
		
		
		OtherButton push = new OtherButton("push");
		otherB.add(push);
		push.setBackground(Color.decode("#61A6D4"));

		
		OtherButton pop = new OtherButton ("pop");
		otherB.add(pop);
		pop.setBackground(Color.decode("#61A6D4"));
		
		
		for(OtherButton b : otherB) {
			b.addActionListener(other);
		}
		
		
		JCheckBox chBox= new JCheckBox("Inv");
		chBox.setBackground(Color.decode("#61A6D4"));
		
		ActionListener check = a -> {
			Util.changeInv(mB);
		};
		
		chBox.addActionListener(check);
		
		int index = 0;
		
		for(int i = 5; i > 1; i --) {
			for(int j = 3; j < 6; j++) {
				nB.get(index).setBackground(Color.decode("#61A6D4"));
				cp.add(nB.get(index), new RCPosition(i, j));
				index++;
				if(i == 5) {
					j += 2;
				}
			}
		}
		
		index = 0;
		
		for(int i = 2; i >= 1; i--) {
			for(int j = 2; j < 6; j++) {
				
				mB.get(index).setBackground(Color.decode("#61A6D4"));
				cp.add(mB.get(index), new RCPosition(j, i));
				index++;
			}
		}
		
		index = 0;
		
		for(int i = 2; i < 6; i++) {
			oB.get(index).setBackground(Color.decode("#61A6D4"));
			cp.add(oB.get(index), new RCPosition(i, 6));
			index++;
		}
		
		cp.add(equals, new RCPosition(1, 6));
		cp.add(dot, new RCPosition(5, 5));
		cp.add(pop, new RCPosition(4, 7));
		cp.add(res, new RCPosition(2, 7));
		cp.add(sign, new RCPosition(5, 4));
		cp.add(clear, new RCPosition(1, 7));
		cp.add(push, new RCPosition(3, 7));
		cp.add(chBox, new RCPosition(5, 7));
		cp.add(display, new RCPosition(1,1));
		
		
	}
	
	/**
	 * Method is called when some error occurs, and "pop-up" window
	 * is displayed
	 */
	public void errorMessage(String text) {
		
		JOptionPane.showMessageDialog(
				this, //ako ovo ne damo, onda ne zna iscentirati taj dijalog prozor. inače će negdje ga postaviit, centar prozora vjv
				text, 
				"Error", 
				JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Main method that is called in the beginning of the program
	 * @param args arguments of a command line
	 */
	public static void main (String[] args) {
		
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new Calculator();
			frame.pack();
			frame.setVisible(true);
		});
		
	}
}
