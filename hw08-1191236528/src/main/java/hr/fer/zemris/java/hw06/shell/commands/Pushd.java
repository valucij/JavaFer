package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.util.Util;

/**
 * Class implements interface {@link ShellCommand}. This class
 * represents command 'pushd'. 
 * This command expects one argument. When called, it pushed
 * current directory on stack, and sets current directory
 * to the one given with argument. GIven path has to be valid
 * or nothing will happen. Of course
 * because previous current directory is pushed
 * to the stack, it is saved.
 *		
 * @author Lucija Valentić
 *
 */
public class Pushd implements ShellCommand{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		List<String> list = Util.parseArguments(arguments);
		
		if(list.size() != 1) {
			Util.message(env, "1", list.size());
			return ShellStatus.CONTINUE;
		}
		
		Path path = Paths.get(list.get(0)).toAbsolutePath().normalize();
		
		
		if(!path.toFile().exists()) {
			env.writeln("Given direcotry doesn't exist");
			return ShellStatus.CONTINUE;
		}
		
		env.setSharedData("cdstack", env.getCurrentDirectory());
		
		env.setCurrentDirectory(path);
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "pushd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		
		List<String> list = new LinkedList<>();
		
		list.add("This command expects one argument. When called, it pushed"
				+ "current directory on stack, and sets current directory");
		list.add("to the one given with argument. GIven path has to be valid"
				+ "or nothing will happen. Of course");
		list.add("because previous current directory is pushed"
				+ "to the stack, it is saved.");
		
		return List.copyOf(list);

	}
	
	

}
