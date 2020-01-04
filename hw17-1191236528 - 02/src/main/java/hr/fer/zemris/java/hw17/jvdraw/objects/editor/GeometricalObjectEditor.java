package hr.fer.zemris.java.hw17.jvdraw.objects.editor;

import javax.swing.JPanel;
/**
 * Abstract class, extends {@link JPanel}. It is used
 * when user wants to edit some geometrical object
 * @author Lucija Valentić
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Height of canvas
	 */
	protected double height;
	/**
	 * Width of canvas
	 */
	protected double width;
	
	/**
	 * Method checks if entered values are valid
	 */
	public abstract void checkEditing();
	/**
	 * Method does action after entered values are valid
	 */
	public abstract void acceptEditing();
	/**
	 * Sets this.height
	 * @param height
	 */
	public void setHeight(double height) {
		this.height = height;
	}
	/**
	 * Sets this.width
	 * @param width
	 */
	public void setWidth(double width) {
		this.width = width;
	}
	/**
	 * Checks if sent string is hex-number
	 * @param string
	 * @return
	 */
	protected boolean isHex(String string) {
		
		if(string.isBlank()) {
			return false;
		}
		
		if(string.startsWith("#")) {
			string = string.substring(1);
		}
		
		char[] chars = string.toUpperCase().toCharArray();
		
		for(int i = 0; i < chars.length; i++) {
			if(!Character.isDigit(chars[i]) && chars[i] != 'A' &&
					chars[i] != 'B' && chars[i] != 'C' && chars[i] != 'D' && chars[i] != 'E'
					&& chars[i] != 'F'){
				return false;
			}
		}
		
		return true;
		
	}
	
}
