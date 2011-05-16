package semant.second_pass;

import semant.Env;
import symbol.ClassInfo;
import symbol.Symbol;
import syntaxtree.MethodDecl;
import syntaxtree.Type;
import syntaxtree.TypeVisitorAdapter;

public class MethodDeclHandler extends TypeVisitorAdapter {

	Env env;
	ClassInfo parentClass;
	
	private MethodDeclHandler(Env e, ClassInfo c) {
		super();
		env = e;
		parentClass = c;
	}
	
	public static void secondPass(Env e, ClassInfo c, MethodDecl node) {
		MethodDeclHandler h = new MethodDeclHandler(e, c);
		node.accept(h);
	}

	public Type visit(MethodDecl node) {
		Symbol key = Symbol.symbol(node.name.s);
		
		// Verifica se o tipo retornado da expressao equivale ao da assinatura 
		Type retExpType = ExpHandler.secondPass(env, parentClass, null, node.returnExp);
		if (retExpType != node.returnType) {
			env.err.Error(node, new Object[]{
					"Tipo da expressao de retorno do metodo " + key + "invalido.",
					"Esperado: " + node.returnType,
					"Encontrado: " + retExpType
			});
		}
		return node.returnType;
	}
}
