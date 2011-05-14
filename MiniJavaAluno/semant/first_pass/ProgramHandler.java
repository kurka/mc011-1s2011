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
    System.out.println("firstPass do ProgramHandler");
    return null;
  }

  public void visit(Program node) {
    // Call firsPass() for MainClass
    MainClassHandler.firstPass(result, node.mainClass);

    // And then call firstPass() for ClasDeclList
    ClassDesclListHandler.firstPass(result, node.classList);
  }

}
