package csc426.syntax;

import java.util.ArrayList;
import java.util.List;

import csc426.ast.*;

public class Parser {
	public Parser(Scanner scanner) {
		this.scanner = scanner;
		this.current = scanner.next();
	}

	/**
	 * If the current token matches the given type, return it and advance to the
	 * next token; otherwise print an error message and exit. Harsh.
	 * 
	 * @param type
	 */
	private Token match(TokenType type) {
		if (current.type == type) {
			Token result = current;
			current = scanner.next();
			return result;
		} else {
			System.err.println("Expected " + type + " but found " + current);
			System.exit(1);
			return null; // never reaches this
		}
	}

	/**
	 * Check whether the current token's type is one of a given list of type.
	 * 
	 * @param types a variable number of arguments listing the expected token types
	 * @return true if the current token type is in the given list
	 */
	private boolean check(TokenType... types) {
		for (TokenType type : types) {
			if (current.type == type) {
				return true;
			}
		}
		return false;
	}

	/*-
	 * Program --> EOF           FIRST = EOF
	 *           | RUN           FIRST = RUN
	 *           | Line Program  FIRST = NUM
	 */
	public Program parseProgram() {
			match(TokenType.PROGRAM);
			
			List<Line> line = new ArrayList<>();
			return new Program(lines);
	}

	/*-
	 * Line --> NUM Commands EOL
	 */
	private Line parseLine() {
		Token num = match(TokenType.NUM);
		int lineNum = Integer.parseInt(num.lexeme);

		List<Command> commands = parseCommands();
		match(TokenType.EOL);

		return new Line(lineNum, commands);
	}

	/*-
	 * Commands --> Command CommandsRest   FIRST = END, LET, GOTO, GOSUB, RETURN, FOR, NEXT, PRINT, INPUT
	 *            | IF Expr THEN Commands  FIRST = IF
	 */
	private List<Command> parseCommands() {
		if (check(TokenType.IF)) {
			match(TokenType.IF);
			Expr test = parseExpr();
			match(TokenType.THEN);
			List<Command> commands = parseCommands();
			Command command = new IfCommand(test, commands);
			List<Command> result = new ArrayList<>();
			result.add(command);
			return result;
		} else {
			Command command = parseCommand();
			List<Command> rest = parseCommandsRest();
			rest.add(0, command);
			return rest;
		}
	}

	/*-
	 * CommandsRest --> ε               FOLLOW = EOL
	 *                | COLON Commands  FIRST = COLON
	 */
	private List<Command> parseCommandsRest() {
		if (check(TokenType.EOL)) {
			return new ArrayList<Command>();
		} else {
			match(TokenType.COLON);
			return parseCommands();
		}
	}

	/*-
	 * Command --> END                              FIRST = END
	 *           | LET ID EQ Expr                   FIRST = LET
	 *           | GOTO NUM                         FIRST = GOTO
	 *           | GOSUB NUM                        FIRST = GOSUB
	 *           | RETURN                           FIRST = RETURN
	 *           | FOR ID EQ Expr TO Expr           FIRST = FOR
	 *           | NEXT                             FIRST = NEXT
	 *           | PRINT Exprs                      FIRST = PRINT
	 *           | INPUT ID                         FIRST = INPUT
	 *           | DEF FN ID LPAR Ids RPAR EQ Expr  FIRST = DEF
	 */
	private Command parseCommand() {
		if (check(TokenType.END)) {
			match(TokenType.END);
			return new EndCommand();
		} else if (check(TokenType.LET)) {
			match(TokenType.LET);
			Token id = match(TokenType.ID);
			match(TokenType.EQ);
			Expr expr = parseExpr();
			return new LetCommand(id.lexeme, expr);
		} else if (check(TokenType.GOTO)) {
			match(TokenType.GOTO);
			Token num = match(TokenType.NUM);
			int lineNum = Integer.parseInt(num.lexeme);
			return new GotoCommand(lineNum);
		} else if (check(TokenType.GOSUB)) {
			match(TokenType.GOSUB);
			Token num = match(TokenType.NUM);
			int lineNum = Integer.parseInt(num.lexeme);
			return new GosubCommand(lineNum);
		} else if (check(TokenType.RETURN)) {
			match(TokenType.RETURN);
			return new ReturnCommand();
		} else if (check(TokenType.FOR)) {
			match(TokenType.FOR);
			Token id = match(TokenType.ID);
			match(TokenType.EQ);
			Expr from = parseExpr();
			match(TokenType.TO);
			Expr to = parseExpr();
			return new ForCommand(id.lexeme, from, to);
		} else if (check(TokenType.NEXT)) {
			match(TokenType.NEXT);
			return new NextCommand();
		} else if (check(TokenType.PRINT)) {
			match(TokenType.PRINT);
			List<Expr> exprs = parseExprs();
			return new PrintCommand(exprs);
		} else if (check(TokenType.INPUT)) {
			match(TokenType.INPUT);
			Token id = match(TokenType.ID);
			return new InputCommand(id.lexeme);
		} else if (check(TokenType.DEF)) {
			match(TokenType.DEF);
			match(TokenType.FN);
			Token id = match(TokenType.ID);
			match(TokenType.LPAR);
			List<String> ids = parseIds();
			match(TokenType.RPAR);
			match(TokenType.EQ);
			Expr body = parseExpr();
			return new DefCommand(id.lexeme, ids, body);
		} else {
			System.err.println("Expected a command; got " + current);
			System.exit(1);
			return null; // never reaches this
		}
	}
	
