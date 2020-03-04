package csc426.ast;

import csc426.interp.State;

public class GotoCommand extends Command {
	private int lineNum;

	public GotoCommand(int lineNum) {
		this.lineNum = lineNum;
	}

	public int getLineNum() {
		return lineNum;
	}

	public void display(String indent) {
		System.out.println(indent + "goto " + lineNum);
	}

	public boolean execute(State state) {
		state.doGoto(lineNum);
		return true;
	}
}
