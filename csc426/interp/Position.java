package csc426.interp;

import java.util.List;

import csc426.ast.Command;

public class Position implements StackEntry {
	public List<Command> currentCommands;
	public int nextLineNumber;

	public boolean isPosition() {
		return true;
	}
	
	public boolean isLoop() {
		return false;
	}
}
