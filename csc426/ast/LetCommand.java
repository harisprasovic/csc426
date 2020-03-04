package csc426.ast;

import csc426.interp.State;

public class LetCommand extends Command {
	private String lhs;
	private Expr rhs;

	public LetCommand(String lhs, Expr rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	public String getLhs() {
		return lhs;
	}

	public Expr getRhs() {
		return rhs;
	}

	public void display(String indent) {
		System.out.println(indent + "let " + lhs);
		rhs.display(indent + "  ");
	}

	public boolean execute(State state) {
		state.bind(lhs, rhs.eval(state));
		return true;
	}
}
