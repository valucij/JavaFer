package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.color.ColorChangeListener;
import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;

/**
 * Class implements {@link JColorArea} and extends {@link JComponent}.
 * It is used for choosing color for drawing
 * @author Lucija Valentić
 *
 */
public class JColorArea extends JComponent implements IColorProvider{

	/**
	 * Current selected color
	 */
	private Color selectedColor;
	/**
	 * List of listeners
	 */
	private List<ColorChangeListener> listeners = new ArrayList<ColorChangeListener>();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 * @param color
	 */
	public JColorArea(Color color) {
		this.selectedColor = color;
		this.setBackground(color);
		
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Color color = JColorChooser.showDialog(JColorArea.this, "Choose a color", selectedColor);
				setNewColor(color);
				
			}
		});
		//tu prispojiti sve listenere potrebne
	}
	
	/**
	 * Sets newly chosen color. If color is not valid, then 
	 * exception is thrown. After new color has been set, all
	 * listeners are notified
	 * @param color
	 */
	private void setNewColor(Color color) {
		if(color == null) {
			throw new JVDrawException();
		}
		
		Color oldColor = selectedColor;
		selectedColor = color;
		this.setBackground(color);
		
		for(ColorChangeListener l : listeners) {
			l.newColorSelected(this, oldColor, selectedColor);
		}
		
		repaint();
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeColorChanegListener(ColorChangeListener l) {
		listeners.remove(l);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g) {
		g.setColor(selectedColor);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
}
