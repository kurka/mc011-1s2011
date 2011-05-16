package semant.second_pass;

import semant.Env;
import symbol.ClassInfo;
import symbol.Symbol;
import syntaxtree.ClassDecl;
import syntaxtree.ClassDeclExtends;
import syntaxtree.ClassDeclSimple;
import syntaxtree.VisitorAdapter;

public class ClassDeclHandler extends VisitorAdapter{

	private Env env;

	private ClassDeclHandler(Env e) {
		super();
	    env = e;
	}

	static void secondPass(Env e, ClassDecl node) {
		ClassDeclHandler h = new ClassDeclHandler(e);
		node.accept(h);
	}

	public void visit(ClassDeclSimple node) {
		Symbol key = Symbol.symbol(node.name.s);
		ClassInfo classInfo = env.classes.get(key);
		
	}

	public void visit(ClassDeclExtends node) {
		Symbol key = Symbol.symbol(node.name.s);
	}

}
