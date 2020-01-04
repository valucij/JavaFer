package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Class extends {@link JFrame}. When runned, it displays two lists and 
 * one button. When button is pressed, new prime number is generated, and 
 * added to list, essentialy, written in one and in other list. Once lists become
 * too big, scroll bar is made
 * @author Lucija Valentić
 *
 */
public class PrimDemo extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public PrimDemo() {
		
		setLocation(500, 100);
		setSize(300, 200);
		setTitle("Primarni brojevi");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		initGUI();
	}
	
	/**
	 * Initialize the whole scene, in the same way
	 * decribed above.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel<Integer> model = new PrimListModel<Integer>();
		JList<Integer> list1 = new JList<Integer>(model);
		JList<Integer> list2 = new JList<Integer>(model);
		
		JButton sljedeci = new JButton("Sljedeći!");
		
		ActionListener listen = a ->{
			model.next();
		};
		
		sljedeci.addActionListener(listen);
		
		JPanel central = new JPanel(new GridLayout(1,0));
		central.add(new JScrollPane(list1));
		central.add(new JScrollPane(list2));
		
		cp.add(central, BorderLayout.CENTER);
		cp.add(sljedeci, BorderLayout.PAGE_END);
		
	}
	 
	/**
	 * Method that is called in the beginning of a program
	 * @param args arguments of command line
	 */
	public static void main(String[] args) {
	
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new PrimDemo();
			frame.pack();
			frame.setVisible(true);
		});

	}

}
