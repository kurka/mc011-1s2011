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
import syntaxtree.IntegerType;
import syntaxtree.LessThan;
import syntaxtree.Minus;
import syntaxtree.Plus;
import syntaxtree.Times;
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
		return null;
	}

	//////////////////////
	// Equal Expression //
	//////////////////////
	public Type visit(Equal node) {
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
		return null;
	}

	/////////////////////
	// Plus Expression //
	/////////////////////
	public Type visit(Plus node) {
		return null;
	}

	//////////////////////
	// Times Expression //
	//////////////////////
	public Type visit(Times node) {
		return null;
	}

}
