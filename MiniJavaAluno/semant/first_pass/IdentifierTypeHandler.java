package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.IdentifierType;

class IdentifierTypeHandler extends VisitorAdapter {

  private Env env;

  private IdentifierTypeHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, IdentifierType node) {
    // Do firstPass() here...
  }

  public void visit(IdentifierType node) {
    // Do visit() here...
  }

}

