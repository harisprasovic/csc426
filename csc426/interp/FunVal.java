package csc426.interp;

import java.util.List;

import csc426.ast.Expr;

public class FunVal {
	private List<String> params;
	private Expr body;

	public FunVal(List<String> params, Expr body) {
		this.params = params;
		this.body = body;
	}

	public int eval(List<Integer> vals, State state) {
		if (vals.size() != params.size()) {
			System.err.println("Invalid number of arguments");
			System.exit(1);
		}
		state.enter();
		for (int i = 0; i < vals.size(); i++) {
			String id = params.get(i);
			int val = vals.get(i);
			state.bind(id, val);
		}
		int result = body.eval(state);
		state.exit();
		return result;
	}

}
