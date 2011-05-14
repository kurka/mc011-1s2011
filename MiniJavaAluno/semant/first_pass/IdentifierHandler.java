package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.Identifier;

class IdentifierHandler extends VisitorAdapter {

  private Env env;

  private IdentifierHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, Identifier node) {
    // Do firstPass() here...
  }

  public void visit(Identifier node) {
    // Do visit() here...
  }

}

