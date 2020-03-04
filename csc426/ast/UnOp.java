package csc426.ast;

import csc426.interp.State;

public class UnOp extends Expr {
	private Op op;
	private Expr expr;

	public UnOp(Op op, Expr expr) {
		this.op = op;
		this.expr = expr;
	}

	public Op getOp() {
		return op;
	}

	public Expr getExpr() {
		return expr;
	}

	public void display(String indent) {
		System.out.println(indent + "unop " + op);
		expr.display(indent + "  ");
	}

	public int eval(State state) {
		switch (op) {
		case ADD:
			return expr.eval(state);
		case SUBTRACT:
			return -expr.eval(state);
		default:
			System.err.println("Unexpected unary operator: " + op);
			System.exit(1);
			return 0;
		}
	}
}
