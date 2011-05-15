package semant.first_pass;

import util.List;

import semant.Env;

import symbol.Symbol;
import symbol.ClassInfo;
import symbol.MethodInfo;

import syntaxtree.MethodDecl;
import syntaxtree.Formal;
import syntaxtree.VarDecl;


/**
 * Handler de lista de declaracoes de metodos.
 * Nao eh de fato um visitor, apenas um involucro para lidar com a lista.
 */
class MethodDeclListHandler {

  private MethodDeclListHandler() {
    super();
  }

  static void firstPass(Env e, ClassInfo c, List<MethodDecl> methodList) {
    // Chama o firstPass do MethodDeclHandler para cada metodo declarado
    for (List<MethodDecl> m = methodList; m != null; m = m.tail) {
      MethodDeclHandler.firstPass(e, c, m.head);
    }
  }
}
