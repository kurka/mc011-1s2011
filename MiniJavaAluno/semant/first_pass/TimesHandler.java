package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.Times;

class TimesHandler extends VisitorAdapter {

  private Env env;

  private TimesHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, Times node) {
    // Do firstPass() here...
  }

  public void visit(Times node) {
    // Do visit() here...
  }

}

