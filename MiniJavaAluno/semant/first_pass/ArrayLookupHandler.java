package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.ArrayLookup;

class ArrayLookupHandler extends VisitorAdapter {

  private Env env;

  private ArrayLookupHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, ArrayLookup node) {
    // Do firstPass() here...
  }

  public void visit(ArrayLookup node) {
    // Do visit() here...
  }

}

