package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.util.SortedMap;
/**
 * Interface that represents some environment. It can be 
 * implemented ad some concrete environment and used while
 * handling some program, for example program {@link MyShell}
 * 
 * @author Lucija Valentić
 *
 */
public interface Environment {

	/**
	 * Method reads one whole line of input. It is 
	 * similar as method nextLine() in Scanner
	 * @return string
	 * @throws ShellIOException if line cannot be read
	 */
	public String readLine() throws ShellIOException;
	/**
	 * Method writes in command line given text. It doesn't 
	 * terminates the line
	 * @param text
	 * @throws ShellIOException if something goes wrong
	 */
	public void write(String text) throws ShellIOException;
	/**
	 * Method writes in command line given text. It does 
	 * terminate the line
	 * @param text
	 * @throws ShellIOException if something goes wrong
	 */
	public void writeln(String text) throws ShellIOException;
	/**
	 * Returns sorted map consisting of command available in 
	 * environment. Returned map is unchangable
	 * @return SortedMap
	 */
	public SortedMap<String, ShellCommand> commands();
	/**
	 * Returns symbol for multiline from this environment
	 * @return character
	 */
	public Character getMultilineSymbol();
	/**
	 * Sets symbol for multiline for this environment with given 
	 * symbol
	 * @param symbol
	 */
	public void setMultilineSymbol(Character symbol);
	/**
	 * Returns symbol for prompt from this environment
	 * @return character
	 */
	public Character getPromptSymbol();
	/**
	 * Sets symbol for prompt for this environment with given 
	 * symbol
	 * @param symbol
	 */
	public void setPromptSymbol(Character symbol);
	/**
	 * Returns symbol for morelines from this environment
	 * @return character
	 */
	public Character getMorelinesSymbol();
	/**
	 * Sets symbol for morelines for this environment with given 
	 * symbol
	 * @param symbol
	 */
	public void setMorelinesSymbol(Character symbol);
	/**
	 * Method returns current directory, where shell
	 * is currently working. This directory doesn't have
	 * to be one from which java program is started
	 * @return this.currentDirectory
	 */
	public Path getCurrentDirectory();
	
	/**
	 * Sets current directory with given path, the one from which
	 * shell will be working, that directory doesn't have
	 * to be the one from which was java program started
	 * Given path is checked if it exists. If it doesn't, it
	 * does nothing.
	 * @param path to the directory
	 */
	public void setCurrentDirectory(Path path);
	
	/**
	 * Returns stack that is mapped with given key. 
	 * If given key is invalid, or is null, null is returned.
	 * Stack represents shared data, that is shared among shell commands
	 * @param key
	 * @return object
	 */
	public Object getSharedData(String key);
	
	/**
	 * Sets shared data that is shared among the commands. More specifically,
	 * it maps stack with value on top of it, to the key. If something
	 * is already mapped to a key, value is just put on top; other wise
	 * new 'input' is made.
	 * @param key
	 * @param value
	 */
	public void setSharedData(String key, Object value);
}



















