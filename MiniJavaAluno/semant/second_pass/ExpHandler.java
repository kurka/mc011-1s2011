package semant.second_pass;

import semant.Env;
import symbol.ClassInfo;
import symbol.MethodInfo;
import symbol.Symbol;
import symbol.VarInfo;
import syntaxtree.And;
import syntaxtree.BooleanType;
import syntaxtree.Equal;
import syntaxtree.Exp;
import syntaxtree.IdentifierExp;
import syntaxtree.IdentifierType;
import syntaxtree.IntegerType;
import syntaxtree.LessThan;
import syntaxtree.Minus;
import syntaxtree.Plus;
import syntaxtree.Times;
import syntaxtree.IntegerLiteral;
import syntaxtree.Not;
import syntaxtree.ArrayLength;
import syntaxtree.ArrayLookup;
import syntaxtree.Call;
import syntaxtree.True;
import syntaxtree.False;
import syntaxtree.This;
import syntaxtree.NewArray;
import syntaxtree.NewObject;
import syntaxtree.Type;
import syntaxtree.TypeVisitorAdapter;

/**
 * Cada expression tera sua propria implementacao do visit().
 */
public class ExpHandler extends TypeVisitorAdapter {
	Env env;
	ClassInfo parentClass;
	MethodInfo parentMethod;
	
	private ExpHandler(Env e, ClassInfo c, MethodInfo m) {
		super();
		env = e;
		parentClass = c;
		parentMethod = m;
	}

	// Helper
	private static VarInfo getVariable(ClassInfo c, MethodInfo m, Symbol key) {
		// Procura primeiro no metodo
		VarInfo data = m.formalsTable.get(key);

		// Se nao estiver no metodo, deve ser um atributo da classe
		if (data == null) {
			data = c.attributes.get(key);
		}

		return data;
	}

	public static Type secondPass(Env e, ClassInfo c, MethodInfo m, Exp node) {
		ExpHandler h = new ExpHandler(e, c, m);
		return node.accept(h);
	}

	////////////////////
	// And Expression //
	////////////////////
	public Type visit(And node) {
		//TODO
		return null;
	}

	//////////////////////
	// Equal Expression //
	//////////////////////
	public Type visit(Equal node) {
		//TODO
		return null;
	}

	//////////////////////////////
	// IdentifierExp Expression //
	//////////////////////////////
	public Type visit(IdentifierExp node) {
		Symbol key = Symbol.symbol(node.name.s);
		VarInfo data = ExpHandler.getVariable(parentClass, parentMethod, key);
		
		// Identificador nao encontrado
		if (data == null) {
			env.err.Error(node, new Object[]{"Identificador " + key + "nao definido no contexto."});
			// Simula que o identificador existe, com o tipo int.
			return new IntegerType(node.line, node.row);
		}
		
		return node.type = data.type;
	}

	/////////////////////////
	// LessThan Expression //
	/////////////////////////
	public Type visit(LessThan node) {
		// Verifica tipo da expressao da esquerda
		Type lhsType = ExpHandler.secondPass(env, parentClass, parentMethod, node.lhs);
		if (!(lhsType instanceof IntegerType)) {
			env.err.Error(node.lhs, new Object[]{
					"Tipo do operando esquerdo de LESS-THAN invalido",
					"Esperado: int",
					"Encontrado: " + lhsType
			});
		}

		// Verifica tipo da expressao da direita
		Type rhsType = ExpHandler.secondPass(env, parentClass, parentMethod, node.rhs);
		if (!(rhsType instanceof IntegerType)) {
			env.err.Error(node.lhs, new Object[]{
					"Tipo do operando direito de LESS-THAN invalido",
					"Esperado: int",
					"Encontrado: " + rhsType
			});
		}
		
		return node.type = new BooleanType(node.line, node.row);
	}

	//////////////////////
	// Minus Expression //
	//////////////////////
	public Type visit(Minus node) {
		// Verifica tipo da expressao da esquerda
		Type lhsType = ExpHandler.secondPass(env, parentClass, parentMethod, node.lhs);
		if (!(lhsType instanceof IntegerType)) {
			env.err.Error(node.lhs, new Object[]{
					"Tipo do operando esquerdo de MINUS invalido",
					"Esperado: int",
					"Encontrado: " + lhsType
			});
		}

		// Verifica tipo da expressao da direita
		Type rhsType = ExpHandler.secondPass(env, parentClass, parentMethod, node.rhs);
		if (!(rhsType instanceof IntegerType)) {
			env.err.Error(node.lhs, new Object[]{
					"Tipo do operando direito de MINUS invalido",
					"Esperado: int",
					"Encontrado: " + rhsType
			});
		}
		
		return node.type = new IntegerType(node.line, node.row);
	}

