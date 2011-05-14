package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.MethodDecl;

class MethodDeclHandler extends VisitorAdapter {

  private Env env;

  private MethodDeclHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, MethodDecl node) {
    // Do firstPass() here...
  }

  public void visit(MethodDecl node) {
    // Do visit() here...
  }

}

