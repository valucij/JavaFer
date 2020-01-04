package hr.fer.zemris.java.hw17.jvdraw.objects.visitor;

import java.awt.Rectangle;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

/**
 * Concrete class that implements {@link GeometricalObjectVisitor}
 * @author Lucija Valentić
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	/**
	 * Minimal x coordinate
	 */
	private int xMin = Integer.MAX_VALUE;
	/**
	 * Minimal y coordinate
	 */
	private int yMin = Integer.MAX_VALUE;
	/**
	 * Maximal x coordinate
	 */
	private int xMax;
	/**
	 * Maximal y coordinate
	 */
	private int yMax;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(Line line) {
		
		int x1 = (int)line.getX1();
		int y1 = (int)line.getY1();
		int x2 = (int)line.getX2();
		int y2 = (int)line.getY2();
		
		int x = x1;
		int y = y1;
		
		int maxX = x2;
		int maxY = y2;
		
		if(x1 > x2) {
			x = x2;
			maxX = x1;
		}
		if(y1 > y2) {
			y = y2;
			maxY = y1;
		}
		
		if(xMax < maxX) {
			xMax = maxX;
		}
		
		if(yMax < maxY) {
			yMax = maxY;
		}
		
		if(xMin > x) {
			xMin = x;
		}
		
		if(yMin > y) {
			yMin = y;
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(Circle circle) {
		
		int x = (int)circle.getX();
		int y = (int)circle.getY();
		int radius = (int)circle.getRadius();

		if(xMax < x + radius) {
			xMax = x + radius;
		}
		
		if(yMax < y + radius) {
			yMax = y + radius;
		}
		
		if(xMin > x - radius) {
			xMin = x - radius;
		}
		if(yMin > y - radius) {
			yMin = y - radius;
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(FilledCircle filledCircle) {
		
		int x = (int)filledCircle.getX();
		int y = (int)filledCircle.getY();
		int radius = (int)filledCircle.getRadius();

		if(xMax < x + radius) {
			xMax = x + radius;
		}
		
		if(yMax < y + radius) {
			yMax = y + radius;
		}
		
		if(xMin > x - radius) {
			xMin = x - radius;
		}
		if(yMin > y - radius) {
			yMin = y - radius;
		}
	}
	
	/**
	 * Returns bounding box
	 * @return Rectangle
	 */
	public Rectangle getBoundingBox() {
		Rectangle rec = new Rectangle();
		rec.setBounds(xMin, yMin, xMax - xMin, yMax - yMin);
		return rec;
	}

}
