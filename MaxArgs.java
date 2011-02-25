import SLL.*;

public class MaxArgs {

  public static void main(String[] args) {
    System.out.println("Starting MaxArgs...");

    //create prog object here...
    //TODO: pass it through param
    Stm prog = getObject();
    maxargs(prog);
    
    System.out.println("resultado:");
    System.out.println(max);
    System.out.println("bye! :)");
  }

  static int max=0;
  /**
   * @param prog
   * @return maximum number of args of "print" statements
   */
  static void maxargs(Stm prog) {
    openStm(prog);
  }

  static void openStm(Stm prog){
    if(prog instanceof CompoundStm)
      openCompoundStm(prog);
    else if(prog instanceof AssignStm)
      openAssignStm(prog);
    else if(prog instanceof PrintStm)
      openPrintStm(prog);
  }

  static void openCompoundStm(Stm prog){
    CompoundStm comp = (CompoundStm) prog;
	//analyse stm1
	openStm(comp.stm1);

    //analyse stm2
    openStm(comp.stm2);
  }

  static void openAssignStm(Stm prog){
    AssignStm assign = (AssignStm) prog;
	//ignore id (just a id)
	
    //analyse exp
    openExp(assign.exp);
  }
  
  static void openPrintStm(Stm prog){
    PrintStm print = (PrintStm) prog;
	//TODO: CONTAR O BAGUIO!
    //getLen(ExpList prog.expList)
    //analyse ExpList
    openExpList(print.exps);
  }

  static void openExp(Exp prog){
    if(prog instanceof IdExp)
      openIdExp(prog);
    else if(prog instanceof NumExp)
      openNumExp(prog);
    else if(prog instanceof OpExp)
      openOpExp(prog);
    else if(prog instanceof EseqExp)
      openEseqExp(prog);
  }

  static void openIdExp(Exp prog){
	IdExp idExp = (IdExp) prog;
    //prog.id
	return;
  }
  
  static void openNumExp(Exp prog){
	NumExp numExp = (NumExp) prog;
    //prog.num
	return;
  }
  
  static void openOpExp(Exp prog){
	OpExp oper = (OpExp) prog;
    //analyse left
    openExp(oper.left);
	//analyse operator 
	//oper.oper (int)
	//analyse right
	openExp(oper.right);	
  }
  
  static void openEseqExp(Exp prog){
	EseqExp eseq = (EseqExp) prog;
	//analyse statement
	openStm(eseq.stm);
	//analyse expression
	openExp(eseq.exp);
  }

  static void openExpList(ExpList prog){
    if(prog instanceof PairExpList)
		openPairExpList(prog);
	else if(prog instanceof LastExpList)
		openLastExpList(prog);
	 
  }

  static void openPairExpList(ExpList prog){
	PairExpList pair = (PairExpList) prog;
  	//analyse head
	openExp(pair.head);
	//analyse tail
	openExpList(pair.tail);
  }

  static void openLastExpList(ExpList prog){
	LastExpList last = (LastExpList) prog;
    //analyse head
	openExp(last.head); 
  }

  /**
   * @return Stm object with program to be interpreted
   */
  public static Stm getObject(){
    Stm prog = 
		new CompoundStm(new AssignStm("a",
					new OpExp(new NumExp(5),
						OpExp.Plus, new NumExp(3))),
				new CompoundStm(new AssignStm("b",
						new EseqExp(new PrintStm(new PairExpList(new IdExp("a"),
									new LastExpList(new OpExp(new IdExp("a"),
											OpExp.Minus,new NumExp(1))))),
							new OpExp(new NumExp(10), OpExp.Times,
								new IdExp("a")))),
					new PrintStm(new LastExpList(new IdExp("b")))));
    
    return prog;
  }
}

