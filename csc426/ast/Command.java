package csc426.ast;

import csc426.interp.State;

public abstract class Command extends ASTNode {
	public abstract boolean execute(State state);
}
