package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.True;

class TrueHandler extends VisitorAdapter {

  private Env env;

  private TrueHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, True node) {
    // Do firstPass() here...
  }

  public void visit(True node) {
    // Do visit() here...
  }

}

