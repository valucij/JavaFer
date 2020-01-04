package hr.fer.zemris.java.hw06.shell.commands;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.util.Getter;
import hr.fer.zemris.java.hw06.shell.util.Setter;
import hr.fer.zemris.java.hw06.shell.util.Util;

/**
 * Class implements interface {@link ShellCommand}. It represents command
 * 'symbol'. 
 * Takes one or two arguments. The first argument
 * is special word and is mandatory. The second
 * argument should be char. If only one argument is
 * provided, and that argument is 'PROMPT', 'MORELINES'
 * or 'MULTILINE'
 * , then it returns current symbol defined for that
 * environment
 * If two arguments are provided, then this command changes 
 * symbol for purpesed for first argument into given char.
 * If there are more arguments given, appropriate message is written
 *		
 * @author Lucija Valentić
 *
 */
public class Symbol implements ShellCommand{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		List<String> listArguments = Util.parseArguments(arguments);
		
		if(listArguments.size() < 1 || listArguments.size() > 2) {
			Util.message(env, "1 or 2", listArguments.size());
			return ShellStatus.CONTINUE;
		}
		String specialWord = listArguments.get(0);
		
		if(listArguments.size() == 1) {
			try {
				env.writeln("Symbol for " + specialWord + " is '" + Getter.get(env, specialWord) + "'");	
			}catch(ShellIOException ex) {
				env.writeln("Invalid arguments");
			}
		}
		if(listArguments.size() == 2) {
			String character = listArguments.get(1);
			
			if(character.length() > 1) {
				env.writeln("Invalid character");
			}else if(character.length() == 1){
				Setter.isSet(env, specialWord.toUpperCase(), character.toCharArray()[0]);
				env.writeln("Symbol for " + specialWord + " changed to '" + character +"'");
			}
			
		}
		
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "symbol";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		
		List<String> list = new LinkedList<>();
		
		list.add("Takes one or two arguments. The first argument");
		list.add("is special word and is mandatory. The second");
		list.add("argument should be char. If only one argument is");
		list.add("provided, and that argument is 'PROMPT', 'MORELINES',"
				+ "or 'MULTILINE'");
		list.add(", then it returns current symbol defined for that"
				+ " environment");
		list.add("If two arguments are provided, then this command changes "
				+ "symbol for purpesed for first argument into given char.");
		list.add("If there are more arguments given, appropriate message is written");
		
		return List.copyOf(list);
	}

}
