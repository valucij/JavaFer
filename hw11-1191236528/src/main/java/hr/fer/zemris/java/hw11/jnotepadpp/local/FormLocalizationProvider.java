package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * Class extends {@link LocalizationProvider}. This class is made so
 * when window is open, it can connect itself to object that knows
 * how to translate text. If this class wasn't made, redundant references
 * to other objects would be made and garbage collector wouldn't be able
 * to remove essentially unused objects.
 * @author Lucija Valentić
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge{

	/**
	 * Constructor
	 * @param provider
	 * @param frame
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		
		frame.addWindowListener(new WindowListener() {
			
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void windowIconified(WindowEvent e) {
				
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void windowDeiconified(WindowEvent e) {
				
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void windowDeactivated(WindowEvent e) {
				
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void windowActivated(WindowEvent e) {
				
			}
		});
	}

}
