package semant.second_pass;

import semant.Env;

import symbol.ClassInfo;
import symbol.Symbol;
import syntaxtree.VisitorAdapter;
import syntaxtree.MainClass;

class MainClassHandler extends VisitorAdapter {

  private Env env;

  private MainClassHandler(Env e) {
    super();
    env = e;
  }

  static void secondPass(Env e, MainClass node) {
    MainClassHandler h = new MainClassHandler(e);
    node.accept(h);
  }

  public void visit(MainClass node) {
    Symbol key = Symbol.symbol(node.className.s);
    ClassInfo data = env.classes.get(key);

    // Verifica o statement do metodo main().
    StatementHandler.secondPass(env, data, null, node.s);
  }

}

