package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
/**
 * Class represents graph. It extends {@link JComponent}. 
 * Instances of this class are given one instance of class
 * {@link BarChart}. That object holds all the information
 * object of this class needs to be made. All the "drawing"
 * is done directly inside of it, and no other components
 * or containers are used. Graph made by this class has
 * x and y axes, names of x and y axes written next to them
 * and of course columns that represent some values. This class
 * doesn't know hot to interpret those informations and values,
 * it only know how to draw them to look like a graph. So if 
 * wrong informations are put in object {@link BarChart}, this
 * object will just draw them. So validation of the informations
 * needs to be checked somewhere else.
 * @author Lucija Valentić
 *
 */
public class BarChartComponent extends JComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Reference to {@link BarChart} object,
	 * represents all the information this
	 * object needs to draw a graph
	 */
	private BarChart chart;

	/**
	 * Constructor
	 * @param chart object {@link BarChart}
	 */
	public BarChartComponent(BarChart chart) {
		super();
		this.chart = chart;
	}
	
	/**
	 * {@inheritDoc}	
	 */
	@Override
	protected void paintComponent(Graphics g) {
		//super.paintComponent(g);
		
		//SETUP
		Graphics2D g2 = (Graphics2D) g.create();
		
		this.setOpaque(true);
		
		Insets in = getInsets();
		
		double height = getHeight() - in.top - in.bottom;
		double width = getWidth() - in.left - in.right - 40;
	
		
		//tekst koji piše na x/y osi
		String yAxisText = chart.getDescY();
		String xAxisText = chart.getDescX();
		
		//onaj pravilni razmak između teksta i brojeva, brojeva i stupaca
		double fixedSpace = 7;
		
		if(height < 5 || width < 5) {
			fixedSpace = 1;
		}
		
		FontMetrics fm = g2.getFontMetrics();
				
		double stringHeight = fm.getHeight();
		
		//visina i širina same slike stupaca, bez onih tekstova i brojki
		double chartHeight = height - stringHeight * 2 - fixedSpace * 3 - 10;
		double chartWidth = width - fixedSpace * 3 - stringHeight - fm.stringWidth(String.valueOf(chart.getyMax()));
		
		//od kud počinje sama slika stupaca, bez brojki i tekstova
		double xBeginningOfChart = width - chartWidth;// + fixedSpace;
		double yBeginningOfChart = height - stringHeight * 2 - fixedSpace * 2;
		
		int lastY = returnLastY();
		
		int distanceTwoColumns = 2;
		
		
		//koliko je svaka pojedina brojka udaljena, na kojoj se udaljenosti mora crtati (udaljenost između n i n+1), u crtanju prilagoditi; vertikalno
		double oneRowHeight = (double) chartHeight / (lastY - chart.getyMin());
		
		//koliko je širok svaki stupac na grafu; horizontalno; na sredini ispisati brojke
		double oneColumnWidth = (double) chartWidth / chart.getList().size();
		
		//liste svih x i y vrijednosti koje će se crtati
		List<Integer> listX = new ArrayList<Integer>();
		List<Integer> listY = new ArrayList<Integer>();
		
		List<XYValue> list = chart.getList();
		
		for(XYValue value : list) {
			listX.add(Integer.valueOf(value.getX()));
		}
		
		for(int i = chart.getyMin(); i <= lastY; i += chart.getDiff()) {
			listY.add(Integer.valueOf(i));
		}
		
		//CRTANJE
		//predstavljaju koordinate koje će služiti za crtanje; stalno se mijenjaju s obzirom koji dio slike se crta
		double x;
		double y;
		
		//crtanje teksta na x osi
		
		x = in.left + fixedSpace * 2 + stringHeight + fm.stringWidth(String.valueOf(chart.getyMax())) + (chartWidth + distanceTwoColumns*(listX.size() - 1))/2 - fm.stringWidth(chart.getDescX())/2;
		y = height - 5;		
		g2.setColor(Color.BLACK);

		g2.drawString(xAxisText, (float)x, (float)y);
		
		y += 5;
		
		//crtanje brojki na x osi
		
		x = xBeginningOfChart + distanceTwoColumns;
		y = y - fixedSpace - stringHeight;
		
		for(Integer n : listX) {
			
			String number = String.valueOf(n);
			x = x + (double) oneColumnWidth/2 - (double)fm.stringWidth(number)/2;
			g2.drawString(number, (float)x, (float)y);
			
			x += (double) oneColumnWidth/2 + (double)fm.stringWidth(number)/2 + distanceTwoColumns;
		}
		
		//crtanje linije na x osi
		
		y = y - fixedSpace - stringHeight;
				
		x = xBeginningOfChart + 2;
		g2.drawLine((int)(x - 7), (int)y, (int)(x + chartWidth + 10), (int)y);
		
		int[] xPoints = {(int)(x + chartWidth + 10), (int)(x + chartWidth + 10), (int)(x+chartWidth+10 +12)};
		int[] yPoints = {(int) (y - 5),(int) (y + 5), (int)y };
		g2.fillPolygon(xPoints, yPoints, 3);
		
		//crtanje stupaca u grafu
		
		g2.setColor(Color.decode("#7EC0AC"));
		
		for(XYValue value : list) {
			
			double valueY = value.getY();
			double rectHeight = oneRowHeight * valueY;			
			
			g2.fillRect((int)x, (int)(y - rectHeight),(int) oneColumnWidth, (int)rectHeight);
			x += oneColumnWidth + distanceTwoColumns;
		}
		
		g2.setColor(Color.BLACK);
		//crtanje teksta na y osi
		
		 AffineTransform defaultAt = g2.getTransform();
		 AffineTransform at = new AffineTransform();
		 at.rotate(-Math.PI/2);
		 g2.setTransform(at);
		 
		 x = in.left + 10;//5 je dodano tek toliko da tekst ne bude skroz lijevo
		 y = (double)chartHeight/2 + (double)fm.stringWidth(yAxisText)/2 - fixedSpace * 3 + stringHeight;
		 
		 g2.drawString(yAxisText, (int) -y, (int)x);
		 
		 x -= 10;
		 g2.setTransform(defaultAt);
		
		//crtanje brojki na y osi
		 
		 x += stringHeight + fixedSpace;
		 y = yBeginningOfChart + (double)oneRowHeight/2 - 5;
		 
		 for(Integer n : listY) {
			 
			 //y = y - oneRowHeight * n;
			 String number = String.valueOf(n);
		
			 g2.drawLine((int)(x + fm.stringWidth(String.valueOf(chart.getyMax())) + fixedSpace + 1),(int) (y - oneRowHeight*chart.getDiff() - 5) , (int)(x + fm.stringWidth(String.valueOf(chart.getyMax())) + fixedSpace + 14), (int) (y - oneRowHeight*chart.getDiff()-5));
			 
			 if(fm.stringWidth(number) < fm.stringWidth(String.valueOf(chart.getyMax()))) {
				 
				 g2.drawString(number, (int)(x + (fm.stringWidth(String.valueOf(chart.getyMax())) - fm.stringWidth(number))), (int)y);
			 }else {
				 g2.drawString(number, (int)x, (int)y);
			 }
			 y = y - oneRowHeight * chart.getDiff();
			 
		 }
		
		 x += fm.stringWidth(String.valueOf(lastY)) + fixedSpace;
		 y = yBeginningOfChart;
		 //crtanje linije na y osi
		 g2.setTransform(at);
		 
		 g2.drawLine((int)-(y - chartHeight), (int)(x + fixedSpace), (int)-(y + 20), (int)(x + fixedSpace));
		 
		 int[] xPoints2 = {(int)(-y + chartHeight - 10), (int)(-y + chartHeight - 10), (int)(-y + chartHeight + 4)};
		 int[] yPoints2 = {(int) (x-5 + 6),(int) (x+5 + 6), (int)(x + 6) };
		 g2.fillPolygon(xPoints2, yPoints2, 3);
			
		 //kraj
		 g2.setTransform(defaultAt);
		 g2.dispose();
	}
	
	/**
	 * Checks if distance between given number
	 * and smallest y shown on the graph is some
	 * multiple of a difference that is given in
	 * the information of the graph. Returns <code>true</code>
	 * if it is, <code>false</code> otherwise
	 * @param x number that needs to be checked.
	 * @return boolean
	 */
	private boolean isNormalDifference(int x) {
		
		int n = chart.getyMin();
		while(n < x) {
			n += chart.getDiff();
		}
		
		return n == x;
		
	}
	
	/**
	 * Method that returns last y that will be written in graph,
	 * more precisely shown in graph
	 * @return
	 */
	private int returnLastY() {
		
		if(isNormalDifference(chart.getyMax())) {
			return chart.getyMax();
		}
		
		int n = chart.getyMin();
		
		while(n < chart.getyMax()) {
			n += chart.getDiff();
		}
		
		
		return n;
		
	}
	
	
}
