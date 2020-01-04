package hr.fer.zemris.java.hw03.prob1;

/**
 * Class <code>Lexer</code> represents some form of language processor. 
 * It interprets given text by some rules. It joins each word with some 
 * TokenType. Each word is separated by tabulator or space, one or more. 
 * This lexer then decideds if word is TokenType.WORD, TokenType.NUMBER,
 *  TokenType.SYMBOL.TokenType.NUMBER is a word that consists of numbers 
 *  and only numbers, but we have to be careful with its size. TokenType.WORD 
 *  is every word that is not number, but has at least some letter, or 
 *  number in it. TokenType.SYMBOL is everything that is left when all
 *   tabulators, spaces, numbers, and letters
 * are removed. TokenType.EOF is special token type, and it signals the end
 *  of given text. Also, escaping is permited. This class has two 
 *  states - BASIC and EXTENDED. When lexer is in BASIC state, it works in
 *   the wey decribed above. If it is in EXTENDED state, then everything 
 *   is a word. States are switch with stumbling upon character '#'.
 * In next example it is shown how class <code>Lexer</code> works.
 *   
 * String input = "Ovo je 123ica, ab57.\nKraj";
 * 
 * Lexer then interprets this input and creates tokens. These are tokens
 *  that should be created >
 * (WORD, Ovo)
 * (WORD, je)
 * (NUMBER, 123)
 * (WORD, ica)
 * (SYMBOL, ,)
 * (WORD, ab)
 * (NUMBER, 57)
 * (SYMBOL, .)
 * (WORD, Kraj)
 * (EOF, null)
 *   
 * @author Lucija Valentić
 *
 */

public class Lexer {
	
	/**
	 * Array of charachters that needs to be parsed.
	 */
	private char[] data;
	/**
	 * One token, more precisely, token that has been just made
	 * from characters.
	 */
	private Token token;
	/**
	 * Current position, it represents next character that needs
	 * to be parsed.
	 */
	private int currentIndex;
	/**
	 * State in which lexer is.
	 */
	private LexerState currentState;
	
