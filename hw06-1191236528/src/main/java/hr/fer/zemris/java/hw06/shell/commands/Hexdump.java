package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.*;
import hr.fer.zemris.java.hw06.shell.util.Util;

/**
 * Class implements interface {@link ShellCommand}. This
 * class represents command hexdump. This command expects
 * single argument: file name, and produces hex-output
 * in special form. There are three columns: first one is
 * numbers that count lines, second one are bytes from
 * given file in hex digits, and last one is actual file name. 
 * All character that are not from standard charset 
 * are not showed, but replaced with character '.' .
 * @author Lucija Valentić
 *
 */
public class Hexdump implements ShellCommand{

	/**
	 * {@inheritDoc}
	 * If there is more then one argument, 
	 * appropriate message is written, and this command 
	 * finishes it's job. It is returned ShellStatus.CONTINUE
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		String fileName = Util.parse(env, arguments, 0);
		
		if(fileName == null || fileName.isBlank() || !Paths.get(fileName).toFile().isFile()) {
			env.writeln("Invalid arguments");
			return ShellStatus.CONTINUE;
		}
		
		byte[] reading = new byte[16];
		InputStream in = null;
		InputStream helpInput = null;
		
		try {
			helpInput = Files.newInputStream(Paths.get(fileName));
			in = Files.newInputStream(Paths.get(fileName));
		} catch (IOException e) {
			throw new ShellIOException("There was an error with opening InputStream");
		}
		
		byte[] b = {0};
		byte[] text = null;
		try {
			
			text = helpInput.readAllBytes();
		} catch (IOException e2) {
			throw new ShellIOException();
		}
		
		
		try {
			while(true) {									
				
				int flag = in.read(reading);
				
				
				if(flag == -1){
					break;
				}else if(flag < 16 && text.length > reading.length) {
					
					byte[] help  = new byte[16];
					
					for(int i = 0, j = 0; i < flag; i++, j++) {
						help[j] = reading[i];
					}
					reading = help;
				}
				
				b[0] += 16;
				String hex = String.format("%02X", b[0]);
				
				String s = "";
				for(int k = 0; k < 8 - hex.length(); k++) {
					s += "0";
				}
					
				s += hex;
				
				String out = s + ": " + Util.bytetohex(reading) + supportedStringName(reading);
				env.writeln(out);
		
			}
		}catch (ShellIOException | IOException e1) {
			throw new ShellIOException();
		}
		
		try {
			in.close();
		} catch (IOException e) {
			throw new ShellIOException();
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * Method that receives string, and then returns 
	 * the exact same string, but characters that are not supported
	 * by default charset are replaced with character '.' .
	 * @param file
	 * @param i
	 * @return
	 */
	private String supportedStringName(byte[] file) {
		
		String returnString = " ";
		char[] inFileString = new String(file, Charset.defaultCharset()).toCharArray();
	
		for(int i = 0; i < inFileString.length; i++) {
			
			if( (file[i] < 32 || file[i] > 127) && file[i] != 0) {
				returnString += ".";
			}else {
		
				if(Character.isLetter(inFileString[i]) || Character.isDigit(inFileString[i]) || Character.isWhitespace(inFileString[i])) {
					returnString += String.valueOf(inFileString[i]);	
				}else {
					returnString += " ";
				}
				
			}
			
		}
		
		while(returnString.length() < 16) {
			returnString += " ";
		}
		
		return returnString;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "hexdump";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		
		List<String> list = new LinkedList<>();
		
		list.add("This command expects a single argument: file name"
				+ ", and produces hex-output. This output");
		list.add("consist of three columns. First column is counter"
				+ "second colummn is bytes from given file in hex-form");
		list.add("and third column is name of a file. All characters "
				+ "that are not supproted in standard charset are");
		list.add("replaced with character '.' .");		
		
		return List.copyOf(list);
	}

}
