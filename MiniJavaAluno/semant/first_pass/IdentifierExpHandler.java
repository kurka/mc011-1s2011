package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.IdentifierExp;

class IdentifierExpHandler extends VisitorAdapter {

  private Env env;

  private IdentifierExpHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, IdentifierExp node) {
    // Do firstPass() here...
  }

  public void visit(IdentifierExp node) {
    // Do visit() here...
  }

}

