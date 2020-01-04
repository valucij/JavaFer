package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JLabel;

/**
 * Class extends {@link JLabel}, and behaves in the same way, 
 * but instances of this class can dynamically change its text, 
 * they can change language
 * @author Lucija Valentić
 *
 */
public class LocalizableJLabel extends JLabel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Internal translation provider
	 */
	private ILocalizationProvider lp;
	/**
	 * Represents key for translation
	 */
	private String key;
	
	/**
	 * Constructor
	 * @param key
	 * @param lp
	 */
	public LocalizableJLabel(String key, ILocalizationProvider lp) {
		
		this.key = key;
		this.lp = lp;
		
		updateLabel();
		
		this.lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				updateLabel();
			}
		});
		
		
	}
	/**
	 * Updates text on label
	 */
	private void updateLabel() {
		String translation = lp.getString(key);
		setText(translation);

	}
}
