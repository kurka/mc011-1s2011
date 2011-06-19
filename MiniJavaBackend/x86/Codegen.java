package x86;

import assem.Instr;
import tree.*;
import temp.*;
import util.List;

/**
 * Performs instruction selection for x86.
 *
 * Intermediate Representation (IR) has the following possible node types:
 *  - expstm
 *  - stm
 *  - exp
 *  - label
 *  - move
 *  - temp
 *  - call
 *  - name
 *  - const
 *  - jump
 */
public class Codegen {

  Frame frame;
  public Codegen(Frame f) {
    frame = f;
  }

  private List<Instr> ilist = null;
  private List<Instr> last = null;

  private void emit(Instr inst) {
    if (last != null) {
      last = last.tail = new List<Instr>(inst,null);
    }
    else {
      last = ilist = new List<Instr>(inst,null);
    }
  }

  private void munchStm (Stm s) {
    if (s instanceof MOVE) {
			munchMove((MOVE) s);
    }
		else if (s instanceof EXPSTM) {
			munchExpStm((EXPSTM) s);
    }
		else if (s instanceof CJUMP) {
			munchCjump((CJUMP) s);
    }
		else if (s instanceof LABEL) {
			munchLabel((LABEL) s);
    }
		else if (s instanceof JUMP) {
			munchJump((JUMP) s);
    }
		else {
			throw new Error("Unhandled: " + s.getClass());
    }
  }

  private void munchMove(MOVE s) {
    if (s.dst instanceof MEM) {
      munchMove((MEM) s.dst, s.src);
    }
    else {
      munchMove((TEMP) s.dst, s.src);
    }
  }

  /**
   * mov [`s0] `s1
   */
  private void munchMove(MEM d, Exp s) {
    Temp val = munchExp(s);
    Temp address = munchExp(d.exp);

    emit(new assem.OPER("mov [`s0], `s1",
                        null,
                        new List<Temp>(address, new List<Temp>(val, null))));
    return;
  }

  /**
   * mov `d0 `s0
   */
  private void munchMove(TEMP d, Exp s) {
    Temp val = munchExp(s);
    emit(new assem.OPER("mov `d0, `s0",
                        new List<Temp>(d.temp, null),
                        new List<Temp>(val, null)));
    return;
  }

  private void munchExpStm(EXPSTM s) {
    munchExp(s.exp);
  }

  private void munchCjump(CJUMP s) {
    //TODO
  }
  private void munchLabel(LABEL s) {
    //TODO
  }
  private void munchJump(JUMP s) {
    if (s.exp instanceof NAME) {
      NAME l = (NAME) s.exp;
      emit(new assem.OPER("jmp 'j0",
                          null,
                          null,
                          new List<Label>(l.label, null)));
    }
    else {
      Temp target = munchExp(s.exp);
      emit(new assem.OPER("jmp 's0",
                          null,
                          new List<Temp>(target,null),
                          s.targets));
    }
    return;
  }

  /**
   * Generic method that call specific methods 
   * in order to tile an Exp.
   */
  private Temp munchExp(Exp e) {
    if (e instanceof BINOP) {
      return munchExpBinop((BINOP) e);
    }
    else if (e instanceof CALL) {
      return munchExpCall((CALL) e);
    }
    else if (e instanceof CONST) {
      return munchExpConst((CONST) e);
    }
    else if (e instanceof ESEQ) {
      return munchExpEseq((ESEQ) e);
    }
    else if (e instanceof MEM) {
      return munchExpMem((MEM) e);
    }
    else if (e instanceof NAME) {
      return munchExpName((NAME) e);
    }
    else if (e instanceof TEMP) {
      //If only remain (e instanceof TEMP), directly create a new Temp
      return new Temp();
    }
    else {
      throw new Error("Unexpected: " + e.getClass());
    }
    return null; // this will never happen, but javac requires this return
  }

  /**
   * Tile an individual CONST Exp
   */
  private Temp munchExpConst(CONST e) {
    Temp ret = new Temp();
    String asm = sprintf("mov `d0 %l", e.value);
    emit(new assem.MOVE(asm, ret, null));
    return ret;
  }

  private Temp munchExpBinop(BINOP e) {
    Temp left = munchExp(e.left);
    Temp right = munchExp(e.right);

    // Por enquanto, trata apenas as SOMAS
    if (e.binop == BINOP.PLUS){
		munchExpBinopAdd(e, left, right);
    }

    return left;
  }
	
  private void munchExpBinopAdd(BINOP e, Temp left, Temp right){
    if(right instanceof CONST){
      String asm = String.format("addi `d0 %ld", right.value)
        emit(new assem.OPER(asm,
                          new List<Temp>(left, null),
                          new List<Temp>(left, null)));
    }
    else{
      emit(new assem.OPER("addi `d0 `s0",
                        new List<Temp>(left, null),
                        new List<Temp>(left, new List<Temp>(right, null))));
    }
    return;
  }
       
  
  private Temp munchExpCall(CALL e){
  //TODO
  }
  private Temp munchExpConst(CONST e){
  //TODO
  }
  private Temp munchExpEseq(ESEQ e){
  //TODO
  }
  private Temp munchExpMem(MEM e){
  //TODO
  }
  private Temp munchExpName(NAME e){
  //TODO
  }

  /*-------------------------------------------------------------*
   *                              MAIN                           *
   *-------------------------------------------------------------*/
  List<Instr> codegen(Stm s) {
    List<Instr> l;
    munchStm(s);
    l = ilist;
    ilist = last = null;
    return l;
  }

  List<Instr> codegen(List<Stm> body) {
    List<Instr> l = null, t = null;

    for( ; body != null; body = body.tail ) {
      munchStm(body.head);
      if (l == null) {
        l = ilist;
      }
      else {
        t.tail = ilist;
      }
      t = last;
      ilist=last=null;
    }
    return l;
  }
}
