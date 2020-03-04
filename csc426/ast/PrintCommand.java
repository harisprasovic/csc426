package csc426.ast;

import java.util.List;

import csc426.interp.State;

public class PrintCommand extends Command {
	private List<Expr> exprs;

	public PrintCommand(List<Expr> exprs) {
		this.exprs = exprs;
	}

	public List<Expr> getExprs() {
		return exprs;
	}

	public void display(String indent) {
		System.out.println(indent + "print");
		for (Expr expr : exprs) {
			expr.display(indent + "  ");
		}
	}

	public boolean execute(State state) {
		boolean space = false;

		for (Expr expr : exprs) {
			if (space) {
				System.out.print(" ");
			} else {
				space = true;
			}
			System.out.print(expr.eval(state));
		}
		System.out.println();

		return true;
	}
}