	/*
	 * Ids --> ID IdsRest
	 */
	private List<String> parseIds() {
		Token id = match(TokenType.ID);
		List<String> rest = parseIdsRest();
		rest.add(0, id.lexeme);
		return rest;
	}
	
	/*
	 * IdsRest --> COMMA Ids  FIRST = COMMA
	 *           | ε          FOLLOW = RPAR
	 */
	private List<String> parseIdsRest() {
		if (check(TokenType.COMMA)) {
			match(TokenType.COMMA);
			return parseIds();
		} else {
			return new ArrayList<>();
		}
	}

	/*-
	 * Exprs --> Expr ExprsRest
	 */
	private List<Expr> parseExprs() {
		Expr expr = parseExpr();
		List<Expr> rest = parseExprsRest();
		rest.add(0, expr);
		return rest;
	}

	/*-
	 * ExprsRest --> ε            FOLLOW = EOL, COLON, RPAR
	 *             | COMMA Exprs  FIRST = COMMA
	 */
	private List<Expr> parseExprsRest() {
		if (check(TokenType.COMMA)) {
			match(TokenType.COMMA);
			return parseExprs();
		} else {
			return new ArrayList<>();
		}
	}

	/*-
	 * Expr --> AExpr ExprRest
	 */
	private Expr parseExpr() {
		Expr left = parseAExpr();
		return parseExprRest(left);
	}

	/*-
	 * ExprRest --> ε            FOLLOW = COMMA, EOL, COLON, RPAR, TO, THEN
	 *            | RelOp AExpr  FIRST = EQ, LT, GT
	 */
	private Expr parseExprRest(Expr left) {
		if (check(TokenType.EQ, TokenType.LT, TokenType.GT)) {
			Op op = parseRelOp();
			Expr right = parseAExpr();
			return new BinOp(left, op, right);
		} else {
			return left;
		}
	}

	/*-
	 * AExpr --> MExpr AExprRest
	 */
	private Expr parseAExpr() {
		Expr left = parseMExpr();
		return parseAExprRest(left);
	}

	/*-
	 * AExprRest --> AddOp MExpr AExprRest  FIRST = PLUS, MINUS
	 *             | ε                      FOLLOW = COMMA, EOL, COLON, RPAR, TO, THEN, EQ, LT, GT
	 */
	private Expr parseAExprRest(Expr left) {
		if (check(TokenType.PLUS, TokenType.MINUS)) {
			Op op = parseAddOp();
			Expr right = parseMExpr();
			return parseAExprRest(new BinOp(left, op, right));
		} else {
			return left;
		}
	}

	/*-
	 * MExpr --> Factor MExprRest
	 */
	private Expr parseMExpr() {
		Expr left = parseFactor();
		return parseMExprRest(left);
	}

