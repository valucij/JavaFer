package hr.fer.zemris.java.hw06.shell;

import java.util.*;

import hr.fer.zemris.java.hw06.shell.commands.*;

/**
 * Class represents one shell. It tries to mimic
 * what terminal does. It can read data from files,
 * copy files, make directories, get informations
 * from directory's files and more. Also, this shell
 * has it's own environment, so input and output are
 * formated in special way, and handled with different
 * functions other than System.out functions. When
 * sending paths, user cannot mix symbols '/' and
 * '\'. Path has to be written only with '/' or with '\'.
 * Next example shows how to use this class.
 * 
 * <pre>
 * 
 * First example:
 * 
 * OUTPUT: Welcome to MyShell v 1.0
 * OUTPUT: > 
 * INPUT: symbol PROMPT
 * OUTPUT: Symbol for PROMPT is '>'
 * OUTPUT: >
 * INPUT: symbol PROMPT #
 * OUTPUT: Symbol for PROMPT changed from '>' to '#'
 * OUTPUT:#
 * INPUT: symbol \
 * OUTPUT: |
 * INPUT: MORELINES \
 * OUTPUT: |
 * INPUT: !
 * OUTPUT: Symbol for MORELINES changed from '\' to '!'
 * OUTPUT: # 
 * INPUT: symbol !
 * OUTPUT: |
 * INPUT: MORELINES
 * OUTPUT: Symbol for MORELINES is '!'
 * OUTPUT: #
 * INPUT: symbol MULTILINE
 * OUTPUT:Symbol for MULTILINE is '|'	
 * OUTPUT: #
 * INPUT: exit
 * OUTPUT: Goodbye!
 * (program terminates)
 * 
 * 
 * Second example:
 * 
 * INPUT: ls test
 * OUTPUT:
 * drwx       4096 2019-04-16 17:15:53 test2
 * -rw-       3308 2019-04-16 17:15:32 test1.txt
 * -rw-         14 2019-04-16 17:11:33 test.txt
 * 
 * Third example:
 * 
 * INPUT: charsets
 * OUTPUT:
 * Big5
 * Big5-HKSCS
 *	.
 * 	.
 * 	.
 * UTF-8
 * 	.
 * 	.
 * 	.
 * x-windows-iso2022jp
 * 
 * </pre>
 * 
 * @author Lucija valentić
 *
 */
public class MyShell {

	/**
	 * Represents given environment which will be
	 * used when handling this shell
	 */
	public static Environment environment;
	/**
	 * represent status of a shell
	 */
	public static ShellStatus status;
	
	/**
	 * Main method called in the beginning of a program
	 * @param args arguments of command line
	 */
	public static void main(String[] args) {
		
		environment = new ShellEnvironment();
		status = ShellStatus.CONTINUE;
		
		environment.writeln("Welcome to MyShell v 1.0");
		
		while(status.equals(ShellStatus.CONTINUE)) {
		
			String lines = readLineOrLines(environment);
			String commandName = extractCommandName(lines);
			String arguments = extractArguments(lines);
			ShellCommand command = environment.commands().get(commandName);
			
			if(command == null) {
				environment.writeln("Command doesn't exist.");
			}else {
				status = command.executeCommand(environment, arguments);
				
			}
				
		}

		environment.writeln("Goodbye!");
	}
	
	/**
	 * Extracts arguments from string. Usually, arguments are all words
	 * after first one. After first word, all arguments are just
	 * written in one string and that string is returned. Later
	 * other methods extract single arguments if needed.
	 * 
	 * @param string
	 * @return string
	 */
	private static String extractArguments(String string) {
		
		char[] chars = string.toCharArray();
		
		int i = nextNotBlank(string, 0);
		int j = nextBlank(string, i);
		i = nextNotBlank(string, j);
		
		if(i == chars.length) {
			return "";
		}
		
		return string.substring(i);
		
	}

	/**
	 * Returns position of next character from given string that is blank. 
	 * Calculating starts from given offset.
	 * @param string
	 * @param offset
	 * @return int
	 */
	private static int nextBlank(String string, int offset) {
		
		char[] chars = string.toCharArray();
		int i = offset;
		
		while(i < chars.length && !Character.isWhitespace(chars[i])) {
			i++;
		}
		
		return i;
	}
	
	/**
	 * Returns position of next character from given string that is not blank. 
	 * Calculating starts from given offset.
	 * @param string
	 * @param offset
	 * @return int
	 */
	private static int nextNotBlank(String string, int offset) {
		
		char[] chars = string.toCharArray();
		int i = offset;
		
		while(i < chars.length && Character.isWhitespace(chars[i])) {
			i++;
		}
		
		return i;
	}
	
