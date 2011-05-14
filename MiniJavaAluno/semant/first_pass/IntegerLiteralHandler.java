package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.IntegerLiteral;

class IntegerLiteralHandler extends VisitorAdapter {

  private Env env;

  private IntegerLiteralHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, IntegerLiteral node) {
    // Do firstPass() here...
  }

  public void visit(IntegerLiteral node) {
    // Do visit() here...
  }

}

