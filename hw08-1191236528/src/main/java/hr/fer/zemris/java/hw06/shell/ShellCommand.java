package hr.fer.zemris.java.hw06.shell;

import java.util.List;
/**
 * Interface that can be implemented in the form
 * of some kind of commands. These commands can be used 
 * for performing stuff for user - they can e.g. copy
 * files, write content of some files, etc.
 * 
 * @author Lucija Valentić
 *
 */
public interface ShellCommand {

	/**
	 * Based on environment and given arguments, this command
	 * executes its job. It returns {@link ShellStatus}, and
	 * that status depend upon command.
	 * 
	 * @param env
	 * @param arguments
	 * @return status
	 */
	public ShellStatus executeCommand(Environment env, String arguments);
	/**
	 * Returns name of a command.
	 * @return string
	 */
	public String getCommandName();
	/**
	 * Returns list of string, which represent
	 * description of a command
	 * @return list of strings
	 */
	public List<String> getCommandDescription();
}
