package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.While;

class WhileHandler extends VisitorAdapter {

  private Env env;

  private WhileHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, While node) {
    // Do firstPass() here...
  }

  public void visit(While node) {
    // Do visit() here...
  }

}

