package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.Assign;

class AssignHandler extends VisitorAdapter {

  private Env env;

  private AssignHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, Assign node) {
    // Do firstPass() here...
  }

  public void visit(Assign node) {
    // Do visit() here...
  }

}

