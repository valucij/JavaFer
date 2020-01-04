package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class extends class {@link AbstractLocalizationProvider}, but 
 * instances of this class actually know how to give translation
 * of words when asked. Also, constructor of this class is private,
 * which means that only one instance of this class can exist, and
 * user can get to it bby calling method {@link #getInstance()}
 * @author Lucija Valentić
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider{

	/**
	 * String that represents current language
	 */
	private String language;
	/**
	 * Internal reference to bundle
	 */
	private ResourceBundle bundle;
	/**
	 * Instance of this class, there can only be one
	 * instance of this class
	 */
	private static LocalizationProvider instance;
	/**
	 * Internal reference local
	 */
	private Locale local;
	
	/**
	 * Constructor
	 */
	private LocalizationProvider() {
		setLanguage("en");	
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getString(String key) {
		
		return bundle.getString(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCurrentLanguage() {
		return language;
	}
	
	/**
	 * Returns instance of this class
	 * @return LocalizationProvider
	 */
	public static LocalizationProvider getInstance() {
		
		if(instance == null) {
			instance = new LocalizationProvider();
		}
		return instance;
	}
	/**
	 * Method sets current language to given one, and
	 * notifies all listeners that change has occurred
	 * @param string
	 */
	public void setLanguage(String string) {
		language = string;
		local = Locale.forLanguageTag(string);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", local);
		fire();
		
	}

}
