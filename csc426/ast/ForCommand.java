package csc426.ast;

import csc426.interp.State;

public class ForCommand extends Command {
	private String id;
	private Expr from;
	private Expr to;

	public ForCommand(String id, Expr from, Expr to) {
		this.id = id;
		this.from = from;
		this.to = to;
	}

	public String getId() {
		return id;
	}

	public Expr getFrom() {
		return from;
	}

	public Expr getTo() {
		return to;
	}

	public void display(String indent) {
		System.out.println(indent + "for " + id);
		from.display(indent + "  ");
		to.display(indent + "  ");
	}

	public boolean execute(State state) {
		state.doFor(id, from.eval(state), to.eval(state));
		return true;
	}
}
