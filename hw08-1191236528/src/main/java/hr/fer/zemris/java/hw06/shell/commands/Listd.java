package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.util.Util;

/**
 * Class implements interface {@link ShellCommand}. Class represents
 * command 'listd'. In terminal, it writes out all paths
 * that are on the stack, starting with the top one. Stack from
 * which those paths are read is stack representing shared data
 * @author Lucija Valentić
 *
 */
public class Listd implements ShellCommand{

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
		
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
		
		if(stack == null || stack.isEmpty()) {
			env.writeln("Nema pohranjenih direktorija");
			return ShellStatus.CONTINUE;
		}
		
		for(Path path : stack) {
			env.writeln(path.toString());
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "listd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {

		List<String> list = new LinkedList<>();
		
		list.add("In terminal, it writes out all paths" + 
				" that are on the stack, starting with the top one. ");
		
		return List.copyOf(list);	
	}

}
