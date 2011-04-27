/* This file was generated by SableCC (http://www.sablecc.org/). */

package minijava.node;

import java.util.*;
import minijava.analysis.*;

@SuppressWarnings("nls")
public final class AClassDeclSimpleClassDecl extends PClassDecl
{
    private TId _classname_;
    private final LinkedList<PVarDecl> _vars_ = new LinkedList<PVarDecl>();
    private final LinkedList<PMethodDecl> _methods_ = new LinkedList<PMethodDecl>();

    public AClassDeclSimpleClassDecl()
    {
        // Constructor
    }

    public AClassDeclSimpleClassDecl(
        @SuppressWarnings("hiding") TId _classname_,
        @SuppressWarnings("hiding") List<PVarDecl> _vars_,
        @SuppressWarnings("hiding") List<PMethodDecl> _methods_)
    {
        // Constructor
        setClassname(_classname_);

        setVars(_vars_);

        setMethods(_methods_);

    }

    @Override
    public Object clone()
    {
        return new AClassDeclSimpleClassDecl(
            cloneNode(this._classname_),
            cloneList(this._vars_),
            cloneList(this._methods_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAClassDeclSimpleClassDecl(this);
    }

    public TId getClassname()
    {
        return this._classname_;
    }

    public void setClassname(TId node)
    {
        if(this._classname_ != null)
        {
            this._classname_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._classname_ = node;
    }

    public LinkedList<PVarDecl> getVars()
    {
        return this._vars_;
    }

    public void setVars(List<PVarDecl> list)
    {
        this._vars_.clear();
        this._vars_.addAll(list);
        for(PVarDecl e : list)
        {
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
        }
    }

    public LinkedList<PMethodDecl> getMethods()
    {
        return this._methods_;
    }

    public void setMethods(List<PMethodDecl> list)
    {
        this._methods_.clear();
        this._methods_.addAll(list);
        for(PMethodDecl e : list)
        {
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
        }
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._classname_)
            + toString(this._vars_)
            + toString(this._methods_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._classname_ == child)
        {
            this._classname_ = null;
            return;
        }

        if(this._vars_.remove(child))
        {
            return;
        }

        if(this._methods_.remove(child))
        {
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._classname_ == oldChild)
        {
            setClassname((TId) newChild);
            return;
        }

        for(ListIterator<PVarDecl> i = this._vars_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PVarDecl) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        for(ListIterator<PMethodDecl> i = this._methods_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PMethodDecl) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        throw new RuntimeException("Not a child.");
    }
}
