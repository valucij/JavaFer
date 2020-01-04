package hr.fer.zemris.java.hw17.trazilica;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw17.beans.Document;
import hr.fer.zemris.java.hw17.trazilica.util.Util;

/**
 * Class has all method for handling documents, calculating its
 * frequency vectors, TF-IDF vectors, calculating similarities
 * between vectors, etc. It is used with class {@link Konzola}.
 * 
 * @author Lucija Valentić
 *
 */
public class DocumentsHandling {

	/**
	 * List of all documents
	 */
	private static List<Document> documents;
	/**
	 * One IDF vector
	 */
	private static DocumentVector idfVector;
	/**
	 * String that represents directory where
	 * all documents/files are located
	 */
	private static String environmentPath;
	
	/**
	 * Constructor
	 */
	private DocumentsHandling() {
	}

	/**
	 * Returns this.environmentPath
	 * @return String
	 */
	public String getEnvironmentPath() {
		return environmentPath;
	}

	/**
	 * Sets this.environmnetPath
	 * @param path String
	 */
	public static void setEnvironmentPath(String path) {
		environmentPath = path;
	}
	
	/**
	 * Method calculates IDf vector. If environmnet
	 * is not set, exception is thrown.
	 * @throws SearchException
	 */
	private static void setIdfVector() {
		
		if(environmentPath == null || environmentPath.isBlank()) {
			throw new SearchException("You didn't set environment path!");
		}
		
		int len = Vocabulary.sizeVocabulary();
		double[] idf = new double[len];
		
		double numberOfDocuments = documents.size();
		
		for(int i = 0; i < len; i++) {
			idf[i] = Math.log(numberOfDocuments/howManyDocumentsHaveWord(i));
		}
		
		idfVector = new DocumentVector(idf);
	}

	/**
	 * Method returns number of document that have word on position
	 * that is given with index
	 * @param index int
	 * @return double
	 */
	private static double howManyDocumentsHaveWord(int index) {
		
		int count = 0;
		for(Document d : documents) {
			
			if(d.getFrequencyVector().getDoubleOnIndex(index) != 0.0) {
				count++;
			}
		}
		return (double)count;
	}
	
	/**
	 * Method goes through all documents/files in 
	 * environment and calculates its frequency and TF-IDf vectors,
	 * and also saves its path. Exception is throw if some
	 * file cannot be read.
	 * @throws SearchException
	 */
	public static void gatherDocumentsVectors() {
		
		
		for(Document document : documents) {
			
			List<String> fileContent = Util.getFileContent(document.getFilePath());
			
			if(fileContent == null) {
				throw new SearchException("Not able to read file content");
			}
			
			List<String> wordsInFile = Util.convertFileContentToListOfWords(fileContent);
			
			DocumentVector frequencyVector = makeFrequencyVector(wordsInFile);
			
			document.setFrequencyVector(frequencyVector);
			
		}
		
		if(idfVector == null) {
			setIdfVector();
		}
		
		for(Document d : documents) {
			
			DocumentVector frequencyVector = d.getFrequencyVector();
			DocumentVector tfidfVector = makeTfIdfVector(frequencyVector);
			d.setTfidfVector(tfidfVector);	
		}
		
	}
	
	
	/**
	 * Method calculates frequency vector of 
	 * given list of words  
	 * @param listOfWords List<String>
	 * @return DocumentVector
	 */
	public static DocumentVector makeFrequencyVector(List<String> listOfWords) {

		int len = Vocabulary.sizeVocabulary();
		List<String> vocabulary = Vocabulary.getVocabulary();
		
		double[] freq = new double[len];
		
		int i = 0;
		for(String word : vocabulary) {
			
			freq[i] = numberOfGivenWordInListOfWords(word, listOfWords);
			i++;
		}
		
		return new DocumentVector(freq);		
	}

	/**
	 * Method returns how many times given word can be found
	 * in given list of words
	 * @param word String
	 * @param listOfWords List<String>
	 * @return double
	 */
	private static double numberOfGivenWordInListOfWords(String word, List<String> listOfWords) {
		
		int count = 0;
		
		for(String w : listOfWords) {
			
			if(w.toLowerCase().equals(word)) {
				count++;
			}
		}
		
		return (double)count;
	}

	/**
	 * Method makes TF-IDF vector from given frequencyVector. This
	 * method is called after all documents have been read. Method 
	 * returns new vector
	 * @param frequencyVector DocumentVector
	 * @return DocumentVector
	 */
	public static DocumentVector makeTfIdfVector(DocumentVector frequencyVector) {
		return frequencyVector.multiply(idfVector);	
	}
	
	/**
	 * Returns document from given index
	 * @param index int 
	 * @return Document
	 */
	public static Document getDocumentFromIndex(int index) {
		return documents.get(index);
	}

	/**
	 * Method calculates similarities between one
	 * document that is represented by given vector vector2
	 * and all documents from internal list of documents.
	 * This method creates map -- i-th key of this map
	 * represents similarity of i-th document and given one,
	 * and i-th value represents path of document on disk.
	 * @param vector2 DocumentVector
	 * @return Map<Double, String>
	 */
	public static Map<Double, String> calculateSimilaritiesBetweenAllDocuments(DocumentVector vector2) {
		
		Map<Double, String> result = new HashMap<Double, String>();
		
		for(Document d : documents) {
			
			DocumentVector vector1 = d.getTfidfVector();
			
			double norm1 = vector1.norm();
			double norm2 = vector2.norm();
			double dot = vector1.dot(vector2);
			
			double sim12 = dot/(norm1 * norm2);
			result.put(sim12, d.getFilePath());
			
		}
		
		return result;
	}
	
	public static void setDocuments(List<Document> d) {
		documents = d;
	}
}
