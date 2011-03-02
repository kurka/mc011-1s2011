import SLL.*;

/**
 * Definition of exception case.
 */
class CellNotFoundException extends Exception {
  CellNotFoundException() {
    super(); /* call Exception constructor */
  }
}

/**
 * Table that stores linked key:value pairs.
 */
class Table {
  /**
   * Table attributes.
   */
  String id; 
  int value; 
  Table tail;

  /**
   * Table constructor.
   */
  Table(String i, int v, Table t) {
    id = i; value = v; tail = t;
  }

  /**
   * Table methods...
   */

  /* Print table contents (it can be useful). */
  void print() {
    System.out.println(id + " : " + value);
    if (tail != null) {
      tail.print();
    }
  }

  /**
   * Search and update the value identified by i.
   * If no cell is found, insert a new one.
   */
  void update(String i, int v) {
    if (id == i) {
      /* cell found */
      value = v;
    }
    else if (tail != null) {
      tail.update(i, v);
    }
    else {
      tail = new Table(i, v, (Table) null);
    }
  }

  /* @return The value in cell identified by i. */
  int get(String i) throws CellNotFoundException {
    if (id == i) {
      return value;
    }
    else if (tail != null) {
      return tail.get(i);
    }
    else {
      /* searched through entire table and didn't find */
      throw new CellNotFoundException();
    }
  }
}

class IntAndTable {
  int i; 
  Table t;

  IntAndTable(int ii, Table tt) {i=ii; t=tt;}
}

public class Interpreter {

  public static void main(String[] args) {
    System.out.println("Starting...");
    Stm prog = getProgram();
    interp(prog); 
  }

  static void interp(Stm s){
    Table t = interpStm(s,null);
    t.print();
  }

  static Table interpStm(Stm prog, Table t){
    if(prog instanceof CompoundStm)
      t = interpCompoundStm((CompoundStm) prog, t);
    else if(prog instanceof AssignStm)
      t = interpAssignStm((AssignStm) prog, t);
    else if(prog instanceof PrintStm)
      t = interpPrintStm((PrintStm) prog, t);
    return t;
  }

  static Table interpCompoundStm(CompoundStm prog, Table t){
    //analyse stm1
    t = interpStm(prog.stm1, t);

    //analyse stm2
    t = interpStm(prog.stm2, t);
    return t;
  }

  static Table interpAssignStm(AssignStm prog, Table t){
    //prog.id = prog.exp
    //analyse exp
    IntAndTable i = new IntAndTable(0, t);
    i = interpExp(prog.exp, i);
    t = i.t;
    if(t != null) //just updates if the table exists
      t.update(prog.id, i.i);
    else
      t = new Table(prog.id, i.i, null);
    return t;
  }

  static Table interpPrintStm(PrintStm prog, Table t){
    //analyse ExpList
    IntAndTable i = new IntAndTable(0, t);
    i = interpExpList(prog.exps, i);
    return i.t;
  }

  static IntAndTable interpExp(Exp prog, IntAndTable i){
    if(prog instanceof IdExp){
      try{
        i = interpIdExp((IdExp) prog, i);
      }
      catch(CellNotFoundException e){
        System.out.println("ERROR! ID not found!");
        System.exit(1);
      }
    }
    else if(prog instanceof NumExp)
      i = interpNumExp((NumExp) prog, i);
    else if(prog instanceof OpExp)
      i = interpOpExp((OpExp) prog, i);
    else if(prog instanceof EseqExp)
      i = interpEseqExp((EseqExp) prog, i);
    return i;
  }

  static IntAndTable interpIdExp(IdExp prog, IntAndTable i) throws CellNotFoundException {
    //prog.id
    i.i = i.t.get(prog.id);
    
    return i;
  }

  static IntAndTable interpNumExp(NumExp prog, IntAndTable i){
    //prog.num
    i.i = prog.num;
    return i;
  }


  static IntAndTable interpOpExp(OpExp prog, IntAndTable i){
    //analyse left
    int left, right;
    i = interpExp(prog.left, i);
    //save expression result
    left = i.i;
    //analyse right
    i = interpExp(prog.right, i);	
    //save expression result
    right = i.i;

    //do the operation
    //Plus=1, Minus=2, Times=3, Div=4;
    if(prog.oper == 1)
      i.i = left + right;
    else if(prog.oper == 2)
      i.i = left - right;
    else if(prog.oper == 3)
      i.i = left * right;
    else if(prog.oper == 4)
      i.i = left / right;
    //TODO: usar switch?	

    return i;
  }

  static IntAndTable interpEseqExp(EseqExp prog, IntAndTable i){
    //analyse statement
    i.t = interpStm(prog.stm, i.t);
    //analyse expression
    i = interpExp(prog.exp, i);

    return i;
  }

  static IntAndTable interpExpList(ExpList prog, IntAndTable i){
    if(prog instanceof PairExpList)
      i = interpPairExpList((PairExpList) prog, i);
    else if(prog instanceof LastExpList)
      i = interpLastExpList((LastExpList) prog, i);

    return i;
  }

  static IntAndTable interpPairExpList(PairExpList prog, IntAndTable i){
    //analyse head
    i = interpExp(prog.head, i);
    //print the expression result (this expression is inside print arguments)
    System.out.println(">>>"+i.i);
    //analyse tail
    i = interpExpList(prog.tail, i);

    return i;
  }

  static IntAndTable interpLastExpList(LastExpList prog, IntAndTable i){
    //analyse head
    i = interpExp(prog.head, i); 
    //print the expression result (this expression is inside print arguments)
    System.out.println(">>>"+i.i);

    return i;
  }

  /**
    * @return Stm object with program to be interpreted
    */
  static Stm getProgram(){
    Stm p = new CompoundStm(
        new AssignStm(
          "a",
          new OpExp(
            new NumExp(5), OpExp.Plus, new NumExp(3)
            )
          ),
        new CompoundStm(
          new AssignStm(
            "b",
            new EseqExp(
              new PrintStm(
                new PairExpList(
                  new IdExp("a"), new LastExpList(
                    new OpExp(new IdExp("a"), OpExp.Minus,new NumExp(1)
                      )
                    )
                  )
                ),
              new OpExp(
                new NumExp(10), OpExp.Times, new IdExp("a")
                )
              )
            ),
          new PrintStm(new LastExpList(new IdExp("b"))))
          );

    return p;
  }

}
