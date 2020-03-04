package csc426.ast;

import java.util.List;

public class Program extends ASTNode {
	private List<Line> lines;

	public Program(List<Line> lines) {
		this.lines = lines;
	}

	public List<Line> getLines() {
		return lines;
	}

	public void display(String indent) {
		System.out.println(indent + "program");
		for (Line line : lines) {
			line.display(indent + "  ");
		}
	}
}
