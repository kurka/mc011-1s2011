package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.IntArrayType;

class IntArrayTypeHandler extends VisitorAdapter {

  private Env env;

  private IntArrayTypeHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, IntArrayType node) {
    // Do firstPass() here...
  }

  public void visit(IntArrayType node) {
    // Do visit() here...
  }

}

