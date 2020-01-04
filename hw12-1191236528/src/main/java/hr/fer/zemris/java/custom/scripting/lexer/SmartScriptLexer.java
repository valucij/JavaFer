package hr.fer.zemris.java.custom.scripting.lexer;


/**
 * This SmartScriptLexer handles texts, and extracts from it useful 
 * informations. It 'translates' text with help of tokens. There are 
 * couple of different tokens that words can be. Also, there is possibility
 *  of escaping. In this lexer, there are two modes, one is 
 * TAG mode, and other is TEXT mode. In TEXT mode, everything is a text.
 *  In TAG mode, words can be different things, like function
 * variable, numbers, operator. If some name of a tag, or function, or
 *  variable is invalid, exception is thrown. Next example
 * shows how this Lexer can be used.
 * 
 * Input > This is sample text.
 *			{$ FOR i 1 10 1 $}
 * 
 * Output > (TEXT, This is sample text.)
 * 			(TAGNAME, FOR)
 * 			(VARIABLE, i)
 * 			(NUMBER, 1)
 * 			(NUMBER, 10)
 * 			(NUMBER, 1)
 * 			(ENDTAG, null)
 * 			 
 * 
 * @author Lucija Valentić
 *
 */
public class SmartScriptLexer {
	
	/**
	 * Array that keeps string in form of characters, ready to
	 * be parsed
	 */
	private char data[];
	/**
	 * Token just created.
	 */
	private SmartScriptToken token;
	/**
	 * Position of next character that is ready to be observed
	 */
	private int currentIndex;
	/**
	 * Current state in which Lexer is. There are two states - TAG and TEXT
	 */
	private SmartLexerState currentState;
	
		
	/**
	 * Constructor
	 * 	
	 * @param text
	 */
	public SmartScriptLexer(String text) {
		data = text.toCharArray();
		currentIndex = 0;
		token = null;
		currentState = SmartLexerState.TEXT;
		
	}
	/**
	 * Changes state in which SmartLexer performs.
	 * 	
	 * @param state
	 */
	public void setState(SmartLexerState state) {
		
		if(state.equals(null)) throw new NullPointerException();
			
		currentState = state;
	}
	/**
	 * Gets next token from given text (it was given in constructor).
	 *  More specifically, it extracts new word, and adds it to appropriate tag.
	 *  
	 * @return token newly created in this method
	 */
	public SmartScriptToken nextToken() {
		
		/*Lexer is in state TEXT*/
		if(currentState == SmartLexerState.TEXT) {
			
			if(token != null && token.getType().equals(SmartTokenType.EOF)) {
				
				throw new SmartLexerException();
			}else if(data.length == 0) {
				throw new NullPointerException();
			}
			
			
			if(String.copyValueOf(data).isBlank() || currentIndex == data.length) {
				token = makeEofToken();
				return token;
			}
			
			int nextTagBegin = nextTagBeginning();
			
			if(nextTagBegin == -1) {
				nextTagBegin = data.length;
			}
			
			String newText = String.copyValueOf(data, currentIndex, nextTagBegin - currentIndex);
		
			/*If there is no characters before beginning of a tag, create token that
			 * represent opening of a tag, and enter TAG state*/
			if(currentIndex == nextTagBegin) {
				
				token = makeTagNameToken();//he moves currentIndex
				setState(SmartLexerState.TAG);
				return token;
			}
			
			/*If there is characters like spaces or tabulators, make TEXT token,
			 * cause other wise, thex will be skipped*/
			if(newText.isBlank()) {
				
				token = makeTextToken(newText);
				currentIndex = nextTagBegin;
				return token;
			}
			
			newText = fixEscapeTextState(newText);
			
			currentIndex = nextTagBegin;
			/*If string of characters before next beginning of a tag are 'normal' characters
			 * just create TEXT token*/
			
			
			token = makeTextToken(newText);
			return token;
			
		}//SmartLexerState = TAG, tagname already made;
			
		/*Lexer is in state TAG*/			
		int indexClosingTag = nextTagEndingDollarSign();
		
		if(indexClosingTag == -1) {
			throw new SmartLexerException("Tag not closed");
		}
			
		/*We are taking whole string, from current index to ending of a tag, and we are trying to see what that string is, what does it consists of.
		 * Then if we encounter let's say a variable, we extract that variable from the string, make token type VARIABLE, and shift currentIndex, so
		 * in the next iteration, string inside of tag is smaller.*/
		currentIndex = nextNotBlank();
					
		if(currentIndex == indexClosingTag) {	
			token = makeClosingTag();
			setState(SmartLexerState.TEXT);
			return token;
		}
			
		String newString = String.copyValueOf(data, currentIndex, indexClosingTag - currentIndex);
					
		
		newString = fixEscapeTagState(newString);
		
		/*String help is used just to check what is at the beginning of a string, so we can make appropriate token*/
		String help;
					
		if(newString.length() == 1) {
			help = newString.substring(0);
		}else {
			help = newString.substring(0, 1);
		}
			
		if(help.matches("[a-ž]") || help.matches("[A-Ž]")) {
			token = makeVariableToken(newString);
			return token;
		}else if(help.equals("@")){
			token = makeFunctionToken(newString);
			return token;
		}else if(isOperator(newString)) {
			token = makeOperatorToken(newString);
			return token;
		}else if(help.matches("[\"]")){
			token = makeStringToken(newString);
		
			return token;
		}else {
			if(!isNumber(newString)) {
		
				throw new SmartLexerException("Wrong informations in tag");
			}
			
			token = makeNumberToken(newString);
			return token;
		}
			
		
		
	}
	/**
	 * This method makes new Number Token, with first number in given string.
	 * 	
	 * @param string given string from we must extract number
	 * @return token newly made number token
	 */
	private SmartScriptToken makeNumberToken(String string) {
		
		int endingNumberIndex = lastNumberIndex(string);
		
		currentIndex += endingNumberIndex;
		
		String newString;
		
		if(endingNumberIndex == string.length()) {
			newString = string.substring(0);
		}
		else {
			newString = string.substring(0, endingNumberIndex);
		}
		
		try {
			
			int newInt = Integer.parseInt(newString);
			
			SmartScriptToken newToken = new SmartScriptToken(SmartTokenType.NUMBER, newInt);
			return newToken;
			
			
			
		}catch(NumberFormatException ex) {}
		
		double newDouble = Double.parseDouble(newString);
		
		SmartScriptToken newToken = new SmartScriptToken(SmartTokenType.NUMBER, newDouble);
		return newToken;
		
		
	}
	/**
	 * Return last index + 1 of number, it is used in method in which we are
	 *  making new number token
	 * 	
	 * @param string given string where we are searching for last number digit
	 * @return int
	 * @throws SmartLexerException if number is invalid, if there is 'number
	 * 			 with character "," as its decimal point'
	 */
	private int lastNumberIndex(String string) {
		
		int flag = 0;
		
		for(int i = 0, n = string.length(); i < n; i++) {
			
			String help;
			
			if(i + 1 == string.length()) {
				help = string.substring(i);
			}else {
				help = string.substring(i, i + 1);
			}
			
			if(help.matches("[0-9]") || (help.equals(".") && flag == 0)) {
				
				if(flag == 0) flag = 1;
				
			}else {
				
				if(help.equals(",")) {
					throw new SmartLexerException();
				}
				
				return i;
			}
			
		}
		
		return string.length();
		
	}
	/**
	 * This method makes only string token. 
	 * 	
	 * @param string given string from we must extract new string
	 * @return token
	 * @throws exception if string is not given properly
	 */
	private SmartScriptToken makeStringToken(String string) {
		
		int endingStringIndex = lastIndexString(string);
		
		if(endingStringIndex == string.length() && string.isEmpty()) {
			throw new SmartLexerException();
		}
		
		currentIndex += endingStringIndex + 1;
		String newString;
		
		if(endingStringIndex + 1 == string.length()) {
			newString = string.substring(0);
		}else {
		
			newString = string.substring(0, endingStringIndex + 1);
		}
		
		newString = erasingEscapeSign(newString);
		
		newString = fixBlanks(newString);
		SmartScriptToken newToken = new SmartScriptToken(SmartTokenType.STRING, newString);
		return newToken;	
		
	}
	private String fixBlanks(String string) {
		
		char[] chars = string.toCharArray();
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0, n = string.length(); i < n; i++) {
			
			if(chars[i] == '\\' && ((i+1) < n) && chars[i+1] == 'r') {
				sb.append("\r");
				i++;
			}else if(chars[i] == '\\' && ((i+1) < n) && chars[i+1] == 'n') {
				sb.append("\n");
				i++;
			}else {
				sb.append(chars[i]);
			}
		}
		
