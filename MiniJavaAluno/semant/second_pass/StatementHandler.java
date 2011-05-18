package semant.second_pass;

import semant.Env;
import symbol.ClassInfo;
import symbol.MethodInfo;
import syntaxtree.Assign;
import syntaxtree.BooleanType;
import syntaxtree.If;
import syntaxtree.Print;
import syntaxtree.Statement;
import syntaxtree.Type;
import syntaxtree.VisitorAdapter;
import syntaxtree.While;

/**
 * Cada statement tera sua propria implementacao do visit().
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
		// Verifica o tipo da expressao de condicao
		Type conditionType = ExpHandler.secondPass(env, parentClass, parentMethod, node.condition);
		if (!(conditionType instanceof BooleanType)) {
			env.err.Error(node, new Object[] {
					"Tipo invalido para a condicao do WHILE.",
					"Esperado: boolean",
					"Encontrado: " + conditionType
			});
		}

		// Chama secondPass() no body do while
		StatementHandler.secondPass(env, parentClass, parentMethod, node.body);
	}

	//////////////////
	// If Statement //
	//////////////////
	public void visit(If node) {
		//TODO
	}

	//////////////////////
	// Assign Statement //
	//////////////////////
	public void visit(Assign node) {
		//TODO
	}

	/////////////////////
	// Print Statement //
	/////////////////////
	public void visit(Print node) {
		//TODO
	}
	
}