	/**
	 * Constructor, creates one lexer, and set state of lexer to BASIC
	 * 	
	 * @param text given string that lexer has to interpret
	 */
	public Lexer(String text) {
		data = text.toCharArray();
		currentIndex = 0;
		token = null;
		currentState = LexerState.BASIC;
		
	}
	/**
	 * It sets state of lexer.	
	 * @param state given LexerState that we should set in our lexer
	 */
	public void setState(LexerState state) {
		
		if(state.equals(null)) throw new NullPointerException();
			
		currentState = state;
	}
	/**
	 * Creates new token by above decribed rules. If this method is called 
	 * when the whole string has been interpreted, then it throws exception.
	 * 	
	 * @return token Token that is created 
	 * @throws LexerException if token cannot be created
	 */
	public Token nextToken() throws LexerException {
		
		int nextNotBlank = nextNotBlank();
		currentIndex = nextNotBlank;
		int indexNextBlank = nextBlank();
		
		/*If lexer is in state = EXTENDED */
		if(currentState == LexerState.EXTENDED) {
			
			if(token != null && token.getType().equals(TokenType.EOF)) {throw new LexerException();}
			if(nextNotBlank == data.length ) {
				Token newtoken = new Token(TokenType.EOF, null);
				token = newtoken;
				return newtoken;
			}
			String string = String.copyValueOf(data, nextNotBlank, indexNextBlank - nextNotBlank);
			int nextHashtagIndex = nextHashtag();
			
			if(currentIndex == nextHashtagIndex) {
				Token newtoken = new Token(TokenType.SYMBOL, Character.valueOf('#'));
				token = newtoken;
				currentIndex++;
				return newtoken;
			}
			
			if(indexNextBlank < nextHashtagIndex) {
				string = String.copyValueOf(data, currentIndex, indexNextBlank - currentIndex);
				currentIndex = indexNextBlank;
			}else {
				string = String.copyValueOf(data, currentIndex, nextHashtagIndex - currentIndex);
				currentIndex = nextHashtagIndex;
			}
			

			Token newtoken = new Token(TokenType.WORD, string);
			token = newtoken;
			return newtoken;
		}
		
		
		/*If lexer is in state = BASIC*/
		if(token != null && token.getType().equals(TokenType.EOF)) {
			throw new LexerException();
		}else {
			/*If we are at the end of a string needed to be parsed*/
			if(nextNotBlank == data.length ) {
				Token newtoken = new Token(TokenType.EOF, null);
				token = newtoken;
				return newtoken;
			}
				
			int nextHashtagIndex = nextHashtag();
				
			if(nextHashtagIndex == currentIndex) {	
				currentIndex++;
				Token newToken = new Token(TokenType.SYMBOL,Character.valueOf('#'));
				token = newToken;
				return token;	
			}
				
			String newString = String.copyValueOf(data, currentIndex, nextHashtagIndex - currentIndex);
			String help;
				
			/*Helps to determine what is the next string of characters */
			if(newString.length() == 1) {
				help = newString.substring(0);
			}else {
				help = newString.substring(0, 1);
			}
				
			if(help.matches("[0-9]")) {
				
				token = makeNumberToken(newString);
				return token;
					
			}else if(help.matches("[a-ž]") || help.matches("[A-Ž]")) {				
				token = makeWordToken(newString);
				return token;					
			}				
				
			if(!help.matches("\\\\")) {
				token = makeSymbolToken(help);
				return token;
			}
			
			/*if program comes to here, it means that next string of characters begin with \ */
			if(newString.length() == 1) {
				throw new LexerException();
			}
				
			if(newString.length() == 2) {
				help = newString.substring(1);
			}else {
				help = newString.substring(1, 2);
			}
			
			/*If character \ is followed by letter, exception is thrown*/
			if(help.matches("[a-ž]") || help.matches("[A-Ž]")) {
				throw new LexerException();
			}
			
			/*If observed character is / and we are at the end of string, exception is thrown*/
			if(currentIndex + 1 == nextBlank()) {
				throw new LexerException();
			}				
			
			/*If ecerything is oke, then make word token*/
			token = makeWordToken(newString);
			return token;			
		}
			
	}
	/**
	 * Method makes new word token	
	 * @param string
	 * @return token newly made
	 */
	private Token makeWordToken(String string) {
		
		int lastIndexWord = lastIndexWord(string) - currentIndex;
		String newString;
		
		
		if(lastIndexWord != string.length()) {
			newString = string.substring(0, lastIndexWord);
		}else {
			newString = string.substring(0);
		}
		
		currentIndex = currentIndex + lastIndexWord;
		
		
		newString = fixEscape(newString);
		
		newString = fixingForToken(newString);
		
		Token newToken = new Token(TokenType.WORD, newString);
		return newToken;
	}
	/**
	 * Escaping string that should be put in the token
	 * 	
	 * @param string
	 * @return string that has been changed
	 */
	private String fixingForToken(String string) {
		
		String newString = "";
		
		for(int i = 0, n = string.length(); i < n; i++) {
			
			String help;
			
			if(i + 1 == string.length()) {
				help = string.substring(i);
			}else {
				help = string.substring(i, i + 1);
			}
			
			if(help.matches("\\\\")) {
				
				if(i + 1 == string.length()) {
					newString = newString + "\\";
					break;
				}
				
				if(i + 2 == string.length()) {
					help = string.substring(i + 1);
				}else {
					help = string.substring(i + 1, i + 2);
				}
				
				if(help.matches("\\\\")) {
					newString = newString + "\\";
					i++;
				}
				
				
			}else {
				
				newString = newString + help;
			}
			
		}
		
		return newString;
		
	}
	/**
	 * Searches for position of last character that is not included in the word	
	 * @param string
	 * @return position of a character that is not in the word
	 */
	private int lastIndexWord(String string) {
		
		int n;
		 
		if(string.length() < nextBlank() - currentIndex) {
			n = string.length();
		}else {
			n = nextBlank();
		}
				
		for(int i = currentIndex; i < n; i++) {
			
			if(Character.toString(data[i]).matches("[0-9]") || isSymbol(Character.toString(data[i]))) {
				return i;
			}
			
			if(Character.toString(data[i]).matches("\\\\")) {
				
				if(i + 1 == n) {
					return n;
				}
				
				if(Character.toString(data[i + 1]).matches("[0-9]")) {
					i++;
					continue;
				}
				
				if(!Character.toString(data[i + 1]).matches("[a-ž]") && !Character.toString(data[i + 1]).matches("[A-Ž]") && !Character.toString(data[i + 1]).matches("\\\\")) {
					return i + 1;
				}
				
			}
			
			
		}
		
		return n;
	}
	
