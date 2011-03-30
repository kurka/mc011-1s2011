/* This file was generated by SableCC (http://www.sablecc.org/). */

package minijava.analysis;

import java.util.*;
import minijava.node.*;

public class DepthFirstAdapter extends AnalysisAdapter
{
    public void inStart(Start node)
    {
        defaultIn(node);
    }

    public void outStart(Start node)
    {
        defaultOut(node);
    }

    public void defaultIn(@SuppressWarnings("unused") Node node)
    {
        // Do nothing
    }

    public void defaultOut(@SuppressWarnings("unused") Node node)
    {
        // Do nothing
    }

    @Override
    public void caseStart(Start node)
    {
        inStart(node);
        node.getPProgram().apply(this);
        node.getEOF().apply(this);
        outStart(node);
    }

    public void inAProgram(AProgram node)
    {
        defaultIn(node);
    }

    public void outAProgram(AProgram node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAProgram(AProgram node)
    {
        inAProgram(node);
        if(node.getMainclass() != null)
        {
            node.getMainclass().apply(this);
        }
        {
            List<PClassdecl> copy = new ArrayList<PClassdecl>(node.getClassdecl());
            for(PClassdecl e : copy)
            {
                e.apply(this);
            }
        }
        outAProgram(node);
    }

    public void inAMainclass(AMainclass node)
    {
        defaultIn(node);
    }

    public void outAMainclass(AMainclass node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAMainclass(AMainclass node)
    {
        inAMainclass(node);
        if(node.getClassid() != null)
        {
            node.getClassid().apply(this);
        }
        if(node.getArgid() != null)
        {
            node.getArgid().apply(this);
        }
        if(node.getStm() != null)
        {
            node.getStm().apply(this);
        }
        outAMainclass(node);
    }

    public void inAClassdeclsimpleClassdecl(AClassdeclsimpleClassdecl node)
    {
        defaultIn(node);
    }

    public void outAClassdeclsimpleClassdecl(AClassdeclsimpleClassdecl node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAClassdeclsimpleClassdecl(AClassdeclsimpleClassdecl node)
    {
        inAClassdeclsimpleClassdecl(node);
        if(node.getClassname() != null)
        {
            node.getClassname().apply(this);
        }
        {
            List<PVardecl> copy = new ArrayList<PVardecl>(node.getVars());
            for(PVardecl e : copy)
            {
                e.apply(this);
            }
        }
        {
            List<PMethoddecl> copy = new ArrayList<PMethoddecl>(node.getMethods());
            for(PMethoddecl e : copy)
            {
                e.apply(this);
            }
        }
        outAClassdeclsimpleClassdecl(node);
    }

    public void inAClassdeclextendsClassdecl(AClassdeclextendsClassdecl node)
    {
        defaultIn(node);
    }

    public void outAClassdeclextendsClassdecl(AClassdeclextendsClassdecl node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAClassdeclextendsClassdecl(AClassdeclextendsClassdecl node)
    {
        inAClassdeclextendsClassdecl(node);
        if(node.getClassname() != null)
        {
            node.getClassname().apply(this);
        }
        if(node.getSuper() != null)
        {
            node.getSuper().apply(this);
        }
        {
            List<PVardecl> copy = new ArrayList<PVardecl>(node.getVars());
            for(PVardecl e : copy)
            {
                e.apply(this);
            }
        }
        {
            List<PMethoddecl> copy = new ArrayList<PMethoddecl>(node.getMethods());
            for(PMethoddecl e : copy)
            {
                e.apply(this);
            }
        }
        outAClassdeclextendsClassdecl(node);
    }

    public void inAVardecl(AVardecl node)
    {
        defaultIn(node);
    }

    public void outAVardecl(AVardecl node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAVardecl(AVardecl node)
    {
        inAVardecl(node);
        if(node.getVartype() != null)
        {
            node.getVartype().apply(this);
        }
        if(node.getVarid() != null)
        {
            node.getVarid().apply(this);
        }
        outAVardecl(node);
    }

    public void inAMethoddecl(AMethoddecl node)
    {
        defaultIn(node);
    }

    public void outAMethoddecl(AMethoddecl node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAMethoddecl(AMethoddecl node)
    {
        inAMethoddecl(node);
        if(node.getReturntype() != null)
        {
            node.getReturntype().apply(this);
        }
        if(node.getMethodid() != null)
        {
            node.getMethodid().apply(this);
        }
        {
            List<PFormal> copy = new ArrayList<PFormal>(node.getFormals());
            for(PFormal e : copy)
            {
                e.apply(this);
            }
        }
        {
            List<PVardecl> copy = new ArrayList<PVardecl>(node.getLocals());
            for(PVardecl e : copy)
            {
                e.apply(this);
            }
        }
        {
            List<PStatement> copy = new ArrayList<PStatement>(node.getBody());
            for(PStatement e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getReturnexp() != null)
        {
            node.getReturnexp().apply(this);
        }
        outAMethoddecl(node);
    }

    public void inAIntArrayTypeType(AIntArrayTypeType node)
    {
        defaultIn(node);
    }

    public void outAIntArrayTypeType(AIntArrayTypeType node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAIntArrayTypeType(AIntArrayTypeType node)
    {
        inAIntArrayTypeType(node);
        outAIntArrayTypeType(node);
    }

    public void inABooleanTypeType(ABooleanTypeType node)
    {
        defaultIn(node);
    }

    public void outABooleanTypeType(ABooleanTypeType node)
    {
        defaultOut(node);
    }

    @Override
    public void caseABooleanTypeType(ABooleanTypeType node)
    {
        inABooleanTypeType(node);
        outABooleanTypeType(node);
    }

    public void inAIntegerTypeType(AIntegerTypeType node)
    {
        defaultIn(node);
    }

    public void outAIntegerTypeType(AIntegerTypeType node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAIntegerTypeType(AIntegerTypeType node)
    {
        inAIntegerTypeType(node);
        outAIntegerTypeType(node);
    }

    public void inAIdentifierTypeType(AIdentifierTypeType node)
    {
        defaultIn(node);
    }

    public void outAIdentifierTypeType(AIdentifierTypeType node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAIdentifierTypeType(AIdentifierTypeType node)
    {
        inAIdentifierTypeType(node);
        if(node.getId() != null)
        {
            node.getId().apply(this);
        }
        outAIdentifierTypeType(node);
    }

    public void inAFormal(AFormal node)
    {
        defaultIn(node);
    }

    public void outAFormal(AFormal node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAFormal(AFormal node)
    {
        inAFormal(node);
        if(node.getType() != null)
        {
            node.getType().apply(this);
        }
        if(node.getId() != null)
        {
            node.getId().apply(this);
        }
        outAFormal(node);
    }

    public void inABlockStatement(ABlockStatement node)
    {
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
        print("System.out.println(");
    }

    public void outAPrintStatement(APrintStatement node)
    {
        println(");");
    }

    @Override
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
		print(node.getVar().getText() + " = ");
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
        print(node.getVar().getText() + "[");
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
        print("!");
    }

    public void caseAArraylenghtExp(AArraylenghtExp node)
    {
        inAArraylenghtExp(node);
        if(node.getExp() != null)
        {
            node.getExp().apply(this);
        }
		print(".lenght");
        outAArraylenghtExp(node);
    }


    public void caseACallExp(ACallExp node)
    {
        inACallExp(node);
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        print("." + node.getMemberName().getText() + "(")
		
		//tirar isso?
		if(node.getName() != null)
        {
            node.getName().apply(this);
        }
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
        print(node.getInteger().getText());
    }

    public void inATrueExp(ATrueExp node)
    {
        print("true");
    }
    
	public void inAFalseExp(AFalseExp node)
    {
        print("false");
    }
    
	public void inAIdentifierexpExp(AIdentifierexpExp node)
    {
        print(node.getId().getText());
    }

    public void inAThisExp(AThisExp node)
    {
        print("this");
    }

    public void inANewarrayExp(ANewarrayExp node)
    {
        print("new int[");
    }

    public void outANewarrayExp(ANewarrayExp node)
    {
        print( "]");
    }


    public void inANewobjectExp(ANewobjectExp node)
    {
        print( "new " + node.getClassName().getText() + "()");
    }
}
