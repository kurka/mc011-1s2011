package semant.second_pass;

import semant.Env;
import symbol.ClassInfo;
import symbol.MethodInfo;
import syntaxtree.Formal;
import util.List;

public class FormalListHandler {

	public static void secondPass(Env e, ClassInfo c, MethodInfo m, List<Formal> formals) {
		// Chama o firstPass do FormalHandler para cada formal da lista.
		for (List<Formal> f = formals; f != null; f = f.tail) {
			FormalHandler.secondPass(e, c, m, f.head);
		}
	}

}
