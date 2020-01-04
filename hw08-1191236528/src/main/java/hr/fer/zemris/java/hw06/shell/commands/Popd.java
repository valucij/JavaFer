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
 * Class implements interface {@link ShellCommand}. It represents
 * command 'popd'. It expects no argument. It pops/removes from
 * the stack path that is on top of the stack, and sets current 
 * directory to that path, of course if that path is valid/if it
 * still exists. If stack is empty, appropriate message is written
 * and command finishes with work.
 * @author Lucija Valentić
 *
 */
public class Popd implements ShellCommand{

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
		
		Path path = (Path) stack.peek();
		stack.pop();
		
		if(path.toFile().exists()) {
			env.setCurrentDirectory(path);
		}
		
		return ShellStatus.CONTINUE;
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "popd";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {

		List<String> list = new LinkedList<>();
		
		list.add(" It expects no argument. It pops/removes from" + 
				" the stack path that is on top of the stack, and sets current ");
		list.add( "directory to that path, of course if that path is valid/if it" + 
				" still exists.");
		
		return List.copyOf(list);	

	}

}
