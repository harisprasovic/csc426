package csc426.ast;

import csc426.interp.State;

public class Var extends Expr {
	private String name;

	public Var(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void display(String indent) {
		System.out.println(indent + "var " + name);
	}

	public int eval(State state) {
		return state.lookup(name);
	}
}