		return sb.toString();
	}
	/**
	 * We call this function when we want to escape the text, erase some 
	 * of the characters "\". There are rules of course.
	 * \\ is interpreted as \
	 * \"something" is interpreted as a string, and not a ending od a string	
	 * @param string
	 * @return newString newly created with erasing some of the \ 
	 */
	private String erasingEscapeSign(String string) {
		
		String newString = "\"";
		
		for(int i = 1, n = string.length(); i < n; i++) {
			
			String help;
			
			if(i + 1 == string.length()) {
				help = string.substring(i);
			}else {
				help = string.substring(i, i + 1);
			}
			
			if(help.matches("\\\\")) {
				
				if(i + 1 == string.length()) {
					throw new SmartLexerException();
				}
				
				if(i + 2 == string.length()) {
					help = string.substring(i + 1);
				}else {
					help = string.substring(i + 1, i + 2);
				}
				
				if(help.matches("[\"]")) {
					newString = newString + "\"";
					i+= 1;
				}else {
					
					newString = newString + "\\";
				}
				
			}else {
				newString = newString + help;
			}
			
		}
		
		return newString;
		
	}
	/**
	 * Method return last index of a string in given string, so we can
	 *  easly create token.
	 * 	
	 * @param string
	 * @return index int that says the position of last character in string
	 */
	private int lastIndexString(String string) {
		
		for(int i = 1, n = string.length(); i < n; i++) {
			
			String help;
			
			if(i + 1 == string.length()) {
				help = string.substring(i);
			}else {
				
				help = string.substring(i, i + 1);
			}
			
			
			if(help.matches("\"")) {
				
		
				return i;
		
			}
			
		}
		
		return string.length();
	}
	/**
	 * Makes function token.
	 * 		
	 * @param string
	 * @return token newly made.
	 * @throws SmartLexerException if function name is invalid.
	 */
	private SmartScriptToken makeFunctionToken(String string) {
		
		int indexNextBlank = nextBlank();
		int closingTagIndex = nextTagEndingDollarSign();
		int endingFunction;
		
		if(closingTagIndex < indexNextBlank) {
			endingFunction = closingTagIndex - currentIndex;
		}else {
			endingFunction = indexNextBlank - currentIndex;
		}
		
		String newString;
		
		if(endingFunction == string.length()) {
			newString = string.substring(1);
		}else {
			newString = string.substring(1, endingFunction);
		}
		
		
		if(!isValidFunctionName(newString)) {

			throw new SmartLexerException("Invalid function name");
		}
		
		currentIndex += endingFunction;
		SmartScriptToken newToken = new SmartScriptToken(SmartTokenType.FUNCTION, newString);
		return newToken;
		
		
	}
	/**
	 * Checks if name of the function, (of given string in this case) is valid
	 * 	
	 * @param string
	 * @return true id the name is valid, false if it is not
	 */
	private boolean isValidFunctionName(String string) {
		
		
		if(string.length() == 1) {
			return false;
		} 
		
		String help;
		
		if(string.length() == 2) {
			help = string.substring(1);
		}else {
			help = string.substring(1, 2);
		}
		
		if(!help.matches("[a-ž]") && !help.matches("[A-Ž]")) {
			
			return false;
		} 
		
		for(int i = 3, n = string.length(); i < n; i++) {
			
			if(string.length() == i +1) {
				help = string.substring(i);
			}else {
				help = string.substring(i, i + 1);
			}
			
			if(!help.matches("[0-9]") && !help.matches("[a-ž]") && !help.equals("_") && !help.matches("[A-Ž]")) {
			
				return false;
			}
		}
		
		return true;
		
	}
	/**
	 * Method makes variable token
	 * 	
	 * @param string
	 * @return token newly made
	 * 
	 */
	private SmartScriptToken makeVariableToken(String string) {
		
		int indexEndingWord = lastVariableIndex(string);
		String newString;
		
		if(indexEndingWord == string.length()) {
			newString = string.substring(0);
		}else {
			newString = string.substring(0, indexEndingWord);
		}
		
		currentIndex += indexEndingWord;
		SmartScriptToken newToken = new SmartScriptToken(SmartTokenType.VARIABLE, newString);
		return newToken;
		
		
	}
	/**
	 * Searches for the last position of variable name
	 * 	
	 * @param string
	 * @return index int that represents position of a last character in 
	 * variable name
	 */
	private int lastVariableIndex(String string) {
		
		for(int i = 0; i < string.length(); i++) {
			String help;
			
			if(i + 1 == string.length()) {
				help = string.substring(i);
			}else {
				help = string.substring(i, i + 1);
			}
			
			if(!help.matches("[0-9]") && !help.matches("[a-ž]") && !help.matches("[A-Ž]") && !help.equals("_")) {
				return i;
			}
			
		}
		
		return string.length();
		
		
	}
	/**
	 * Checks if the given number in string is valid
	 * 	
	 * @param string
	 * @return true if the number is valid, false otherwise
	 */
	private boolean isNumber(String string) {
		
		if(string.length() == 1 && !string.matches("[0-9]")) {
			return false;
		}
		
		if(string.length() == 1 && string.matches("[0-9]")) {
			return true;
		}
		
		String helpFirst = string.substring(0,1);
		String helpSecond;
		
		if(string.length() == 2) {
			helpSecond = string.substring(1);
		}else {
			helpSecond = string.substring(1, 2);
		}
		
		if( (helpFirst.equals("-") && helpSecond.matches("[0-9]")) || helpFirst.matches("[0-9]")) {
			return true;
		}
		
		return false;
	}
	/**
	 * Method makes operator token	
	 * @param string
	 * @return token newly made
	 */
	private SmartScriptToken makeOperatorToken(String string) {
		
		String newString;
		
		if(string.length() == 1) {
			newString = string.substring(0);
		}else {
			newString = string.substring(0, 1);
		}
		
		currentIndex++;
		
		SmartScriptToken newToken = new SmartScriptToken(SmartTokenType.OPERATOR, newString);
		return newToken;	
	}
	
	/**
	 * Checks if the beginning of the given string is operator or not.
	 * 	
	 * @param string
	 * @return true if it is, false otherwise
	 */
	private boolean isOperator(String string) {
		
		if(string.length() == 1 && string.matches("[0-9]")) {
			return false;
		}
		
		if(string.length() == 1 && (string.equals("+") || string.equals("-") || string.equals("*") || string.equals("/")) ) {
			return true;
		}
		
		String helpFirst = string.substring(0, 1);	
		
		String helpSecond;
		
		if(string.length() == 2) {
			helpSecond = string.substring(1);
		}else {
			helpSecond = string.substring(1, 2);
		}
		
		if(helpFirst.equals("-") && helpSecond.matches("[0-9]")) {
			return false;
		}
		
		if(helpFirst.equals("+") || helpFirst.equals("-") || helpFirst.equals("/") || helpFirst.equals("*")) {
			return true;
		}
				
		return false;
		
	}
	/**
	 * Method does escaping, but it is called only in tag state.
	 * 	
	 * @param string that needs to be changed 
	 * @return srting newly made
	 */
	private String fixEscapeTagState(String string) {
		
		
		String newString = "";
		
		for(int i = 0, n = string.length(); i < n; i++) {
			String help;
			
			if(i + 1 == string.length()) {
				help = string.substring(i);
			}else{
				help = string.substring(i, i + 1);
			}
			
			if(help.matches("\\\\")) {
				
				if(i + 1 == string.length()) {
					throw new SmartLexerException();
				}else{
					help = string.substring(i + 1, i + 2);
				}
				
				if(help.matches("\\\\")) {
					newString = newString + "\\";
					i += 2;
				}else if(help.matches("\"")) {
					newString = newString + "\\\"";
					i += 1;
				}else if(help.matches("r") ){
					
					newString = newString + "\\" + help;
					i +=1;
				}else if(help.matches("n")){
					newString = newString + "\\" + help;
					i +=1;
				}else {
					throw new SmartLexerException();
				}
				
			}else {
				newString = newString + help;
			}	
		}		
		return newString;
	}
	/**
	 * Method makes token that represents closing tag.
	 * 	
	 * @return token newly made
	 */
	private SmartScriptToken makeClosingTag() {
		
		currentIndex+= 2; 
		SmartScriptToken newToken = new SmartScriptToken(SmartTokenType.ENDTAG, null);
		return newToken;
	}
	/**
	 * Method makes token that represents name of a tag
	 * 	
	 * @return token newly made
	 */
	private SmartScriptToken makeTagNameToken() {
		
		int indexClosingTag = nextTagEndingDollarSign();
		
		if(indexClosingTag == -1) {
			throw new SmartLexerException("Tag not closed");
		}
		
		if(currentIndex + 2 >= data.length) {
			throw new SmartLexerException("There is no tag name");
		}
		currentIndex+= 2;
		
		int indexNextNotBlank = nextNotBlank();
		currentIndex = indexNextNotBlank;
		int indexNextBlank = nextBlank();
		
		
		if(indexNextNotBlank == data.length) {
			throw new SmartLexerException("There is no tag name");
		}
		
		int endingName;
		if(indexClosingTag < indexNextBlank) {
			endingName = indexClosingTag;
		}else {
			endingName = indexNextBlank;
		}
		
		if(String.copyValueOf(data, currentIndex, 1).equals("=")) {
			
			currentIndex = currentIndex + 1;
			SmartScriptToken newToken = new SmartScriptToken(SmartTokenType.TAGNAME, "=");
			return newToken;
		}
		
		String newString = String.copyValueOf(data, currentIndex, endingName - currentIndex);
		
		if(newString.isBlank()) {
			throw new SmartLexerException("There is no tag name");
		}
		
		
		if(isValidTagName(newString)) {
			
			currentIndex = endingName;
		
			SmartScriptToken newToken = new SmartScriptToken(SmartTokenType.TAGNAME, newString);
			return newToken;
			
		}else {
			throw new SmartLexerException("Tag name is not valid");
		}
		
		
	}
	/**
	 * Checks if the name of tag is valid. It is valid if it starts with a 
	 * letter, and then is followed (or not) by letters, numbers, or underscores.
	 * Everything else is invalid.
	 * 	
	 * @param string
	 * @return true if the name is valid, false otherwise
	 */
	private boolean isValidTagName(String string) {
	
		String help = string.substring(0, 1);
		
		if(!help.matches("[a-ž]") && !help.matches("[A-Ž]") && !help.equals("=")) {
			throw new SmartLexerException("Invalid tag name");
		}
		
		if(string.length() == 1 && string.equals("=")) {
			return true;
		}
			
		int indexSymbol = nextSymbol(string);
		
		if(indexSymbol == string.length()) return true;
		
		return false;
	}
	/**
	 * Makes text token;
	 * 	
	 * @param string
	 * @return token newly made
	 */
	private SmartScriptToken makeTextToken(String string) {
		
		SmartScriptToken newToken = new SmartScriptToken(SmartTokenType.TEXT, string);
		return newToken;
		
	}
	/**
	 * Method searches for position of a next symbol.
	 * 	
	 * @param string
	 * @return position of a next symbol
	 */
	private int nextSymbol(String string) {
		
		int returnIndex = 0;	
		
		while(returnIndex < string.length()) {
			
			String help;
			
			if(returnIndex + 1 == string.length()) {
				help = string.substring(returnIndex);
			}else {help = string.substring(returnIndex, returnIndex + 1);}
			
			if(help.matches("[a-ž]") || help.matches("[A-Ž]") || help.matches("[0-9]") || help.equals("_")) {returnIndex++;}
			else break;
		}
		
		return returnIndex;		
	}
	/**
	 * Method makes speacial kind od token, EOF token, that represent ending
	 * of string
	 * 	
	 * @return token newly made
	 */
	private SmartScriptToken makeEofToken() {
		
		SmartScriptToken newToken = new SmartScriptToken(SmartTokenType.EOF, null);
		return newToken;
	}
	/**
	 * Does escaping, but in TEXT mode.
	 * 	
	 * @param string
	 * @return string that has been changed.
	 */
	private String fixEscapeTextState(String string) {
		
		String newString = "";
		
		for(int i = 0, n = string.length(); i < n; i++) {
			
			String help;
			
			if(i + 1 == string.length()) {
				help = string.substring(i);
			}else {
				help = string.substring(i, i+1);
			}
			
			
			if(help.matches("\\\\")) {
				
				if(i + 1 == string.length()) {
					throw new SmartLexerException();
				}
				
				if(i + 2 == string.length()) {
					help = string.substring(i+1);
					
				}else {
					help = string.substring(i+1, i+2);
				}
				
				if(help.matches("\\\\")) {
					
					newString = newString + "\\";
					i++;
					if(i + 2 == string.length()) break;
					
				}else if(help.equals("{")) {
					
					newString = newString + "{";
					i++;
					if(i + 2 == string.length()) break;
					
				}else {
					throw new SmartLexerException("Illegal placing of character \\");
				}
			}else {
				newString = newString + help;
			}
			
		}
		
		return newString;	
	}
	/**
	 * Searches for the position of a next character that equals to blank,
	 *  or some kind of tabulator.	
	 * @return position of a next blank
	 */
	private int nextBlank() {
		
		int help = currentIndex;
				
		while(help < data.length) {
			if(this.data[help] != ' ' && this.data[help] != '\t' && this.data[help] != '\r' && this.data[help] != '\n') {
				help++;
			}else {break;}
		}
		return help;
	}
	/**
	 * Method searches for position of next character that is not blank	
	 * @return position of a next not blank character
	 */
	private int nextNotBlank() {
		
		int help = currentIndex;
		
		while(help < data.length) {
			
			if(this.data[help] == ' ' || this.data[help] == '\t' || this.data[help] == '\r' || this.data[help] == '\n') {
				help++;
			}else {break;}
			
		}
		return help;
	}
	/**
	 * Method that searches for position of a ending of a tag
	 * 
	 * @return -1 if it's not found, index otherwise
	 */
	private int nextTagEndingDollarSign() {
		
		for(int i = currentIndex, n = data.length; i < n; i++) {
			
			if(data[i] == '$') {
				
				if(i + 1  == data.length) {
					return -1;
				}else {
					if(data[i+1] == '}') return i;
				}
			}
		}
		return -1;
	}
	/**
	 * Searches for position of next beginning of a tag
	 * 	
	 * @return position of a beginning of a next tag
	 */
	private int nextTagBeginning() {	
		
		for(int i = currentIndex, n = data.length; i < n; i++) {
			
			if(data[i] == '{') {
				
				if(i + 1 == data.length) {
					return -1;
				}else {
					
					if(data[i+1] == '$') {
						if(i == 0 || data[i -1] != '\\') return i;

					}
				}
				
			}
				
		}
		return -1;
	}	
	/**
	 * Method returns this.token;
	 * 	
	 * @return this.token
	 */
	public SmartScriptToken getToken() {
		return token;
	}
}
