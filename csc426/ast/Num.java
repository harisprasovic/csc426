package csc426.ast;

import csc426.interp.State;

public class Num extends Expr {
	private int value;
	
	public Num(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void display(String indent) {
		System.out.println(indent + "num " + value);
	}

	public int eval(State state) {
		return value;
	}
}
