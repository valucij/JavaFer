package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw17.jvdraw.objects.editor.CircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.objects.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.objects.visitor.GeometricalObjectVisitor;

/**
 * Concrete geometric object, represents circle
 * @author Lucija Valentić
 *
 */
public class Circle extends GeometricalObject {

	/**
	 * Center of a circle
	 */
	private JVPoint center;
	/**
	 * Radius of a circle
	 */
	private double radius;
	/**
	 * Color of a circle
	 */
	Color color;
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
		return new CircleEditor(this);
	}
	
	/**
	 * Returns this.color
	 * @return Color
	 */
	public Color getColor() {
		return this.color;
	}
	
	/**
	 * Returns this.center
	 * @return JVPoint
	 */
	public JVPoint getCenter() {
		return this.center;
	}
	
	/**
	 * Return this.radius
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
	 * Sets this.color
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Sets this.center
	 * @param center
	 */
	public void setCenter(JVPoint center) {
		this.center = center;
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
	 * Sets this.radius
	 * @param radius
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	/**
	 * Sets all at once
	 * @param center
	 * @param radius
	 * @param color
	 */
	public void setAll(JVPoint center, double radius, Color color) {
		setCenter(center);
		setRadius(radius);
		setColor(color);
		
		for(GeometricalObjectListener l : listeners) {
			l.geometricalObjectChanged(this);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Circle (" + (int)center.getX() + ", " + (int)center.getY() + "), " + (int)radius;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String saveToString() {
		return "CIRCLE " + (int)center.getX() + " " + (int)center.getY() + " " + (int)radius + " " + color.getRed() + " " + color.getGreen() + " " + color.getBlue();
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
