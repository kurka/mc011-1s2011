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
  
  /**
   * PrintStm args counter
   */
  private static int max = 0;

  /**
   * @param prog
   * @return maximum number of args of "print" statements
   */
  static void maxargs(Stm prog) {
    openStm(prog);
  }

  static void openStm(Stm prog){
    if(prog instanceof CompoundStm)
      openCompoundStm((CompoundStm) prog);
    else if(prog instanceof AssignStm)
      openAssignStm((AssignStm) prog);
    else if(prog instanceof PrintStm)
      openPrintStm((PrintStm) prog);
  }

  static void openCompoundStm(CompoundStm prog){
	//analyse stm1
	openStm(prog.stm1);

    //analyse stm2
    openStm(prog.stm2);
  }

  static void openAssignStm(AssignStm prog){
    //analyse exp
    openExp(prog.exp);
  }
  
  static void openPrintStm(PrintStm prog){
    //analyse ExpList
    openExpList(prog.exps, 0);
  }

  static void openExp(Exp prog){
    if(prog instanceof IdExp)
      openIdExp((IdExp) prog);
    else if(prog instanceof NumExp)
      openNumExp((NumExp) prog);
    else if(prog instanceof OpExp)
      openOpExp((OpExp) prog);
    else if(prog instanceof EseqExp)
      openEseqExp((EseqExp) prog);
  }

  static void openIdExp(IdExp prog){
    //prog.id
	return;
  }
  
  static void openNumExp(NumExp prog){
    //prog.num
	return;
  }
  
  static void openOpExp(OpExp prog){
    //analyse left
    openExp(prog.left);
	//analyse operator 
	//prog.oper (int)
	//analyse right
	openExp(prog.right);	
  }
  
  static void openEseqExp(EseqExp prog){
	//analyse statement
	openStm(prog.stm);
	//analyse expression
	openExp(prog.exp);
  }

  static void openExpList(ExpList prog, int size){
	if(prog instanceof PairExpList){
	  size++;
	  openPairExpList((PairExpList) prog, size);
	}
	else if(prog instanceof LastExpList){
	  size++;
	  openLastExpList((LastExpList) prog);
	}
	
	if(size > max)
		max = size;
	 
  }

  static void openPairExpList(PairExpList prog, int size){
  	//analyse head
	openExp(prog.head);
	//analyse tail
	openExpList(prog.tail, size);
  }

  static void openLastExpList(LastExpList prog){
    //analyse head
	openExp(prog.head); 
  }

  /**
   * @return Stm object with program to be interpreted
   */
  public static Stm getObject(){
    Stm p1 = new CompoundStm(
      new AssignStm(
        "a",
        new OpExp(
          new NumExp(5), OpExp.Plus, new NumExp(3)
        )
      ),
      new CompoundStm(
        new AssignStm(
          "b",
          new EseqExp(
            new PrintStm(
              new PairExpList(
                new IdExp("a"), new LastExpList(
                  new OpExp(new IdExp("a"), OpExp.Minus,new NumExp(1)
                )
              )
            )
          ),
          new OpExp(
            new NumExp(10), OpExp.Times, new IdExp("a")
          )
        )
      ),
      new PrintStm(new LastExpList(new IdExp("b"))))
    );
    
    return p1;
  }
}

