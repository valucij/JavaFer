package hr.fer.zemris.java.hw06.shell.util;

import java.util.regex.Pattern;

import hr.fer.zemris.java.hw06.shell.ShellIOException;

/**
 * Class represents lexer that is used in {@link NameBuilderParser}. It creates
 * tokens with type {@link NameBuilderTokenType}, and in that way, parser, when
 * going through some text, knows what kind of word next word is, and what to
 * do with it
 * @author Lucija Valentić
 *
 */
public class NameBuilderLexer {

	/**
	 * Internal token
	 */
	private NameBuilderToken token;
	/**
	 * Data that needs to be checked
	 */
	private char[] data;
	/**
	 * Position of a 'point' that points to 
	 * some place in this.data, it represents
	 * what needs to be checked next
	 */
	private int current;
	
	/**
	 * Constructor
	 * @param string
	 */
	public NameBuilderLexer(String string){
	
		if(string == null) {
			data = null;
		}else {
			data = string.toCharArray();	
		}
		
		current = 0;
	}
	
	/**
	 * Creates next token from this.data. It throws exception
	 * if something in data is written wrong
	 * @return token
	 */
	public NameBuilderToken nextToken() {
		
		while(true) {
			
			StringBuilder sb = new StringBuilder();
			
			if(data == null || current >= data.length) {
				token = new NameBuilderToken(NameBuilderTokenType.EOF, " ");
				return token;
			}
			
			while(current < data.length && data[current] != '$') {
			
				sb.append(data[current]);
				current++;
			}
			
			
			if(sb.length() != 0) {
				token = new NameBuilderToken(NameBuilderTokenType.TEXT, sb.toString());
				return token;
			}
			
			
			if(current + 1 == data.length || data[current + 1] != '{') {
				throw new ShellIOException();
			}
			
			current += 2;
			
			while(current < data.length && data[current] != '}') {
				sb.append(data[current]);
				current++;
			}
			current ++;
			
			if(  Pattern.matches("\\d+" , sb.toString())) {
				
				token = new NameBuilderToken(NameBuilderTokenType.GROUP1, sb.toString());
				return token;
			}else if(  Pattern.matches("(\\s+)?\\d+(\\s+)?,(\\s+)?\\d+(\\s+)?" , sb.toString())) {
				token = new NameBuilderToken(NameBuilderTokenType.GROUP2, sb.toString());
				return token;
			}else {
				
				throw new ShellIOException();
			}
				
		}
		
	}
	
	/**
	 * Checks if there is more to parse
	 * @return boolean
	 */
	public boolean hasNext() {
		
		if(token == null) {
			return false;
		}
		
		if(!token.getType().equals(NameBuilderTokenType.EOF)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns internal token
	 * @return this.token
	 */
	public NameBuilderToken getToken() {
		return token;
	}
	
}
