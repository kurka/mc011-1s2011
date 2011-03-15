package minijava;
//import minijava.parser.*;
import minijava.lexer.*;
import minijava.node.*;
import java.io.*;

public class TestLexer{ 
  public static void main(String[] arguments){ 
    try{ 
      System.out.println(">>>>Testing the Lexer...");

      System.out.println(">>>>Enter the program to be analysed:");
	  Lexer l =  new Lexer(
                   new PushbackReader(
                     new InputStreamReader(System.in), 1024));

      Token t =  l.next();
	  System.out.println("\n\n>>>>Lexer Output:\n");
	  while(!t.getText().equals("")){
		//System.out.print(t.toString());
		if(t instanceof TBlank)
			System.out.print(" ");
		else if(t instanceof TBooleanType)
			System.out.print("BOOLEANTYPE ");
		else if(t instanceof TClass)
			System.out.print("CLASS ");
		else if(t instanceof TComma)
			System.out.print("COMMA ");
		else if(t instanceof TComment)
			System.out.print("COMMENT ");
		else if(t instanceof TDot)
			System.out.print("DOT ");
		else if(t instanceof TElse)
			System.out.print("ELSE ");
		else if(t instanceof TEqual)
			System.out.print("EQUAL ");
		else if(t instanceof TExtends)
			System.out.print("EXTENDS ");
		else if(t instanceof TFalse)
			System.out.print("FALSE ");
		else if(t instanceof TId)
			System.out.print("ID ");
		else if(t instanceof TIf)
			System.out.print("IF ");
		else if(t instanceof TIntType)
			System.out.print("INT_TYPE ");
		else if(t instanceof TLcurly)
			System.out.print("LCURLY ");
		else if(t instanceof TLenght)
			System.out.print("LENGHT ");
		else if(t instanceof TLparen)
			System.out.print("LPAREN ");
		else if(t instanceof TLsquare)
			System.out.print("LSQUARE ");
		else if(t instanceof TMain)
			System.out.print("MAIN ");
		else if(t instanceof TNew)
			System.out.print("NEW ");
		else if(t instanceof TNot)
			System.out.print("NOT ");
		else if(t instanceof TNum)
			System.out.print("NUM ");
		else if(t instanceof TOp)
			System.out.print("OP ");
		else if(t instanceof TPrint)
			System.out.print("PRINT ");
		else if(t instanceof TPublic)
			System.out.print("PUBLIC ");
		else if(t instanceof TRcurly)
			System.out.print("RCURLY ");
		else if(t instanceof TReturn)
			System.out.print("RETURN ");
		else if(t instanceof TRparen)
			System.out.print("RPAREN ");
		else if(t instanceof TRsquare)
			System.out.print("RSQUARE ");
		else if(t instanceof TSemicolon)
			System.out.print("SEMICOLON ");
		else if(t instanceof TStringType)
			System.out.print("STRING_TYPE ");
		else if(t instanceof TThis)
			System.out.print("THIS ");
		else if(t instanceof TTrue)
			System.out.print("TRUE ");
		else if(t instanceof TWhile)
			System.out.print("WHILE ");
		else
			System.out.print("\n\n>>>>!!!ERROR!!!: token not recognized!\n\n");

		t = l.next();
	  }
	  System.out.println("\n\n>>>>Finish! Succes?? I hope so! :)");
    } 
    catch(Exception e){ 
      System.out.println(e.getMessage()); 
    } 
  } 
} 
