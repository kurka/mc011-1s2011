package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.Call;

class CallHandler extends VisitorAdapter {

  private Env env;

  private CallHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, Call node) {
    // Do firstPass() here...
  }

  public void visit(Call node) {
    // Do visit() here...
  }

}

