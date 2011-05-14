package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.ClassDeclExtends;

class ClassDeclExtendsHandler extends VisitorAdapter {

  private Env env;

  private ClassDeclExtendsHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, ClassDeclExtends node) {
    // Do firstPass() here...
  }

  public void visit(ClassDeclExtends node) {
    // Do visit() here...
  }

}

