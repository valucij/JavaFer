package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw06.shell.*;
import hr.fer.zemris.java.hw06.shell.util.*;

public class MassRename implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		List<String> list = Util.parseArguments(arguments);
		
		
		if(list.size() < 4 || list.size() > 5) {
			Util.message(env, "at least 4, but no more then 5", list.size());
			return ShellStatus.CONTINUE;
		}
		
		String dir1 = list.get(0);
		String dir2 = list.get(1);
		String cmd = list.get(2);
		String mask = list.get(3);
		String other = null;
		
		if(list.size() == 5 && (!cmd.equals("show") && !cmd.equals("execute"))) {
			env.writeln("Error, wrong arguments");
			return ShellStatus.CONTINUE;
		}else if(list.size() == 5){
			other = list.get(4);
		}
		
		if(!Paths.get(dir1).toFile().isDirectory() || !Paths.get(dir2).toFile().isDirectory()) {
			env.writeln("Given arguments for directories are not directories");
			return ShellStatus.CONTINUE;
		}
		
		List<FilterResult> files;
		try {
			files = filter(Paths.get(dir1), mask);
		} catch (IOException e1) {
			env.writeln("Invalid arguments");
			return ShellStatus.CONTINUE;
		}
		NameBuilderParser parser = new NameBuilderParser(other);
		NameBuilder builder = parser.getNameBuilder();		
		
		switch(cmd) {
		
		case "filter":
		
			for(FilterResult result : files) {
				env.writeln(result.group(0));
			}
			
			return ShellStatus.CONTINUE;
		case "groups":
		
			for(FilterResult result : files) {
				
				StringBuilder sb = new StringBuilder();
				
				sb.append(result.group(0));

				for(int i = 0, n = result.numberOfGroups(); i <= n; i++) {
					sb.append(" ");
					sb.append(String.valueOf(i));
					sb.append(": ");
					sb.append(result.group(i));
				}
				env.writeln(sb.toString());
			}
			
			return ShellStatus.CONTINUE;
		case "execute":
			

			for(FilterResult result : files) {
				StringBuilder sb = new StringBuilder();
				
				try {
					builder.execute(result, sb);	
				}catch(ShellIOException ex) {
					env.writeln("Invalid arguments, couldn't execute");
					return ShellStatus.CONTINUE;
				}
				
				String newName = sb.toString();
				
				try {
					
					Files.move(Paths.get(dir1 + "/" + result.group(0)), Paths.get(dir2 + "/" + newName));
				} catch (IOException e) {
					env.writeln("Invalid arguments, couldn't move");
					return ShellStatus.CONTINUE;
				}
			}
			
			return ShellStatus.CONTINUE;
		case "show":
			
			for(FilterResult result : files) {
				StringBuilder sb = new StringBuilder();

				try {
					builder.execute(result, sb);	
				}catch(ShellIOException ex) {
					env.writeln("Invalid arguments, couldn't execute");
					return ShellStatus.CONTINUE;
				}
				
				String newName = sb.toString();
				
				env.writeln(result.group(0) + " => " + newName);
			}
			
			return ShellStatus.CONTINUE;	
		}
		
		env.writeln("Wrong arguments");
		return ShellStatus.CONTINUE;
		
	}

	@Override
	public String getCommandName() {
		return "massrename";
	}

	@Override
	public List<String> getCommandDescription() {

		List<String> list = new LinkedList<>();
		
		list.add("");
		list.add("");
		
		return List.copyOf(list);	

	}
	
	private static List<FilterResult> filter(Path dir, String pattern) throws IOException{
		
		File[] files = dir.toFile().listFiles();
		List<FilterResult> list = new LinkedList<>();
		
		for(File file : files) {
			
			if(Pattern.matches(pattern, file.getName())) {
				list.add(new FilterResult(file.getName(), pattern));
			}
		}
		
		return list;
	}

}
