package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.ClassDeclSimple;

class ClassDeclSimpleHandler extends VisitorAdapter {

  private Env env;

  private ClassDeclSimpleHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, ClassDeclSimple node) {
    // Do firstPass() here...
  }

  public void visit(ClassDeclSimple node) {
    // Do visit() here...
  }

}

