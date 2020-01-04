package hr.fer.zemris.java.hw06.shell.util;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellIOException;

public class Getter {

	private static boolean flag = false;
	
	public static char get(Environment env, String string) {
		
		switch(string) {
		case "PROMPT":
			flag = true;
			return env.getPromptSymbol();
		case "MULTILINE":
			flag = true;
			return env.getMultilineSymbol();
		case "MORELINES":
			flag = true;
			return env.getMorelinesSymbol();
		}
		
		if(!flag) {
			throw new ShellIOException();
		}
		
		return 0;
	}
}
