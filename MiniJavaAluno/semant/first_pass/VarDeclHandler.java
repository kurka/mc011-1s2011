package semant.first_pass;

import semant.Env;

import syntaxtree.VisitorAdapter;
import syntaxtree.VarDecl;

import symbol.Symbol;
import symbol.ClassInfo;
import symbol.MethodInfo;
import symbol.VarInfo;

class VarDeclHandler extends VisitorAdapter {

  private Env env;
  private ClassInfo parentClass;
  private MethodInfo parentMethod; // <- será NULL se estiver declarando atributo da classe

  private VarDeclHandler(Env e, ClassInfo c, MethodInfo m) {
    super();
    env = e;
    parentClass = c;
    parentMethod = m;
  }

  static void firstPass(Env e, ClassInfo c, MethodInfo m, VarDecl node) {
    VarDeclHandler h = new VarDeclHandler(e, c, m);
    node.accept(h);
  }

  public void visit(VarDecl node) {
    Symbol key = Symbol.symbol(node.name.s);
    VarInfo data = new VarInfo(node.type, key);

    // Checa se a variavel eh um atributo da classe, ou uma variavel local de metodo
    if (parentMethod == null) {

      ////////////////////////
      // Atributo da Classe //
      ////////////////////////

      if (!parentClass.addAttribute(data)) {
        String msg = "Redeclaração do atributo " + key + " na classe " + parentClass.name;
        env.err.Error(node, new Object[]{msg});
      }
    }
    else {

      //////////////////////////////
      // Variavel local do Metodo //
      //////////////////////////////

      if (!parentMethod.addLocal(data)) {
        String msg = "Redeclaração da variavel " + key + " no metodo " + parentMethod.name;
        env.err.Error(node, new Object[]{msg});
      }
    }
  }

}

