package semant.first_pass;

import semant.Env;

import syntaxtree.VisitorAdapter;
import syntaxtree.MainClass;
import syntaxtree.MethodDecl;
import syntaxtree.Type;
import syntaxtree.Identifier;
import syntaxtree.IdentifierType;
import syntaxtree.Formal;
import syntaxtree.Statement;
import syntaxtree.VarDecl;
import syntaxtree.Exp;

import symbol.Symbol;
import symbol.ClassInfo;
import symbol.MethodInfo;
import symbol.VarInfo;

class FormalHandler extends VisitorAdapter {

  private Env env;
  private ClassInfo parentClass;
  private MethodInfo parentMethod;

  private FormalHandler(Env e, ClassInfo c, MethodInfo m) {
    super();
    env = e;
    parentClass = c;
    parentMethod = m;
  }

  static void firstPass(Env e, ClassInfo c, MethodInfo m, Formal node) {
    FormalHandler h = new FormalHandler(e, c, m);
    node.accept(h);
  }

  public void visit(Formal node) {
    Symbol key = Symbol.symbol(node.name.s);
    VarInfo data = new VarInfo(node.type, key);

    // Insere formal no metodo.
    if (!parentMethod.addFormal(data)) {
      // Caso não tenha conseguido inserir, pega o formal ja existente e gera log do erro
      VarInfo existent = parentMethod.formalsTable.get(key);
      if (existent == null) {
        throw new NullPointerException("Parametro supostamente existente nao encontrado.");
      }
      else {
        String msg = "Parametro \'" + key + "\' ja em uso no metodo " + parentMethod.name + 
          " da classe " + parentMethod.parent;
        env.err.Error(node, new Object[]{msg});
      }
    }
  }

}

