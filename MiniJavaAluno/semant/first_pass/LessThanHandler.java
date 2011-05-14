package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.LessThan;

class LessThanHandler extends VisitorAdapter {

  private Env env;

  private LessThanHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, LessThan node) {
    // Do firstPass() here...
  }

  public void visit(LessThan node) {
    // Do visit() here...
  }

}

