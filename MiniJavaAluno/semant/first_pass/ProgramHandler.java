package semant.first_pass;

import syntaxtree.VisitorAdapter;
import syntaxtree.ClassDecl;
import syntaxtree.MainClass;
import syntaxtree.Program;

class ProgramHandler extends VisitorAdapter{
	private Env result;
	
	private ProgramHandler(){
		super();
		result = new Env(err);
	}

	static Env firstPass(ErrorEchoer err, Program p){
		ProgramHandler h = new ProgramHandler(err);
		p.accept(h);
		return h.result;
		}

	public void visit(Program node){
		MainClassHandler.firstPass(result, node.mainClass);
		ClassDeclListHandler.firstpass(result, node.classList);
	}
}
