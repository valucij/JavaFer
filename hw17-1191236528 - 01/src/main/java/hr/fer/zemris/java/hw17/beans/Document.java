package hr.fer.zemris.java.hw17.beans;

import hr.fer.zemris.java.hw17.trazilica.DocumentVector;

/**
 * Class represent one document. Every document
 * has its file path, path that says where document is located
 * on disk, frequency vector - vector that shows how many times
 * some word from vocabulary is in this document, and TF-IDF vector.
 * TF-IDF vector on i-th position has double value that is 
 * product of number of time i-th word from dictionary is in
 * that document and idf value of i-th word from document.  
 * Idf value of word is calculated like this -> number of
 * all documents divided by number document that have that
 * i-th word.
 * 
 * @author Lucija Valentić
 *
 */
public class Document {

	/**
	 * String that represents path to this document
	 */
	private String filePath;
	/**
	 * Frequency vector of this document
	 */
	private DocumentVector frequencyVector;
	/**
	 * TF-IDF vector of this document
	 */
	private DocumentVector tfidfVector;
	
	/**
	 * Constructor
	 */
	public Document() {
	}
	
	/**
	 * Returns this.frequencyVector
	 * @return DocumentVector
	 */
	public DocumentVector getFrequencyVector() {
		return this.frequencyVector;
	}
	
	/**
	 * Returns this.filePath
	 * @return String
	 */
	public String getFilePath() {
		return this.filePath;
	}
	
	/**
	 * Sets this.frequencyVector
	 * @param frequencyVector DocumentVector
	 */
	public void setFrequencyVector(DocumentVector frequencyVector) {
		this.frequencyVector = frequencyVector;
	}
	
	/**
	 * Sets this.filePath
	 * @param filePath String
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * Method returns this.tfidfVector
	 * @return DocumentVector
	 */
	public DocumentVector getTfidfVector() {
		return tfidfVector;
	}

	/**
	 * Sets this.tfidfVector
	 * @param tfidfVector DocumentVector
	 */
	public void setTfidfVector(DocumentVector tfidfVector) {
		this.tfidfVector = tfidfVector;
	}
	
	
}
