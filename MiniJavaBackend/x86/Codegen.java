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
    System.out.println("entrando em munchStm");
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
    System.out.println("saindo de munchStm");
  }

  /**
   * STM MOVE
   */
  private void munchMove(MOVE s) {
    System.out.println("entrando em munchMove");
    if (s.dst instanceof MEM) {
      munchMove((MEM) s.dst, s.src);
    }
    else if (s.dst instanceof TEMP) {
      munchMove((TEMP) s.dst, s.src);
    }
    else {
      throw new Error("Error on Move");
    }
    System.out.println("saindo de munchMove");
  }

  /**
   * STM MOVE
   * MEM <- VALUE
   */
  private void munchMove(MEM d, Exp s) {
    System.out.println("entrando em munchMoveMem");

    // Calculate VALUE and save it in a new Temp
    Temp val = munchExp(s);

    // Case 1: MEM is addressed by an arithmetic expression involving REG and CONST
    // On this case, do NOT create a new Temp.
    // Instead, use the [reg + const] assembly notation
    if (d.exp instanceof BINOP) {
      BINOP op = (BINOP) d.exp;
      switch (op.binop) {
        case BINOP.PLUS:
          // [REG + CONST]
          if ((op.left instanceof TEMP) && (op.right instanceof CONST)) {
            TEMP t = (TEMP) op.left;
            CONST c = (CONST) op.right;
            String assem = String.format("mov [`s0 + %d], `s1", c.value);
            emit(new assem.OPER(assem,
                                null,
                                new List<Temp>(t.temp, new List<Temp>(val, null))));
          }
          // [CONST + REG]
          else if ((op.left instanceof CONST) && (op.right instanceof TEMP)) {
            CONST c = (CONST) op.left;
            TEMP t = (TEMP) op.right;
            String assem = String.format("mov [%d + `s0], `s1", c.value);
            emit(new assem.OPER(assem,
                                null,
                                new List<Temp>(t.temp, new List<Temp>(val, null))));
          }
          break;
        case BINOP.MINUS:
          // [REG - CONST]
          if ((op.left instanceof TEMP) && (op.right instanceof CONST)) {
            TEMP t = (TEMP) op.left;
            CONST c = (CONST) op.right;
            String assem = String.format("mov [`s0 - %d], `s1", c.value);
            emit(new assem.OPER(assem,
                                null,
                                new List<Temp>(t.temp, new List<Temp>(val, null))));
          }
          // [CONST - REG]
          else if ((op.left instanceof CONST) && (op.right instanceof TEMP)) {
            CONST c = (CONST) op.left;
            TEMP t = (TEMP) op.right;
            String assem = String.format("mov [%d - `s0], `s1", c.value);
            emit(new assem.OPER(assem,
                                null,
                                new List<Temp>(t.temp, new List<Temp>(val, null))));
          }
          break;
      }
    }

    // Case 2: We must calculate address expression and save it in Temp
    else {
      Temp address = munchExp(d.exp);

      emit(new assem.OPER("mov [`s0], `s1",
                          null,
                          new List<Temp>(address, new List<Temp>(val, null))));
    }
    System.out.println("saindo de munchMoveMem");
  }

  /**
   * STM MOVE
   * TEMP <- VALUE
   */
  private void munchMove(TEMP d, Exp s) {
    System.out.println("entrando em munchMoveTemp");
    Temp val = munchExp(s);
    emit(new assem.MOVE("mov `d0, `s0",
                        d.temp, val));
    System.out.println("saindo de munchMoveTemp");
  }

  /**
   * STM EXPSTM
   */
  private void munchExpStm(EXPSTM s) {
    System.out.println("entrando em munchExpStm");
    munchExp(s.exp);
    System.out.println("saindo de munchExpStm");
  }

  /**
   * STM CJUMP
   */
  private void munchCjump(CJUMP s) {
    System.out.println("entrando em munchCjump");
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
    System.out.println("saindo de munchCjump");
  }

  /**
   * STM LABEL
   */
  private void munchLabel(LABEL s) {
    System.out.println("entrando em munchLabel");
    emit(new assem.LABEL(s.label.toString() + ":", s.label));
    System.out.println("saindo de munchLabel");
  }

  /**
   * STM JUMP
   */
  private void munchJump(JUMP s) {
    System.out.println("entrando em munchJump");
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
    System.out.println("saindo de munchJump");
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
    System.out.println("entrando em munchSeq");
    munchStm(s.left);
    munchStm(s.right);
    System.out.println("saindo de munchSeq");
  }

  /**
   * Generic method that call specific methods
   * in order to tile an Exp.
   */
  private Temp munchExp(Exp e) {
    System.out.println("entrando em munchExp");
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
      return munchTemp((TEMP) e);
    }
    else {
      throw new Error("Unexpected: " + e.getClass());
    }
  }

  /**
   * EXP CONST
   */
  private Temp munchConst(CONST e) {
    System.out.println("entrando em munchConst");
    Temp ret = new Temp();
    emit(new assem.OPER("mov `d0, " + e.value,
                    new List<Temp>(ret,null),
                    null));
    System.out.println("saindo de munchConst");
    return ret;
  }

  /**
   * EXP BINOP
   */
  private Temp munchBinop(BINOP e) {
    System.out.println("entrando em munchBinop");
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
    System.out.println("saindo de munchBinop");
    return left;
  }

  /**
   * EXP ESEQ
   */
  private Temp munchEseq(ESEQ e) {
    System.out.println("entrando em munchEseq");
    munchStm(e.stm);
    System.out.println("saindo de munchEseq");
    return munchExp(e.exp);
  }

  /**
   * EXP CALL
   */
  private Temp munchCall(CALL e) {
    System.out.println("entrando em munchCall");
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
    System.out.println("saindo de munchCall");
    return ret;
  }

  /**
   * EXP MEM
   */
  private Temp munchMem(MEM e) {
    System.out.println("entrando em munchMem");
    Temp ret = new Temp();
    Temp exp = munchExp(e.exp);//FIXME: there are more efficient ways to solve the expression
    // TODO [eax +- 12]
    emit(new assem.OPER("mov `d0, [`s0]",
                        new List<Temp>(ret, null),
                        new List<Temp>(exp, null)));
    System.out.println("saindo de munchMem");
    return ret;
  }

  /**
   * EXP NAME
   */
  private Temp munchName(NAME e) {
    System.out.println("entrando em munchName");
    Temp ret = new Temp();
    emit(new assem.OPER("mov `d0, " + e.label.toString(),
                        new List<Temp>(ret, null),
                        null));
    System.out.println("saindo de munchName");
    return ret;
  }

  /**
   * EXP TEMP
   */
  private Temp munchTemp(TEMP e) {
    System.out.println("entrando em munchTemp");
    System.out.println("saindo de munchTemp");
    return e.temp;
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
