package hr.fer.zemris.java.hw17.trazilica;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw17.beans.Document;
import hr.fer.zemris.java.hw17.trazilica.util.Util;
/**
 * Class represents one vocabulary. Instances of this
 * class cannot be made, but internal list that keeps all the words
 * from some files, thus making it a vocabulary, can be filled multiple 
 * times. This class also has method for filling vocabulary with words
 * from documents that are placed in some directory. Path to that directory
 * is given to that method.
 * 
 * @author Lucija Valentić
 *
 */
public class Vocabulary {

	/**
	 * List of words, represents actual vocabulary of some
	 * sorts
	 */
	private static List<String> vocabulary;
	
	private static List<Document> documents;
	
	/**
	 * Constructor
	 */
	private Vocabulary() {	
	}
	
	/**
	 * Fills vocabulary with words from documents/files
	 * located in given directory. Later, all the words that are stop words
	 * are removed from vocabulary. Document that has those stop words is given 
	 * with a path to it
	 * @param pathToDocuments String
	 * @param pathToFileWithStopWords String
	 */
	public static void fillVocabulary(String pathToDocuments, String pathToFileWithStopWords) {
		
		if(vocabulary == null) {
			vocabulary = new ArrayList<String>();
		}
		
		if(documents == null) {
			documents = new ArrayList<Document>();
		}
		
		try {
			Files.walkFileTree(Paths.get(pathToDocuments), new TreeVisit());
		} catch (IOException e) {
			throw new SearchException();
		}
		
		removeStopWords(pathToFileWithStopWords);
		
		DocumentsHandling.setDocuments(documents);
		
	}

	/**
	 * Private class, represents visitor for walking through files and directories
	 * @author Lucija Valentić
	 *
	 */
	private static class TreeVisit implements FileVisitor<Path>{

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			readFileAndPutWordsInVocabulary(file.toString());
			
			Document document = new Document();
			document.setFilePath(file.toString());
			documents.add(document);
			
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			throw new SearchException();
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}
		
		
		
		
	}
	
	/**
	 * Reads content of a file given with fileName, and puts those words in 
	 * vocabulary
	 * @param fileName String
	 */
	private static void readFileAndPutWordsInVocabulary(String fileName) {
		
		List<String> lines = Util.getFileContent(fileName);
		for(String s : lines) {
			
			List<String> words = Util.makeWordsFromLine(s);
			
			for(String word : words) {			
				if(!vocabulary.contains(word.trim().toLowerCase())) {
					vocabulary.add(word.trim().toLowerCase());
				}
			}
		}	
		
	}

	/**
	 * Removes all stop word from dictionary. File
	 * having those stop words is received with fileName
	 * @param fileName String
	 */
	private static void removeStopWords(String fileName) {
		
		List<String> lines = Util.getFileContent(fileName);
		
		for(String s : lines) {
			vocabulary.remove(s.trim().toLowerCase());
		}
	}
	
	/**
	 * Returns length of vocabulary, aka how
	 * many words are there in vocabulary.
	 * @return int
	 */
	public static int sizeVocabulary() {
		return vocabulary.size();
	}
	
	/**
	 * Returns vocabulary, aka list of all words 
	 * from documents
	 * @return List<String>
	 */
	public static List<String> getVocabulary(){
		return vocabulary;
	}
	/**
	 * Removes words that are not in vocabulary
	 * @param words
	 * @return
	 */
	public static List<String> removeWordsNotInVocabulary(String[] words) {
		
		List<String> list = new ArrayList<String>();
		
		for(int i = 1; i < words.length; i++) {
			if(vocabulary.contains(words[i].toLowerCase())) {
				list.add(words[i].toLowerCase());
			}
		}
		return list;
	}
	
	
}
