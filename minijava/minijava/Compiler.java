package minijava;
import minijava.parser.*;
import minijava.lexer.*;
import minijava.node.*;
import java.io.*;

public class Compiler { 

  public static void main(String[] arguments) { 
    try { 
      System.out.println("Starting compiler...");
      // Create a Parser instance. 
      Parser p = 
        new Parser( 
            new Lexer( 
              new PushbackReader( 
                new InputStreamReader(System.in), 1024)));

      // Parse the input. 
      Start tree = p.parse();
      System.out.println("\nResult:");
      // Apply the translation. 
      tree.apply(new Translation()); 
      System.out.println("\n");
      
    } 
    catch(Exception e) { 
      System.out.println(e.getMessage()); 
    } 
  } 

} 
