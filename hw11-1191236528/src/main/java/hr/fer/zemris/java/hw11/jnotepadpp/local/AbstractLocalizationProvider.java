package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Class implements {@link ILocalizationProvider}
 * @author Lucija Valentić
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider{

	/**
	 * Internal list of listeners
	 */
	private List<ILocalizationListener> listeners = new ArrayList<ILocalizationListener>();
	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract String getString(String key);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract String getCurrentLanguage();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fire() {
		
		for(ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
		
	}

}
