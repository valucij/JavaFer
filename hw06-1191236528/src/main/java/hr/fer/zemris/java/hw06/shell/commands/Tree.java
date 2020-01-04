package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.*;
import hr.fer.zemris.java.hw06.shell.util.Util;

/**
 * Class implements interface {@link ShellCommand}. This class
 * represents command tree. Command expects a single argument:
 * directory name, and then prints a tree
 * 
 * @author Lucija Valentić
 *
 */
public class Tree implements ShellCommand{

	/**
	 * {@inheritDoc}
	 * If there is no arguments, or number of arguments
	 * are above one, then appropriate message is written, and
	 * this command terminates it's job. It returns ShellStatus.Continue
	 * in that case.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String directory = Util.parse(env, arguments, 0);
		
		if(directory == null || directory.isBlank() || !Paths.get(directory).toFile().isDirectory()) {
			env.writeln("Invalid argument");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Files.walkFileTree(Paths.get(directory), new TreeVisit(env));
		} catch (IOException e) {
			env.writeln("There was a mistake with going through directories");
		}
		
		return ShellStatus.CONTINUE;
		
	}

	/**
	 * Private class that implements interface {@link FileVisitor}. 
	 * It is used for command {@link Tree} when writing out
	 * directory structure.
	 * 
	 * @author Lucija Valentić
	 *
	 */
	private static class TreeVisit implements FileVisitor<Path>{

		private int level = 0;
		private Environment env;
		
		public TreeVisit(Environment environment) {
			env = environment;
		}
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			env.writeln("-".repeat(level * 2) + dir.getFileName());
			level++;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			env.writeln("-".repeat(level * 2) + file.getFileName());
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			throw new ShellIOException();
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level--;
			return FileVisitResult.CONTINUE;
		}
		
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "tree";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		
		List<String> list = new LinkedList<>();
		
		list.add("This command expects a single argument: a directory"
				+ " name and prints a tree.");
		list.add("This tree shows structure of given directory");
		
		return List.copyOf(list);
	}

}
