package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Class extends class {@link AbstractLocalizationProvider}. This
 * class is like a bridge for actual translator and frames. That way, no
 * redundant informations are made, and garbage collector can do its job
 * @author Lucija Valentić
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider{

	/**
	 * Internal {@link ILocalizationProvider}
	 */
	private ILocalizationProvider provider;
	/**
	 * Flag that indicates whether object is connected or not
	 */
	private boolean connected;
	/**
	 * Reference to current lanugage
	 */
	private String currentLanguage;
	
	/**
	 * Constructor
	 * @param provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
	}
	
	/**
	 * Returns translation of given string
	 */
	public String getString(String key) {
		return provider.getString(key);
	}
	/**
	 * Method that disconnects some
	 * {@link ILocalizationListener} from internal
	 * reference to translation provider
	 * 
	 */
	public void disconnect() {
		
		provider.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				fire();
			}
		});
		
		connected = false;
	}
	
	/**
	 * Method that connects some
	 * {@link ILocalizationListener} to internal
	 * reference to translation provider
	 * 
	 */
	public void connect() {
		
		if(connected) {
			return;
		}
		
		if(currentLanguage != provider.getCurrentLanguage()) {
			fire();
			currentLanguage = provider.getCurrentLanguage();
		}
		
		provider.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {	
				fire();
			}
		});
		
		connected = true;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCurrentLanguage() {
		return provider.getCurrentLanguage();
	}
	

}
