package semant.second_pass;

import semant.Env;
import symbol.ClassInfo;
import symbol.MethodInfo;
import symbol.Symbol;
import symbol.VarInfo;
import syntaxtree.Formal;
import syntaxtree.Type;
import syntaxtree.TypeVisitorAdapter;
import syntaxtree.VarDecl;

public class FormalHandler extends TypeVisitorAdapter {
	private Env env;
	private ClassInfo parentClass;
	private MethodInfo parentMethod;
	
	private FormalHandler(Env e, ClassInfo c, MethodInfo m) {
		super();
		env = e;
		parentClass = c;
		parentMethod = m;
	}

	public static void secondPass(Env e, ClassInfo c, MethodInfo m, Formal node) {
		FormalHandler h = new FormalHandler(e, c, m);
		node.accept(h);
	}
	
	public Type visit(Formal node) {
		Symbol key = Symbol.symbol(node.name.s);
		VarInfo data = parentMethod.formalsTable.get(key);

		// Verifica se os tipos correspondem
		if (!node.type.toString().equals(data.type.toString())) {
			env.err.Error(node, new Object[]{
					"Tipo do parametro " + key + "invalido.",
					"Esperado: " + data.type,
					"Encontrado: " + node.type
			});
		}
		return node.type = data.type;
	}
}
