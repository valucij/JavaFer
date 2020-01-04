package hr.fer.zemris.java.hw17.trazilica.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Class that has methods that help method
 * from classes in package hr.fer.zemris.java.hw17.trazilica.
 * 
 * @author Lucija Valentić
 *
 */
public class Util {

	/**
	 * Constructor
	 */
	private Util() {
	}
	
	/**
	 * From given line, this method extracts word from line,
	 * and returns list of extracted words. Word is continuous
	 * series of alphabetic letters. Everything else is not
	 * a word.
	 * @param line
	 * @return
	 */
	public static List<String> makeWordsFromLine(String line) {
		
		char[] chars = line.toCharArray();
		List<String> words = new ArrayList<String>();
		
		for(int i = 0, n = chars.length; i < n;){
		
			if(!Character.isAlphabetic(chars[i])) {
				i++;
			}else {
				StringBuilder sb = new StringBuilder();
				
				while(i < n && Character.isAlphabetic(chars[i])) {
					sb.append(chars[i]);
					i++;
				}
				words.add(sb.toString());	
			}
		}
		
		return words;
	}
	
	/**
	 * Returns list of string representing content
	 * from file given with fileName
	 * @param fileName String
	 * @return List<String>
	 */
	public static List<String> getFileContent(String fileName) {
		
		try {
			return Files.readAllLines(Paths.get(fileName),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			return null;
		}
		
	}

	/**
	 * Method receives list of string. Each string consists of some words.
	 * This method returns list of all words from given list of strings. 
	 * @param fileContent List<String>
	 * @return List<String>
	 */
	public static List<String> convertFileContentToListOfWords(List<String> fileContent) {
		
		List<String> words = new ArrayList<String>();
		
		for(String line : fileContent) {
			
			List<String> individualWords = makeWordsFromLine(line);
			
			for(String word : individualWords) {
				words.add(word);
			}
			
		}
		
		return words;
	}
	
	/**
	 * Method receives one map, and sorts that map by its keys.
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return 
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map){
		Map<K, V> treeMap = new TreeMap<K, V>(Collections.reverseOrder());
		treeMap.putAll(map);
		return treeMap;
	}
	
}
