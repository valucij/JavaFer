package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interface that represents model for objects that
 * are capable of tracking changes on {@link MultipleDocumentModel}, 
 * and performing actions implemented in its methods when called
 * @author Lucija Valentić
 *
 */
public interface MultipleDocumentListener {

	/**
	 * Action is performed when current document changes
	 * from <code>previousModel</code>, to <code>currentModel</code>
	 * @param previousModel SingleDocumentModel
	 * @param currentModel SingleDocumentModel
	 */
	public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	/**
	 * Action is performed when new document is added. New document
	 * is represented with given <code>model</code>
	 * @param model SingleDocumentModel
	 */
	public void documentAdded(SingleDocumentModel model);
	/**
	 * Action is performed when given document, represented
	 * with <code>model</code> is removed 
	 * @param model SingleDocumentModel
	 */
	public void documentRemoved(SingleDocumentModel model);
}
