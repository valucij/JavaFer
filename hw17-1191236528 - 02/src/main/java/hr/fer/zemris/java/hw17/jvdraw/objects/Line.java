package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw17.jvdraw.objects.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.objects.editor.LineEditor;
import hr.fer.zemris.java.hw17.jvdraw.objects.visitor.GeometricalObjectVisitor;

/**
 * Class extends {@link GeometricalObject}. It is concrete class
 * that represents line
 * @author Lucija Valentić
 *
 */
public class Line extends GeometricalObject {

	/**
	 * Second point
	 */
	private double x2;
	/**
	 * First point
	 */
	private double x1;
	/**
	 * First point
	 */
	private double y1;
	/**
	 * Second point
	 */
	private double y2;
	/**
	 * Color of a line
	 */
	private Color color;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);		
	}

	/**
	 * Returns this.x1
	 * @return double
	 */
	public double getX1() {
		return this.x1;
	}

	/**
	 * Returns this.y1 
	 * @return double
	 */
	public double getY1() {
		return this.y1;
	}

	/**
	 * Returns this.x2
	 * @return double
	 */
	public double getX2() {
		return this.x2;
	}

	/**
	 * Returns this.y2
	 * @return double
	 */
	public double getY2() {
		return this.y2;
	}

	/**
	 * Sets this.x2
	 * @param x2
	 */
	public void setX2(double x2) {
		this.x2 = x2;
	}

	/**
	 * Sets this.x1
	 * @param x1
	 */
	public void setX1(double x1) {
		this.x1 = x1;
	}

	/**
	 * Sets this.y2
	 * @param y1
	 */
	public void setY1(double y1) {
		this.y1 = y1;
	}

	/**
	 * Sets this.y2
	 * @param y2
	 */
	public void setY2(double y2) {
		this.y2 = y2;
	}
	
	/**
	 * Returns this.color
	 * @return Color
	 */
	public Color getColor() {
		return this.color;
	}
	
	/**
	 * Sets this.color
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Sets all
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param color
	 */
	public void setAll(double x1, double y1, double x2, double y2, Color color) {
		setX1(x1);
		setY1(y1);
		setX2(x2);
		setY2(y2);
		setColor(color);
		
		for(GeometricalObjectListener l : listeners) {
			l.geometricalObjectChanged(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Line ("+(int)x1 + ", "+(int)y1 + ")-("+(int)x2+", "+(int)y2+")" ;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String saveToString() {
		return "LINE " + (int)x1 + " " + (int)y1 + " " + (int)x2 + " " + (int)y2 + " " +color.getRed() + " " + color.getGreen() + " " + color.getBlue(); 
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void moveUp() {
		
		if(y1 > 0 ) {
			y1 -= 1;
			y2 -= 1;
			
			for(GeometricalObjectListener l : listeners) {
				l.geometricalObjectChanged(this);
			}
			
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void moveDown(double h) {
		
		if(y2 < h) {
		
			y1 += 1;
			y2 += 1;
			
			for(GeometricalObjectListener l : listeners) {
				l.geometricalObjectChanged(this);
			}
			
		}
		
		
	}
}
