package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.ArrayLength;

class ArrayLengthHandler extends VisitorAdapter {

  private Env env;

  private ArrayLengthHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, ArrayLength node) {
    // Do firstPass() here...
  }

  public void visit(ArrayLength node) {
    // Do visit() here...
  }

}

