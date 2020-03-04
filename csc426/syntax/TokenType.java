package csc426.syntax;

/**
 * Enumeration of the different kinds of tokens in the BASIC subset.
 * 
 * @author bhoward
 */
public enum TokenType {
	NUM, // numeric literal
	SEMI, // semicolon (;)
	PLUS, // plus operator (+)
	MINUS, // minus operator (-)
	STAR, // times operator (*)
	EOF, // end-of-file
	ID, // identifier
	PROGRAM, //"program"
	VAL,//
	BEGIN,//"begin"
	PRINT,//"print"
	END,//"end"
	DIV,//"div"
	MOD,//(%)
	ASSIGN,//(=)
	PERIOD,//(.)
	ERROR,
	VAR,
	INT,
	BOOL,
	VOID,
	FUN,
	LET,
	IF,
	THEN,
	ELSE,
	WHILE,
	DO,
	INPUT,
	AND,
	OR,
	NOT,
	TRUE,
	FALSE,
	COLON,
	LPAREN,
	RPAREN,
	COMMA,
	EQUAL,
	NOTEQUAL,
	LESSEQUAL,
	GREATEREQUAL,
	LESS,
	GREATER,
	STRING
	
}
