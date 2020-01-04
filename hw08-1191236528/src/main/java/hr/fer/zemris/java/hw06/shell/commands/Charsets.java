package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.*;
import hr.fer.zemris.java.hw06.shell.*;
import hr.fer.zemris.java.hw06.shell.util.Util;
/**
 * Class that implements interface {@link ShellCommand}.
 * This command represents command charsets. This command
 * takes no arguments and lists names of supported charsets for
 * current Java platform.
 * 
 * @author Lucija Valentić
 *
 */
public class Charsets implements ShellCommand{

	/**
	 * {@inheritDoc}
	 * If there are some arguments, then appropriate message is written
	 * and this command finishes its job (that doesn't mean that
	 * main program terminates). It returns ShellStatus.CONTINUE 
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		SortedMap<String, Charset> available = Charset.availableCharsets();
		
		List<String> listArguments = Util.parseArguments(arguments);
		
		if(listArguments.size() != 0) {
			Util.message(env, "0", listArguments.size());
			return ShellStatus.CONTINUE;
		}
		
		env.writeln("List of charsets supported on this java platform:");
		for(String key : available.keySet()) {
			env.writeln(key);
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "charsets";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		
		List<String> list = new LinkedList<>();
		
		list.add("Takes no arguments, and then lists names of");
		list.add("supported charsets for Java platform");
		
		return List.copyOf(list);
	}

}
