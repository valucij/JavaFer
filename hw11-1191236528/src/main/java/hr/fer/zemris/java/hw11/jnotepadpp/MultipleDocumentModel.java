package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
/**
 * Interface represent a model capable of holding zero, one, or more documents,
 * it also has concept of current document - the one which is 
 * shown to the user, and on which user works. It is also capable
 * of tracking all the changes that might have happened in documents
 * with {@link MultipleDocumentListener}. So those listeners,
 * when added and appropriate operation implemented, can
 * manage changing of tabs/documents etc.
 * @author Lucija Valentić
 *
 */
public interface MultipleDocumentModel  extends Iterable<SingleDocumentModel>{
	/**
	 * Creates new document, instance of {@link DefaultSingleDocumentModel}, 
	 * and puts that new document in new tab
	 * @return current new document
	 */
	public SingleDocumentModel createNewDocument();
	/**
	 * Returns some {@link SingleDocumentModel} that is current
	 * one and on which one is user working
	 * @return SingleDocumentModel
	 */
	public SingleDocumentModel getCurrentDocument();
	/**
	 * Loads new document (if possible) from given <code>path</code>.
	 * Returns new document as current, and notifies all the
	 * listeners about the change
	 * @param path Path from which user wants to load new document
	 * @return SingleDocumentModel
	 */
	public SingleDocumentModel loadDocument(Path path);
	/**
	 * Saves given <code>model</code> to given <code>newPath</code>.
	 * Notifies all listeners about the change
	 * @param model SingleDocumentModel that needs to be saved
	 * @param newPath Path file path where document needs to be saved
	 */
	public void saveDocument(SingleDocumentModel model, Path newPath);
	/**
	 * Closes given <code>model</code>, and notifies all listeners
	 * about the change.
	 * @param model
	 */
	public void closeDocument(SingleDocumentModel model);
	/**
	 * Add given {@link MultipleDocumentListener}, so that he can
	 * track all the changes that may occur in documents
	 * @param l MultipleDocumentListener
	 */
	public void addMultipleDocumentListener(MultipleDocumentListener l);
	/**
	 * Removes given {@link MultipleDocumentListener}; it can't track
	 * changes made on document anymore
	 * @param l MultipleDocumentListener
	 */
	public void removeMultipleDocumentListener(MultipleDocumentListener l);
	/**
	 * Returns current number of open/made documents
	 * @return int
	 */
	public int getNumberOfDocuments();
	/**
	 * Returns document on given <code>index</code>
	 * @param index int
	 * @return SingleDocumentModel
	 */
	public SingleDocumentModel getDocument(int index);

}