	/**
	 * Extracts command name from given string.
	 * Usually command name is first one. If given string is blank
	 * then returned string is also empty.
	 * 
	 * @param string
	 * @return string
	 */
	private static String extractCommandName(String string) {
		
		char[] chars = string.toCharArray();
		int i = nextNotBlank(string, 0);
		int j = nextBlank(string, i);
		
		if(i == string.length()) {
			return " ";
		}
		
		if(j == chars.length) {
			string = string.substring(i);
		}
		
		return string.substring(i, j);
		
		
	}

	/**
	 * Method reads one or more lines from input. Usually, method
	 * reads only one line, unless last character, and that character
	 * must be surrounded with blank, is special symbol that represents
	 * that more lines will follow and that those lines should be read
	 * @param env
	 * @return string
	 */
	private static String readLineOrLines(Environment env) {
		
		env.write(String.valueOf(env.getPromptSymbol()));
		env.write(" ");
		String returnString = "";
		
		while(true) {
		
			String string = env.readLine();
			
			if(string.isEmpty()) {
				return string;
			}
			
			char lastCharacter = last(string);
			
			
			if(lastCharacter == env.getMorelinesSymbol()) {
				
				string = string.substring(0, string.lastIndexOf(lastCharacter));
			
				returnString = returnString + string;		
			
				env.write(String.valueOf(env.getMultilineSymbol()));
			}else {
				
				if(returnString.equals(" ")) {
					return string;
				}else {
					return returnString + string;
				}
			}
		}
		
		
		
	}
		
	/**
	 * Method returns last character in (surrounded with blanks)
	 * in given string
	 * 
	 * @param string
	 * @return char
	 */
	private static char last(String string) {
		
		char[] chars = string.toCharArray();
		
		for(int i = chars.length - 1; i > 0; i--) {
			
			if(!Character.isWhitespace(chars[i]) && Character.isWhitespace(chars[i - 1])) {
			//	System.out.println("last u lastu ." + String.valueOf(chars[i]) + "is white space= " + Character.isWhitespace(chars[i]));
				return chars[i];
			}
		}
		
		if(!Character.isWhitespace(chars[0])) {
			return chars[0];
		}
		
		return ' ';
	}

	/**
	 * Class implements interface {@link Environment}. It represent
	 * enviroment in which data can be written, read, and
	 * program {@link MyShell} uses this class for intput and output, and
	 * for formating input and output
	 * 
	 * @author Lucija Valentić
	 *
	 */
	public static class ShellEnvironment implements Environment{

		/**
		 * Scanner with what this environment can read input
		 */
		public Scanner sc;
		/**
		 * sorted map of all command available in environment
		 */
		private SortedMap<String, ShellCommand> commands;
		/**
		 * symbol for PROMPT
		 */
		private char promptSymbol;
		/**
		 * symbol for MORELINES
		 */
		private char moreLinesSymbol;
		/**
		 * symbol for MULTILINE
		 */
		private char multiLineSymbol;
		
		/**
		 * Constructor
		 */
		public ShellEnvironment() {
			
			sc = new Scanner(System.in);
			commands = new TreeMap<>();
			commands.put("charsets", new Charsets());
			commands.put("cat", new Cat());
			commands.put("ls", new Ls());
			commands.put("tree", new Tree());
			commands.put("copy", new Copy());
			commands.put("mkdir", new Mkdir());
			commands.put("hexdump", new Hexdump());
			commands.put("help", new Help());
			commands.put("exit", new Exit());
			commands.put("symbol", new Symbol());
			promptSymbol = '>';
			moreLinesSymbol = '\\';
			multiLineSymbol = '|';
		}
		
		/**
		 * {@inheritDoc}
		 */
		public String readLine() throws ShellIOException {
			
			//System.out.println("Ušlo je");
			String string = "";
		
	
			if(sc.hasNext()) {
				
				string = sc.nextLine();
			}
			
			return string;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void write(String text) throws ShellIOException {
			System.out.printf("%s", text);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void writeln(String text) throws ShellIOException {
			System.out.println(text);
			
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public SortedMap<String, ShellCommand> commands() {
		
			return Collections.unmodifiableSortedMap(commands);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Character getMultilineSymbol() {
			return multiLineSymbol;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setMultilineSymbol(Character symbol) {
			multiLineSymbol = symbol;
			
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setPromptSymbol(Character symbol) {
			promptSymbol = symbol;
			
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Character getMorelinesSymbol() {
			return moreLinesSymbol;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setMorelinesSymbol(Character symbol) {
			moreLinesSymbol = symbol;
			
		}
		
	}

}
