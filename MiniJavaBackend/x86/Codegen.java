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
  }

  /**
   * STM MOVE
   */
  private void munchMove(MOVE s) {
    if (s.dst instanceof MEM) {
      munchMove((MEM) s.dst, s.src);
    }
    else if (s.dst instanceof TEMP) {
      munchMove((TEMP) s.dst, s.src);
    }
    else {
      throw new Error("Error on Move");
    }
  }

  /**
   * STM MOVE
   * MEM <- VALUE
   */
  private void munchMove(MEM d, Exp s) {

    // Calculate VALUE and save it in a new Temp
    Temp val = munchExp(s);

    // Case 1: MEM is addressed by an arithmetic expression involving REG and CONST
    // On this case, do NOT create a new Temp.
    // Instead, use the [reg + const] assembly notation
    if (d.exp instanceof BINOP) {
      BINOP op = (BINOP) d.exp;
      String assem;

      // [REG + CONST]
      if ((op.binop == BINOP.PLUS) && (op.left instanceof TEMP) && (op.right instanceof CONST)) {
        TEMP t = (TEMP) op.left;
        CONST c = (CONST) op.right;
        assem = String.format("mov [`s0+%d], `s1", c.value);
        emit(new assem.OPER(assem,
              null,
              new List<Temp>(t.temp, new List<Temp>(val, null))));
        return;
      }
      // mov [REG + X*REG], REG
      else if((op.left instanceof TEMP || op.left instanceof MEM) && op.right instanceof BINOP){
        BINOP op_shl = (BINOP) op.right;
        Temp t = munchExp(op.left);
        if (op_shl.binop == BINOP.LSHIFT) {
          CONST c = (CONST) op_shl.right;
          if (op_shl.left instanceof CONST) {
            CONST op_shl_left = (CONST) op_shl.left;
            assem = String.format("mov [`s0+%d], `s1", (int)Math.pow(2,c.value)*op_shl_left.value);
            emit(new assem.OPER(assem,
                  null,
                  new List<Temp>(t, new List<Temp>(val, null))));
          }
          else {
            Temp index = munchExp(op_shl.left);
            assem = String.format("mov [`s0+%d*`s1], `s2", (int)Math.pow(2,c.value));
            emit(new assem.OPER(assem,
                  null,
                  new List<Temp>(t, new List<Temp>(index, new List<Temp>(val, null)))));
          }
          return; 
        }
      }
      else if ((op.binop == BINOP.MINUS) && (op.left instanceof TEMP) && (op.right instanceof CONST)) {
        TEMP t = (TEMP) op.left;
        CONST c = (CONST) op.right;
        assem = String.format("mov [`s0-%d], `s1", c.value);
        emit(new assem.OPER(assem,
              null,
              new List<Temp>(t.temp, new List<Temp>(val, null))));
        return;
      }
    }

    // Case 2: We must calculate address expression and save it in Temp
    Temp address = munchExp(d.exp);

    emit(new assem.OPER("mov [`s0], `s1",
          null,
          new List<Temp>(address, new List<Temp>(val, null))));
  }

  /**
   * STM MOVE
   * TEMP <- VALUE
   */
  private void munchMove(TEMP d, Exp s) {
    Temp val = munchExp(s);
    emit(new assem.MOVE("mov `d0, `s0",
                        d.temp, val));
  }

  /**
   * STM EXPSTM
   */
  private void munchExpStm(EXPSTM s) {
    munchExp(s.exp);
  }

  /**
   * STM CJUMP
   */
  private void munchCjump(CJUMP s) {
    Temp left = munchExp(s.left);
    Temp right = munchExp(s.right);

    // Emit cmp instruction, to set the Machine Status Word.
    emit(new assem.OPER("cmp `s0, `s1",
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
                        new List<Label>(s.ifTrue, new List<Label>(s.ifFalse, null))));
  }

  /**
   * STM LABEL
   */
  private void munchLabel(LABEL s) {
    emit(new assem.LABEL(s.label.toString() + ":", s.label));
  }

  /**
   * STM JUMP
   */
  private void munchJump(JUMP s) {
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
      return munchESeq((ESEQ) e);
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
    Temp ret = new Temp();
    emit(new assem.OPER("mov `d0, " + e.value,
                    new List<Temp>(ret, null),
                    null));
    return ret;
  }

  /**
   * EXP BINOP
   */
  private Temp munchBinop(BINOP e) {

    HashMap<Integer,String> dict; //aux
    String assem; //aux String to build assembly code
    Temp right;
    Temp left;
    Temp tmp;
    CONST c;
    
    


    switch (e.binop) {

      /**
       * PLUS
       */
		case BINOP.PLUS:
			
			// ADD REG, IMMED
			if (e.right instanceof CONST) {
              tmp = munchExp(e.left);
              left = new Temp();

              //make the binop operation in two stages (see two address instructions in book's p 151)
              emit(new assem.MOVE("mov `d0, `s0", left, tmp));
              c = (CONST) e.right;
              if(c.value == 1){
                emit(new assem.OPER("inc `d0",
                      new List<Temp>(left, null),
                      new List<Temp>(left, null)));
              }
              else{
                assem = String.format("add `d0, %d", c.value);
                emit(new assem.OPER(assem,
                      new List<Temp>(left, null),
                      new List<Temp>(left, null)));
              }
              return left;
            }
            // ADD IMMED, REG
            if (e.left instanceof CONST) {
              tmp = munchExp(e.right);
              right = new Temp();

              //make the binop operation in two stages (see two address instructions in book's p 151)
              emit(new assem.MOVE("mov `d0, `s0", right, tmp));
              c = (CONST) e.left;
              if(c.value == 1){
                emit(new assem.OPER("inc `d0",
                      new List<Temp>(right, null),
								new List<Temp>(right, null)));
				}
				else{
					assem = String.format("add `d0, %d", c.value);
					emit(new assem.OPER(assem,
								new List<Temp>(right, null),
								new List<Temp>(right, null)));
				}
				return right;
			}
			// ADD REG, REG
            else {
              tmp = munchExp(e.left);
              left = new Temp();

              //make the binop operation in two stages (see two address instructions in book's p 151)
              emit(new assem.MOVE("mov `d0, `s0", left, tmp));
              
              right = munchExp(e.right);
              emit(new assem.OPER("add `d0, `s1",
                    new List<Temp>(left, null),
                    new List<Temp>(left, new List<Temp>(right, null))));
              return left;
            }

      /**
       * LSHIFT and RSHIFT
       */
        case BINOP.LSHIFT:
        case BINOP.RSHIFT:
            tmp = munchExp(e.left);
            left = new Temp();

            //make the binop operation in two stages (see two address instructions in book's p 151)
            emit(new assem.MOVE("mov `d0, `s0", left, tmp));

            c = (CONST) e.right;

            // Build a dictionary to relate BINOP type and x86 instruction.
            dict = new HashMap<Integer, String>();
            dict.put(BINOP.LSHIFT, "shl");
            dict.put(BINOP.RSHIFT, "shr");

            // Now, find which instruction we need and emit it
            assem = dict.get(e.binop) + " `d0, " + c.value;
            emit(new assem.OPER(assem,
                  new List<Temp>(left, null),
                  new List<Temp>(left, null)));
            return left;
            //break;

      /**
       * OTHERS
       */
        case BINOP.MINUS:
        case BINOP.TIMES:
        case BINOP.AND:
        case BINOP.OR:
        case BINOP.XOR:

            tmp = munchExp(e.left);
            left = new Temp();

            //make the binop operation in two stages (see two address instructions in book's p 151)
            emit(new assem.MOVE("mov `d0, `s0", left, tmp));

            // Calculate right value
            right = munchExp(e.right);

            // Build a dictionary to relate BINOP type and x86 instruction.
            dict = new HashMap<Integer, String>();
            dict.put(BINOP.MINUS, "sub");
            dict.put(BINOP.TIMES, "imul");
            dict.put(BINOP.AND, "and");
            dict.put(BINOP.OR, "or");
            dict.put(BINOP.XOR, "xor");

            // Now,  find which instruction we need and emit it
            assem = dict.get(e.binop) + " `d0, `s1";
            emit(new assem.OPER(assem,
                  new List<Temp>(left, null),
                  new List<Temp>(left, new List<Temp>(right, null))));
            return left;
            //break;

        default:
            throw new Error("Unhandled BinOp: " + e.binop);

    } // End of swith

    // Always return left Temp
    //return left;
  }

  /**
   * EXP ESEQ
   */
  private Temp munchESeq(ESEQ e) {
    munchStm(e.stm);
    return munchExp(e.exp);
  }

  /**
   * EXP CALL
   */
  private Temp munchCall(CALL e) {

    // Find the amount of params that will go to the stack
    ArrayList<Exp> reversedParams = new ArrayList<tree.Exp>();
    int numArgs = 0;

    for (List<Exp> aux = e.args; aux != null; aux = aux.tail) {
      numArgs += 1;
      reversedParams.add(0, aux.head);
    }

    // Put the params in stack, in reverse order.
    // Also, build the list of Temp params
    List<Temp> paramsTemp = null;
    for (Exp param : reversedParams) {
      Temp p = munchExp(param);
      paramsTemp = new List<Temp>(p, paramsTemp);
      emit(new assem.OPER("push `s0",
                          new List<Temp>(Frame.esp, null),
                          new List<Temp>(p, new List<Temp>(Frame.esp, null))));
    }

    String assem; // aux

    // Perform direct call whether func is already a NAME
    if (e.func instanceof NAME) {
      NAME n = (NAME) e.func;
      assem = String.format("call %s", n.label.toString());
      emit(new assem.OPER(assem,
                          new List<Temp>(Frame.esp, Frame.calldefs),
                          new List<Temp>(Frame.esp, paramsTemp)));
    }
    // Otherwise, it's necessary to process the call address
    else {
      Temp callAddress = munchExp(e.func);
      emit(new assem.OPER("call `s0",
                          new List<Temp>(Frame.esp, Frame.calldefs),
                          new List<Temp>(callAddress, new List<Temp>(Frame.esp, paramsTemp))));
    }

    // Restore the stack
    if (numArgs != 0) {
      assem = String.format("add esp, %d", (frame.wordsize() * numArgs));
      emit(new assem.OPER(assem,
                          new List<Temp>(Frame.esp, null),
                          new List<Temp>(Frame.esp, null)));
    }

    // Get the return value
    Temp ret = new Temp();
    emit(new assem.MOVE("mov `d0, eax", ret, Frame.eax));
    return ret;
  }

  /**
   * EXP MEM
   */
  private Temp munchMem(MEM e) {
    Temp ret = new Temp();

    // Deal with MOV `d0, [ REG +- CONST ]
    if (e.exp instanceof BINOP) {
      BINOP op = (BINOP) e.exp;

      if (op.binop == BINOP.PLUS){
        // [ REG + CONST ]
        if((op.left instanceof TEMP) && (op.right instanceof CONST)) {
          TEMP t = (TEMP) op.left;
          CONST c = (CONST) op.right;
          String assem = String.format("mov `d0, [`s0+%d]", c.value);
          emit(new assem.OPER(assem,
                new List<Temp>(ret, null),
                new List<Temp>(t.temp, null)));
          return ret;
        }
        // [REG + X*REG]
        else if((op.left instanceof TEMP || op.left instanceof MEM) && op.right instanceof BINOP){
          BINOP op_shl = (BINOP) op.right;
          Temp t = munchExp(op.left);
          if (op_shl.binop == BINOP.LSHIFT) {
            CONST c = (CONST) op_shl.right;
            if (op_shl.left instanceof CONST) {
              CONST op_shl_left = (CONST) op_shl.left;
              String assem = String.format("mov `d0, [`s0+%d]", (int)Math.pow(2, c.value)*op_shl_left.value);
              emit(new assem.OPER(assem,
                    new List<Temp>(ret, null),
                    new List<Temp>(t, null)));
            }
            else {
              Temp index = munchExp(op_shl.left);
              String assem = String.format("mov `d0, [`s0+%d*`s1]", (int)Math.pow(2, c.value));
              emit(new assem.OPER(assem,  
                    new List<Temp>(ret, null),
                    new List<Temp>(t, new List<Temp>(index, null))));
            }
            return ret; 
          }
        }
      }

      // [ REG - CONST ]
      else if ((op.binop == BINOP.MINUS) && (op.left instanceof TEMP) && (op.right instanceof CONST)) {
        TEMP t = (TEMP) op.left;
        CONST c = (CONST) op.right;
        String assem = String.format("mov `d0, [`s0-%d]", c.value);
        emit(new assem.OPER(assem,
              new List<Temp>(ret, null),
              new List<Temp>(t.temp, null)));
        return ret;
      }
    }

    // Deal with common case
    Temp src = munchExp(e.exp);
    emit(new assem.OPER("mov `d0, [`s0]", 
          new List<Temp>(ret, null),
          new List<Temp>(src, null)));
    return ret;
  }


  /**
   * EXP NAME
   */
  private Temp munchName(NAME e) {
    Temp ret = new Temp();
    String assem = String.format("mov `d0, %s", e.label.toString());
    emit(new assem.OPER(assem,
                        new List<Temp>(ret, null),
                        null));
    return ret;
  }

  /**
   * EXP TEMP
   */
  private Temp munchTemp(TEMP e) {
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
