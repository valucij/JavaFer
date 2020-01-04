package hr.fer.zemris.java.hw17.jvdraw.objects.editor;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.JVDrawException;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.JVPoint;
import hr.fer.zemris.java.hw17.jvdraw.util.Util;

/**
 * This class extends {@link GeometricalObjectEditor}. It
 * is used when user clicks in list and wants to edit objects
 * @author Lucija Valentić
 *
 */
public class FilledCircleEditor extends GeometricalObjectEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Filled circle
	 */
	private FilledCircle filledCircle;
	/**
	 * Text filed for x
	 */
	private JTextField x;
	/**
	 * Text filed for y
	 */
	private JTextField y;
	/**
	 * Text filed for radius
	 */
	private JTextField r;
	/**
	 * Text field for background color
	 */
	private JTextField bgColor;
	/**
	 * Text field for foreground color
	 */
	private JTextField fgColor;
	
	/**
	 * Constructor
	 * @param filledCircle
	 */
	public FilledCircleEditor(FilledCircle filledCircle) {
		this.filledCircle = filledCircle;
		
		this.setLayout(new GridLayout(2, 5));
		
		this.x = new JTextField();
		x.setText(String.valueOf((int)filledCircle.getX()));
		this.y = new JTextField();
		y.setText(String.valueOf((int)filledCircle.getY()));
		this.r = new JTextField();
		r.setText(String.valueOf((int)filledCircle.getRadius()));
		this.bgColor = new JTextField();
		Color color = filledCircle.getBgColor();
		String s = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
		bgColor.setText(s);
		
		color = filledCircle.getFgColor();
		s = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
		this.fgColor = new JTextField();
		fgColor.setText(s);
		
		
		JLabel lx = new JLabel("FCircle x");
		JLabel ly = new JLabel("FCircle y");
		JLabel lr = new JLabel("FCirlce radius");
		JLabel lbg = new JLabel("FCircle bgColor");
		JLabel lfg = new JLabel("FCircle fgColor");
		
		
		this.add(lx);
		this.add(ly);
		this.add(lr);
		this.add(lbg);
		this.add(lfg);
		
		this.add(x);
		this.add(y);
		this.add(r);
		this.add(bgColor);
		this.add(fgColor);
	}

	/**
	 * First point
	 */
	private double xField;
	/**
	 * Second point
	 */
	private double yField;
	/**
	 * Radius
	 */
	private double radius;
	/**
	 * Background color
	 */
	private String bgC;
	/**
	 * Foreground color
	 */
	private String fgC;
	/**
	 * Sets this.xField
	 * @param xField
	 */
	public void setxField(double xField) {
		this.xField = xField;
	}

	/**
	 * Sets this.yField
	 * @param yField
	 */
	public void setyField(double yField) {
		this.yField = yField;
	}

	/**
	 * Sets this.radius
	 * @param radius
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkEditing() {
		
		xField = Double.parseDouble(x.getText());
		yField = Double.parseDouble(y.getText());
		radius = Double.parseDouble(r.getText());
		bgC = bgColor.getText();
		fgC = fgColor.getText();
		
		if((bgC.length() == 7 && (!bgC.startsWith("#") || (!isHex(bgC)))) 
				|| (fgC.length() == 7 && (!fgC.startsWith("#") || !isHex(fgC))) ||
				(bgC.length() == 6 && !isHex(bgC)) || (fgC.length() == 6 && !isHex(fgC))) {
			throw new JVDrawException("Index out of bounds");
		}
		
		if(xField < 0 || yField < 0 || radius < 0 ||
				xField + radius >= width || yField + radius >= height ||
			xField - radius <= 0 || yField - radius <= 0) {
			throw new JVDrawException("Index out of bounds");
		}
		
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void acceptEditing() {
		
		if(!bgC.startsWith("#")) {
			bgC = "#" + bgC;
		}
		if(!fgC.startsWith("#")) {
			fgC = "#" + fgC;
		}
		
		filledCircle.setAll(new JVPoint(xField, yField), radius, Color.decode(fgC), Color.decode(bgC));
		Util.setSaveStatus();
	}


}
