package hr.fer.zemris.java.hw17.jvdraw.objects.editor;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import hr.fer.zemris.java.hw17.jvdraw.JVDrawException;
import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.JVPoint;
import hr.fer.zemris.java.hw17.jvdraw.util.Util;

/**
 * Class extends {@link GeometricalObjectEditor}. It is 
 * used when user choose to edit some circle
 * @author Lucija Valentić
 *
 */
public class CircleEditor extends GeometricalObjectEditor {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Circle
	 */
	private Circle circle;
	/**
	 * first component of center
	 */
	private double xField;
	/**
	 * Second component of center
	 */
	private double yField;
	/**
	 * Radius
	 */
	private double radius;
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
	 * Text field for foreground color
	 */
	private JTextField fgColor;
	/**
	 * Color
	 */
	private String fgC;
	
	/**
	 * Constructor
	 * @param circle
	 */
	public CircleEditor(Circle circle) {
		this.circle = circle;
		
		this.setLayout(new GridLayout(2, 4));
		
		this.x = new JTextField();
		x.setText(String.valueOf((int)circle.getX()));
		this.y = new JTextField();
		y.setText(String.valueOf((int)circle.getY()));
		this.r = new JTextField();
		r.setText(String.valueOf((int)circle.getRadius()));
		this.fgColor = new JTextField();
		Color color = circle.getColor();
		String s = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
		fgColor.setText(s);
		
		JLabel lx = new JLabel("Circle x");
		JLabel ly = new JLabel("Circle y");
		JLabel lr = new JLabel("Cirlce radius");
		JLabel lfg = new JLabel("Circle color");
		
		this.add(lx);
		this.add(ly);
		this.add(lr);
		this.add(lfg);
		
		this.add(x);
		this.add(y);
		this.add(r);
		this.add(fgColor);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkEditing() {
		
		xField = Double.parseDouble(x.getText());
		yField = Double.parseDouble(y.getText());
		radius = Double.parseDouble(r.getText());
		fgC = fgColor.getText();
		
		if((fgC.length() == 7 && (!fgC.startsWith("#") || !isHex(fgC))) ||
			(fgC.length() == 6 && !isHex(fgC))) {
			throw new JVDrawException("Index out of bounds");
		}
		
		if(xField < 0 || yField < 0 || radius < 0 ||
				xField + radius >= width|| yField + radius >= height||
			xField - radius <= 0 || yField - radius <= 0) {
			throw new JVDrawException("Index out of bounds");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void acceptEditing() {
		if(!fgC.startsWith("#")) {
			fgC = "#" + fgC;
		}
		circle.setAll(new JVPoint(xField, yField), radius, Color.decode(fgC));
		Util.setSaveStatus();
	}

}
