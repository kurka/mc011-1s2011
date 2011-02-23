import SLL.*;

public class MaxArgs {

  public static void main(String[] args) {
    System.out.println("Starting MaxArgs...");

    //create prog object here...
    //TODO: pass it through param
    Stm prog = getObject();
    int max = maxargs(prog);
    
    system.out.println("resultado:");
    system.out.println(max);
    system.out.println("bye! :)");
  }

  /**
   * @param prog
   * @return maximum number of args of "print" statements
   */
  void maxargs(Stm prog) {
    openStm(prog);
  }

  void openStm(Stm Prog){
    if(prog instanceof CompoundStm)
      openCompoundStm(prog);
    else if(prog instanceof AssignStm)
      openAssignStm(prog);
    else if(prog instanceof PrintStm)
      openPrintStm(prog);
  }

  void openCompoundStm(CompoundStm prog){
    //analyse stm1
    openStm(prog.stm1);

    //analyse stm2
    openStm(prog.stm2);
  }

  void openAssignStm(AssignStm prog){
    //ignore id (just a id)
	
    //analyse exp
    openExp(Exp prog.exp);
  }
  
  void openPrintStm(PrintStm prog){
    //TODO: CONTAR O BAGUIO!
    //getLen(ExpList prog.expList)
    //analyse ExpList
    openExpList(ExpList prog.exps);
  }

  void openExp(Exp prog){
    if(prog instanceof IdExp)
      openIdExp(prog);
    else if(prog instanceof NumExp)
      openNumExp(prog);
    else if(prog instanceof OpExp)
      OpenOpExp(prog);
    else if(prog instanceof EseqExp)
      OpenEseqExp(prog);
  }

  //TODO: criar esses metodos
  openIdExp
    openNumExp
    openOpExp
    openEseqExp

    void openExpList(ExpList prog){
    //TODO
  }

  // openPairExpList
  //   openLastExpList

  /**
   * @return Stm object with program to be interpreted
   */
  public static Stm getObject(){
    SLL sll = new SLL();
    Stm prog = 
      sll.new CompoundStm(sll.new AssignStm("a",
                                            sll.new OpExp(sll.new NumExp(5),
                                                          OpExp.Plus, sll.new NumExp(3))),
                          sll.new CompoundStm(sll.new AssignStm("b",
                                                                sll.new EseqExp(sll.new PrintStm(sll.new PairExpList(sll.new IdExp("a"),
                                                                                                                     sll.new LastExpList(sll.new OpExp(sll.new IdExp("a"),
                                                                                                                                                       OpExp.Minus,sll.new NumExp(1))))),
                                                                                sll.new OpExp(sll.new NumExp(10), OpExp.Times,
                                                                                              sll.new IdExp("a")))),
                                              sll.new PrintStm(sll.new LastExpList(sll.new IdExp("b")))));
    
    return prog;
  }
}

