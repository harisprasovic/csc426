package csc426.ast;

import java.util.List;

import csc426.interp.FunVal;
import csc426.interp.State;

public class DefCommand extends Command {
	private String name;
	private List<String> params;
	private Expr body;

	public DefCommand(String name, List<String> params, Expr body) {
		this.name = name;
		this.params = params;
		this.body = body;
	}

	@Override
	public boolean execute(State state) {
		state.bindfn(name, new FunVal(params, body));
		return true;
	}

	@Override
	public void display(String indent) {
		System.out.println(indent + "def " + name);
		for (String param : params) {
			System.out.println(indent + "  " + param);
		}
		body.display(indent + "  ");
	}

}
