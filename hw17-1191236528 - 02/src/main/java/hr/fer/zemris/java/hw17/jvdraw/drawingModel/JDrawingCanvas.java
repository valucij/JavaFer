package hr.fer.zemris.java.hw17.jvdraw.drawingModel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.function.Supplier;
import javax.swing.JComponent;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.visitor.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;

/**
 * This class extends {@link JComponent} and implements
 * {@link DrawingModelListener}. It represents component
 * where we can draw things
 * @author Lucija Valentić
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Drawing model
	 */
	private DrawingModel source;
	/**
	 * Last index
	 */
	@SuppressWarnings("unused")
	private int index0;
	/**
	 * First index
	 */
	@SuppressWarnings("unused")
	private int index1;
	/**
	 * Current tool for drawing
	 */
	private Tool currentTool;
	/**
	 * Flag that show whether object was added or not
	 */
	private boolean addedFlag = false;

	/**
	 * Constructor
	 * @param supplier
	 */
	public JDrawingCanvas(DrawingModel model, Supplier<Tool> supplier) {
		currentTool = supplier.get();
		this.source = model;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
	
		this.source = source;
		this.index0 = index0;
		this.index1 = index1;
		addedFlag = true;
		repaint();
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		this.source = source;
		this.index0 = index0;
		this.index1 = index1;
		repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		this.source = source;
		this.index0 = index0;
		this.index1 = index1;
		repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		if(addedFlag) {
			currentTool.paint(g2d);
			addedFlag = false;
		}else {
			paintComponents(g);
		}
		
	}
	
	@Override
	public void paintComponents(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		for(int i = 0; i < source.getSize(); i++) {
			
			GeometricalObject object = source.getObject(i);
			if(object == null) {
				return;
			}
			object.accept(new GeometricalObjectPainter(g2d));
		}
	}
	
	public void setTool(Tool tool) {
		currentTool = tool;
	}

}
