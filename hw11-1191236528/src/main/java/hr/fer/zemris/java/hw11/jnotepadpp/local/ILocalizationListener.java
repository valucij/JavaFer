package hr.fer.zemris.java.hw11.jnotepadpp.local;
 
/**
 * Interface that represents object that can "listen"
 * for changes in language. When language is changed, appropriate
 * action is performed
 * @author Lucija Valentić
 *
 */
public interface ILocalizationListener {

	public void localizationChanged();
	
}
