package semant.first_pass;

import semant.Env;

import syntaxtree.VisitorAdapter;
import syntaxtree.ClassDecl;
import syntaxtree.ClassDeclSimple;
import syntaxtree.ClassDeclExtends;

import symbol.Symbol;
import symbol.ClassInfo;

class ClassDeclHandler extends VisitorAdapter {

  private Env env;

  private ClassDeclHandler(Env e) {
    super();
    env = e;
  }

  static void firstPass(Env e, ClassDecl node) {
    ClassDeclHandler h = new ClassDeclHandler(e);
    node.accept(h);
  }

  /**
   * visit() method for ClassDeclSimple instance of ClassDecl.
   */
  public void visit(ClassDeclSimple node) {
    System.out.println("Visitando a classe simples: " + node.name.s);
    Symbol key = Symbol.symbol(node.name.s);
    ClassInfo data = new ClassInfo(key);

    // Chama o firstPass() para as variaveis
    VarDeclListHandler.firstPass(env, data, node.varList);

    // Chama o firstPass() para os metodos
    MethodDeclListHandler.firstPass(env, data, node.methodList);

    // Insere a classe na tabela
    if (!env.classes.put(key, data)) {
      env.err.Error(node, new Object[] {"Nome de classe redefinido: " + key});
    }
  }

  /**
   * visit() method for ClassDeclExtends instance of ClassDecl.
   */
  public void visit(ClassDeclExtends node) {
    System.out.println("Visitando a classe extendida: " + node.name.s);
    Symbol key = Symbol.symbol(node.name.s);
    ClassInfo data = new ClassInfo(key);

    // Chama o firstPass() para as variaveis
    VarDeclListHandler.firstPass(env, data, node.varList);

    // Chama o firstPass() para os metodos
    MethodDeclListHandler.firstPass(env, data, node.methodList);

    // Insere a classe na tabela
    if (!env.classes.put(key, data)) {
      env.err.Error(node, new Object[] {"Nome de classe redefinido: " + key});
    }
  }

}

