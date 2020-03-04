package csc426;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import csc426.ast.Program;
import csc426.interp.State;
import csc426.syntax.Parser;
import csc426.syntax.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		Parser parser = new Parser(scanner);
		
		Program program = parser.parseProgram();
		program.display("");
		
		State state = new State(program);
		while (state.step()) /* process one command */;
		System.out.println("DONE");
	}
}
