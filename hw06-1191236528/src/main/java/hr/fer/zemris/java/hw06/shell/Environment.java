package hr.fer.zemris.java.hw06.shell;

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
}



















