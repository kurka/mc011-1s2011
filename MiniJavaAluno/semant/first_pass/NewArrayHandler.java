package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.NewArray;

class NewArrayHandler extends VisitorAdapter {

  private Env env;

  private NewArrayHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, NewArray node) {
    // Do firstPass() here...
  }

  public void visit(NewArray node) {
    // Do visit() here...
  }

}

