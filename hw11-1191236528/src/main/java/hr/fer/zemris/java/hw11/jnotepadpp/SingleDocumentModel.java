package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;
/**
 * Interface represents one single document, holding information
 * about file path where documents has been saved (can be <code>null</code>),
 * swing component which is used for editing and document modification status.
 * This interface can be implemented and then used with {@link JNotepadPP}
 * and {@link DefaultMultipleDoumentModel};
 * @author Lucija Valentić
 *
 */
public interface SingleDocumentModel {

	/**
	 * Returns internal swing component that is used for editing
	 * @return JTextArea
	 */
	public JTextArea getTextComponent();
	/**
	 * Returns file path where document has been save, 
	 * can be <code>null</code>
	 * @return Path
	 */
	public Path getFilePath();
	/**
	 * Changes file path, and notifies all the listeners
	 * @param path Path, new path
	 */
	public void setFilePath(Path path);
	/**
	 * Returns status of modification of document,
	 * whether document has been edited or not
	 * @return boolean
	 */
	public boolean isModified();
	/**
	 * Changes modification status of a document,
	 * notifies all the listeners about the change
	 * @param modified boolean, new modification status
	 */
	public void setModified(boolean modified);
	/**
	 * Add {@link SingleDocumentListener}, objects that perform
	 * some action based on how they are implemented. The are added into
	 * internal arrays of objects that are instances of this class when
	 * user want to track all the changes on document, and
	 * modifications
	 * @param l SingleDocumentListener
	 */
	public void addSingleDocumentListener(SingleDocumentListener l);
	/**
	 * Removes given {@link SingleDocumentListener} from internal arrays
	 * of listeners. That listener can track anymore all the 
	 * changes in the document
	 * @param l SingleDocumentListener
	 */
	public void removeSingleDocumentListener(SingleDocumentListener l);
}
