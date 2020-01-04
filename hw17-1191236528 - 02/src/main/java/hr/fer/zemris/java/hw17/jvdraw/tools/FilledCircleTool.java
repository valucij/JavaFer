package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawingModel.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.JVPoint;

/**
 * Concrete class that implements class {@link Tool}. 
 * It represents tool that can draw filled circles
 * @author Lucija Valentić
 *
 */
public class FilledCircleTool extends AbstractTool implements Tool {

	/**
	 * Provider for background color
	 */
	private IColorProvider bgSources;
	
	/**
	 * Constructor
	 * @param model
	 * @param fgSources
	 * @param bgSources
	 * @param canvas
	 */
	public FilledCircleTool(DrawingModel model, IColorProvider fgSources, IColorProvider bgSources) {
		super(model, fgSources);
		this.bgSources = bgSources;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(firstClick) {
			firstClick = false;
			firstPoint = currentPoint;
		}else {
			FilledCircle filledCircle = new FilledCircle();
			filledCircle.setAll(new JVPoint(firstPoint.getX(), firstPoint.getY()), firstPoint.distance(currentPoint), source.getCurrentColor(), bgSources.getCurrentColor());
			firstClick = true;
			model.add(filledCircle);
			paint((Graphics2D)canvas.getGraphics());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics2D g2d) {
		canvas.paintComponents(g2d);
		g2d.setColor(source.getCurrentColor());
		g2d.drawOval((int)firstPoint.getX() - (int)firstPoint.distance(currentPoint),(int) firstPoint.getY() - (int)firstPoint.distance(currentPoint), (int)firstPoint.distance(currentPoint) * 2, (int)firstPoint.distance(currentPoint) * 2);
		g2d.setColor(bgSources.getCurrentColor());
		g2d.fillOval((int)firstPoint.getX() - (int)firstPoint.distance(currentPoint),(int) firstPoint.getY() - (int)firstPoint.distance(currentPoint), (int)firstPoint.distance(currentPoint) * 2 - 1, (int)firstPoint.distance(currentPoint) * 2 - 1);
	}

}
