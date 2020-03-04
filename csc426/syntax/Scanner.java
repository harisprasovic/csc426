package csc426.syntax;


import java.io.IOException;
import java.io.Reader;

//@author: Haris Prasovic
// csc426A
public class Scanner {
	//new reader
	public Scanner(Reader in) {
		source = new Source(in);
	}
	

	public Token next() {
		int state=0;
		int templine=0;
		int tempcolumn=0;
		String lexeme="";
		TokenType type=null;
		while(true){
			switch(state) {
			case 0://Initial state; tests every character and sends it to the right state accordingly.
				lexeme="";
				if(source.atEOF) {
					//end of file reached
					return new Token(source.line, source.column, TokenType.EOF, lexeme);
				}
				else if(Character.isWhitespace(source.current)==true  ) {
					//advances through whitespaces
					source.advance();
				}
				else if((source.current=='>') || (source.current=='<') || (source.current=='+') || (source.current=='-') || (source.current=='=') || (source.current=='*')) {
					//works with operators
					templine=source.line;
					tempcolumn=source.column;
					state= 4;
				}
				else if( source.current==','|| source.current==':'|| source.current==';' || source.current=='.' || source.current=='(' || source.current==')') {
					//works with punctuation and period
					templine=source.line;
					tempcolumn=source.column;
					state=5;	
				}
				
				else if(source.current=='/') {
					//start of command
					templine=source.line;
					tempcolumn=source.column;
					source.advance();
					state=7;
				}
				else if(source.current=='"') {
					templine=source.line;
					tempcolumn=source.column;
					source.advance();
					state=11;
				}
				else if(Character.isDigit(source.current)) {
					//works with numbers
					state=1;
				}
				else if(Character.isLetter(source.current)) {
					//works with identifiers and values
					state=2;
				}
				else {
					//unexpected character, prints a message and skips
					System.err.println("Illegal character: " + source.current);
					System.err.println("  at " + source.line + ":" + source.column);
					source.advance();
				}
				break;
			case 1://Number state assigns for the number tokens
					if(source.current=='0') {
						templine=source.line;
						tempcolumn=source.column;
						lexeme="0";
						source.advance();
						type=TokenType.NUM;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else {
						lexeme+="" + source.current;
						templine=source.line;
						tempcolumn=source.column;
						type=TokenType.NUM;
						source.advance();
						while(Character.isDigit(source.current) ) {
							lexeme= lexeme + source.current;
							source.advance();
						}
						return new Token(templine, tempcolumn, type, lexeme);
					}
					
			case 2://Identifier state; tests if the word is an identifier or a keyword
					templine=source.line;
					tempcolumn=source.column;
					type=TokenType.ID;
					while(Character.isLetterOrDigit(source.current) ) {
						lexeme+="" + source.current;
						source.advance();
						if(lexeme.equals("false") ||lexeme.equals("true") ||lexeme.equals("not") ||lexeme.equals("or") ||lexeme.equals("and") ||lexeme.equals("input") ||lexeme.equals("do") ||lexeme.equals("while") ||lexeme.equals("else") ||lexeme.equals("then") ||lexeme.equals("if") || lexeme.equals("let") || lexeme.equals("fun") || lexeme.equals("void") || lexeme.equals("bool") || lexeme.equals("val") || lexeme.equals("program") || lexeme.equals("begin") || lexeme.equals("print") || lexeme.equals("end") || lexeme.equals("div") || lexeme.equals("mod") || lexeme.equals("var") || lexeme.equals("int")) {
							state=3;
						}
						else {
							state=6;
						}
					}
					break;
			case 3://Keyword state;assigns the right keyword 
					if(lexeme.equals("program")) {
						//source.advance();
						lexeme="";
						type=TokenType.PROGRAM;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("val")) {
						//source.advance();
						lexeme="";
						type=TokenType.VAL;
						return new Token(templine, tempcolumn, type, lexeme);
						}
					else if(lexeme.equals("begin")) {
						//source.advance();
						lexeme="";
						type=TokenType.BEGIN;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("print")) {
						//source.advance();
						lexeme="";
						type=TokenType.PRINT;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("end")) {
						//source.advance();
						lexeme="";
						type=TokenType.END;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("mod")) {
						lexeme="";
						type=TokenType.MOD;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("div")) {
						lexeme="";
						type=TokenType.DIV;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("var")) {
						lexeme="";
						type=TokenType.VAR;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("int")) {
						lexeme="";
						type=TokenType.INT;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("bool")) {
						lexeme="";
						type=TokenType.BOOL;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("void")) {
						lexeme="";
						type=TokenType.VOID;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("fun")) {
						lexeme="";
						type=TokenType.FUN;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("let")) {
						lexeme="";
						type=TokenType.LET;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("if")) {
						lexeme="";
						type=TokenType.IF;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("then")) {
						lexeme="";
						type=TokenType.THEN;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("else")) {
						lexeme="";
						type=TokenType.ELSE;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("while")) {
						lexeme="";
						type=TokenType.WHILE;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("div")) {
						lexeme="";
						type=TokenType.DIV;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("while")) {
						lexeme="";
						type=TokenType.WHILE;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("do")) {
						lexeme="";
						type=TokenType.DO;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("input")) {
						lexeme="";
						type=TokenType.INPUT;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("and")) {
						lexeme="";
						type=TokenType.AND;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("or")) {
						lexeme="";
						type=TokenType.OR;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("not")) {
						lexeme="";
						type=TokenType.NOT;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("true")) {
						lexeme="";
						type=TokenType.TRUE;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					else if(lexeme.equals("false")) {
						lexeme="";
						type=TokenType.FALSE;
						return new Token(templine, tempcolumn, type, lexeme);
					}
					break;
			case 4://Operator state;assigns the right operator
					if(source.current=='+') {
						source.advance();
						type=TokenType.PLUS;
					}
					else if(source.current=='-') {
						source.advance();
						type=TokenType.MINUS;
					}
					else if(source.current=='*') {
						source.advance();
						type=TokenType.STAR;
					}
					else if(source.current=='=') {
						source.advance();
						type=TokenType.ASSIGN;
						if(source.current=='=') {
							source.advance();
							type=TokenType.EQUAL;
						}
					}
					else if(source.current=='<') {
						source.advance();
						type=TokenType.LESS;
						if(source.current=='=') {
							source.advance();
							type=TokenType.LESSEQUAL;
						}
						if(source.current=='>') {
							source.advance();
							type=TokenType.NOTEQUAL;
						}
					}
					else if(source.current=='>') {
						source.advance();
						type=TokenType.GREATER;
						if(source.current=='=') {
							source.advance();
							type=TokenType.GREATEREQUAL;
						}
					}
					return new Token(templine, tempcolumn, type, lexeme);
					
			case 5://Punctuation state;assigns the right punctuation
					if(source.current==';') {
						source.advance();
						type=TokenType.SEMI;
					}
					else if(source.current=='.') {
						source.advance();
						type=TokenType.PERIOD;
					}
					else if(source.current=='(') {
						source.advance();
						type=TokenType.LPAREN;
					}
					else if(source.current==')') {
						source.advance();
						type=TokenType.RPAREN;
					}
					else if(source.current==':') {
						source.advance();
						type=TokenType.COLON;
					}
					else if(source.current==',') {
						source.advance();
						type=TokenType.COMMA;
					}
					return new Token(templine, tempcolumn, type, lexeme);
					
			case 6://final state; returns token with its correct type and location
					return new Token(templine, tempcolumn, type, lexeme);
					
			case 7://commment state;tests for comments and skips over them
					 if(source.atEOF || (source.current != '*' && source.current != '/')) {
						// Illegal start of comment; print error message but leave current character
							System.err.println(" Abnormal comment: found " + source.current + " after /");
							System.err.println("  at " + templine + ":" + tempcolumn);
							state = 0;
					 }
					 else if(source.current=='*') {
						 source.advance();
						 state=8;
					 }
					 else if(source.current=='/') {
						 source.advance();
						 state=10;
					 }

					 	break;
			case 8: // Skip the rest of a slash-star comment
				if (source.atEOF) {
					// Unclosed comment at EOF; print error message
					System.err.println("Unclosed comment at end of file");
					state = 0;
				} else if (source.current == '*') {
					// Possible ending star-slash
					source.advance();
					state = 9;
				} else {
					// Still in comment
					source.advance();
				}
				break;

			case 9: // Just saw a star in a slash-star comment
				if (source.atEOF==true) {
					// Unclosed comment at EOF; print error message
					System.err.println("Unclosed comment at end of file");
					state = 0;
				} else if (source.current == '/') {
					// End of comment
					source.advance();
					state = 0;
				} else if (source.current == '*') {
					// Maybe _this_ is the ending star; stay in this state
					source.advance();
				} else {
					// Still in comment
					source.advance();
					state = 8;
				}
				break;

			case 10: // Skip the rest of a slash-slash comment
				if (source.atEOF || source.current == '\n') {
					// End of comment
					state = 0;
				} else {
					// Still in comment
					source.advance();
				}
				break;
			case 11://String comment section
				type=TokenType.STRING;
				if(source.current=='"') {
					source.advance();
					state=6;
					if(source.current=='"') {
						lexeme+=source.current;
						state=11;
					}
				}
				else if(source.current!='"') {
					lexeme+=source.current;
					source.advance();
				}
				break;
			}
		}
}
					
	
	//closes reader
	public void close() throws IOException {
		source.close();
	}
	private Source source;
}
