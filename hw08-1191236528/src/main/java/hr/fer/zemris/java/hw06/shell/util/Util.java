package hr.fer.zemris.java.hw06.shell.util;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;

/**
 * Class that is used for parsing arguments for different
 * {@link ShellCommand}. There are different versions of
 * parsing because each shell command uses different arguments,
 * and needs different number of arguments.
 * 
 * @author Lucija Valentić
 *
 */
public class Util {

	/**
	 * Method that from given string parses arguments for
	 * {@link ShellCommand}. More specifically, it
	 * extracts single words from given string. It
	 * returns list of string, more specifically, list
	 * of parsed arguments
	 * @param string
	 * @return list<string>
	 */
	public static List<String> parseArguments(String string){
		
		List<String> list = new LinkedList<>();
		
		
		if(string.isBlank()) {
			return list;
		}
		
		String[] arguments = string.split("\\s+");
		int flag = 0;
		
		for(String s : arguments) {
		
			flag++;
			
			if(s.toCharArray()[0] == '"') {
		
				list.add(extractString(s, flag));
			}else {
				list.add(s);
			}
			
		}
		
		return list;
	}
	
	/**
	 * It extracts given string from string in a form "*". 
	 * It pays attention on escaping - character '\' followed
	 * by '\' is considered as one single '\', followed by '"'
	 * is considered as '"' and everything else is regular character.
	 * 
	 * @param string
	 * @return string
	 */
	private static String extractString(String string, int flag) {
		char[] chars = string.toCharArray();
		String returnString = "";

		for(int i = 1; i < chars.length; i++) {
			
			if(chars[i] == '\\') {
				
				if(i + 1 < chars.length && chars[i + 1] == '\\' && flag < 3) {
					returnString += '/';
					i++;
				}else if(i + 1 < chars.length && chars[i + 1] == '\\' && flag >= 3){
					returnString += '\\';
					i++;
				} else if(i + 1 < chars.length  && flag < 3){
					returnString += '/';
					
				}else if(i + 1 < chars.length  && flag >= 3) {
					returnString += '\\';
					
				}else if(i + 1 < chars.length && chars[i] == '"') {
					break;
				}
			}else if(chars[i] == '"'){
				
				if( i + 1 < chars.length && !Character.isWhitespace(chars[i + 1])) {
					return null;
				}
				
				break;
			}else {
				returnString += chars[i];
			}
		}
		return returnString;
	}
	/**
	 * Method used when we want to parse Charset name. It is used by 
	 * cat command. Name of Charset begin at given offset. If charset
	 * by that name doesn't exist, then exception is thrown.	 * 
	 * 
	 * @param env
	 * @param arguments
	 * @param offset
	 * @throws UnsupportedCharsetException()
	 */
	public static Charset parseCharset(Environment env, String string) {
		
		if(string == null || string.isBlank()) {
			return null;
		}
		
		Charset charset = Charset.forName(string.toUpperCase());
		
		return charset;
	}

	/**
	 * Public method that returns string of hex-numbers
	 * that are corresponding to given array of
	 * bytes.
	 * 
	 * @param bytes
	 * @return string
	 */
	public static String bytetohex(byte[] bytes) {
		
		if(bytes == null) {
			return null;
		}
		
		String s =""; 
		
		for(int i = 0; i < 8; i++) {
			
			if(bytes[i] != 0) {
				s = s + String.format("%02X ", bytes[i]);	
			}
			
		}
		
		if(s.length() != 24) {
		
			for(int i = s.length() - 1; i < 23; i++) {
				s = s + " ";
			}
		}
		
		
		s = s + "|";
		
		for(int i = 8; i < 16; i++) {
			if(bytes[i] != 0) {
				s = s + String.format("%02X ", bytes[i]);	
			}
			
		}	
		
		
		if( s.length() != 49) {
			for(int i = s.length() - 1; i < 48; i++) {
				s = s + " ";
			}
		}
				
		return s += "|";
		
	}
	
	/**
	 * Help method, used in {@link ShellCommand}, 
	 * it writes out error message based on given arguments
	 * @param env
	 * @param expected
	 * @param given
	 */
	public static void message(Environment env, String expected, int given) {
		
		env.writeln("Invalid number of arguments. Expected: " + expected + ", given: " + given + ".");
	}
	/**
	 * It resolves given path, with help of Paths.resolve(...). It is
	 * used in {@link ShellCommand}
	 * @param env
	 * @param path
	 * @return path
	 */
	public static Path resolvesPath(Environment env, String path) {
		return env.getCurrentDirectory().resolve(Paths.get(path));
	}
	
}
