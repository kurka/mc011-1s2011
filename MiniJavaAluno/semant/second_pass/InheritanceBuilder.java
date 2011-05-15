package semant.second_pass;

import util.List;

import semant.Env;

import syntaxtree.VisitorAdapter;
import syntaxtree.ClassDecl;
import syntaxtree.ClassDeclExtends;
import syntaxtree.Program;

import symbol.Symbol;
import symbol.ClassInfo;

class InheritanceBuilder extends VisitorAdapter {

  private Env env;
  List<ClassDecl> classes;

  private InheritanceBuilder(Env e, List<ClassDecl> c) {
    super();
    env = e;
    classes = c;
  }

  static void secondPass(Env e, Program p) {
    System.out.println("    secondPass chamando accept do node..");
    InheritanceBuilder b = new InheritanceBuilder(e, p.classList);

    for (List<ClassDecl> c = p.classList; c != null; c = c.tail) {
      ClassDecl cd = c.head;
      cd.accept(b);
    }
  }

  public void visit(Program p) {
    System.out.println("        Rodando visit do Inheritance, tendo recebido PROGRAM...");
  }

  public void visit(ClassDeclExtends node) {
    System.out.println("        Rodando visit do Inheritance...");

    Symbol name = Symbol.symbol(node.name.s);
    ClassInfo cinfo = env.classes.get(name);

    Symbol base = Symbol.symbol(node.superClass.s);
    ClassInfo sinfo = env.classes.get(base);

    System.out.println("           Name: " + name);
    System.out.println("           Base: " + base);

    
    // Apenas para garantir que a classe existe e ainda nao foi setado seu base.
    if (cinfo == null || cinfo.base == null) {
      System.out.println("             Saindo pela culatra");
      return;
    }

    // Caso em que uma classe herda de outra que nao existe
    if (sinfo == null) {
      System.out.println("             Saindo pela culatra2");

      String msg = "Classe " + name + " deriva de classe nao declarada: " + base;
      env.err.Error(node, new Object[] {msg});
    }
    else {
      // Classe pai existe. Checa heranca.

      if (!TypeHandler.canInheritFrom(cinfo, sinfo)) {
        String msg = "Heranca ciclica detectada entre " + name + " e " + base;
        env.err.Error(node, new Object[] {msg});
      }
      else {
        // Finalmente salva heranca
        cinfo.setBase(sinfo);
      }
    }

  }
}
