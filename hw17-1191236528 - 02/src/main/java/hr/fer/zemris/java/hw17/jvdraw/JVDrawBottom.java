package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw17.jvdraw.color.ColorChangeListener;
import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;

/**
 * Class extends {@link JLabel} and implements {@link ColorChangeListener}.
 * This component/object always shows which colors were chosen
 * for background or for foreground 
 * @author Lucija Valentić
 *
 */
public class JVDrawBottom extends JLabel implements ColorChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Provider for foreground color
	 */
	private IColorProvider fgArea;
	/**
	 * Provider for background color
	 */
	private IColorProvider bgArea;
	/**
	 * Text that will be displayed in this components
	 */
	private String bottomText;
	
	/**
	 * Constructor
	 * @param fgArea
	 * @param bgArea
	 */
	public JVDrawBottom(IColorProvider fgArea, IColorProvider bgArea) {
		
		fgArea.addColorChangeListener(this);
		bgArea.addColorChangeListener(this);
		
		this.fgArea = fgArea;
		this.bgArea = bgArea;	
		
		setBottomText(this.fgArea.getCurrentColor(), this.bgArea.getCurrentColor());
	}

	/**
	 * Sets what will be written inside of this label
	 * @param fgColor
	 * @param bgColor
	 */
	private void setBottomText(Color fgColor, Color bgColor) {
		
		bottomText = "Foreground color: (" + fgColor.getRed() +", " + fgColor.getGreen() + ", " + fgColor.getBlue() +"), "
				+ " background color: (" + bgColor.getRed() + ", " + bgColor.getGreen() +", " + bgColor.getBlue() +").";	
	
		this.setText(bottomText);
		
		this.setVisible(true);
		this.setOpaque(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		
		if(source.equals(fgArea)) {
			fgArea = source;
			setBottomText(newColor, bgArea.getCurrentColor());
		}else {
			bgArea = source;
			setBottomText(fgArea.getCurrentColor(), newColor);
		}
		
	}

}
