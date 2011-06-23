package x86;

import assem.Instr;
import tree.*;
import temp.*;
import util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Performs instruction selection for x86.
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

  /**
   * Generic method to "tile" a Statement
   */
  private void munchStm(Stm s) {
    System.out.println(">munchStm");
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
    else if (s instanceof SEQ) {
      munchSeq((SEQ) s);
    }
    else {
      throw new Error("Unhandled: " + s.getClass());
    }
    System.out.println(">saindo do munchStm");
  }

  /**
   * STM MOVE
   */
  private void munchMove(MOVE s) {
    System.out.println("munchMove");
    if (s.dst instanceof MEM) {
      munchMove((MEM) s.dst, s.src);
    }
    else {
      munchMove((TEMP) s.dst, s.src);
    }
  }

  /**
   * STM MOVE
   * MEM <- VALUE
   */
  private void munchMove(MEM d, Exp s) {
    System.out.println("munchMoveMem");
    Temp val = munchExp(s);
    Temp address = munchExp(d.exp);

    emit(new assem.OPER("mov [`s0], `s1",
                        null,
                        new List<Temp>(address, new List<Temp>(val, null))));
    return;
  }

  /**
   * STM MOVE
   * TEMP <- VALUE
   */
  private void munchMove(TEMP d, Exp s) {
    System.out.println("munchMoveTemp");
    Temp val = munchExp(s);
    emit(new assem.MOVE("mov `d0, `s0",
                        d.temp, val));
    return;
  }

  /**
   * STM EXPSTM
   */
  private void munchExpStm(EXPSTM s) {
    System.out.println("munchExpStm");
    munchExp(s.exp);
  }

  /**
   * STM CJUMP
   */
  private void munchCjump(CJUMP s) {
    System.out.println("munchCjump");
    Temp left = munchExp(s.left);
    Temp right = munchExp(s.right);

    // Emit cmp instruction, to set the Machine Status Word.
    emit(new assem.OPER("cmp `s0 `s1",
                        null,
                        new List<Temp>(left, new List<Temp>(right, null))));

    // Build a dictionary to relate CJUMP comparison type and x86 instruction.
    HashMap<Integer,String> dict = new HashMap<Integer, String>();
    dict.put(CJUMP.EQ, "je");
    dict.put(CJUMP.NE, "jne");
    dict.put(CJUMP.LT, "jl");
    dict.put(CJUMP.LE, "jle");
    dict.put(CJUMP.GT, "jg");
    dict.put(CJUMP.GE, "jge");

    // Now,  find which instruction we need
    String instr = dict.get(s.op);
    if (instr == null) {
      throw new Error("Invalid CJUMP op: " + s.op);
    }

    // Emit cjump instruction ("cjump LABEL") using ifTrue Label
    emit(new assem.OPER(instr + " `j0",
                        null,
                        null,
                        new List<Label>(s.ifTrue, null)));
  }

  /**
   * STM LABEL
   */
  private void munchLabel(LABEL s) {
    System.out.println("munchLabel");
    emit(new assem.LABEL(s.label.toString() + ":", s.label));
  }

  /**
   * STM JUMP
   */
  private void munchJump(JUMP s) {
    System.out.println("munchJump");
    if (s.exp instanceof NAME) {
      NAME l = (NAME) s.exp;
      emit(new assem.OPER("jmp `j0",
                          null,
                          null,
                          new List<Label>(l.label, null)));
    }
    else {
      Temp target = munchExp(s.exp);
      emit(new assem.OPER("jmp `s0",
                          null,
                          new List<Temp>(target,null),
                          s.targets));
    }
  }

  /**
   * STM SEQ
   *
   * Eg.:
   * b = 0;
   * (a = b + 10, b++); <- SEQ Stm
   * No return value
   */
  private void munchSeq(SEQ s) {
    munchStm(s.left);
    munchStm(s.right);
  }

  /**
   * Generic method that call specific methods
   * in order to tile an Exp.
   */
  private Temp munchExp(Exp e) {
    System.out.println("munchExp");
    if (e instanceof BINOP) {
      return munchBinop((BINOP) e);
    }
    else if (e instanceof CALL) {
      return munchCall((CALL) e);
    }
    else if (e instanceof CONST) {
      return munchConst((CONST) e);
    }
    else if (e instanceof ESEQ) {
      return munchEseq((ESEQ) e);
    }
    else if (e instanceof MEM) {
      return munchMem((MEM) e);
    }
    else if (e instanceof NAME) {
      return munchName((NAME) e);
    }
    else if (e instanceof TEMP) {
      //If only remain (e instanceof TEMP), directly create a new Temp
      TEMP t = (TEMP) e;
      return t.temp;
    }
    else {
      throw new Error("Unexpected: " + e.getClass());
    }
  }

  /**
   * EXP CONST
   */
  private Temp munchConst(CONST e) {
    System.out.println("munchConst");
    Temp ret = new Temp();
    emit(new assem.OPER("mov `d0, " + e.value,
                    new List<Temp>(ret,null),
                    null));
    return ret;
  }

  /**
   * EXP BINOP
   */
  private Temp munchBinop(BINOP e) {
    System.out.println("munchBinop");
    String instrstr;

    //get instruction name
    if (e.binop == BINOP.PLUS){
		instrstr = "add";
    }
    else if (e.binop == BINOP.MINUS){
        instrstr = "sub";
    }
    else if (e.binop == BINOP.TIMES){
        instrstr = "imul";
    }
    else if (e.binop == BINOP.DIV){
        instrstr = "idiv";
    }
    else if (e.binop == BINOP.AND){
        instrstr = "and";
    }
    else if (e.binop == BINOP.OR){
        instrstr = "or";
    }
    else if (e.binop == BINOP.LSHIFT){
        instrstr = "shl";
    }
    else if (e.binop == BINOP.RSHIFT){
        instrstr = "shr";
    }
    else if (e.binop == BINOP.ARSHIFT){
        instrstr = "sar"; //??
    }
    else if (e.binop == BINOP.XOR){
        instrstr = "xor";
    }
    else {
      throw new Error("Unexpected: " + e.getClass() + " in munchBinop");
    }


    //FIXME: dumb way. don't need to call munchExp and get a new temporary everytime.
    Temp left = munchExp(e.left);
    Temp right = munchExp(e.right);

    String asm = String.format(instrstr + " `d0, `s1");

    emit(new assem.OPER(asm,
                        new List<Temp>(left, null),
                        new List<Temp>(left, new List<Temp>(right, null))));
    return left;
  }

  /**
   * EXP ESEQ
   */
  private Temp munchEseq(ESEQ e) {
    System.out.println("munchEseq");
    munchStm(e.stm);
    return munchExp(e.exp);
  }

  /**
   * EXP CALL
   */
  private Temp munchCall(CALL e) {
    System.out.println("munchCall");
    //find the amount of params that will go to stack
    ArrayList<Exp> reversedParams = new ArrayList<tree.Exp>();
    int numArgs = 0;

    for( List<Exp> aux = e.args; aux != null; aux = aux.tail ){
      numArgs += 1;
      reversedParams.add(0, aux.head);
    }

    //put the params in stack, in reverse order
    for(Exp param : reversedParams){
      Temp p = munchExp(param);
      emit(new assem.OPER("push `s0",
                        null,
                        new List<Temp>(p, null)));
    }

    //do the call expression
    Temp funcAdd = munchExp(e.func);//FIXME: dumb way
    emit(new assem.OPER("call `s0",
                        null,
                        new List<Temp>(funcAdd, frame.calldefs)));


    //restore the stack
    emit(new assem.OPER("add esp, " + (numArgs*4),
                        new List<Temp>(frame.esp, null),
                        new List<Temp>(frame.esp, null)));
    //get the return value
    Temp ret = new Temp();
    emit(new assem.MOVE("mov `d0, eax", ret, frame.eax));
    return ret;
  }

  /**
   * EXP MEM
   */
  private Temp munchMem(MEM e) {
    System.out.println("munchMem");
    Temp ret = new Temp();
    Temp exp = munchExp(e.exp);//FIXME: there are more efficient ways to solve the expression
    emit(new assem.OPER("mov `d0, [`s0]",
                        new List<Temp>(ret, null),
                        new List<Temp>(exp, null)));
    return ret;
  }

  /**
   * EXP NAME
   */
  private Temp munchName(NAME e) {
    System.out.println("munchName");
    Temp ret = new Temp();
    emit(new assem.OPER("mov `d0, " + e.label.toString(),
                        new List<Temp>(ret, null),
                        null));
    return ret;
  }



  /*-------------------------------------------------------------*
   *                              MAIN                           *
   *-------------------------------------------------------------*/
  List<Instr> codegen(Stm s) {
    System.out.println("1 codegen");
	List<Instr> l;
    munchStm(s);
    l = ilist;
    ilist = last = null;
    return l;
  }

  List<Instr> codegen(List<Stm> body) {
    System.out.println("2 codegen");
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
    System.out.println("saindo do codegen 2");
    return l;
  }
}
