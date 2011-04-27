package minijava;

import java.util.*;
import java.io.PrintStream;
import minijava.node.*;
import minijava.analysis.*;

public class PrettyPrint extends DepthFirstAdapter
{
    PrintStream out;
    
    private int ident;
    private boolean printSpace;
	
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
		out.print(" ");
	out.print(s);
		
	printSpace = false;
    }
	
    private void println(String s)
    {
	if ( printSpace )
	    for (int i = 0; i < ident; i++ )
		out.print(" ");
	out.println(s);
		
	printSpace = true;
    }
	
    public PrettyPrint(PrintStream p)
    {
	super();
        out = p;
	ident = 0;
	printSpace = true;
    }
    
    public PrettyPrint()
    {
        this(System.out);
    }

    public void inAMainclass(AMainclass node)
    {
		defaultIn(node);
		print("class " + node.getClassid().getText());
		println(" {");
		identRight();

		print( "public static void main(String[] " + node.getArgid().getText());
		println( ") {");
		identLeft();
	}

	public void outAMainclass(AMainclass node)
	{
		identRight();
		println("}");
		identLeft();
		println("}");
		defaultOut(node);
	}

    public void caseAClassdeclsimpleClassdecl(AClassdeclsimpleClassdecl node)
    {
        inAClassdeclsimpleClassdecl(node);
        if(node.getClassname() != null)
        {
            node.getClassname().apply(this);
        }
		print("class "+ node.getClassname().getText());
		println(" {");
		identRight();
        {
            List<PVardecl> copy = new ArrayList<PVardecl>(node.getVars());
            for(PVardecl e : copy)
            {
                e.apply(this);
				println(";");
            }
        }
        {
            List<PMethoddecl> copy = new ArrayList<PMethoddecl>(node.getMethods());
            for(PMethoddecl e : copy)
            {
                e.apply(this);
            }
        }
		identLeft();
		println("}");
        outAClassdeclsimpleClassdecl(node);
    }

    public void caseAClassdeclextendsClassdecl(AClassdeclextendsClassdecl node)
    {
        inAClassdeclextendsClassdecl(node);
        if(node.getClassname() != null)
        {
            node.getClassname().apply(this);
        }
		print("class " + node.getClassname().getText());
        if(node.getSuper() != null)
        {
            node.getSuper().apply(this);
        }
		print(" extends "+ node.getSuper().getText());
		println(" {");
		identRight();
        {
            List<PVardecl> copy = new ArrayList<PVardecl>(node.getVars());
            for(PVardecl e : copy)
            {
                e.apply(this);
				println(";");
            }
        }
        {
            List<PMethoddecl> copy = new ArrayList<PMethoddecl>(node.getMethods());
            for(PMethoddecl e : copy)
            {
                e.apply(this);
            }
        }
		identLeft();
		println("}");
        outAClassdeclextendsClassdecl(node);
    }

    public void outAVardecl(AVardecl node)
    {
        print(node.getVarid().getText());
		defaultOut(node);
    }

    public void caseAMethoddecl(AMethoddecl node)
    {
        inAMethoddecl(node);
		print("public ");
        if(node.getReturntype() != null)
        {
            node.getReturntype().apply(this);
        }
        if(node.getMethodid() != null)
        {
            node.getMethodid().apply(this);
        }
		print(node.getMethodid().getText());
		print("(");
        {
            List<PFormal> copy = new ArrayList<PFormal>(node.getFormals());
            for(PFormal e : copy)
            {
                e.apply(this);
				print(", ");
            }
        }
		print(")");
		println("{ ");
		identRight();
        {
            List<PVardecl> copy = new ArrayList<PVardecl>(node.getLocals());
            for(PVardecl e : copy)
            {
                e.apply(this);
				println(";");
            }
        }
        {
            List<PStatement> copy = new ArrayList<PStatement>(node.getBody());
            for(PStatement e : copy)
            {
                e.apply(this);
            }
        }
		print("return ");
        if(node.getReturnexp() != null)
        {
            node.getReturnexp().apply(this);
        }
		println(";");
		identLeft();
		println("}");
        outAMethoddecl(node);
    }

    public void outAFormal(AFormal node)
    {
		print(node.getId().getText() + " ");
        defaultOut(node);
    }
    public void inAIntArrayTypeType(AIntArrayTypeType node)
    {
        defaultIn(node);
		print("int [] ");
    }

    public void inABooleanTypeType(ABooleanTypeType node)
    {
        defaultIn(node);
		print("boolean ");
    }

    public void inAIntegerTypeType(AIntegerTypeType node)
    {
        defaultIn(node);
		print("int ");
    }

    public void inAIdentifierTypeType(AIdentifierTypeType node)
    {
        defaultIn(node);
		print(node.getId().getText() + " ");

    }

    public void inABlockStatement(ABlockStatement node)
    {
        defaultIn(node);
        println("{");
		identRight();
    }

    public void outABlockStatement(ABlockStatement node)
    {
        identLeft();
		println("}");
		defaultOut(node);
    }

    public void caseAWhileStatement(AWhileStatement node)
    {
        inAWhileStatement(node);
        print("while (");
		if(node.getExp() != null)
        {
            node.getExp().apply(this);
        }
		print(")");
        if(node.getStatement() != null)
        {
            node.getStatement().apply(this);
        }
        outAWhileStatement(node);
    }

    public void caseAIfStatement(AIfStatement node)
    {
        inAIfStatement(node);
		print("if (");
        if(node.getCondition() != null)
        {
            node.getCondition().apply(this);
        }
		print(") ");
        if(node.getIfstm() != null)
        {
            node.getIfstm().apply(this);
        }
        if(node.getElsestm() != null)
        {
			print("else ");
            node.getElsestm().apply(this);
        }
        outAIfStatement(node);
    }

    public void inAPrintStatement(APrintStatement node)
    {
        defaultIn(node);
        print("System.out.println(");
    }

    public void outAPrintStatement(APrintStatement node)
    {
        println(");");
    }

    public void caseAPrintStatement(APrintStatement node)
    {
        inAPrintStatement(node);
        if(node.getExp() != null)
        {
            node.getExp().apply(this);
        }
        outAPrintStatement(node);
    }

    public void caseAAssignStatement(AAssignStatement node)
    {
        inAAssignStatement(node);
        if(node.getId() != null)
        {
            node.getId().apply(this);
        }
		print(node.getId().getText() + " = ");
        if(node.getRightexp() != null)
        {
            node.getRightexp().apply(this);
        }
		println(";");
        outAAssignStatement(node);
    }

    public void caseAArrayAssignStatement(AArrayAssignStatement node)
    {
        inAArrayAssignStatement(node);
		if(node.getId() != null)
        {
            node.getId().apply(this);
        }
        print(node.getId().getText() + "[");
        if(node.getIndexexp() != null)
        {
            node.getIndexexp().apply(this);
        }
        print("] = ");
		if(node.getRightexp() != null)
        {
            node.getRightexp().apply(this);
        }
		println(";");
        outAArrayAssignStatement(node);
    }

    public void caseAAndExp(AAndExp node)
    {
        inAAndExp(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
		print("&&");
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outAAndExp(node);
    }

    public void caseALessthanExp(ALessthanExp node)
    {
        inALessthanExp(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
		print("<");
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outALessthanExp(node);
    }

    public void caseAPlusExp(APlusExp node)
    {
        inAPlusExp(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
		print("+");
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outAPlusExp(node);
    }

    public void caseAMinusExp(AMinusExp node)
    {
        inAMinusExp(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        print("-");
		if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outAMinusExp(node);
    }

    public void caseATimesExp(ATimesExp node)
    {
        inATimesExp(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        print("*");
		if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        outATimesExp(node);
    }

    public void inANotExp(ANotExp node)
    {
        defaultIn(node);
        print("!");
    }

    public void caseAArraylengthExp(AArraylengthExp node)
    {
        inAArraylengthExp(node);
        if(node.getExp() != null)
        {
            node.getExp().apply(this);
        }
		print(".length");
        outAArraylengthExp(node);
    }


    public void caseACallExp(ACallExp node)
    {
        inACallExp(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
		
		//tirar isso?
		if(node.getName() != null)
        {
            node.getName().apply(this);
        }
        print("." + node.getName().getText() + "(");
        {
            List<PExp> copy = new ArrayList<PExp>(node.getArgs());
            for(PExp e : copy)
            {
                e.apply(this);

				print(",");
            }
        }
		print(")");
        outACallExp(node);
    }

    public void caseAArraylookupExp(AArraylookupExp node)
    {
        inAArraylookupExp(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        print("[");
		if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
		print("]");
        outAArraylookupExp(node);
    }

    public void inAIntegerliteralExp(AIntegerliteralExp node)
    {
        defaultIn(node);
        print(node.getInteger().getText());
    }

    public void inATrueExp(ATrueExp node)
    {
        defaultIn(node);
        print("true");
    }
    
	public void inAFalseExp(AFalseExp node)
    {
        defaultIn(node);
        print("false");
    }
    
	public void inAIdentifierexpExp(AIdentifierexpExp node)
    {
        defaultIn(node);
        print(node.getId().getText());
    }

    public void inAThisExp(AThisExp node)
    {
        defaultIn(node);
        print("this");
    }

    public void inANewarrayExp(ANewarrayExp node)
    {
        defaultIn(node);
        print("new int[");
    }

    public void outANewarrayExp(ANewarrayExp node)
    {
        print( "]");
    }


    public void inANewobjectExp(ANewobjectExp node)
    {
        defaultIn(node);
        print( "new " + node.getId().getText() + "()");
    }
}
