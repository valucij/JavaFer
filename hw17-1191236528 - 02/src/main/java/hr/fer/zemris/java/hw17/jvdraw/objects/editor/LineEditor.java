package hr.fer.zemris.java.hw17.jvdraw.objects.editor;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import hr.fer.zemris.java.hw17.jvdraw.JVDrawException;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;
import hr.fer.zemris.java.hw17.jvdraw.util.Util;

/**
 * Class extends {@link GeometricalObjectEditor}. It is 
 * used when user choose to edit some line
 * @author Lucija Valentić
 *
 */
public class LineEditor extends GeometricalObjectEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Geometrical line
	 */
	private Line line;
	/**
	 * First component first point 
	 */
	private double x1Field;
	/**
	 * First component second point
	 */
	private double x2Field;
	/**
	 * Second component first point
	 */
	private double y1Field;
	/**
	 * Second component second point
	 */
	private double y2Field;
	/**
	 * Text field for x1
	 */
	private JTextField x1;
	/**
	 * Text field for y1
	 */
	private JTextField y1;
	/**
	 * Text field for x2
	 */
	private JTextField x2;
	/**
	 * Text field for y2
	 */
	private JTextField y2;
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
	 * @param line
	 */
	public LineEditor(Line line) {
		this.line = line;
		
		this.setLayout(new GridLayout(2, 5));
		
		this.x1 = new JTextField();
		x1.setText(String.valueOf((int)line.getX1()));
		this.y1 = new JTextField();
		y1.setText(String.valueOf((int)line.getY1()));
		this.x2 = new JTextField();
		x2.setText(String.valueOf((int)line.getX2()));
		this.y2 = new JTextField();
		y2.setText(String.valueOf((int)line.getY2()));
		this.fgColor = new JTextField();
		Color color = line.getColor();
		String s = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
		fgColor.setText(s);
		
		JLabel lx1 = new JLabel("Line x1");
		JLabel ly1 = new JLabel("Line y1");
		JLabel lx2 = new JLabel("Line x2");
		JLabel ly2 = new JLabel("Line y2");
		JLabel lfg = new JLabel("Line color");
		
		this.add(lx1);
		this.add(ly1);
		this.add(lx2);
		this.add(ly2);
		this.add(lfg);
		
		this.add(x1);
		this.add(y1);
		this.add(x2);
		this.add(y2);
		this.add(fgColor);
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkEditing() {
		
		x1Field = Double.parseDouble(x1.getText());
		y1Field = Double.parseDouble(y1.getText());
		x2Field = Double.parseDouble(x2.getText());
		y2Field = Double.parseDouble(y2.getText());
		
		fgC = fgColor.getText();
		
		if((fgC.length() == 7 && (!fgC.startsWith("#") || !isHex(fgC))) ||
			(fgC.length() == 6 && !isHex(fgC))) {
			throw new JVDrawException("Index out of bounds");
		}
		
		if(x1Field < 0 || y1Field < 0 || x2Field < 0 || y2Field < 0 ||
				x1Field >= width || y1Field >= height|| x2Field >= width|| y2Field >= height) {
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
		line.setAll(x1Field, y1Field, x2Field, y2Field, Color.decode(fgC));
		Util.setSaveStatus();
	}
	
}
