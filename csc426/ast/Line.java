package csc426.ast;

import java.util.List;

public class Line extends ASTNode {
	private int lineNum;
	private List<Command> commands;

	public Line(int lineNum, List<Command> commands) {
		this.lineNum = lineNum;
		this.commands = commands;
	}

	public int getLineNum() {
		return lineNum;
	}

	public List<Command> getCommands() {
		return commands;
	}

	public void display(String indent) {
		System.out.println(indent + "line " + lineNum);
		for (Command command : commands) {
			command.display(indent + "  ");
		}
	}
}
