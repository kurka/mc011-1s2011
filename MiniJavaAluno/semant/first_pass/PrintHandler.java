package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.Print;

class PrintHandler extends VisitorAdapter {

  private Env env;

  private PrintHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, Print node) {
    // Do firstPass() here...
  }

  public void visit(Print node) {
    // Do visit() here...
  }

}

