package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawingModel.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawingModel.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.objects.JVPoint;
/**
 * Abstract class that implements class {@link Tool} so
 * when concrete classes are written, some code
 * won't be redundant
 * @author Lucija Valentić
 *
 */
public abstract class AbstractTool implements Tool {
	
	/**
	 * Model that keeps all drawing objects together
	 */
	protected DrawingModel model;
	/**
	 * Source of color
	 */
	protected IColorProvider source;
	/**
	 * Canvas where something can be drawn
	 */
	protected static JDrawingCanvas canvas;
	/**
	 * First point, where mouse clicked 
	 */
	protected JVPoint firstPoint = new JVPoint(0, 0);
	/**
	 * Point where mouse is now
	 */
	protected JVPoint currentPoint = new JVPoint(0, 0);
	/**
	 * Flag that shows how many time mouse has been clicked
	 */
	protected static boolean firstClick = true;

	/**
	 * Constructor
	 * @param model
	 * @param source
	 * @param canvas
	 */
	public AbstractTool(DrawingModel model, IColorProvider source) {
		this.model = model;
		this.source = source;
		//this.canvas = canvas;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract void mouseClicked(MouseEvent e);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		
		if(!e.getComponent().equals(canvas)) {
			return;
		}
		
		currentPoint = new JVPoint(e.getX(), e.getY());
		
		if(!firstClick) {
			Graphics2D g2d = (Graphics2D)canvas.getGraphics();
			paint(g2d);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract void paint(Graphics2D g2d);
	/**
	 * Sets canvas
	 * @param c
	 */
	public static void setCanvas(JDrawingCanvas c) {
		canvas = c;
	}
	
	public static void setFirstClick() {
		firstClick = true;
	}
	
}
