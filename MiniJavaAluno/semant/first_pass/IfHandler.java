package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.If;

class IfHandler extends VisitorAdapter {

  private Env env;

  private IfHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, If node) {
    // Do firstPass() here...
  }

  public void visit(If node) {
    // Do visit() here...
  }

}

