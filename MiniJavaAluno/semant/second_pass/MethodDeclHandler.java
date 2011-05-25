package semant.second_pass;

import semant.Env;
import semant.MethodSymbol;
import symbol.ClassInfo;
import symbol.MethodInfo;
import symbol.Symbol;
import syntaxtree.MethodDecl;
import syntaxtree.Type;
import syntaxtree.TypeVisitorAdapter;
import java.util.Enumeration;

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
		//Symbol key = Symbol.symbol(node.name.s);
		Symbol key = MethodSymbol.getSymbol(node);
		MethodInfo data = parentClass.methods.get(key);
	
		// Verifica se o tipo de retorno de funcao sobrescrita eh igual ao pai
		//if(!TypeHandler.compatible(env, data.type, node.returnType)){
		//	System.out.println("ERRO");
		//}

		// Verifica os parametros (formals)
		FormalListHandler.secondPass(env, parentClass, data, node.formals);
		
		// Verifica se o tipo retornado da expressao equivale ao da assinatura 
		Type retExpType = ExpHandler.secondPass(env, parentClass, data, node.returnExp);
		if ((retExpType != null) && !TypeHandler.compatible(env, retExpType, node.returnType)){
			env.err.Error(node, new Object[]{
					"Tipo da expressao de retorno do metodo " + key + " invalido.",
					"Esperado: " + node.returnType,
					"Encontrado: " + retExpType
			});
		}
		return node.returnType;
	}

}
