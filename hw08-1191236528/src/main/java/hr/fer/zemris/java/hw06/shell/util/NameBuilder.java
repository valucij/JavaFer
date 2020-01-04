package hr.fer.zemris.java.hw06.shell.util;

import hr.fer.zemris.java.hw06.shell.commands.MassRename;

/**
 * Interface that is used for building a name of some file. 
 * When using {@link MassRename} command, sometimes user wants to 
 * rename files in special way, and then we use this interface, that
 * can be implemented to help user in creating that name any way he 
 * wants it
 * @author Lucija Valentić
 *
 */
public interface NameBuilder {

	/**
	 * Method pushes into given {@link StringBuilder} informations
	 * from given result. Each implementation of this interface dictates
	 * how exactly this method will play out. It can push into
	 * given sb just parts of result, meaning just one group, 
	 * or the whole string (whole file name) 
	 * @param result
	 * @param sb
	 */
	public void execute(FilterResult result, StringBuilder sb);
}
