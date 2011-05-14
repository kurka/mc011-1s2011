package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.BooleanType;

class BooleanTypeHandler extends VisitorAdapter {

  private Env env;

  private BooleanTypeHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, BooleanType node) {
    // Do firstPass() here...
  }

  public void visit(BooleanType node) {
    // Do visit() here...
  }

}