	/////////////////////
	// Plus Expression //
	/////////////////////
	public Type visit(Plus node) {
		// Verifica tipo da expressao da esquerda
		Type lhsType = ExpHandler.secondPass(env, parentClass, parentMethod, node.lhs);
		if (!(lhsType instanceof IntegerType)) {
			env.err.Error(node.lhs, new Object[]{
					"Tipo do operando esquerdo de PLUS invalido",
					"Esperado: int",
					"Encontrado: " + lhsType
			});
		}

		// Verifica tipo da expressao da direita
		Type rhsType = ExpHandler.secondPass(env, parentClass, parentMethod, node.rhs);
		if (!(rhsType instanceof IntegerType)) {
			env.err.Error(node.lhs, new Object[]{
					"Tipo do operando direito de PLUS invalido",
					"Esperado: int",
					"Encontrado: " + rhsType
			});
		}
		
		return node.type = new IntegerType(node.line, node.row);
	}

	//////////////////////
	// Times Expression //
	//////////////////////
	public Type visit(Times node) {
		//TODO
		return null;
	}

	////////////////////////////////
	// integerLiteral  Expression //
	////////////////////////////////
	public Type visit(IntegerLiteral node)
	{
		return node.type = new IntegerType(node.line, node.row);
	}


	//////////////////////
	// This  Expression //
	//////////////////////
	public Type visit(This node)
	{
		return node.type = new IdentifierType(node.line, node.row, parentClass.name.toString());
	}
	
	//////////////////////
	// Call  Expression //
	//////////////////////
	//public Type visit(Call node)
	//{
	//	Type t = ExpHandler.secondPass(env, parentClass, parentMethod, node.object);
	//	Symbol m = Symbol.symbol(node.method.s);
//
//		if(!(t instanceof IdentifierType)){
///			env.err.Error(node, new Object[]{
//					"Chamada de metodo aplicada a tipo invalido",
//					"Esperado: id",
//					"Encontrado: " + t
//			});
//			return node.type = new IntegerType(node.line, node.row);
//		}
//
//		IdentifierType tt = (IdentifierType) t;
//		MethodInfo mi = getMethod(Symbol.symbol(tt.name), m);
//
//		if(mi==null){
//			env.err.Error(node, new Object[]{
//				"Metodo " + m + " nao definido para classe \'" + tt.name + "\'"
//			});
//			return node.type = new IntegerType(node.line, node.row);
//		}
//
//		//verificando os parametros
//		List<Type> actuals = ExpListHandler.secondPass(env, parentClass, parentMethod, node.actuals); 
//		List<VarInfo> formals;
//		int i;
//		for(formals = mi.formals, i=1; actuals != null && formals != null; actuals = actuals.tail, formals = formals.tail, i++){
//			if(!TypeHandler.compatible(env, formals.head.type, actuals.head))
//				env.err.Error(node, new Object[]{
//						"Tipo do argumento #" + i + " para o metodo " + tt.name + "." + mi.name + "nao eh compativel.",
//						"Esperado: " + formals.head.type,
//						"Encontrado: " + actuals.head
//						});
//		}
//
//		if(actuals != null || formals != null)
//			env.err.Error(node, new Object[]{
//				"Numero de parametros invalidos para o metodo " + tt.name + "." + mi.name + "."
//			});
//
//		return node.type;
//	}
//
//
//	public MethodInfo getMethod(Symbol objeto, Symbol metodo){
//		Symbol key = Symbol.symbol(node.name.s);
//		ClassInfo iclass = env.classes.get(objeto);
//		MethodInfo imetodo = iclass.methods.get(metodo);
//		return imetodo;
//	}

}
    //TODO
	//{not} [token]:not [value]:exp |
    //{array_length} [array]:exp [token]:tok_length |
    //{array_lookup} [array]:exp [token]:l_brack [index]:exp |
    //{true} [token]:tok_true |
    //{false} [token]:tok_false |
    //{new_array} [token]:tok_new [size]:exp |
    //{new_object} [name]:id;
