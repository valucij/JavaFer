package hr.fer.zemris.java.hw17.jvdraw.objects.visitor;

import java.awt.Graphics2D;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

/**
 * Class implements {@link GeometricalObjectVisitor}. It is used
 * for painting geometric objects
 * @author Lucija Valentić
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	/**
	 * Internal graphics object
	 */
	private Graphics2D g2d;
	
	/**
	 * Constructor
	 * @param g2d
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(Line line) {
		
		g2d.setColor(line.getColor());
		g2d.drawLine((int)line.getX1(), (int)line.getY1(), (int)line.getX2(), (int)line.getY2());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(Circle circle) {
		g2d.setColor(circle.getColor());
		g2d.drawOval((int)circle.getX() - (int)circle.getRadius(),(int) circle.getY() - (int)circle.getRadius(), (int)circle.getRadius() * 2, (int)circle.getRadius() * 2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void visit(FilledCircle filledCircle) {
		g2d.setColor(filledCircle.getFgColor());
		g2d.drawOval((int)filledCircle.getX() - (int)filledCircle.getRadius(),(int) filledCircle.getY() - (int)filledCircle.getRadius(), (int)filledCircle.getRadius() * 2, (int)filledCircle.getRadius() * 2);
		g2d.setColor(filledCircle.getBgColor());
		g2d.fillOval((int)filledCircle.getX() - (int)filledCircle.getRadius(),(int) filledCircle.getY() - (int)filledCircle.getRadius(), (int)filledCircle.getRadius() * 2 - 1, (int)filledCircle.getRadius() * 2 - 1);
	}

}
