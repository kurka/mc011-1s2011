package semant.second_pass;

import util.List;
import java.util.Enumeration;
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

		MethodDeclListHandler.secondPass(env, classInfo, node.methodList);
	}

	public void visit(ClassDeclExtends node) {
		Symbol name = Symbol.symbol(node.name.s);
		ClassInfo cinfo = env.classes.get(name);

		Symbol base = Symbol.symbol(node.superClass.s);
		ClassInfo sinfo = env.classes.get(base);

		//System.out.println("Env:");
		//for (Enumeration e = env.classes.keys() ; e.hasMoreElements() ;) 
		//	  System.out.println(e.nextElement());
		//System.out.println(cinfo.name.toString());

		// Apenas para garantir que a classe existe e ainda nao foi setado seu base.
		if (cinfo == null || cinfo.base != null) {
			return;
		}

		// Caso em que uma classe herda de outra que nao existe
		if (sinfo == null) {
			String msg = "Classe " + name + " deriva de classe nao declarada: " + base;
			env.err.Error(node, new Object[] {msg});
		}
		else {
			
			// Classe pai existe. Checa heranca.
			if (!TypeHandler.canInheritFrom(cinfo, sinfo)) {
				String msg = "Heranca ciclica detectada entre " + name + " e " + base;
				env.err.Error(node, new Object[] {msg});
			}
			else {
				// Finalmente salva heranca
				cinfo.setBase(sinfo);
			}
		}
		MethodDeclListHandler.secondPass(env, cinfo, node.methodList);
	}

}
