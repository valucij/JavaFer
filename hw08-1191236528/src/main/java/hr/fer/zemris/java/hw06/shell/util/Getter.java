package hr.fer.zemris.java.hw06.shell.util;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellIOException;

/**
 * Class that helps in getting symbols for {@link Environment}
 * @author Lucija Valentić
 *
 */
public class Getter {
	
	/**
	 * Method that returns symbol for environment, based 
	 * on given arguments. It can return symbol for
	 * 'prompt', 'multiline' or 'morelines', based on given
	 * arguments. It throws exception if user demands that symbol
	 * for other than these three is returned.
	 * @param env
	 * @param string
	 * @return char
	 * @throws ShellIOException()
	 */
	public static char get(Environment env, String string) {
		
		switch(string) {
		case "PROMPT":
	
			return env.getPromptSymbol();
		case "MULTILINE":
			
			return env.getMultilineSymbol();
		case "MORELINES":
			
			return env.getMorelinesSymbol();
		}
		
		
		throw new ShellIOException();
		
		
		
	}
}
