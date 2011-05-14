package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.NewObject;

class NewObjectHandler extends VisitorAdapter {

  private Env env;

  private NewObjectHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, NewObject node) {
    // Do firstPass() here...
  }

  public void visit(NewObject node) {
    // Do visit() here...
  }

}

