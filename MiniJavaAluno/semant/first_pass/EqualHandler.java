package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.Equal;

class EqualHandler extends VisitorAdapter {

  private Env env;

  private EqualHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, Equal node) {
    // Do firstPass() here...
  }

  public void visit(Equal node) {
    // Do visit() here...
  }

}

