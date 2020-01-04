package hr.fer.zemris.java.hw06.shell.util;

import java.nio.charset.Charset;
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
	 * Method is used for parsing given string of arguments,
	 * and parsing starts from position of given offset.
	 * First word in given string is returned. If given string has 
	 * string in it, it calls other methods to extract string 
	 * inside of that string.
	 * If given string is empty, null, null is returned. 
	 *
	 * @param env
	 * @param string
	 * @param offset
	 * @return string, or null
	 */
	public static String parse(Environment env, String string, int offset) {//kad može biti više argumenata
		
		if(string == null || string.isBlank() || string.isEmpty()) {
			return null;
		}
		
		String forReturn = "";
		
		char[] charString = string.toCharArray();
		
		int j = nextNotBlank(string, offset);
		
		if(charString.length != 0 && charString[0] == '"') {
			forReturn = extractString(string);
		}else {
			for(int i = j; i < string.length() && !Character.isWhitespace(charString[i]); i++) {
				
				if(charString[i] == '\\') {
					forReturn += "/";
				}else {
					forReturn += charString[i];	
				}
				
			}
			
		}
		
		return forReturn;
		
	}
	/**
	 * Method returns position of next non blank character in given string,
	 * it calculates that position from given offset.
	 * @param string
	 * @param offset
	 * @return int
	 */
	private static int nextNotBlank(String string, int offset) {
		
		char[] chars = string.toCharArray();
		int i = offset;
		
		while(i < chars.length && Character.isWhitespace(chars[i])) {
			i++;
		}
		
		return i;
	}

	/**
	 * It extracts given string from string in a form "*". 
	 * It pays attention on escaping - character '\' followed
	 * by '\' is considered as one single '\', followed by '"'
	 * is considered as '"' and everthing else is regular character.
	 * 
	 * @param string
	 * @return string
	 */
	private static String extractString(String string) {
		char[] chars = string.toCharArray();
		String returnString = "";

		for(int i = 1; i < chars.length; i++) {
			
			if(chars[i] == '\\') {
				
				if(i + 1 < chars.length && chars[i] == '\\') {
					returnString += '/';
					i++;
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
	public static Charset parseCharset(Environment env, String arguments, int offset) {
		String string = parse(env, arguments, offset);
		
		if(string == null || string.isBlank()) {
			return null;
		}
		
		Charset charset = Charset.forName(string.toUpperCase());
		
		return charset;
	}
	/**
	 * Method parses first word in given argument. This method
	 * is used with commands that don't expect any arguments after first one.
	 * If there is more arguments than one, then appropriate message
	 * is written and null is returned.
	 *  
	 * @param env
	 * @param arguments
	 * @return string, or null
	 */
	public static String parse(Environment env, String arguments) {//kad se očekuje samo jedan argument, poziva parse za više argumenata
		
		
		String string = parse(env, arguments, 0);
		
		if(string == null) {
			return null;
		}
		
		
		if( string.length() != arguments.length() && !arguments.substring(string.length()).isBlank() && !string.equals(extractString(arguments))) {
			//env.writeln("Invalid number of arguments");
			return null;
		}
		
		return string;
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
		
		if(bytes.length < 8) {
			
			for(byte b : bytes) {
				s = s + String.format("%02X ", b);
			}
			
			for(int i = bytes.length - 1; i < 8; i++) {
				s = s + "   ";
			}
			
			s = s + "|";
			s = s + "   ".repeat(8);
			
		}else if(bytes.length < 16) {
			
			for(int i = 0; i < 8; i++) {
				s = s + String.format("%02X ", bytes[i]);
			}
			s += "|";
			
			for(int i = 8; i < bytes.length; i++) {
				s = s + String.format("%02X ", bytes[i]);
			}
			
			for(int i = bytes.length - 1; i < 8; i++) {
				s = s + "   ";
			}
		}else {
			
			for(int i = 0; i < 8; i++) {
				s = s + String.format("%02X ", bytes[i]);
			}
			s = s + "|";
			
			for(int i = 8; i < 16; i++) {
				s = s + String.format("%02X ", bytes[i]);
			}	
		}
		
				
		return s += "|";
		
	}
	/**
	 * Method returns position of next blank character in given string.
	 * @param string
	 * @return int
	 */
	private static int nextBlank(String arguments) {
		
		char[] chars = arguments.toCharArray();
		
		int j = 0;
		
		while(j < arguments.length() && !Character.isWhitespace(chars[j])) {
			j++;
		}
		return j;
	}
	
	/**
	 * Method receives string with words written in it. It receives new string
	 * without the first word.
	 * If given string is null, null is returned. If there is no words
	 * after the first one, null is also returned.
	 * 
	 * @param arguments
	 * @return string
	 */
	public static String startFromNextWord(String arguments) {
		
		if(arguments == null) {
			return null;
		}
		
		int i = nextBlank(arguments);
		int j = nextNotBlank(arguments, i);
		
		if(j == arguments.length()) {
			return null;
		}
		return arguments.substring(j);
	}
	
}
