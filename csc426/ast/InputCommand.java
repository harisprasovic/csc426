package csc426.ast;

import csc426.interp.State;

public class InputCommand extends Command {
	private String id;

	public InputCommand(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void display(String indent) {
		System.out.println(indent + "input " + id);
	}

	public boolean execute(State state) {
		System.out.print("? ");
		System.out.flush();
		int response = state.readInt();
		state.bind(id, response);
		return true;
	}
}
