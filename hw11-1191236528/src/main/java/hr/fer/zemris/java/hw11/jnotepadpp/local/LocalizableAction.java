package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Class extends class {@link AbstractAction}, and
 * it behaves in the same way, but this one
 * can be dynamically change, it can change its lanugage
 * @author Lucija Valentić
 *
 */
@SuppressWarnings("unused")
public abstract class LocalizableAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Internal provider of translation
	 */
	private ILocalizationProvider lp;
	/**
	 * String that represents key for translation
	 */
	private String key;
	/**
	 * Constructor
	 * @param key String 
	 * @param lp ILocalizationProvider
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
	
		this.key = key;
		this.lp = lp;
		
		if(lp == null) {
			return;
		}
		
		String translation = lp.getString(key);
		putValue(NAME, translation);
		
		this.lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				String translation = lp.getString(key);
				putValue(NAME, translation);
			}
		});
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract void actionPerformed(ActionEvent e);

}
