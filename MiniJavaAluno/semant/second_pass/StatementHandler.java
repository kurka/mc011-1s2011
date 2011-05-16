package semant.second_pass;

import semant.Env;
import symbol.ClassInfo;
import symbol.MethodInfo;
import syntaxtree.Assign;
import syntaxtree.If;
import syntaxtree.Print;
import syntaxtree.Statement;
import syntaxtree.VisitorAdapter;
import syntaxtree.While;

/**
 * Cada tipo de statement tera sua propria implementacao do visit().
 */
public class StatementHandler extends VisitorAdapter {
	private Env env;
	private ClassInfo parentClass;
	private MethodInfo parentMethod;
	
	StatementHandler(Env e, ClassInfo c, MethodInfo m) {
		super();
		env = e;
		parentClass = c;
		parentMethod = m;
	}
	
	public static void secondPass(Env e, ClassInfo c, MethodInfo m, Statement node) {
		StatementHandler h = new StatementHandler(e, c, m);
		node.accept(h);
	}

	/////////////////////
	// While Statement //
	/////////////////////
	public void visit(While node) {
	}

	//////////////////
	// If Statement //
	//////////////////
	public void visit(If node) {
	}

	//////////////////////
	// Assign Statement //
	//////////////////////
	public void visit(Assign node) {
	}

	/////////////////////
	// Print Statement //
	/////////////////////
	public void visit(Print node) {
	}
	
}
