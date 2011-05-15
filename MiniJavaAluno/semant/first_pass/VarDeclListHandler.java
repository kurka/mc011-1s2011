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
 * Handler de lista de variaveis.
 * Nao eh de fato um visitor, apenas um involucro para lidar com a lista.
 */
class FormalListHandler {

  private FormalListHandler() {
    super();
  }

  /**
   * firstPass() para quando o handler estiver sendo chamado para a definição das
   * variáveis de uma classe.
   */
  static void firstPass(Env e, ClassInfo c, List<VarDecl> varList) {
    // TODO
  }

  /**
   * firstPass() para quando o handler estiver sendo chamado para a definição dos
   * locals (variáveis locais) de um método.
   */
  static void firstPass(Env e, ClassInfo c, MethodInfo m, List<VarDecl> locals) {
    // TODO
  }
}
