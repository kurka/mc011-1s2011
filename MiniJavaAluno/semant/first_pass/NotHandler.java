package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.Not;

class NotHandler extends VisitorAdapter {

  private Env env;

  private NotHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, Not node) {
    // Do firstPass() here...
  }

  public void visit(Not node) {
    // Do visit() here...
  }

}

