package csc426.ast;

import java.util.List;

import csc426.interp.State;

public class IfCommand extends Command {
	private Expr test;
	private List<Command> commands;

	public IfCommand(Expr test, List<Command> commands) {
		this.test = test;
		this.commands = commands;
	}

	public Expr getTest() {
		return test;
	}

	public List<Command> getCommands() {
		return commands;
	}

	public void display(String indent) {
		System.out.println(indent + "if");
		test.display(indent + "  ");
		for (Command command : commands) {
			command.display(indent + "  ");
		}
	}

	public boolean execute(State state) {
		if (test.eval(state) != 0) {
			state.setCurrentCommands(commands);
		}
		return true;
	}
}
