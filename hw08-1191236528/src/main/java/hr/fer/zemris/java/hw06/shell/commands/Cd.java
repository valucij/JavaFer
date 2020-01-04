package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.*;
import hr.fer.zemris.java.hw06.shell.util.Util;
/**
 * Class implements interface {@link ShellCommand}. This class
 * represents command 'cd'. This command expects one argument. Given arguments
 * should be some path to directory that is inside of current one.
 * If there is no such directories, appropriate message is written.
 * Otherwise, current directory is set to given one.
 * Of course, what this essentially does, is work like command 'cd' in
 * normal terminal.
 * @author Lucija Valentić
 *
 */
public class Cd implements ShellCommand{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		List<String> listArguments = Util.parseArguments(arguments);
		
		if(listArguments.size() != 1) {
			Util.message(env, "1", listArguments.size());
			return ShellStatus.CONTINUE;
		}
		
		
		Path newPath = Util.resolvesPath(env, listArguments.get(0));
		
		if(!newPath.toFile().exists()) {
			env.writeln("Given directory doesn't exist");
			return ShellStatus.CONTINUE;
		}
		
		env.setCurrentDirectory(newPath.toAbsolutePath().normalize());
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "cd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		
		list.add("This command expects one argument. Given arguments"
				+ "should be some path to directory that is inside of current one.");
		list.add("If there is no such directories, appropriate message is written."
				+ "Otherwise, current directory is set to given one.");
		list.add("Of course, what this essentially does, is work like command 'cd' in"
				+ "normal terminal.");
		list.add("");
		return List.copyOf(list);

	}

}
