package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.ArrayAssign;

class ArrayAssignHandler extends VisitorAdapter {

  private Env env;

  private ArrayAssignHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, ArrayAssign node) {
    // Do firstPass() here...
  }

  public void visit(ArrayAssign node) {
    // Do visit() here...
  }

}

