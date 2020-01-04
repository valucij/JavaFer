package hr.fer.zemris.java.hw17.jvdraw.color;

import java.awt.Color;

/**
 * Interface, listener that is notified when current color is 
 * changed. It doesn't matter whether is background color
 * or foreground color
 * @author Lucija Valentić
 *
 */
public interface ColorChangeListener {
	/**
	 * Action performed when new color si selected
	 * @param source
	 * @param oldColor
	 * @param newColor
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
