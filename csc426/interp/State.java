package csc426.interp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeMap;

import csc426.ast.Command;
import csc426.ast.Line;
import csc426.ast.Program;

public class State {
	private NavigableMap<Integer, List<Command>> prog;
	private Stack<Map<String, Integer>> vtable;
	private Map<String, FunVal> ftable;
	private Position position;
	private Stack<StackEntry> stack;
	private Scanner input;

	public State(Program program) {
		prog = new TreeMap<>();
		for (Line line : program.getLines()) {
			prog.put(line.getLineNum(), line.getCommands());
		}

		vtable = new Stack<>();
		enter();
		ftable = new HashMap<>();

		position = new Position();
		position.currentCommands = null;
		position.nextLineNumber = 0;
		
		stack = new Stack<>();

		input = new Scanner(System.in);
	}

	public boolean step() {
		List<Command> cc = position.currentCommands;
		if (cc != null && !cc.isEmpty()) {
			Command command = cc.get(0);
			position.currentCommands = cc.subList(1, cc.size());
			return command.execute(this);
		} else {
			Integer next = prog.ceilingKey(position.nextLineNumber);
			if (next == null) {
				return false;
			} else {
				position.currentCommands = prog.get(next);
				position.nextLineNumber = next + 1;
				return step();
			}
		}
	}

	public int lookup(String id) {
		for (int i = vtable.size() - 1; i >= 0; i--) {
			if (vtable.get(i).containsKey(id)) {
				return vtable.get(i).get(id);
			}
		}
		return 0;
	}

	public void bind(String id, int value) {
		vtable.peek().put(id, value);
	}

	public FunVal lookupfn(String id) {
		return ftable.get(id);
	}
	
	public void bindfn(String id, FunVal funVal) {
		ftable.put(id, funVal);
	}

	public void doGoto(int lineNum) {
		position.currentCommands = null;
		position.nextLineNumber = lineNum;
	}

	public void doGosub(int lineNum) {
		stack.push(position);
		position = new Position();
		position.currentCommands = null;
		position.nextLineNumber = lineNum;
	}
	
	public void doReturn() {
		while (!stack.isEmpty() && !stack.peek().isPosition()) {
			stack.pop();
		}
		
		if (!stack.isEmpty()) {
			position = (Position) stack.pop();
		} else {
			System.err.println("RETURN without GOSUB");
			System.exit(1);
		}
	}

	public void doFor(String id, int from, int to) {
		Loop loop = new Loop();
		loop.id = id;
		loop.index = from;
		loop.limit = to;
		loop.position = new Position();
		loop.position.currentCommands = position.currentCommands;
		loop.position.nextLineNumber = position.nextLineNumber;
		stack.push(loop);
		bind(id, from);
	}
	
	public void doNext() {
		if (!stack.isEmpty() && stack.peek().isLoop()) {
			Loop loop = (Loop) stack.peek();
			loop.index++;
			bind(loop.id, loop.index);
			if (loop.index <= loop.limit) {
				position.currentCommands = loop.position.currentCommands;
				position.nextLineNumber = loop.position.nextLineNumber;
			} else {
				stack.pop();
			}
		} else {
			System.err.println("NEXT without FOR");
			System.exit(1);
		}
	}

	public void setCurrentCommands(List<Command> commands) {
		position.currentCommands = commands;
	}

	public int readInt() {
		return input.nextInt();
	}

	public void enter() {
		vtable.push(new HashMap<>());
	}
	
	public void exit() {
		vtable.pop();
	}
}
