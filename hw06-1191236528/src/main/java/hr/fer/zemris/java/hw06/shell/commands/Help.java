package hr.fer.zemris.java.hw06.shell.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.util.Util;

public class Help implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		/*
		System.out.println("Absoulte path: " + Paths.get(Util.parse(env, arguments)).toAbsolutePath());
		System.out.println("Path običan " + Paths.get(Util.parse(env, arguments)));
		*/
		
		if(arguments.isBlank()) {
			SortedMap<String, ShellCommand> list = env.commands();
			
			env.writeln("This is the list of supported commands in this shell:");
			
			for(String key : list.keySet()) {
				env.writeln(key);
			}
		}else {
			String commandName = Util.parse(env, arguments);
			
			if(commandName == null) {
				return ShellStatus.CONTINUE;
			}
			
			//System.out.println("Unutar help comand name (argument zapravo) = ." + commandName + ".");
		
			ShellCommand command = env.commands().get(commandName);
			
			if(command == null) {
				env.writeln("Invalid command name");
				return ShellStatus.CONTINUE;	
			}
			
			List<String> list = command.getCommandDescription();
			
			env.writeln("Command: " + command.getCommandName());
			env.write("Command description: ");
			for(String string : list) {
				env.writeln(string);
			}
		}
		
			
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		
		List<String> list = new LinkedList<>();
		
		list.add("If it is called without arguments, then it lists");
		list.add("all the commands available. If it is called with one");
		list.add("argument, and that argument is command name, then ");
		list.add("it writes command description. If it is written with more");
		list.add("arguments, then appropriate message is written");
		
		return List.copyOf(list);
	}

}
