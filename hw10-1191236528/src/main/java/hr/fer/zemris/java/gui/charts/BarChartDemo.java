package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * 
 * Class was made so picture of a graph can be shown/made.
 * All the user needs is to enter the path to some file where all the 
 * information about the graph are being held in command line. Those
 * informations need to be in special format, because otherwise
 * exception will be thrown. Form is:
 * first two lines are names for axes, then there are pairs of numbers,
 * each pair is divided with spaces, and each number in pair is divided with
 * ",", then there is the biggest y value, the smallest, and distance
 * between two y values.
 * 
 * @author Lucija Valentić
 *
 */
public class BarChartDemo extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Represents name of x axis
	 */
	private static String xAxis;
	/**
	 * Represents name of y axis
	 */
	private static String yAxis;
	/**
	 * Represents pairs of values
	 */
	private static List<XYValue> list;
	/**
	 * Represents the smallest y value shown in graph
	 */
	private static int yMin;
	/**
	 * Represents the largest y value shown in graph
	 */
	private static int yMax;
	/**
	 * Represents distance between each shown value on y axis
	 */
	private static int diff;
	/**
	 * Represents the name of a file from
	 * which data was used
	 */
	private static String inLabel;
	
	/**
	 * Constructor
	 */
	public BarChartDemo() {
	
		setLocation(500, 200);
		setSize(1000, 500);
		setTitle("Chart");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		initGUI();
	}
	/**
	 * Method that initializes the frame and the picture
	 * that will be shown
	 */
	private void initGUI() {
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		BarChart chart = new BarChart(list, xAxis, yAxis, yMin, yMax, diff);
		
		BarChartComponent componentChart = new BarChartComponent(chart);
		
		JLabel name = new JLabel(inLabel);
		name.setOpaque(true);
		name.setHorizontalAlignment(SwingConstants.CENTER);
		
		cp.add(name, BorderLayout.PAGE_START);
		cp.add(componentChart);
	}
	
	/**
	 * Main method called in the beginning of a program
	 * @param args arguments of command line
	 */
	public static void main(String[] args) {
		
		if(args.length != 1) {
			throw new IllegalArgumentException();
		}
		
		List<String> lines;
		
		try {
			lines = Files.readAllLines(Paths.get(args[0]),
					StandardCharsets.UTF_8);			
		} catch (IOException e) {
			System.out.println("da");
			return;
		}
		
		if(lines.size() < 6) {
			throw new IllegalDataException();
		}
		
		
		xAxis = lines.get(0);
		yAxis = lines.get(1);
		list = getListOfXY(lines.get(2));
		yMin = Integer.valueOf(lines.get(3));
		yMax = Integer.valueOf(lines.get(4));
		diff = Integer.valueOf(lines.get(5));
		inLabel = Paths.get(args[0]).toString();
			
		
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new BarChartDemo();
			frame.setVisible(true);
		});
		
	}
	
	/**
	 * Method returns list made from values given in <code>string</code>
	 * Pair of values are divided with spaces, and numbers in each pair
	 * are divided with ",". If something is not as
	 * described above, exception is thrown
	 * @param string needs to be extracted from
	 * @return List<XYValue>
	 */
	private static List<XYValue> getListOfXY(String string){
		
		if(string == null || string.isBlank()) {
			throw new IllegalDataException("Wrong format");
		}
		
		
		List<XYValue> xylist = new ArrayList<XYValue>();
		String[] pairs = string.split("\\s+");
		
		for(String s : pairs) {
			
			String[] number = s.split(",");
			
			try {
				int x = Integer.parseInt(number[0]);
				int y = Integer.parseInt(number[1]);	
				xylist.add(new XYValue(x, y));
				
			}catch(NullPointerException | NumberFormatException ex) {
				throw new IllegalDataException("Wrong format");
			}
			
			
		}
		
		return xylist;
	}

}

