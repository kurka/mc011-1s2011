package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.Plus;

class PlusHandler extends VisitorAdapter {

  private Env env;

  private PlusHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, Plus node) {
    // Do firstPass() here...
  }

  public void visit(Plus node) {
    // Do visit() here...
  }

}

