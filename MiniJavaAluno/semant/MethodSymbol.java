package semant;

import semant.second_pass.FormalHandler;
import symbol.Symbol;
import syntaxtree.Formal;
import syntaxtree.MethodDecl;
import util.List;

public class MethodSymbol {
	public static Symbol getSymbol(MethodDecl node) {
		String name = node.name.s;

		// para cada formal, concatena o TYPE no nome do método
		for (List<Formal> f = node.formals; f != null; f = f.tail) {
			Formal formal = f.head;
			name = name + "#" + formal.type.toString();
		}

		return Symbol.symbol(name);
	}
	
	public static String methodName(Symbol key){
		String name = key.toString();
		String[] parts;
		parts = name.split("#");
		return parts[0];
	}
}
