package hr.fer.zemris.java.hw17.jvdraw.color;

import java.awt.Color;

/**
 * interface, represents source that has current selected color for
 * background or foreground, and also notifies its 
 * listeners when color changes
 * @author Lucija Valentić
 *
 */
public interface IColorProvider {
	/**
	 * Returns current selected color
	 * @return Color
	 */
	public Color getCurrentColor();
	/**
	 * Adds {@link ColorChangeListener}
	 * @param l
	 */
	public void addColorChangeListener(ColorChangeListener l);
	/**
	 * Removes {@link ColorChangeListener}
	 * @param l
	 */
	public void removeColorChanegListener(ColorChangeListener l);
}
