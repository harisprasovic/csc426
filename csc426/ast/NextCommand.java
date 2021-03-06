package csc426.ast;

import csc426.interp.State;

public class NextCommand extends Command {
	public void display(String indent) {
		System.out.println(indent + "next");
	}

	public boolean execute(State state) {
		state.doNext();
		return true;
	}
}
