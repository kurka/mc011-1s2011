package semant.second_pass;

import util.List;

import semant.Env;

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
    System.out.println("2pass: visitando main class: " + node.className.s);
  }

}

