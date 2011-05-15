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
 * Handler de lista de variaveis.
 * Nao eh de fato um visitor, apenas um involucro para lidar com a lista.
 */
class VarDeclListHandler {

  private VarDeclListHandler() {
    super();
  }

  /**
   * firstPass() para quando o handler estiver sendo chamado para a definição das
   * variáveis de uma classe.
   */
  static void firstPass(Env e, ClassInfo c, List<VarDecl> varList) {
    // Chama o firstPass do VarDeclHandler para cada variavel da classe
    for (List<VarDecl> v = varList; v != null; v = v.tail) {
      VarDeclHandler.firstPass(e, c, null, v.head); // Note que nao ha MethodInfo
    }
  }

  /**
   * firstPass() para quando o handler estiver sendo chamado para a definição dos
   * locals (variáveis locais) de um método.
   */
  static void firstPass(Env e, ClassInfo c, MethodInfo m, List<VarDecl> locals) {
    // Chama o firstPass do VarDeclHandler para cada variavel da classe
    for (List<VarDecl> v = locals; v != null; v = v.tail) {
      VarDeclHandler.firstPass(e, c, m, v.head);
    }
  }
}
