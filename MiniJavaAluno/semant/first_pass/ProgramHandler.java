package semant.first_pass;

import semant.Env;
import errors.ErrorEchoer;

import syntaxtree.VisitorAdapter;
import syntaxtree.ClassDecl;
import syntaxtree.MainClass;
import syntaxtree.Program;

class ProgramHandler extends VisitorAdapter {
  private ProgramHandler() {
    super();
  }

  static Env firstPass(ErrorEchoer err, Program p) {
    return null;
  }

  public void visit(Program node) {
  }
}
