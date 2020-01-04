package hr.fer.zemris.java.hw06.shell.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import hr.fer.zemris.java.hw06.shell.*;
import hr.fer.zemris.java.hw06.shell.util.Util;

/**
 * Class implements interface {@link ShellCommand}. This class
 * represents command 'dropd'. Command 'pops' element from top of the
 * stack of shared data. Current directory is left unchanged. If stack
 * is empty, appropriate message is written, and command finished with
 * work. 
 * @author Lucija Valentić
 *
 */
public class Dropd implements ShellCommand{

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		List<String> list = Util.parseArguments(arguments);
		
		if(list.size() != 0) {
			Util.message(env, "0", list.size());
			return ShellStatus.CONTINUE;
		}
		
		Stack<Object> stack = (Stack<Object>) env.getSharedData("cdstack");
		
		if(stack == null || stack.isEmpty()) {
			env.writeln("Error");
			return ShellStatus.CONTINUE;
		}
		
		stack.pop();
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "dropd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		
		list.add("Command doesn't expect any arguments. It pops"
				+ "one element on top of the shared stack. It lefts");
		list.add("current directory unchanged. If stack is empty"
				+ ", approprate message is written.");
		
		return List.copyOf(list);

	}

}
