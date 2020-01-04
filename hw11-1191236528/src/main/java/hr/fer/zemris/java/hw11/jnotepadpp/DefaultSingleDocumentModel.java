package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * Class implements {@link SingleDocumentModel}. 
 * 
 * @author Lucija Valentić
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel{

	/**
	 * Represents internal swing component
	 * used for text editing
	 */
	private JTextArea editor;
	/**
	 * Represents file path where document has been saved, 
	 * can be <code>null</code> for new documents
	 */
	private Path filePath;
	/**
	 * Represents flag that tells user whether this
	 * document has been modified or not
	 */
	private boolean modified;
	/**
	 * Internal list of listeners, that can be notified
	 * when change has happened, and they can perfom some action
	 */
	private List<SingleDocumentListener> listeners;
	/**
	 * Constructor
	 * @param filePath Path
	 * @param text String
	 */
	public DefaultSingleDocumentModel(Path filePath, String text) {
		
		this.modified = false;
		this.filePath = filePath;
		this.editor = new JTextArea(text);
		
		if(filePath != null) {
			this.editor.setName(filePath.getFileName().toString());	
		}else {
			this.editor.setName("unamed");
		}
		
		this.listeners = new ArrayList<SingleDocumentListener>();
		
		this.addSingleDocumentListener(new SingleDocumentListener() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void documentModifyStatusUpdate(SingleDocumentModel model) {
				// ako je tekstualni dio modificiran
				//pita se je li modified true ili false
				if(model.isModified()) {
					String name = editor.getName();
					DefaultSingleDocumentModel.this.editor.setName("*" + name);
				}else {
					String name = editor.getName();
					
					int i = name.indexOf('*');
					
					if(i == 0) {
						name = name.substring(1);
					}
					
					editor.setName(name);
				}
			
				
			}
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void documentFilePathUpdate(SingleDocumentModel model) {
				
				try {
					
					Files.writeString(model.getFilePath(), editor.getText());
				} catch (IOException e1) {
					
					JOptionPane.showMessageDialog(editor, "Dogodila se greška pri spremanju",
							"Pogreška", JOptionPane.ERROR_MESSAGE);
					
					return;
				}
				
				//JOptionPane.showMessageDialog(editor, "Spremljeno je", "Obavijest", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JTextArea getTextComponent() {
		return this.editor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Path getFilePath() {
		return this.filePath;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFilePath(Path path) {
		
		if(filePath == null || !this.filePath.equals(path)) {
			
			this.filePath = path;
			
			for(SingleDocumentListener listener : listeners) {
				listener.documentFilePathUpdate(this);
			}
		
		}else {
			this.filePath = path;	
		}
		
		DefaultSingleDocumentModel.this.setModified(false);//jer ako je spremljeno, onda "nema modifikacija više"
		
		return;
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isModified() {
		return modified;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setModified(boolean modified) {
		
		if(this.modified != modified) {
			this.modified = modified;
			
			for(SingleDocumentListener listener : listeners) {
				listener.documentModifyStatusUpdate(this);
			}
			return;
		}
		
		this.modified = modified;
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(filePath);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DefaultSingleDocumentModel))
			return false;
		DefaultSingleDocumentModel other = (DefaultSingleDocumentModel) obj;
		return Objects.equals(filePath, other.filePath);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return editor.getName();
	}
	
}
