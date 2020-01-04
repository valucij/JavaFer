package hr.fer.zemris.java.custom.scripting.nodes;
/**
 * Class that inherits class <code>Node</code>.
 * 
 * @author Lucija
 *
 */
public class TextNode extends Node {

	private String text;
	/**
	 * Constructor
	 * @param string string that is put in this.text
	 */
	public TextNode(String string) {
		text = string;
	}
	/**
	 * Returns this.text in string form
	 * @return this.text
	 */
	public String getText() {
		return text;
	}
}
