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

public class Symbol implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String specialWord = Util.parse(env, arguments, 0);
		/*
		if(specialWord == null) {
			System.out.println("sdrft");
			return ShellStatus.CONTINUE;
		}
		*/
		//System.out.println("SpecialWord ." + specialWord+"." + " isBlank " + specialWord.isBlank());
		
		if(specialWord == null || specialWord.isBlank()) {
			env.writeln("Missing arguments");
			return ShellStatus.CONTINUE;
		}
			
		String character = Util.parse(env, arguments, specialWord.length());
		
		System.out.println("Unutar simbol ." + character + ".");
		
		if(character.length() > 1) {
			env.writeln("Invalid arguments");
		}else if(!character.isBlank() && character.length() == 1) {
			Setter.isSet(env, specialWord.toUpperCase(), character.toCharArray()[0]);
			env.writeln("Symbol for " + specialWord + " changed to '" + character +"'");
		}else if(character.isBlank()) {
			try {
				env.writeln("Symbol for " + specialWord + " is '" + Getter.get(env, specialWord) + "'");	
			}catch(ShellIOException ex) {
				env.writeln("Invalid arguments");
			}
			
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

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
