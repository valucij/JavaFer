package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interface can be implemented so that objects of that new
 * implemented class can perform some new actions when activated. Objects
 * like that can track, and are made for tracking, all the changes
 * that can happen in handling one {@link SingleDocumentModel}. With
 * that kind of objects it is easier to change GUI components
 * and how they are shown based on internal swing component of
 * {@link SingleDocumentModel} and how that component has been changed
 * @author Lucija Valentić
 *
 */
public interface SingleDocumentListener {

	/**
	 * Action is performed when modification on the document
	 * has been made
	 * @param model SingleDocumentModel
	 */
	public void documentModifyStatusUpdate(SingleDocumentModel model);
	/**
	 * Action is performed when file path of a document
	 * has been changed
	 * @param model SingleDocumentModel
	 */
	public void documentFilePathUpdate(SingleDocumentModel model);
}
