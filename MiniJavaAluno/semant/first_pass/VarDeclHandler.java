package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.VarDecl;

class VarDeclHandler extends VisitorAdapter {

  private Env env;

  private VarDeclHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, VarDecl node) {
    // Do firstPass() here...
  }

  public void visit(VarDecl node) {
    // Do visit() here...
  }

}

