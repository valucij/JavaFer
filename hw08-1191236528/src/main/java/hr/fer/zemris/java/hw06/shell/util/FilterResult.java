package hr.fer.zemris.java.hw06.shell.util;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Class represents object that keeps one file name, but kinda in a special way.
 * It divides that file name based on received patter (using class {@link Pattern}), and
 * creates list of different parts of file name based od pattern. Of course, for
 * those groups to be made, given pattern has to have some regex, but divided with characters
 * '(' and ')'. 
 * @author Lucija Valentić
 *
 */
public class FilterResult {

	/**
	 * List of strings, string are part of the 
	 * file name divided into groups
	 */
	private List<String> izraz;
	/**
	 * Number of groups
	 */
	private int number;
	/**
	 * List of regex
	 */
	private List<Pattern> regex;
	
	/**
	 * Constructor
	 * @param file
	 * @param forPattern
	 * @throws IOException
	 */
	public FilterResult(String file, String forPattern) throws IOException {
		
		izraz = new LinkedList<String>();
		izraz.add(file);
		number = 0;
		
		getPatterns(forPattern);
		getGroups(file, forPattern);
		
	}
	
	/**
	 * Method extracts different parts of a given file, 
	 * and saves them in internal list : this.izraz. This
	 * list then represents strings, or part of the file name, 
	 * but divided into groups. For file name to be divided into groups,
	 * given forPattern has to have some characters like '(' and ')' dividing
	 * the regex
	 * @param file
	 * @param forPattern
	 */
	private void getGroups(String file, String forPattern) {
		
		
		char[] fileName = file.toCharArray();
		char[] pattern = forPattern.toCharArray();
		
		
		for(int i = 0, n = file.length(), p = 0, j = 0, s = regex.size(); i < n && j < s; ) {
			
		
			if(fileName[i] != pattern[p]) {
				StringBuilder sb = new StringBuilder();
				while(Pattern.matches(regex.get(j).toString(), String.valueOf(fileName[i]))) {
					sb.append(fileName[i]);
					i++;
				}
				j++;
				izraz.add(sb.toString());
				if(j < s) {
					p += regex.get(j).toString().length();	
				}
				
				
			}else {
				i++;
				p++;
			}
			
		}
				
	}
	
	/**
	 * Method extracts from given string array of regex, and saves
	 * that array in this.regex, internal list. It throws 
	 * exception if something is not written right
	 * @param forPattern
	 * @throws IOException 
	 */
	private void getPatterns(String forPattern) throws IOException{
	
		
		regex = new LinkedList<Pattern>();
		char[] chars = forPattern.toCharArray();
		
		for(int i = 0, n = forPattern.length(); i < n; i++) {
			
			if(chars[i] == '(') {
				number++;
				regex.add(Pattern.compile(getRegex(chars, i)));
				
				i++;
				while(chars[i] != ')') {
					
					if(chars[i] == '(') {
						throw new IOException();
					}
					i++;
				}
			}
		}
	}
	
	/**
	 * From given array of chars, regex is made and 
	 * returned. This method is called when we know that there is 
	 * a regex inside of an array. Also, checking starts from
	 * given index that represents offset.
	 * @param chars
	 * @param index
	 * @return string
	 */
	private String getRegex(char[] chars, int index) {
		
		int i = index + 1;
		StringBuilder sb = new StringBuilder();
		
		while(chars[i] != ')') {
			sb.append(chars[i]);
			i++;
		}
		return sb.toString();
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		
		if(number == 0) {
			return izraz.get(0);
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i < number; i++) {
			sb.append(izraz.get(i));
		}
		return sb.toString();
	}
	
	/**
	 * Returns number of groups in which the 'izraz' is 
	 * made
	 * @return this.number
	 */
	public int numberOfGroups() {
		return number;
	}
	
	/**
	 * Returns specific string from specific group
	 * that is determined with given index
	 * @param index
	 * @return string
	 */
	public String group(int index) {
		return izraz.get(index);
	}
}
