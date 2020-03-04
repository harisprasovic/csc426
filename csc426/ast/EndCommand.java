package csc426.ast;

import csc426.interp.State;

public class EndCommand extends Command {
	public void display(String indent) {
		System.out.println(indent + "end");
	}

	public boolean execute(State state) {
		return false;
	}
}
