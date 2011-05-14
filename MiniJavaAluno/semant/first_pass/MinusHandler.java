package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.Minus;

class MinusHandler extends VisitorAdapter {

  private Env env;

  private MinusHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, Minus node) {
    // Do firstPass() here...
  }

  public void visit(Minus node) {
    // Do visit() here...
  }

}

