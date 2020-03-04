package csc426.ast;

import csc426.interp.State;

public class ReturnCommand extends Command {
	public void display(String indent) {
		System.out.println(indent + "return");
	}

	public boolean execute(State state) {
		state.doReturn();
		return true;
	}
}
