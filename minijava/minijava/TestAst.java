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
      System.out.println(">>>>Pretty Printer:");
	  tree.apply(new PrettyPrint());
      System.out.println("\n\n\n");
      System.out.println(">>>>Ugly Printer =p :");
	  tree.apply(new UglyPrint());
      System.out.println("\n");
    } 
    catch(Exception e){ 
      System.out.println(e.getMessage()); 
    } 
  } 
} 
