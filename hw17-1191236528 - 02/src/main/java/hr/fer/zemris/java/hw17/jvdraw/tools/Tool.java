package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
/**
 * Class that works like a pen. This class, depending
 * on implementation can draw line, circle or filled circle.
 * Depending on what user do with its mouse, different actions will happen
 * @author Lucija Valentić
 *
 */
public interface Tool {
	/**
	 * Action performed when mouse is pressed
	 * @param e
	 */
	public void mousePressed(MouseEvent e);
	/**
	 * Action performed when mouse is released
	 * @param e
	 */
	public void mouseReleased(MouseEvent e);
	/**
	 * Action performed when mouse is clicked
	 * @param e
	 */
	public void mouseClicked(MouseEvent e);
	/**
	 * Action performed when mouse is moved
	 * @param e
	 */
	public void mouseMoved(MouseEvent e);
	/**
	 * Action performed when mouse is dragged
	 * @param e
	 */
	public void mouseDragged(MouseEvent e);
	/**
	 * Method that paints certain object with given 
	 * graphics object
	 * @param g2d
	 */
	public void paint(Graphics2D g2d);
}
