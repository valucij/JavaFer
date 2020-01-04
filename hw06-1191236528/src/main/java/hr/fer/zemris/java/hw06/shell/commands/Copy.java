package hr.fer.zemris.java.hw06.shell.commands;

import java.io.*;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.*;
import hr.fer.zemris.java.hw06.shell.util.Util;

/**
 * Class implements interface {@link ShellCommand}. This class
 * represents command copy. Command expects two arguments: source
 * file name (i.e. paths and names). If destination file
 * exists, question if it is allowed to write over it is asked. This
 * method works only with files. If second argument is directory, given
 * file is copied in given directory
 * 
 * @author Lucija Valentić
 *
 */
public class Copy implements ShellCommand{

	/**
	 * {@inheritDoc}
	 * 
	 * If first argument is directory, then appropriate
	 * message is written and this command terminates it's job.
	 * It returns ShellStatus.CONTINUE;
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String firstArgument = Util.parse(env, arguments, 0);
		
		if(firstArgument == null || firstArgument.isBlank()) {
			env.writeln("Invalid argument");
			return ShellStatus.CONTINUE;
		}
		
		
		if(!Paths.get(firstArgument).toFile().isFile()) {
			env.writeln("Invalid argument, " + Paths.get(firstArgument).getFileName() +" is not a file");
			return ShellStatus.CONTINUE;
		}
		
		arguments = Util.startFromNextWord(arguments);
		
		if(arguments == null) {
			env.writeln("Invalid number of elements.");
			return ShellStatus.CONTINUE;
		}
		
		String secondArgument = Util.parse(env, arguments);
		
		
		if(secondArgument == null || secondArgument.isBlank()) {
			env.writeln("Invalid number of arguments");
			return ShellStatus.CONTINUE;
		}
		
		
		
		if(Paths.get(secondArgument).toFile().isDirectory()) {
		
			String pathToNewFile = Paths.get(secondArgument).toString() + "/" +Paths.get(firstArgument).getFileName();
			Path newPath = Paths.get(pathToNewFile);
			
			if(newPath.toFile().exists()) {
				
				env.writeln(newPath.getFileName() + " already exists. Action will"
						+ " overwrite it. ");
				env.writeln("Continue? Enter 'yes' or 'no' ");
				
				String answer = env.readLine();
				
				if(answer.equals("no")) {
					return ShellStatus.CONTINUE;
				}
				
			}
			
			writingInFile(Paths.get(firstArgument), newPath);
			
		}else {
			
			File fileToWrite = Paths.get(secondArgument).toFile();
			
			if(fileToWrite.exists()) {
				
				if(fileToWrite.isFile()) {
					
					env.writeln(fileToWrite.getName() + " already exists. Action will"
							+ " overwrite it. Continue? Enter 'yes' or 'no' ");
					
					String answer = env.readLine();
					
					if(answer.equals("no")) {
						return ShellStatus.CONTINUE;
					}
					
					writingInFile(Paths.get(firstArgument), Paths.get(secondArgument));
					
				}else {
					env.writeln(fileToWrite.getName() + " is not a file");
					return ShellStatus.CONTINUE;
				}
				
			}else {
				
				writingInFile(Paths.get(firstArgument), Paths.get(secondArgument));
				
			}
			
		}
		
		return ShellStatus.CONTINUE;
		
	}

	/**
	 * Private method that receives two paths, one
	 * for InputStream, and one for OutputStream. Job of this function
	 * is to write from given file inPath to given file outPath
	 * 
	 * @param inPath
	 * @param outPath
	 */
	private void writingInFile(Path inPath, Path outPath) {
		

		try ( InputStream in = Files.newInputStream(inPath); 
				OutputStream out = Files.newOutputStream(outPath)){
			out.write(in.readAllBytes());;
			
		} catch (IOException e) {
			throw new ShellIOException("Reading/writing not completed");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "copy";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		
		List<String> list = new LinkedList<>();
		
		list.add("Command expects two arguments: source file name "
				+ "and destination file name.");
		list.add("If destination file exists, you should ask user is it"
				+ "allowed to overwrite it. If the second argument is");
		list.add(" a directory, original file then copys into that directory"
				+ " using original file name.");
		
		return List.copyOf(list);
	}

}
