package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.*;
import java.util.*;
import hr.fer.zemris.java.hw06.shell.*;
import hr.fer.zemris.java.hw06.shell.util.*;
/**
 * Class that implements interface {@link ShellCommand}. This class
 * represents command cat. This command takes one or two arguments. The first
 * argument is path to some file and is mandatory. The second argument
 * is charset name that should be used to interpret chars from bytes.
 * If not provided, a default platform charset should be used. Command 
 * opens given file and writes its content to console. If invalid numbers
 * of arguments are given, or invalid charset name is given, appropriate
 * message is written, and this command stops with its work (attention
 * this doesn't mean that main program is terminated).
 * 
 * @author Lucija Valentić
 *
 */
public class Cat implements ShellCommand{

	/**
	 * {@inheritDoc}
	 * This command expects just one or two arguments.
	 * If more than two arguments are given, or charset name is 
	 * invalid, appropriate message is written and it returns 
	 * ShellStatus.CONTINUE. If number of argument is valid, 
	 * but arguments are invalid, everything is like 
	 * described above. Basically, exceptions are managed with
	 * appropriate messages and message to continue the main
	 * program is sent.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		List<String> listArguments = Util.parseArguments(arguments);
		
		if(listArguments.size() < 1 || listArguments.size() > 2) {
			Util.message(env, "1 or 2", listArguments.size());
			return ShellStatus.CONTINUE;
		}
		
		Charset charset = null;
		
		if(listArguments.size() == 2) {
			
			try {
				charset = Util.parseCharset(env, listArguments.get(1));	
			}catch(UnsupportedCharsetException ex) {
				env.writeln("Invalid charset.");
				return ShellStatus.CONTINUE;
			}
		}
		Path path = Util.resolvesPath(env, listArguments.get(0));
		
		if(!path.toFile().exists()) {
			env.writeln("Invalid argument, given file doesn't exist.");
			return ShellStatus.CONTINUE;
		}else if(!path.toFile().isFile() ) {
			env.writeln("Invalid argument, given path is not a file.");
			return ShellStatus.CONTINUE;
		}
		
		
		InputStream in = null;
		
		try {
			in = Files.newInputStream(path);
		} catch (IOException e) {
			env.writeln("Invalid file path. Cannot find file %s" + path);
			return ShellStatus.CONTINUE;
		}
			
		byte[] reading = new byte[1024];
			
		try {
			
			while(in.read(reading) != -1) {
					
				String out;
				
				if(charset != null) {
					out = new String(reading, charset);
				}else {
					out = new String(reading, Charset.defaultCharset());
				}	
					
				
				env.writeln(out);
			}
			
		} catch (ShellIOException | IOException e) {
			env.writeln("Error");
			return ShellStatus.CONTINUE;
		}
			
		try {
			//env.writeln("");
			in.close();
		} catch (IOException e) {
			env.writeln("Error");
			return ShellStatus.CONTINUE;
		}
			
		return ShellStatus.CONTINUE;
			
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "cat";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		
		List<String> list = new LinkedList<>();
		
		list.add("Takes one or two arguments. The first argument");
		list.add("is path to some file and is mandatory. The second");
		list.add("argument is charset name that should be used to interpret chars from bytes");
		list.add("If not provided, a default platform charset should be used");
		
		return List.copyOf(list);
	}

}
