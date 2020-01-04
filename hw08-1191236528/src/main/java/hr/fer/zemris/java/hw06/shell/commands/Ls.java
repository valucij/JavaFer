package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.text.SimpleDateFormat;
import java.util.*;

import hr.fer.zemris.java.hw06.shell.*;
import hr.fer.zemris.java.hw06.shell.util.Util;
/**
 * Class implements interface {@link ShellCommand}.
 * This class represents command ls. Command ls takes
 * single argument - directory- and writes a directory listing. Output is
 * formated in special. It consists of 4 columns. First column indicates if 
 * current object is directory (d), readable (r), writable (w) and 
 * executable (x). Second column contains object
 * size in bytes that is right aligned and occupies 10
 * characters. Next columns are file creation date/time, and finaly
 * file name.
 * 
 * @author Lucija Valentić
 *
 */
public class Ls implements ShellCommand{

	/**
	 * {@inheritDoc}
	 * If there is no argument, or there are more than
	 * one argument, appropriate message is written, and
	 * status continue is returned, main program
	 * doesn't terminate, but this command finishes
	 * its job.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		List<String> listArguments = Util.parseArguments(arguments);
		
		if(listArguments.size() != 1) {
		
			Util.message(env, "1", listArguments.size());
			return ShellStatus.CONTINUE;
		}
		
		File file = Util.resolvesPath(env, listArguments.get(0)).toFile();
		
		if(!file.isDirectory()) {
			env.writeln("Given file is not a directory");
			return ShellStatus.CONTINUE;
		}
		
		File[] children = file.listFiles();
		
		for(File f : children) {
			env.writeln(writeOptions(f) + " " + writeSize(f, env) + " " + writeTime(f, env) + " " + f.getName());
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * Method writes time and date that indicates
	 * time and date of creation of given file.
	 * @param f
	 * @param env
	 * @return string
	 * @throws IOException()
	 */
	private String writeTime(File f, Environment env) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Path path = Paths.get(f.getPath());
		
		BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
		
		BasicFileAttributes attributes = null;
		try {
			attributes = faView.readAttributes();
		} catch (IOException e) {
			env.writeln("Error");
			return "";
		}
		
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		return formattedDateTime;
	}

	/**
	 * Method calculates size of given file
	 * and returns string that represents that size.
	 * 
	 * @param f
	 * @param env
	 * @return size
	 */
	private String writeSize(File f, Environment env) {
		
		long forSize = f.length();
		int numberDigit = numberOfDigits(forSize);
		
		char[] number = String.valueOf(forSize).toCharArray();
		
		char[] returnNumber = new char[10];
		for(int i = 9, j = numberDigit - 1; i >= 0; i--, j--) {
			
			if(j >= 0) {
				returnNumber[i] = number[j];
			}else {
				returnNumber[i]=' ';
			}
		}
		
		return String.valueOf(returnNumber);
		
	}
	/**
	 * Method calulates how many digits are there in given
	 * number
	 * 
	 * @param number
	 * @return int
	 */
	private int numberOfDigits(long number) {
		
		int count = 0;
		
		while(number != 0) {
			number /= 10;
			count ++;
		}
		
		return count;
		
	}

	/**
	 * Method gathers some informations from given file.
	 * It returns file that has 4 values: first one is
	 * d if file is readable, r if file is readable, 
	 * w writable and x if file is exectuable. If file is 
	 * not something of that four thing, instead of letter 
	 * d/w/r/x , character '-' is written
	 * 
	 * @param f
	 * @return string
	 */
	private String writeOptions(File f) {
		
		String string = "";
		
		if(f.isDirectory()) {
			string = string + "d";
		}else {
			string = string + "-";
		}
		
		if(f.canRead()) {
			string = string + "r";
		}else {
			string = string + "-";
		}
		
		if(f.canWrite()) {
			string = string + "w";
		}else {
			string = string + "-";
		}
		
		if(f.canExecute()) {
			string = string + "x";
		}else {
			string = string + "-";
		}
		
		return string;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "ls";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		
		list.add("Command ls takes a single argument- directory");
		list.add("and writes a directory listing. It writes");
		list.add("informations about every file in given directory");
		
		return List.copyOf(list);
	}

}
