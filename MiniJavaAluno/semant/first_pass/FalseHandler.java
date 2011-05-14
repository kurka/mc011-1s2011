package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.False;

class FalseHandler extends VisitorAdapter {

  private Env env;

  private FalseHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, False node) {
    // Do firstPass() here...
  }

  public void visit(False node) {
    // Do visit() here...
  }

}

