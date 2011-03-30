package minijava;
import minijava.parser.*;
import minijava.lexer.*;
import minijava.node.*;
//import prettyprint; 
import java.io.*;

public class TestAst { 

  public static void main(String[] arguments) { 

    try { 
      //System.out.println(">>>>Enter the program to be analysed:");

      // Create a Parser instance. 
      Parser p = new Parser(
        new Lexer(
          new PushbackReader(
            new InputStreamReader(System.in), 1024)));

      // Parse the input. 
      Start tree = p.parse();
	  tree.apply(new PrettyPrint());
      System.out.println("\n");
    } 
    catch(Exception e){ 
      System.out.println(e.getMessage()); 
    } 
  } 
} 
