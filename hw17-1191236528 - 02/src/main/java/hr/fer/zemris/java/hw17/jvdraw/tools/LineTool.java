package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawingModel.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.objects.JVPoint;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

/**
 * Class representing tool that can draw line
 * @author Lucija Valentić
 *
 */
public class LineTool extends AbstractTool implements Tool{

	/**
	 * Constructor
	 * @param model 
	 * @param source
	 * @param canvas
	 */
	public LineTool(DrawingModel model, IColorProvider source) {
		super(model, source);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		
		currentPoint = new JVPoint(e.getX(), e.getY());
		
		if(firstClick) {
			firstClick = false;
			firstPoint = currentPoint;
		}else {
			Line line = new Line();
			line.setAll(firstPoint.getX(), firstPoint.getY(), currentPoint.getX(), currentPoint.getY(), source.getCurrentColor());
			firstClick = true;
			model.add(line);
			paint((Graphics2D)canvas.getGraphics());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics2D g2d) {
		//canvas.paint(g2d);
		canvas.paintComponents(g2d);
		g2d.setColor(source.getCurrentColor());
		g2d.drawLine((int)firstPoint.getX(), (int)firstPoint.getY(), (int)currentPoint.getX(), (int)currentPoint.getY());
	}

}
