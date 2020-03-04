package csc426.ast;

import csc426.interp.State;

public abstract class Expr extends ASTNode {
	public abstract int eval(State state);
}
