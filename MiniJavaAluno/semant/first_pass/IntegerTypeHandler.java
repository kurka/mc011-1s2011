package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.IntegerType;

class IntegerTypeHandler extends VisitorAdapter {

  private Env env;

  private IntegerTypeHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, IntegerType node) {
    // Do firstPass() here...
  }

  public void visit(IntegerType node) {
    // Do visit() here...
  }

}

