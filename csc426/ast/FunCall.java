package csc426.ast;

import java.util.ArrayList;
import java.util.List;

import csc426.interp.FunVal;
import csc426.interp.State;
import csc426.syntax.Token;

public class FunCall extends Expr {
	private Token name;
	private List<Expr> args;

	public FunCall(Token name, List<Expr> args) {
		this.name = name;
		this.args = args;
	}

	@Override
	public int eval(State state) {
		FunVal fv = state.lookupfn(name.lexeme);
		if (fv == null) {
			System.err.println("Undefined function " + name);
			System.exit(1);
		}
		List<Integer> vals = new ArrayList<>();
		for (Expr arg : args) {
			vals.add(arg.eval(state));
		}
		return fv.eval(vals, state);
	}

	@Override
	public void display(String indent) {
		System.out.println(indent + "call " + name.lexeme);
		for (Expr arg : args) {
			arg.display(indent + "  ");
		}
	}

}