	/*-
	 * MExprRest --> MulOp Factor MExprRest  FIRST = STAR, SLASH
	 *             | ε                       FOLLOW = PLUS, MINUS, COMMA, EOL, COLON, RPAR, TO, THEN, EQ, LT, GT
	 */
	private Expr parseMExprRest(Expr left) {
		if (check(TokenType.STAR, TokenType.SLASH)) {
			Op op = parseMulOp();
			Expr right = parseFactor();
			return parseMExprRest(new BinOp(left, op, right));
		} else {
			return left;
		}
	}

	/*-
	 * Factor --> NUM                    FIRST = NUM
	 *          | ID                     FIRST = ID
	 *          | AddOp Factor           FIRST = PLUS, MINUS
	 *          | LPAR Expr RPAR         FIRST = LPAR
	 *          | FN ID LPAR Exprs RPAR  FIRST = FN
	 */
	private Expr parseFactor() {
		if (check(TokenType.NUM)) {
			Token num = match(TokenType.NUM);
			int number = Integer.parseInt(num.lexeme);
			return new Num(number);
		} else if (check(TokenType.ID)) {
			Token id = match(TokenType.ID);
			return new Var(id.lexeme);
		} else if (check(TokenType.PLUS, TokenType.MINUS)) {
			Op op = parseAddOp();
			Expr expr = parseFactor();
			return new UnOp(op, expr);
		} else if (check(TokenType.FN)) {
			match(TokenType.FN);
			Token id = match(TokenType.ID);
			match(TokenType.LPAR);
			List<Expr> args = parseExprs();
			match(TokenType.RPAR);
			return new FunCall(id, args);
		} else {
			match(TokenType.LPAR);
			Expr expr = parseExpr();
			match(TokenType.RPAR);
			return expr;
		}
	}

	/*-
	 * RelOp --> EQ         FIRST = EQ
	 *         | LT LTRest  FIRST = LT
	 *         | GT GTRest  FIRST = GT
	 */
	private Op parseRelOp() {
		if (check(TokenType.EQ)) {
			match(TokenType.EQ);
			return Op.EQUAL;
		} else if (check(TokenType.LT)) {
			match(TokenType.LT);
			return parseLTRest();
		} else {
			match(TokenType.GT);
			return parseGTRest();
		}
	}

	/*-
	 * LTRest --> ε   FOLLOW = NUM, ID, PLUS, MINUS, LPAR
	 *          | GT  FIRST = GT
	 *          | EQ  FIRST = EQ
	 */
	private Op parseLTRest() {
		if (check(TokenType.GT)) {
			match(TokenType.GT);
			return Op.NOTEQUAL;
		} else if (check(TokenType.EQ)) {
			match(TokenType.EQ);
			return Op.NOTGREATER;
		} else {
			return Op.LESS;
		}
	}

	/*-
	 * GTRest --> ε   FOLLOW = NUM, ID, PLUS, MINUS, LPAR
	 *          | EQ  FIRST = EQ
	 */
	private Op parseGTRest() {
		if (check(TokenType.EQUAL)) {
			match(TokenType.EQUAL);
			return Op.NOTLESS;
		} else {
			return Op.GREATER;
		}
	}

	/*-
	 * AddOp --> PLUS   FIRST = PLUS
	 *         | MINUS  FIRST = MINUS
	 *         | OR		FIRST = OR
	 */
	private Op parseAddOp() {
		if (check(TokenType.PLUS)) {
			match(TokenType.PLUS);
			return Op.ADD;
		} else if(check(TokenType.MINUS)){
			match(TokenType.MINUS);
			return Op.SUBTRACT;
		}
		else {
			match(TokenType.OR);
			return Op.OR;
		}
	}

	/*-
	 * MulOp --> STAR   FIRST = STAR
	 *         | DIV  FIRST = DIV
	 *         |MOD   FIRST =MOD
	 *         |AND   FIRST = AND
	 */
	private Op parseMulOp() {
		if (check(TokenType.STAR)) {
			match(TokenType.STAR);
			return Op.MULTIPLY;
		}
		else if (check(TokenType.DIV)) {
			match(TokenType.DIV);
			return Op.DIVIDE;
		}
		else if (check(TokenType.MOD)) {
			match(TokenType.MOD);
			return Op.MOD;
		}
		else {
			match(TokenType.AND);
			return Op.AND;
		}
	}

	private Scanner scanner;
	private Token current;
}
