package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.This;

class ThisHandler extends VisitorAdapter {

  private Env env;

  private ThisHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, This node) {
    // Do firstPass() here...
  }

  public void visit(This node) {
    // Do visit() here...
  }

}

