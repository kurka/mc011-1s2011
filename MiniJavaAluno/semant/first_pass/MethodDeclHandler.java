package semant.first_pass;

import semant.Env;

import symbol.Symbol;
import symbol.ClassInfo;
import symbol.MethodInfo;

import syntaxtree.VisitorAdapter;
import syntaxtree.MethodDecl;


class MethodDeclHandler extends VisitorAdapter {

  private Env env;
  private ClassInfo parentClass;

  private MethodDeclHandler(Env e, ClassInfo c) {
    super();
    env = e;
    parentClass = c;
  }

  static void firstPass(Env e, ClassInfo c, MethodDecl node) {
    MethodDeclHandler h = new MethodDeclHandler(e, c);
    node.accept(h);
  }

  public void visit(MethodDecl node) {
    Symbol key = Symbol.symbol(node.name.s);
    MethodInfo method = new MethodInfo(node.returnType, key, parentClass.name);

    // Chama o firstPass() para os formals
    FormalListHandler.firstPass(env, parentClass, method, node.formals);

    // Chama o firstPass() para os locals
    VarDeclListHandler.firstPass(env, parentClass, method, node.locals);

    // Adiciona o metodo na classe
    if (!parentClass.addMethod(method)) {

      // Caso não tenha conseguido inserir, pega o metodo ja existente e gera log do erro
      MethodInfo existent = parentClass.methods.get(key);
      if (existent == null) {
        throw new NullPointerException("Metodo supostamente existente nao encontrado.");
      }
      else {
        String msg = "Metodo \'" + key + "\' previamente declarado na classe \'" + 
          parentClass.name + "\' em [" + existent.type.line + "," + existent.type.row + "]";
        env.err.Error(node, new Object[]{msg});
      }
    }
  }

}
