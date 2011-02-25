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


public class Interpreter {

  public static void main(String[] args) {
    System.out.println("Starting...");
    // empty for now... 
  }

  /**
   * @return Stm object with program to be interpreted
   */
  Stm getProgram(){
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
