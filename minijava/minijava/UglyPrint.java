package minijava;

import java.util.*;
import java.io.PrintStream;
import minijava.node.*;
import minijava.analysis.*;

public class UglyPrint extends DepthFirstAdapter
{
    public UglyPrint()
    {
        super();
    }
    
	public void defaultIn(Node node)
    {
		String className[];
		className = node.getClass().toString().split("\\.");
		//className = className[className.length - 1];
        System.out.print(">>" + className[className.length-1] + "\n");
    }
}
