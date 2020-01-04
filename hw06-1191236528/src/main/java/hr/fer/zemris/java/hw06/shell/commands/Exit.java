package hr.fer.zemris.java.hw06.shell.commands;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class Exit implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.isBlank()) {
			env.writeln("Error");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		
		list.add("It doesn't expect any arguments.");
		list.add("When entered, it terminates the program.");
		list.add("If arguments are given, then appropriate message");
		list.add("is written, and program expects new input.");
		
		return List.copyOf(list);
	}

}
