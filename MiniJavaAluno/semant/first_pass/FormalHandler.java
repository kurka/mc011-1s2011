package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.Formal;

class FormalHandler extends VisitorAdapter {

  private Env env;

  private FormalHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, Formal node) {
    // Do firstPass() here...
  }

  public void visit(Formal node) {
    // Do visit() here...
  }

}

