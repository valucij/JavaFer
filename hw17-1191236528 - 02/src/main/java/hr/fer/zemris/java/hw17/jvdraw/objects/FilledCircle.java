package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw17.jvdraw.objects.editor.FilledCircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.objects.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.objects.visitor.GeometricalObjectVisitor;


/**
 * Class extends {@link GeometricalObject}. It is concrete class
 * that represents filled circle
 * @author Lucija Valentić
 *
 */
public class FilledCircle extends GeometricalObject {

	/**
	 * Center
	 */
	private JVPoint center;
	/**
	 * Radius
	 */
	private double radius;
	/**
	 * Foreground color
	 */
	private Color fgColor;
	/**
	 * Bakcground color
	 */
	private Color bgColor;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}
	
	/**
	 * Returns this.center
	 * @return JVPoint
	 */
	public JVPoint getCenter() {
		return this.center;
	}
	
	/**
	 * Returns this.radius
	 * @return double
	 */
	public double getRadius() {
		return this.radius;
	}
	
	/**
	 * Returns first component of center
	 * @return double
	 */
	public double getX() {
		return center.getX();
	}
	
	/**
	 * Returns second component of center
	 * @return double
	 */
	public double getY() {
		return center.getY();
	}
	
	/**
	 * Sets this.center
	 * @param x
	 * @param y
	 */
	public void setCenter(double x, double y) {
		setCenter(new JVPoint(x, y));
	}
	
	/**
	 * Sets this.center
	 * @param center
	 */
	public void setCenter(JVPoint center) {
		this.center = center;
	}
	
	/**
	 * Returns this.fgColor
	 * @return Color
	 */
	public Color getFgColor() {
		return fgColor;
	}

	/**
	 * Sets this.fgColor
	 * @param fgColor
	 */
	public void setFgColor(Color fgColor) {
		this.fgColor = fgColor;
	}

	/**
	 * Returns this.bgColor
	 * @return Color
	 */
	public Color getBgColor() {
		return bgColor;
	}

	/**
	 * Sets this.bgColor
	 * @param bgColor
	 */
	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	/**
	 * Sets this.radius
	 * @param radius
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	/**
	 * Sets all
	 * @param center
	 * @param radius
	 * @param fgColor
	 * @param bgColor
	 */
	public void setAll(JVPoint center, double radius, Color fgColor, Color bgColor) {
		
		setCenter(center);
		setRadius(radius);
		setFgColor(fgColor);
		setBgColor(bgColor);
		
		for(GeometricalObjectListener l : listeners) {
			l.geometricalObjectChanged(this);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Filled circle (" + (int)center.getX() + ", " + (int)center.getY() + "), " + (int)radius + ", " 
	+ String.format("#%02x%02x%02x", bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue()).toUpperCase();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String saveToString() {
		return "FCIRCLE " + (int)center.getX() + " " + (int)center.getY() + " " + 
				(int)radius + " " + fgColor.getRed()+
				" " + fgColor.getGreen() + " " + fgColor.getBlue() + " "
				+ bgColor.getRed() + " " + bgColor.getGreen() + " " + bgColor.getBlue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void moveUp() {
		
		if(center.getY() - radius > 0) {
			center = new JVPoint(center.getX(), center.getY() - 1);
			
			for(GeometricalObjectListener l : listeners) {
				l.geometricalObjectChanged(this);
			}
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void moveDown(double w) {
		
		if(center.getY() + radius < w) {
			
			center = new JVPoint(center.getX(), center.getY() + 1);
			
			for(GeometricalObjectListener l : listeners) {
				l.geometricalObjectChanged(this);
			}
		}
		
	}
}
