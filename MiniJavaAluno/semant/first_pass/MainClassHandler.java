package semant.first_pass;

import util.List;

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

class MainClassHandler extends VisitorAdapter {

  private Env env;

  private MainClassHandler(Env e) {
    super();
    env = e;
  }

  static void firstPass(Env e, MainClass node) {
    MainClassHandler h = new MainClassHandler(e);
    node.accept(h);
  }

  /**
   * Insere a mainClass na tabela.
   * Para isso, prepara as informações, incluindo a criação direta do método main().
   */
  public void visit(MainClass node) {
    System.out.println("Guardando main class: " + node.className.s + " na tabela.");
    Symbol classKey = Symbol.symbol(node.className.s);
    ClassInfo classData = new ClassInfo(classKey);

    // Cria o node do metodo main() diretamente
    Type methodRetType = new IdentifierType(node.line, node.row, "void");
    Identifier methodId = new Identifier(node.line, node.row, "main");

    //// Precisamos criar as listas dos formals e o body.
    Type methodArgType = new IdentifierType(node.line, node.row, "String[]");
    Formal methodArg = new Formal(node.line, node.row, methodArgType, node.mainArgName);
    List<Formal> methodFormalList = new List<Formal>(methodArg, null);
    List<Statement> methodStatementList = new List<Statement>(node.s, null);

    // Finalmente, cria o node para o metodo main().
    // Note que o metodo main() nao possui locals nem expressao de retorno.
    MethodDecl methodNode = new MethodDecl(node.line, node.row, methodRetType, methodId, methodFormalList, (List<VarDecl>) null, methodStatementList, (Exp) null);

    // Adiciona o metodo main na classe atraves do Handler
    MethodDeclHandler.firstPass(env, classData, methodNode);

    // Insere main class na tabela
    // Nenhuma verificacao eh necessaria aqui, uma vez que esta classe
    // eh sempre a primeira a ser inserida. Nao havera conflito nesse momento.
    env.classes.put(classKey, classData);
  }

}

