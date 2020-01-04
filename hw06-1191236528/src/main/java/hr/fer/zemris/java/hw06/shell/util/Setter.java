package hr.fer.zemris.java.hw06.shell.util;

import hr.fer.zemris.java.hw06.shell.Environment;

public class Setter {

	private static boolean flag = false; 
	public static boolean isSet(Environment env, String string, char c) {
		
		switch(string) {
		case "PROMPT":
			env.setPromptSymbol(c);
			System.out.println("asdrft");
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
