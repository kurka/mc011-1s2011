package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.And;

class AndHandler extends VisitorAdapter {

  private Env env;

  private AndHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, And node) {
    // Do firstPass() here...
  }

  public void visit(And node) {
    // Do visit() here...
  }

}

