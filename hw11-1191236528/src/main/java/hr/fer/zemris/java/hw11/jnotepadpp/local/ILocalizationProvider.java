package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Interface that can be implemented into some classes, so
 * that new objects of that new class know how
 * to translate text based on current language
 * @author Lucija Valentić
 *
 */
public interface ILocalizationProvider {

	/**
	 * Returns translation of given <code>key</code>
	 * in current language
	 * @param key string
	 * @return string
	 */
	public String getString(String key);
	/**
	 * Method adds {@link ILocalizationListener}
	 * @param l ILocalizationListener
	 */
	public void addLocalizationListener(ILocalizationListener l);
	/**
	 * Method removes {@link ILocalizationListener}
	 * @param l ILocalizationListener
	 */
	public void removeLocalizationListener(ILocalizationListener l);
	/**
	 * Return current language
	 * @return string
	 */
	public String getCurrentLanguage();
	/**
	 * Notifies all the listeners that language has been 
	 * changed
	 */
	public void fire();
}
