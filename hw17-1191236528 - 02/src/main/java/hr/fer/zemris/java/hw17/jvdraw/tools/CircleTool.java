package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawingModel.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.JVPoint;

/**
 * Concrete implementation of class {@link Tool}. This class represents tool
 * that can draw circles
 * 
 * @author Lucija Valentić
 *
 */
public class CircleTool extends AbstractTool implements Tool {

	/**
	 * Constructor
	 * 
	 * @param model
	 * @param source
	 * @param canvas
	 */
	public CircleTool(DrawingModel model, IColorProvider source) {
		super(model, source);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

		if (firstClick) {
			firstClick = false;
			firstPoint = currentPoint;
		} else {
			Circle circle = new Circle();
			circle.setAll(new JVPoint(firstPoint.getX(), firstPoint.getY()), firstPoint.distance(currentPoint),
					source.getCurrentColor());
			firstClick = true;
			model.add(circle);
			paint((Graphics2D) canvas.getGraphics());
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics2D g2d) {
		canvas.paintComponents(g2d);
		g2d.setColor(source.getCurrentColor());
		g2d.drawOval((int) firstPoint.getX() - (int) firstPoint.distance(currentPoint),
				(int) firstPoint.getY() - (int) firstPoint.distance(currentPoint),
				(int) firstPoint.distance(currentPoint) * 2, (int) firstPoint.distance(currentPoint) * 2);

	}

}
