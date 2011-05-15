package semant.first_pass;

import util.List;

import semant.Env;

import symbol.Symbol;
import symbol.ClassInfo;
import symbol.MethodInfo;

import syntaxtree.VisitorAdapter;
import syntaxtree.MethodDecl;
import syntaxtree.Formal;


/**
 * Handler de lista de formals (parametros).
 * Nao eh de fato um visitor, apenas um involucro para lidar com a lista.
 */
class FormalListHandler {

  private FormalListHandler() {
    super();
  }

  static void firstPass(Env e, ClassInfo c, MethodInfo m, List<Formal> formals) {
    // Chama o firstPass do FormalHandler para cada formal da lista.
    for (List<Formal> f = formals; f != null; f = f.tail) {
      System.out.println("Formal: " + f.head);
      //FormalHandler.firstPass(e, c, m, f.head);
    }
  }
}
