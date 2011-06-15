package x86;

import assem.Instr;
import tree.*;
import temp.*;
import util.List;

public class Codegen
{
    Frame frame;
    public Codegen(Frame f)
    {
        frame=f;        
    }

    private List<Instr> ilist=null;
    private List<Instr> last=null;

    private void emit(Instr inst)
    {
        if (last!=null)
            last = last.tail = new List<Instr>(inst,null);
        else 
            last = ilist = new List<Instr>(inst,null);
    }

    private void munchStm (Stm s) {
		if(s instanceof MOVE)
			munchMove((MOVE) s);
		else if(s instanceof EXPSTM)
			munchExpStm((EXPSTM) s);
		else if(s instanceof CJUMP)
			munchCjump((CJUMP) s);
		else if(s instanceof LABEL)
			munchLabel((LABEL) s);
		else if(s instanceof JUMP)
			munchJump((JUMP) s);
		else
			throw new Error("Unhandled: " + s.getClass());
    }
	
	private void munchMove(MOVE s){
		if(s.dst instanceof MEM)
			munchMove((MEM) s.dst, s.src);
		else 
			munchMove((TEMP) s.dst, s.src);
		return;
	}

	private void munchMove(MEM d, Exp s){
		Temp val = munchExp(s);
		Temp add = munchExp(d.exp);

		emit(new assem.OPER("mov ['s0], 's1",
			null,
			new List<Temp>(add, new List<Temp>(val, null))));
		return;
	}

	private void munchMove(TEMP d, Exp s){
		Temp val = munchExp(s);
		
		emit(new assem.OPER("mov 'd0, 's0",
							new List<Temp>(d, null),
							new List<Temp>(val, null)));
		return;
	}	

	private void munchExpStm(EXPSTM s){
		Temp exp = munchExp(s);
		return;
	}

	private void munchCjump(CJUMP s){
		//TODO
	}
	private void munchLabel(LABEL s){
		//TODO
	}
	private void munchJump(JUMP s){
		if(s.exp instanceof NAME){
			NAME l = (NAME) s.exp;
			emit(new assem.OPER("jmp 'j0", 
							null,
							null,
							new List<Label>(l.label, null)));
		}
		else{
			Temp target = munchExp(s.exp);
			emit(new assem.OPER("jmp 's0",
							null,
							new List<Temp>(target,null),
							s.targets));
		}
		return;
	}
    
	private Temp munchExp(Exp e){
		//TODO
	}


    /*-------------------------------------------------------------*
     *                              MAIN                           *
     *-------------------------------------------------------------*/
    List<Instr> codegen(Stm s)
    {
        List<Instr> l;
        munchStm(s);
        l=ilist;
        ilist=last=null;
        return l;
    }
    
    List<Instr> codegen(List<Stm> body)
    {
        List<Instr> l = null, t = null;
        
        for( ; body != null; body = body.tail )
        {
            munchStm(body.head);
            if ( l == null )
                l = ilist;
            else
                t.tail = ilist;
            t = last;
            ilist=last=null;
        }
        return l;
    }
}
