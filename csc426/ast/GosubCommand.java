package csc426.ast;

import csc426.interp.State;

public class GosubCommand extends Command {
	private int lineNum;

	public GosubCommand(int lineNum) {
		this.lineNum = lineNum;
	}

	public int getLineNum() {
		return lineNum;
	}

	public void display(String indent) {
		System.out.println(indent + "gosub " + lineNum);
	}

	public boolean execute(State state) {
		state.doGosub(lineNum);
		return true;
	}
}