	private boolean isSymbol(String string) {
		
		if(!string.matches("[0-9]") && !string.matches("[a-ž]") && !string.matches("[A-Ž]") && !string.matches("\\\\") ) {
			return true;
		}
		return false;
	}
	/**
	 * Method makes number token	
	 * @param string
	 * @return token newly made
	 */
	private Token makeNumberToken(String string) {
		
		int count = 0;
		String newNumber;
		
		
		for(int i = 0, n = string.length(); i < n; i++) {
			String help;
			
			if(i + 1 == string.length()) {
				help = string.substring(i);
			}else {
				help = string.substring(i,i + 1);
			}
			
			if(!help.matches("[0-9]")) {
				count = i;
				break;
			}
		}
		
		if(count == 0) {
			newNumber = string;
		}else {
			newNumber = string.substring(0, count);
		}
		
		if(newNumber.length() >= 23) {
		
			throw new LexerException();
		}
		
		currentIndex += newNumber.length(); 
		Token newToken = new Token(TokenType.NUMBER, Long.valueOf(newNumber));
		return newToken;
		
	}
	/**
	 * Method makes Symbol token		
	 * @param string
	 * @return token newly made
	 */
	private Token makeSymbolToken(String string) {
		
		char[] newChar = string.toCharArray();
		currentIndex++;
		Token newToken = new Token(TokenType.SYMBOL, Character.valueOf(newChar[0]));
		return newToken;
	}
	/**
	 * This method returns index of the next character '#' in given string
	 * 	
	 * @param string 
	 * @return returnIndex int that represents position in given string on
	 * 		   which is next character '#'
	 */
	private int nextHashtag() {
		
		for(int i = currentIndex, n = data.length; i < n; i++) {
			
			if(data[i] == '#') {
				return i;
			}
			
		}
		return data.length;
		
		
	}

	/**
	 * This method returns string without unnecessary characters '\', 
	 * it return unescaped string from given one.
	 * 	
	 * @param string
	 * @return newstring unescaped string
	 */
	private String fixEscape(String string) {
		
		String newString = "";
		String help;
		
		for(int i = 0, n = string.length(); i < n; i++) {
			
			if(i + 1 == string.length()) {
				help = string.substring(i);
			}else {
				help = string.substring(i, i + 1);
			}
			
			if(help.matches("\\\\")) {
				
				if(i + 2 >= string.length()) {
					help = string.substring(i + 1);
				}else {
					help = string.substring(i + 1, i + 2);
				}
				
				if(help.matches("[0-9]")) {
					newString = newString + "\\" + help;
					i++;
				}else if(help.matches("\\\\")) {
					newString = newString + "\\";
					i++;
				}else {
					throw new LexerException();
				}
				
				
			}else {
				newString = newString + help;
			}
			
			
		}
		
		return newString;
		
	}
	
	/**
	 * This method returns index of position of next character/number/letter/symbol
	 *  in text that lexer has to interpret.
	 * 
	 * @return help int that represent position of next notBlank character
	 */
	private int nextNotBlank() {
		
		int help = currentIndex;
		
		while(help < data.length) {
			
			if(data[help] == ' ' || data[help] == '\t' || data[help] == '\r' || data[help] == '\n') {
		
				help++;
			}else {
				break;
			}
			
		}
		
		
		return help;
	}
	/**
	 * This method returns index of position of next space or tabulator of some 
	 * kind in text that lexer has to interpret.
	 * 
	 * @return help int that represent position of next blank character
	 */	
	private int nextBlank() {
			
		int help = currentIndex;
		
		while(help < data.length) {
			if(this.data[help] != ' ' && this.data[help] != '\t' && this.data[help] != '\r' && this.data[help] != '\n') {
		
				help++;
			}else {
				break;
			}
		}
		return help;
	}
	/**
	 * This method returns current token	
	 * 
	 * @return this.token that represents current token
	 */
	public Token getToken() {
		return token;
	}
	
}
