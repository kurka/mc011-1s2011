/* This file was generated by SableCC (http://www.sablecc.org/). */

package minijava.node;

import minijava.analysis.*;

@SuppressWarnings("nls")
public final class AArrayAssignStatement extends PStatement
{
    private TId _id_;
    private PExp _indexexp_;
    private PExp _rightexp_;

    public AArrayAssignStatement()
    {
        // Constructor
    }

    public AArrayAssignStatement(
        @SuppressWarnings("hiding") TId _id_,
        @SuppressWarnings("hiding") PExp _indexexp_,
        @SuppressWarnings("hiding") PExp _rightexp_)
    {
        // Constructor
        setId(_id_);

        setIndexexp(_indexexp_);

        setRightexp(_rightexp_);

    }

    @Override
    public Object clone()
    {
        return new AArrayAssignStatement(
            cloneNode(this._id_),
            cloneNode(this._indexexp_),
            cloneNode(this._rightexp_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAArrayAssignStatement(this);
    }

    public TId getId()
    {
        return this._id_;
    }

    public void setId(TId node)
    {
        if(this._id_ != null)
        {
            this._id_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._id_ = node;
    }

    public PExp getIndexexp()
    {
        return this._indexexp_;
    }

    public void setIndexexp(PExp node)
    {
        if(this._indexexp_ != null)
        {
            this._indexexp_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._indexexp_ = node;
    }

    public PExp getRightexp()
    {
        return this._rightexp_;
    }

    public void setRightexp(PExp node)
    {
        if(this._rightexp_ != null)
        {
            this._rightexp_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._rightexp_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._id_)
            + toString(this._indexexp_)
            + toString(this._rightexp_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._id_ == child)
        {
            this._id_ = null;
            return;
        }

        if(this._indexexp_ == child)
        {
            this._indexexp_ = null;
            return;
        }

        if(this._rightexp_ == child)
        {
            this._rightexp_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._id_ == oldChild)
        {
            setId((TId) newChild);
            return;
        }

        if(this._indexexp_ == oldChild)
        {
            setIndexexp((PExp) newChild);
            return;
        }

        if(this._rightexp_ == oldChild)
        {
            setRightexp((PExp) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
