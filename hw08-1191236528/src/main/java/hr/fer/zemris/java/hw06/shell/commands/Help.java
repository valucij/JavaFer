package hr.fer.zemris.java.hw06.shell.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.util.Util;

/**
 * Class implements interface {@link ShellCommand}. This
 * class represents command 'help'. If it is called without arguments, then it lists
 * all the commands available. If it is called with one
 * argument, and that argument is command name, then 
 * it writes command description. If it is written with more
 * arguments, then appropriate message is written
		
 * @author Lucija Valenitć
 *
 */
public class Help implements ShellCommand{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		List<String> listArguments = Util.parseArguments(arguments);
		
		if(listArguments.isEmpty()) {
			
			SortedMap<String, ShellCommand> list = env.commands();
			
			env.writeln("This is the list of supported commands in this shell:");
			
			for(String key : list.keySet()) {
				env.writeln(key);
			}
		}else if(listArguments.size() == 1){
			String commandName = listArguments.get(0);
			
		
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
		}else {
			Util.message(env, "0 or 1", listArguments.size());
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "help";
	}

	/**
	 * {@inheritDoc}
	 */
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
