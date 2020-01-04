package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.ActionEvent;
import javax.swing.Action;

/**
 * Class extends {@link LocalizableAction}, but this
 * class is essentially a dynamic string that can change
 * it text based on current language
 * @author Lucija Valentić
 *
 */
public class LocalizableString extends LocalizableAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param key String
	 * @param lp ILocalizationProvider
	 */
	public LocalizableString(String key, ILocalizationProvider lp) {
		super(key, lp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return Action.NAME;
	}
}
