/* This file was generated by SableCC (http://www.sablecc.org/). */

package minijava.analysis;

import java.util.*;
import minijava.node.*;

public class ReversedDepthFirstAdapter extends AnalysisAdapter
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
        node.getEOF().apply(this);
        node.getPProgram().apply(this);
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
        {
            List<PClassDecl> copy = new ArrayList<PClassDecl>(node.getClassDecl());
            Collections.reverse(copy);
            for(PClassDecl e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getMainClass() != null)
        {
            node.getMainClass().apply(this);
        }
        outAProgram(node);
    }

    public void inAMainClass(AMainClass node)
    {
        defaultIn(node);
    }

    public void outAMainClass(AMainClass node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAMainClass(AMainClass node)
    {
        inAMainClass(node);
        if(node.getStatement() != null)
        {
            node.getStatement().apply(this);
        }
        if(node.getMainArgs() != null)
        {
            node.getMainArgs().apply(this);
        }
        if(node.getName() != null)
        {
            node.getName().apply(this);
        }
        outAMainClass(node);
    }

    public void inAClassDeclSimpleClassDecl(AClassDeclSimpleClassDecl node)
    {
        defaultIn(node);
    }

    public void outAClassDeclSimpleClassDecl(AClassDeclSimpleClassDecl node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAClassDeclSimpleClassDecl(AClassDeclSimpleClassDecl node)
    {
        inAClassDeclSimpleClassDecl(node);
        {
            List<PMethodDecl> copy = new ArrayList<PMethodDecl>(node.getMethods());
            Collections.reverse(copy);
            for(PMethodDecl e : copy)
            {
                e.apply(this);
            }
        }
        {
            List<PVarDecl> copy = new ArrayList<PVarDecl>(node.getVars());
            Collections.reverse(copy);
            for(PVarDecl e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getClassname() != null)
        {
            node.getClassname().apply(this);
        }
        outAClassDeclSimpleClassDecl(node);
    }

    public void inAClassDeclExtendsClassDecl(AClassDeclExtendsClassDecl node)
    {
        defaultIn(node);
    }

    public void outAClassDeclExtendsClassDecl(AClassDeclExtendsClassDecl node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAClassDeclExtendsClassDecl(AClassDeclExtendsClassDecl node)
    {
        inAClassDeclExtendsClassDecl(node);
        {
            List<PMethodDecl> copy = new ArrayList<PMethodDecl>(node.getMethods());
            Collections.reverse(copy);
            for(PMethodDecl e : copy)
            {
                e.apply(this);
            }
        }
        {
            List<PVarDecl> copy = new ArrayList<PVarDecl>(node.getVars());
            Collections.reverse(copy);
            for(PVarDecl e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getSuper() != null)
        {
            node.getSuper().apply(this);
        }
        if(node.getClassname() != null)
        {
            node.getClassname().apply(this);
        }
        outAClassDeclExtendsClassDecl(node);
    }

    public void inAVarDecl(AVarDecl node)
    {
        defaultIn(node);
    }

    public void outAVarDecl(AVarDecl node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAVarDecl(AVarDecl node)
    {
        inAVarDecl(node);
        if(node.getVarid() != null)
        {
            node.getVarid().apply(this);
        }
        if(node.getVartype() != null)
        {
            node.getVartype().apply(this);
        }
        outAVarDecl(node);
    }

    public void inAMethodDecl(AMethodDecl node)
    {
        defaultIn(node);
    }

    public void outAMethodDecl(AMethodDecl node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAMethodDecl(AMethodDecl node)
    {
        inAMethodDecl(node);
        if(node.getReturnexp() != null)
        {
            node.getReturnexp().apply(this);
        }
        {
            List<PStatement> copy = new ArrayList<PStatement>(node.getBody());
            Collections.reverse(copy);
            for(PStatement e : copy)
            {
                e.apply(this);
            }
        }
        {
            List<PVarDecl> copy = new ArrayList<PVarDecl>(node.getLocals());
            Collections.reverse(copy);
            for(PVarDecl e : copy)
            {
                e.apply(this);
            }
        }
        {
            List<PFormal> copy = new ArrayList<PFormal>(node.getFormals());
            Collections.reverse(copy);
            for(PFormal e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getMethodid() != null)
        {
            node.getMethodid().apply(this);
        }
        if(node.getReturntype() != null)
        {
            node.getReturntype().apply(this);
        }
        outAMethodDecl(node);
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
        if(node.getId() != null)
        {
            node.getId().apply(this);
        }
        if(node.getType() != null)
        {
            node.getType().apply(this);
        }
        outAFormal(node);
    }

    public void inABlockStatement(ABlockStatement node)
    {
        defaultIn(node);
    }

    public void outABlockStatement(ABlockStatement node)
    {
        defaultOut(node);
    }

    @Override
    public void caseABlockStatement(ABlockStatement node)
    {
        inABlockStatement(node);
        {
            List<PStatement> copy = new ArrayList<PStatement>(node.getStatement());
            Collections.reverse(copy);
            for(PStatement e : copy)
            {
                e.apply(this);
            }
        }
        outABlockStatement(node);
    }

    public void inAWhileStatement(AWhileStatement node)
    {
        defaultIn(node);
    }

    public void outAWhileStatement(AWhileStatement node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAWhileStatement(AWhileStatement node)
    {
        inAWhileStatement(node);
        if(node.getStatement() != null)
        {
            node.getStatement().apply(this);
        }
        if(node.getExp() != null)
        {
            node.getExp().apply(this);
        }
        outAWhileStatement(node);
    }

    public void inAIfStatement(AIfStatement node)
    {
        defaultIn(node);
    }

    public void outAIfStatement(AIfStatement node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAIfStatement(AIfStatement node)
    {
        inAIfStatement(node);
        if(node.getElsestm() != null)
        {
            node.getElsestm().apply(this);
        }
        if(node.getIfstm() != null)
        {
            node.getIfstm().apply(this);
        }
        if(node.getCondition() != null)
        {
            node.getCondition().apply(this);
        }
        outAIfStatement(node);
    }

    public void inAPrintStatement(APrintStatement node)
    {
        defaultIn(node);
    }

    public void outAPrintStatement(APrintStatement node)
    {
        defaultOut(node);
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

    public void inAAssignStatement(AAssignStatement node)
    {
        defaultIn(node);
    }

    public void outAAssignStatement(AAssignStatement node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAAssignStatement(AAssignStatement node)
    {
        inAAssignStatement(node);
        if(node.getRightexp() != null)
        {
            node.getRightexp().apply(this);
        }
        if(node.getId() != null)
        {
            node.getId().apply(this);
        }
        outAAssignStatement(node);
    }

    public void inAArrayAssignStatement(AArrayAssignStatement node)
    {
        defaultIn(node);
    }

    public void outAArrayAssignStatement(AArrayAssignStatement node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAArrayAssignStatement(AArrayAssignStatement node)
    {
        inAArrayAssignStatement(node);
        if(node.getRightexp() != null)
        {
            node.getRightexp().apply(this);
        }
        if(node.getIndexexp() != null)
        {
            node.getIndexexp().apply(this);
        }
        if(node.getId() != null)
        {
            node.getId().apply(this);
        }
        outAArrayAssignStatement(node);
    }

    public void inAAndExp(AAndExp node)
    {
        defaultIn(node);
    }

    public void outAAndExp(AAndExp node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAAndExp(AAndExp node)
    {
        inAAndExp(node);
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        outAAndExp(node);
    }

    public void inALessthanExp(ALessthanExp node)
    {
        defaultIn(node);
    }

    public void outALessthanExp(ALessthanExp node)
    {
        defaultOut(node);
    }

    @Override
    public void caseALessthanExp(ALessthanExp node)
    {
        inALessthanExp(node);
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        outALessthanExp(node);
    }

    public void inAPlusExp(APlusExp node)
    {
        defaultIn(node);
    }

    public void outAPlusExp(APlusExp node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAPlusExp(APlusExp node)
    {
        inAPlusExp(node);
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        outAPlusExp(node);
    }

    public void inAMinusExp(AMinusExp node)
    {
        defaultIn(node);
    }

    public void outAMinusExp(AMinusExp node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAMinusExp(AMinusExp node)
    {
        inAMinusExp(node);
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        outAMinusExp(node);
    }

    public void inATimesExp(ATimesExp node)
    {
        defaultIn(node);
    }

    public void outATimesExp(ATimesExp node)
    {
        defaultOut(node);
    }

    @Override
    public void caseATimesExp(ATimesExp node)
    {
        inATimesExp(node);
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        outATimesExp(node);
    }

    public void inANotExp(ANotExp node)
    {
        defaultIn(node);
    }

    public void outANotExp(ANotExp node)
    {
        defaultOut(node);
    }

    @Override
    public void caseANotExp(ANotExp node)
    {
        inANotExp(node);
        if(node.getExp() != null)
        {
            node.getExp().apply(this);
        }
        outANotExp(node);
    }

    public void inAArraylengthExp(AArraylengthExp node)
    {
        defaultIn(node);
    }

    public void outAArraylengthExp(AArraylengthExp node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAArraylengthExp(AArraylengthExp node)
    {
        inAArraylengthExp(node);
        if(node.getExp() != null)
        {
            node.getExp().apply(this);
        }
        outAArraylengthExp(node);
    }

    public void inACallExp(ACallExp node)
    {
        defaultIn(node);
    }

    public void outACallExp(ACallExp node)
    {
        defaultOut(node);
    }

    @Override
    public void caseACallExp(ACallExp node)
    {
        inACallExp(node);
        {
            List<PExp> copy = new ArrayList<PExp>(node.getArgs());
            Collections.reverse(copy);
            for(PExp e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getName() != null)
        {
            node.getName().apply(this);
        }
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        outACallExp(node);
    }

    public void inAArraylookupExp(AArraylookupExp node)
    {
        defaultIn(node);
    }

    public void outAArraylookupExp(AArraylookupExp node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAArraylookupExp(AArraylookupExp node)
    {
        inAArraylookupExp(node);
        if(node.getRight() != null)
        {
            node.getRight().apply(this);
        }
        if(node.getLeft() != null)
        {
            node.getLeft().apply(this);
        }
        outAArraylookupExp(node);
    }

    public void inAIntegerliteralExp(AIntegerliteralExp node)
    {
        defaultIn(node);
    }

    public void outAIntegerliteralExp(AIntegerliteralExp node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAIntegerliteralExp(AIntegerliteralExp node)
    {
        inAIntegerliteralExp(node);
        if(node.getInteger() != null)
        {
            node.getInteger().apply(this);
        }
        outAIntegerliteralExp(node);
    }

    public void inATrueExp(ATrueExp node)
    {
        defaultIn(node);
    }

    public void outATrueExp(ATrueExp node)
    {
        defaultOut(node);
    }

    @Override
    public void caseATrueExp(ATrueExp node)
    {
        inATrueExp(node);
        outATrueExp(node);
    }

    public void inAFalseExp(AFalseExp node)
    {
        defaultIn(node);
    }

    public void outAFalseExp(AFalseExp node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAFalseExp(AFalseExp node)
    {
        inAFalseExp(node);
        outAFalseExp(node);
    }

    public void inAIdentifierexpExp(AIdentifierexpExp node)
    {
        defaultIn(node);
    }

    public void outAIdentifierexpExp(AIdentifierexpExp node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAIdentifierexpExp(AIdentifierexpExp node)
    {
        inAIdentifierexpExp(node);
        if(node.getId() != null)
        {
            node.getId().apply(this);
        }
        outAIdentifierexpExp(node);
    }

    public void inAThisExp(AThisExp node)
    {
        defaultIn(node);
    }

    public void outAThisExp(AThisExp node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAThisExp(AThisExp node)
    {
        inAThisExp(node);
        outAThisExp(node);
    }

    public void inANewarrayExp(ANewarrayExp node)
    {
        defaultIn(node);
    }

    public void outANewarrayExp(ANewarrayExp node)
    {
        defaultOut(node);
    }

    @Override
    public void caseANewarrayExp(ANewarrayExp node)
    {
        inANewarrayExp(node);
        if(node.getExp() != null)
        {
            node.getExp().apply(this);
        }
        outANewarrayExp(node);
    }

    public void inANewobjectExp(ANewobjectExp node)
    {
        defaultIn(node);
    }

    public void outANewobjectExp(ANewobjectExp node)
    {
        defaultOut(node);
    }

    @Override
    public void caseANewobjectExp(ANewobjectExp node)
    {
        inANewobjectExp(node);
        if(node.getId() != null)
        {
            node.getId().apply(this);
        }
        outANewobjectExp(node);
    }
}
