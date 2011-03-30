package minijava;
import minijava.parser.*;
import minijava.lexer.*;
import minijava.node.*;
import java.io.*;

public class TestParser { 

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
    } 
    catch(Exception e){ 
      System.out.println(e.getMessage()); 
    } 
  } 
} 
