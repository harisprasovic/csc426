package csc426.ast;

import csc426.interp.State;

public class BinOp extends Expr {
	private Expr left;
	private Op op;
	private Expr right;

	public BinOp(Expr left, Op op, Expr right) {
		this.left = left;
		this.op = op;
		this.right = right;
	}

	public Expr getLeft() {
		return left;
	}

	public Op getOp() {
		return op;
	}

	public Expr getRight() {
		return right;
	}

	public void display(String indent) {
		System.out.println(indent + "binop " + op);
		left.display(indent + "  ");
		right.display(indent + "  ");
	}

	public int eval(State state) {
		int leftValue = left.eval(state);
		int rightValue = right.eval(state);

		switch (op) {
		case ADD:
			return leftValue + rightValue;
		case SUBTRACT:
			return leftValue - rightValue;
		case MULTIPLY:
			return leftValue * rightValue;
		case DIVIDE:
			return leftValue / rightValue;
		case EQUAL:
			return (leftValue == rightValue) ? 1 : 0;
		case NOTEQUAL:
			return (leftValue != rightValue) ? 1 : 0;
		case LESS:
			return (leftValue < rightValue) ? 1 : 0;
		case GREATER:
			return (leftValue > rightValue) ? 1 : 0;
		case NOTLESS:
			return (leftValue >= rightValue) ? 1 : 0;
		case NOTGREATER:
			return (leftValue <= rightValue) ? 1 : 0;
		default:
			System.err.println("Unexpected binary operator: " + op);
			System.exit(1);
			return 0;
		}
	}
}
