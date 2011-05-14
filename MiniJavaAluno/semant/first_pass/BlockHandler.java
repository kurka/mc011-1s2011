package semant.first_pass;

import semant.Env;
import syntaxtree.VisitorAdapter;
import syntaxtree.Block;

class BlockHandler extends VisitorAdapter {

  private Env env;

  private BlockHandler(Env e) {
    super();
    env = e;
  }


  static void firstPass(Env e, Block node) {
    // Do firstPass() here...
  }

  public void visit(Block node) {
    // Do visit() here...
  }

}

