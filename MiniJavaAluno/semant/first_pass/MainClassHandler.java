package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.MainClass;

class MainClassHandler extends VisitorAdapter {

  private Env env;

  private MainClassHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, MainClass node) {
    // Do firstPass() here...
  }

  public void visit(MainClass node) {
    // Do visit() here...
  }

}

