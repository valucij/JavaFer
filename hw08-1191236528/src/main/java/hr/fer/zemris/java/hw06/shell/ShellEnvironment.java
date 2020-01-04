package hr.fer.zemris.java.hw06.shell;

import java.nio.file.*;
import java.util.*;
import hr.fer.zemris.java.hw06.shell.commands.*;
/**
	 * Class implements interface {@link Environment}. It represent
	 * environment in which data can be written, read, and
	 * program {@link MyShell} uses this class for input and output, and
	 * for formating input and output
	 * 
	 * @author Lucija Valentić
	 *
	 */
	public class ShellEnvironment implements Environment{

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
		 * Represents current directory from which 
		 * shell operates
		 */
		private Path currentDirectory;
		/**
		 * Represents shared data
		 */
		private Map<String, Object> map;
		/**
		 * Constructor
		 */
		public ShellEnvironment() {
			
			setCurrentDirectory(Paths.get(""));
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
			commands.put("pwd", new Pwd());
			commands.put("cd", new Cd());
			commands.put("massrename", new MassRename());
			commands.put("pushd", new Pushd());
			commands.put("listd", new Listd());
			commands.put("dropd", new Dropd());
			commands.put("popd", new Popd());
			promptSymbol = '>';
			moreLinesSymbol = '\\';
			multiLineSymbol = '|';
			map = new HashMap<String, Object>();
		}
		
		/**
		 * {@inheritDoc}
		 */
		public String readLine() throws ShellIOException {
			
			
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

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Path getCurrentDirectory() {
			
			return currentDirectory;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setCurrentDirectory(Path path) {
			
			if(path.toAbsolutePath().normalize().toFile().exists()) {
				currentDirectory = path.toAbsolutePath().normalize();
			}else {
				throw new ShellIOException();
			}
			
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object getSharedData(String key) {
		
			if(key == null || !map.containsKey(key)) {
				return null;
			}
			return map.get(key);
		}

		/**
		 * {@inheritDoc}
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void setSharedData(String key, Object value) {
			
			if(key == null) {
				throw new ShellIOException();
			}
			
			if(map.containsKey(key)) {
				
				Stack<Object> stack = (Stack<Object>) map.get(key);
				stack.push(value);
				
			}else {
				Stack<Object> stack = new Stack<>();
				stack.push(value);
				map.put(key, stack);
				
			}
			
		}
		
	}
