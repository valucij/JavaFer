package hr.fer.zemris.java.hw06.shell.util;

import hr.fer.zemris.java.hw06.shell.Environment;

/**
 * Class that helps in setting symbols for {@link Environment}
 * @author Lucija Valentić
 *
 */
public class Setter {

	/**
	 * Flag
	 */
	private static boolean flag = false; 
	/**
	 * Method that sets some symbol for environment, based 
	 * on given arguments. Returns boolean, based on whether the
	 * symbol has been set or not.
	 * @param env
	 * @param string
	 * @param c
	 * @return boolean
	 */
	public static boolean isSet(Environment env, String string, char c) {
		
		switch(string) {
		case "PROMPT":
			env.setPromptSymbol(c);
			flag = true;
			break;
		case "MULTILINE":
			env.setMultilineSymbol(c);
			flag = true;
			break;
		case "MORELINES":
			env.setMorelinesSymbol(c);
			flag = true;
			break;
		}
		
		return flag;
		
	}
	
}
