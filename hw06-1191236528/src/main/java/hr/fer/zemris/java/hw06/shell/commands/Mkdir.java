package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.*;
import hr.fer.zemris.java.hw06.shell.util.Util;
/**
 * Class implements interface {@link ShellCommand}. This
 * class represents command mkdir. This method
 * expects one single argument: directory name, and creates 
 * appropriate directory structure.
 * 
 * @author Lucija Valentić
 *
 */
public class Mkdir implements ShellCommand{

	/**
	 * {@inheritDoc}
	 * If there is more then one given argument, or
	 * given argument is not directory, then appropriate
	 * message is written, and command terminates it's job.
	 * ShellStatumks.CONTINUE is returned.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String directoryName = Util.parse(env, arguments);
		
		if(directoryName == null || directoryName.isBlank()) {
			env.writeln("Invalid arguments");
			return ShellStatus.CONTINUE;
		}
		/*
		int i = directoryName.lastIndexOf('.');
		
		if( i != -1) {
			String check = directoryName.substring(i);
			
			if(check.matches(".*")) {
				env.writeln("Invalid arguments");
				return ShellStatus.CONTINUE;
			}
		}
		*/
		boolean isMadeDirectory = Paths.get(directoryName).toFile().mkdirs();
		
		if(isMadeDirectory) {
			env.writeln("Success.");
		}else {
			env.writeln("Something went wrong");
		} 
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "mkdir";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		
		List<String> list = new LinkedList<>();
		
		list.add("This command takes a single argument: directory name");
		list.add(" and creates appropriate directory structure");
		
		return List.copyOf(list);
	}

}
