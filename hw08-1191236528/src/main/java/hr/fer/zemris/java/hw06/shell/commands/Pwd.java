package hr.fer.zemris.java.hw06.shell.commands;

import java.util.LinkedList;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.*;
import hr.fer.zemris.java.hw06.shell.util.Util;

/**
 * Class implements interface {@link ShellCommand}. It represents 
 * command 'pwd'. Command doesn't expect any arguments. It writes out current 
 * directory.
 * @author Lucija Valentić
 *
 */
public class Pwd implements ShellCommand{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		List<String> list = Util.parseArguments(arguments);
		
		if(list.size() != 0) {
			Util.message(env, "0", list.size());
			return ShellStatus.CONTINUE;
		}
		
		env.writeln(env.getCurrentDirectory().toString());
		return ShellStatus.CONTINUE;
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "pwd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		
		list.add("Command doesn't expect any arguments. It writes out current "
				+ "directory.");
		
		return List.copyOf(list);

	}

}
