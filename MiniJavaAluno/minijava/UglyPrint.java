package minijava;

import java.util.*;
import java.io.PrintStream;
import minijava.node.*;
import minijava.analysis.*;

public class UglyPrint extends DepthFirstAdapter
{
    private int ident;
    private boolean printSpace;
	
	public UglyPrint()
    {
        super();
		ident = 0;
		printSpace = true;
    }
    
    private void identRight()
    {
	ident += 4;
    }
	
    private void identLeft()
    {
	ident -= 4;
    }
	
    private void print(String s)
    {
	if ( printSpace )
	    for (int i = 0; i < ident; i++ )
		System.out.print(" ");
	System.out.print(s);
		
	printSpace = false;
    }
	
    private void println(String s)
    {
	if ( printSpace )
	    for (int i = 0; i < ident; i++ )
		System.out.print(" ");
	System.out.println(s);
		
	printSpace = true;
    }
    
	public void defaultIn(Node node)
    {
		String className[];
		className = node.getClass().toString().split("\\.");
		//className = className[className.length - 1];
        println(">>" + className[className.length-1]);
		identRight();
    }
	
	public void defaultOut(Node node)
    {
		identLeft();
    }
}
