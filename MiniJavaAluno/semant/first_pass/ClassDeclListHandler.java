package semant.first_pass;

import util.List;

import semant.Env;

import symbol.Symbol;
import symbol.ClassInfo;

import syntaxtree.ClassDecl;


/**
 * Handler de lista de declaracao de classes.
 * Nao eh de fato um visitor, apenas um involucro para lidar com a lista.
 */
class ClassDeclListHandler {

  private ClassDeclListHandler() {
    super();
  }

  static void firstPass(Env e, List<ClassDecl> classList) {
    // Chama o firstPass do ClassDeclHandler para cada classe da lista.
    for (List<ClassDecl> c = classList; c != null; c = c.tail) {
      ClassDeclHandler.firstPass(e, c.head);
    }
  }
}
