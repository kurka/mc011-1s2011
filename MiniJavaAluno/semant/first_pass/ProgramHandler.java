package semant.first_pass;

import semant.Env;
import errors.ErrorEchoer;
import syntaxtree.VisitorAdapter;
import syntaxtree.Program;

class ProgramHandler extends VisitorAdapter {

  // Attributes
  private Env result;

  // Constructor
  private ProgramHandler(ErrorEchoer err) {
    super();
    result = new Env(err);
  }

  // Methods
  static Env firstPass(ErrorEchoer err, Program p) {
    ProgramHandler h = new ProgramHandler(err);
    p.accept(h);
    return h.result;
  }

  public void visit(Program node) {
    // Call firsPass() for MainClass
    MainClassHandler.firstPass(result, node.mainClass);

    // And then call firstPass() for ClasDeclList
    ClassDeclListHandler.firstPass(result, node.classList);
  }

}
